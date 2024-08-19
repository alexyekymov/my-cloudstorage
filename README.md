# What is cloud-storage

----

***Cloud-storage*** is a pet project where I practiced some technologies


## Local development

First of all, you need to run all secondary services:

```shell
docker compose --env-file dev.env -f docker-compose-dev.yml up -d
```
> [!NOTE]
> As you can see all variables are taken from the **dev.env** file, so feel free to modify them as needed before executing the previous command.


Then you can start the application with active profile **dev**:

```shell
mvn spring-boot:run -DskipTests -Dspring-boot.run.profiles=dev
```

## Production deployment

> [!IMPORTANT]
> Using the **dev.env** file as a template, create your own **.env** file and fill it with your variables and secrets.

Now, you can start Docker Compose:

```shell
docker compose up -d
```
