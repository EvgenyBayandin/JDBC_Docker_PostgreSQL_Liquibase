# Это приложение для демонстрации работы JDBC и миграции БД PostgreSQL

## Создание и запуск PostgreSQL в docker контейнере:
### 1. Для использования docker в среде windows понадобится Docker desktop. 
Скачайте и установите по [ссылке](https://docs.docker.com/desktop/install/windows-install/). 
### 2. Создание контейнера.
- запустите терминал от имени администратора. В коммандной строке введите команду: `docker pull postgres:latest` 
- по завершении вы увидите запись:
`Status: Downloaded newer image for postgres:latest`
`docker.io/library/postgres:latest`
- если образ ранее был скачан, введите команду: `docker login`
- для запуска образа введите команду: `docker run -d --name mypostgres -p 5432:5432 -e POSTGRES_PASSWORD=password postgres:latest`
используйте свои названия и пароль. Порт по умолчанию 5432, при необходимости укажите свой.
- в случае успеха вы увидите номер контейнера. Проверить запущенный контейнер можно либо в Docker desktop, либо командой: `docker ps`
- остановить контейнер можно в Docker desktop, либо командой: `docker stop <номер контейнера>` 
### 3. Для работы с примерами реализаций необходимо запустить docker образ postgreSQL
- класс StatementExample.java демонстрирует пример подключения к базе данных и работу с Statement и ResultSet;
- класс PreparedStatementExampl.java демонстрирует пример подключения к базе данных и работу с PreparedStatement и ResultSet;
- класс CallableStatementExampl.java демонстрирует пример подключения к базе данных и работу с Statement и хранимыми процедурами;
- класс TransactionExample.java демонстрирует пример подключения к базе данных и работу с Statement и транзакциями в БД;
- класс LiquibaseExample.java демонстрирует пример подключения к базе данных и миграции в БД с помощью файлов миграций.

### Стек: 
`Java 21`
`JDBC`
`Docker`
`PostgreSQL 15`
`Liquibase`