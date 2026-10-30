# Prueba técnica Java

Aplicación sencilla para crear y consultar incidencias.

## Para arrancarla

Necesitas Java 17 y MySQL encendido. En `src/main/resources/application-local.properties` pon tu usuario y contraseña de MySQL:

```properties
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

Desde la carpeta del proyecto ejecuta:

```powershell
.\mvnw.cmd spring-boot:run
```

La base de datos y las tablas se crean al arrancar. La página está en `http://localhost:8080/`.

## API

- `POST /api/incidents/create`: crea una incidencia en estado `OPEN`.
- `GET /api/incidents/findAll`: lista las incidencias. Se puede filtrar con `?status=OPEN`.
- `PATCH /api/incidents/{id}/status`: cambia el estado. No permite reabrir una incidencia cerrada.

Los estados son `OPEN`, `IN_PROGRESS` y `CLOSED`. Las prioridades son `LOW`, `MEDIUM` y `HIGH`.

## Tests

```powershell
.\mvnw.cmd test
```

Hay dos tests del servicio: uno para el estado inicial y otro para la regla de las incidencias cerradas. El test de arranque necesita MySQL y las credenciales locales.

## Decisiones y pendientes

He usado Spring Data JPA y MySQL porque ya lo tenía instalado. Con más tiempo, validaría que el usuario asignado exista y simplificaría la página: ahora usa Bootstrap y jQuery, aunque el enunciado pide JavaScript sin frameworks.

## IA

He usado un asistente de IA para resolver dudas y revisar algunos cambios. He probado la aplicación y los tests para entender lo que hace el código.
