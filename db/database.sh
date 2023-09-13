#!/bin/bash

# environment variables:
#     BACKUP_DB - is necessary create backup
#     DUMP_FILES - name of dump files separated by space in order of priority
#     DB_PORT - database port
#     DB_USER - database user
#     DB_NAME - database name
#     DB_PASS - database password

set -a
export PGPASSWORD="${DB_PASS}"

# check the database exist
DB=$(psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -t -d postgres -c "SELECT datname FROM pg_database WHERE datname='${DB_NAME}'")
# if exist
if [ "$DB" != "" ]; then
        # close all active sessions
        psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -c "SELECT pg_terminate_backend(pg_stat_activity.pid)
                                                                 FROM pg_stat_activity
                                                                 WHERE pg_stat_activity.datname = '${DB_NAME}'
                                                                 AND pid <> pg_backend_pid();"
        # if necessary backup
        if $BACKUP_DB; then
                # rename existing database with time link
                DATE=$(date +%s);
                psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -c "ALTER DATABASE ${DB_NAME} RENAME TO ${DB_NAME}_$DATE;"
        else
                # else just drop database
                psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -c "DROP DATABASE ${DB_NAME};"
        fi
fi

# create new database
psql -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -c "CREATE DATABASE ${DB_NAME};"

# load data from dump files
for item in $DUMP_FILES; do
        psql -h "${DB_HOST}" -p "${DB_PORT}"  -U "${DB_USER}" -f ./db/$item "${DB_NAME}"
done
