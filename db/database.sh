#!/bin/bash
BACKUP_DB=false
DUMP_FILES="data.sql" # через пробел в порядке очереди выполнения перечисляем все дампы

set -a

# Присваимваем значения для сервера, имени пользователя, паролю и названию БД из конфига сервиса.
DB_HOST="localhost"
DB_PORT=5432
DB_USER="postgres"
DB_NAME="timestat"
export PGPASSWORD="postgres"

# проверяем наличие бд
DB=$(psql -h "${DB_HOST}" -U "${DB_USER}" -t -d postgres -c "SELECT datname FROM pg_database WHERE datname='${DB_NAME}'")
# если есть
if [ "$DB" != "" ]; then
        # закрываем все активные сессии бд
        psql -h "${DB_HOST}" -U "${DB_USER}" -c "SELECT pg_terminate_backend(pg_stat_activity.pid)
                                                 FROM pg_stat_activity
                                                 WHERE pg_stat_activity.datname = '${DB_NAME}'
                                                 AND pid <> pg_backend_pid();"
        # если необходим бэкап
        if $BACKUP_DB; then
                # переименовываем существующую базу с привязкой ко времени
                DATE=$(date +%s);
                psql -h "${DB_HOST}" -U "${DB_USER}" -c "ALTER DATABASE ${DB_NAME} RENAME TO ${DB_NAME}_$DATE;"
        else
                # иначе дропаем существующую базу
                psql -h "${DB_HOST}" -U "${DB_USER}" -c "DROP DATABASE ${DB_NAME};"
        fi
fi

# создаем БД
psql -h "${DB_HOST}" -U "${DB_USER}" -c "CREATE DATABASE ${DB_NAME};"

# и накатываем данные из дампов
for item in $DUMP_FILES; do
        psql -h "${DB_HOST}"  -U "${DB_USER}" -f ./db/$item "${DB_NAME}"
done
