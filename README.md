TLS Service

Сервис для проверки сертификатов с использованием PostgreSQL и Liquibase.

Требования:

Docker

Docker Compose

Запуск

Склонировать репозиторий

Построить и запустить контейнеры:

docker-compose up --build

Доступ к приложению:

HTTP: http://localhost:8080

База данных PostgreSQL: localhost:5432

Пользователь: postgres

Пароль: postgres

База: soft

Схема: tls_service

Структура проекта

docker-compose.yml — описывает сервисы Docker.

init.sql — создаёт схему tls_service и даёт права пользователю.

db/changelog/ — миграции Liquibase для создания таблиц.

tls-impl/ — исходный код приложения и Dockerfile.
