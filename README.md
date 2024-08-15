# What is cloud-storage

----

***Cloud-storage*** is a pet project where I practiced some technologies


## Local development

First of all, you need to run all secondary services:

```shell
docker compose --env-file dev.env -f docker-compose-dev.yml up -d
```

As you can see all variables are given from the **dev.env** file, so be free to change it as you need before executing the previous command.

## Production deployment

Using the **dev.env** file as a template, create your **.env** file and fill it with your variables and secrets.
Now, you can start docker-compose:

```shell
docker compose up -d
```
