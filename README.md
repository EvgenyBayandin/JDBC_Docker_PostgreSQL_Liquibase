# JDBC и миграция БД PostgreSQL: Демонстрационное приложение

Это приложение демонстрирует работу с JDBC и миграцию базы данных PostgreSQL, включая использование различных типов SQL-запросов, транзакций и Liquibase для управления схемой БД.

## Предварительные требования

- Java 21
- Maven
- Docker
- Docker Desktop (для Windows)

## Настройка PostgreSQL в Docker

### 1. Установка Docker Desktop (для Windows)
Скачайте и установите [Docker Desktop](https://docs.docker.com/desktop/install/windows-install/).

### 2. Создание и запуск контейнера PostgreSQL
1. Откройте терминал от имени администратора.
2. Выполните следующие команды:<br>
   docker pull postgres:latest<br>
   docker run -d --name mypostgres -p 5432:5432 -e POSTGRES_PASSWORD=password postgres:latest<br>
   Замените `mypostgres` и `password` на свои значения при необходимости.
3. Проверьте статус контейнера: `docker ps`
4. Для остановки контейнера: `docker stop <ID контейнера>`

### 3. Настройка подключения к БД
Создайте файл `application.properties` в `src/main/resources` со следующим содержимым:<br>
jdbc.url=jdbc:postgresql://localhost:5432/postgres<br>
jdbc.user=postgres<br>
jdbc.password=password<br>

## Структура проекта

Проект содержит следующие основные классы:

1. `StatementExample`: Демонстрирует базовые операции с БД используя простые SQL-запросы.
2. `PreparedStatementExample`: Показывает использование подготовленных запросов для более эффективной и безопасной работы с БД.
3. `CallableStatementExample`: Иллюстрирует работу с хранимыми процедурами в БД.
4. `TransactionExample`: Демонстрирует управление транзакциями в JDBC.
5. `LiquibaseExample`: Показывает, как использовать Liquibase для управления схемой БД.
6. `DatabaseManager`: Утилитный класс для централизованного управления подключением к БД.

## Запуск примеров

1. Убедитесь, что контейнер PostgreSQL запущен.
2. Запустите нужный класс с примером (например, `StatementExample`) в вашей IDE или через командную строку.

## Использованные технологии

- Java 21
- JDBC
- PostgreSQL
- Docker
- Liquibase
- Maven

## Дополнительная информация

Каждый класс-пример содержит подробные комментарии, объясняющие его функциональность и особенности использования. Для получения более детальной информации о каждом аспекте работы с JDBC и Liquibase, обратитесь к соответствующему классу в исходном коде.