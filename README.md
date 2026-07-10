# Luxus Suites Management

Sistema web de gestión hotelera desarrollado con **Java 17**, **Spring Boot**, **Thymeleaf**, **Spring Data JPA** y **H2 Database**.

El proyecto simula la administración de un hotel premium, con una **landing pública orientada a clientes** y un **panel administrativo interno** para gestionar suites, reservas, disponibilidad, ingresos estimados y huéspedes.

---

## Índice

1. [Descripción general](#descripción-general)
2. [Objetivo del proyecto](#objetivo-del-proyecto)
3. [Tecnologías utilizadas](#tecnologías-utilizadas)
4. [Arquitectura general](#arquitectura-general)
5. [Funcionalidades principales](#funcionalidades-principales)
6. [Sitio público](#sitio-público)
7. [Panel administrativo](#panel-administrativo)
8. [Gestión de reservas](#gestión-de-reservas)
9. [Gestión de suites](#gestión-de-suites)
10. [Resumen de huéspedes](#resumen-de-huéspedes)
11. [Validaciones de negocio](#validaciones-de-negocio)
12. [Base de datos](#base-de-datos)
13. [Estructura del proyecto](#estructura-del-proyecto)
14. [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
15. [Acceso a la aplicación](#acceso-a-la-aplicación)
16. [Acceso a H2 Console](#acceso-a-h2-console)
17. [Consultas útiles de base de datos](#consultas-útiles-de-base-de-datos)
18. [Modo claro y oscuro](#modo-claro-y-oscuro)
19. [Responsive design](#responsive-design)
20. [Decisiones técnicas](#decisiones-técnicas)
21. [Flujo básico de uso](#flujo-básico-de-uso)
22. [Estado actual](#estado-actual)
23. [Posibles mejoras futuras](#posibles-mejoras-futuras)
24. [Autor](#autor)

---

## Descripción general

**Luxus Suites Management** es una aplicación web orientada a la gestión básica de un hotel de lujo.

El sistema se divide en dos grandes áreas:

- **Sitio público:** landing institucional del hotel, donde el visitante puede conocer las suites, servicios, gastronomía, bienestar, preguntas frecuentes y enviar una solicitud de reserva.
- **Panel administrativo:** área interna donde el staff del hotel puede administrar reservas, suites, estados, ingresos estimados y huéspedes generados automáticamente desde las reservas existentes.

La aplicación fue desarrollada como un proyecto integral de backend, frontend renderizado del lado del servidor y persistencia de datos.

---

## Objetivo del proyecto

El objetivo principal del proyecto es construir una aplicación funcional, presentable y completa, que permita demostrar:

- Desarrollo web con Java y Spring Boot.
- Renderizado de vistas con Thymeleaf.
- Uso del patrón MVC.
- Persistencia con JPA/Hibernate.
- Base de datos embebida H2 en archivo local.
- Validaciones de negocio.
- Manejo de formularios.
- Gestión administrativa de reservas y suites.
- Diseño responsive.
- Separación entre sitio público y panel administrativo.
- Organización de código por capas.

---

## Tecnologías utilizadas

| Tecnología | Uso dentro del proyecto |
|---|---|
| Java 17 | Lenguaje principal del backend |
| Spring Boot | Framework principal de la aplicación |
| Spring MVC | Manejo de rutas, controladores y formularios |
| Spring Data JPA | Persistencia de entidades |
| Hibernate | Implementación ORM utilizada por JPA |
| Thymeleaf | Motor de plantillas HTML del lado del servidor |
| H2 Database | Base de datos local en archivo |
| HTML5 | Estructura de las vistas |
| CSS3 | Estilos, responsive y diseño visual |
| JavaScript | Carrusel, modal de observaciones y modo claro/oscuro |
| Maven Wrapper | Compilación y ejecución sin depender de Maven global |
| Git | Control de versiones |

---

## Arquitectura general

El proyecto utiliza una arquitectura simple basada en capas:

```text
Controller → Service → Repository → Database
```

### Controller

Recibe las peticiones HTTP, prepara los datos para las vistas y redirige según corresponda.

Archivo principal:

```text
HomeController.java
```

### Service

Contiene la lógica de negocio:

- Validaciones.
- Cálculo de noches.
- Cálculo de importes.
- Control de reservas superpuestas.
- Agrupación de huéspedes.
- Gestión de suites.

Archivos principales:

```text
ReservaService.java
SuiteService.java
```

### Repository

Capa de acceso a datos mediante Spring Data JPA.

Archivos principales:

```text
ReservaSolicitudRepository.java
SuiteRepository.java
```

### Model

Contiene las entidades y clases de apoyo del dominio.

Archivos principales:

```text
ReservaSolicitud.java
Suite.java
HuespedResumen.java
```

---

## Funcionalidades principales

### Sitio público

- Landing pública del hotel.
- Presentación institucional.
- Imagen principal del hotel.
- Sección de información rápida.
- Sección de suites.
- Carrusel de suites.
- Imagen editorial de alojamiento.
- Sección de gastronomía.
- Imagen editorial de desayuno.
- Sección de bienestar y spa.
- Imagen editorial de spa.
- Sección de servicios.
- Formulario público de solicitud de reserva.
- Preguntas frecuentes.
- Datos de contacto.
- Footer con acceso discreto al panel administrativo.
- Modo claro y oscuro.
- Diseño responsive para desktop, tablet y mobile.

### Panel administrativo

- Dashboard inicial.
- Métricas principales.
- Gestión de suites.
- Alta de nuevas suites.
- Edición de suites.
- Activación y pausa de suites.
- Gestión de reservas.
- Alta interna de reservas.
- Edición de reservas.
- Confirmación de reservas.
- Cancelación de reservas.
- Reapertura de reservas.
- Filtro por estado.
- Buscador de reservas.
- Modal para observaciones.
- Resumen automático de huéspedes.
- Panel responsive para mobile.

---

## Sitio público

El sitio público se encuentra en:

```text
http://localhost:8081/
```

Su finalidad es mostrar una experiencia visual similar a una web real de hotel premium.

### Secciones principales del sitio público

#### Hero principal

Presenta el hotel con una imagen institucional y textos orientados a conversión.

Incluye llamados a la acción:

- Ver suites.
- Solicitar reserva.

#### Información rápida

Bloque de tarjetas con datos importantes:

- Check-in / Check-out.
- Categoría del hotel.
- Ubicación.
- Suites.

#### Alojamiento

Sección dedicada a la presentación de las suites y la experiencia de descanso.

Incluye:

- Imagen editorial.
- Card sombreada sobre imagen.
- Carrusel de suites disponibles/configuradas.

#### Gastronomía

Presenta la propuesta gastronómica del hotel.

Incluye:

- Imagen de desayuno.
- Card sombreada.
- Beneficios destacados.

#### Bienestar

Presenta los servicios de relax, spa y confort.

Incluye:

- Imagen de spa.
- Card sombreada.
- Tarjetas de servicios.

#### Reservas

Formulario público para solicitar una reserva.

Campos:

- Nombre.
- Email.
- Teléfono.
- Suite.
- Check-in.
- Check-out.
- Observaciones.

#### Preguntas frecuentes

Bloque de consultas comunes antes de reservar.

#### Contacto

Datos del hotel:

- Contacto.
- Dirección.
- Email de reservas.
- Teléfono.

---

## Panel administrativo

El panel administrativo se encuentra en:

```text
http://localhost:8081/admin
```

El menú actual del panel incluye:

```text
Sitio público
Suites
Reservas
Huéspedes
```

El dashboard se muestra al ingresar al panel, por eso no se mantiene como pestaña independiente.

---

## Dashboard administrativo

El dashboard inicial muestra un resumen general del estado del hotel.

Métricas disponibles:

- Total de reservas.
- Reservas pendientes.
- Reservas confirmadas.
- Reservas canceladas.
- Ingresos confirmados.
- Ingresos estimados.
- Suites configuradas.
- Huéspedes agrupados.

También incluye accesos rápidos a:

- Nueva reserva.
- Ver reservas.
- Gestionar suites.

---

## Gestión de reservas

Las reservas se gestionan desde la sección:

```text
/admin#solicitudes
```

Cada reserva contiene:

| Campo | Descripción |
|---|---|
| ID interno | Número automático generado por la base |
| Nombre | Nombre del huésped |
| Email | Email de contacto |
| Teléfono | Teléfono de contacto |
| Suite | Suite seleccionada |
| Check-in | Fecha de ingreso |
| Check-out | Fecha de egreso |
| Noches | Cantidad calculada automáticamente |
| Precio por noche | Precio de la suite al momento de la reserva |
| Importe estimado | Precio por noche multiplicado por noches |
| Observaciones | Comentarios adicionales del huésped o staff |
| Estado | Pendiente, Confirmada o Cancelada |
| Fecha de creación | Fecha en que se registró la solicitud |
| Última actualización | Fecha de última modificación o cambio de estado |

### Estados de una reserva

| Estado | Significado |
|---|---|
| Pendiente | La reserva fue cargada y espera revisión |
| Confirmada | La reserva fue aprobada por el staff |
| Cancelada | La reserva fue rechazada o dada de baja |

### Acciones disponibles

Desde el panel se puede:

- Editar una reserva.
- Confirmar una reserva.
- Cancelar una reserva.
- Reabrir una reserva.
- Ver observaciones en un modal.
- Buscar por nombre, email, teléfono, suite, estado o número interno.
- Filtrar por estado.

---

## Gestión de suites

Las suites se gestionan desde:

```text
/admin#suites-admin
```

Cada suite contiene:

| Campo | Descripción |
|---|---|
| Nombre | Nombre comercial de la suite |
| Descripción | Descripción visible y administrativa |
| Categoría | Categoría o tipo de suite |
| Precio por noche | Valor usado para calcular importes |
| Capacidad | Cantidad de huéspedes |
| Disponibilidad | Activa o pausada |

### Acciones disponibles

Desde el panel se puede:

- Crear una suite.
- Editar una suite.
- Activar una suite.
- Pausar una suite.

Las suites pausadas no aparecen como opción disponible para nuevas reservas públicas o internas.

---

## Resumen de huéspedes

La sección de huéspedes se encuentra en:

```text
/admin#huespedes-admin
```

Esta sección no utiliza una tabla independiente de huéspedes.  
Los datos se generan automáticamente a partir de las reservas existentes.

El sistema agrupa huéspedes usando este criterio:

1. Email.
2. Si no hay email, teléfono.
3. Si no hay teléfono, nombre.
4. Si no hay datos, se genera una clave interna auxiliar.

Para cada huésped se muestra:

| Campo | Descripción |
|---|---|
| Nombre | Nombre del huésped |
| Email | Email registrado |
| Teléfono | Teléfono registrado |
| Total de reservas | Cantidad total de reservas asociadas |
| Confirmadas | Cantidad de reservas confirmadas |
| Última reserva | Fecha de check-in más reciente |
| Importe asociado | Suma de importes no cancelados |

Esta decisión permite tener una vista útil de huéspedes sin complejizar el modelo con una entidad adicional.

---

## Validaciones de negocio

El sistema incluye validaciones tanto en reservas como en suites.

### Validaciones de reservas

Se valida que:

- El nombre sea obligatorio.
- El email sea obligatorio.
- El email tenga formato válido.
- El teléfono sea obligatorio.
- El check-in sea obligatorio.
- El check-out sea obligatorio.
- El check-out sea posterior al check-in.
- La suite seleccionada exista.
- La suite seleccionada esté disponible.
- No exista otra reserva confirmada superpuesta para la misma suite.

### Validación de reservas superpuestas

Una reserva se considera superpuesta cuando:

```text
nuevoCheckin < checkoutExistente
nuevoCheckout > checkinExistente
```

Esto evita confirmar o registrar una reserva si ya existe una reserva confirmada para la misma suite en fechas coincidentes.

### Validaciones de suites

Se valida que:

- El nombre de la suite sea obligatorio.
- La categoría sea obligatoria.
- La descripción sea obligatoria.
- El precio por noche sea mayor a cero.
- La capacidad sea mayor a cero.

---

## Base de datos

El proyecto utiliza **H2 Database** en modo archivo local.

Configuración principal:

```properties
server.port=8081

spring.datasource.url=jdbc:h2:file:./data/luxusdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
```

La base de datos se guarda localmente en:

```text
data/
```

La carpeta `data/` debe permanecer fuera del repositorio, por eso se incluye en `.gitignore`.

---

## Entidades principales

### ReservaSolicitud

Representa una solicitud o reserva dentro del sistema.

Campos principales:

```text
id
nombre
email
telefono
checkin
checkout
suite
observaciones
precioPorNoche
noches
importeEstimado
estado
fechaCreacion
fechaUltimaActualizacion
```

### Suite

Representa una suite del hotel.

Campos principales:

```text
id
nombre
descripcion
categoria
precioPorNoche
capacidad
disponible
```

### HuespedResumen

No es una entidad persistida.  
Es una clase auxiliar usada para mostrar información agrupada de huéspedes.

Campos principales:

```text
nombre
email
telefono
totalReservas
reservasConfirmadas
importeAsociado
ultimaReserva
```

---

## Estructura del proyecto

```text
luxus-suites-management
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
│
├── data/                         # Base H2 local, no versionada
│
└── src
    └── main
        ├── java
        │   └── com
        │       └── luxus
        │           └── suites
        │               ├── LuxusSuitesManagementApplication.java
        │               │
        │               ├── controller
        │               │   └── HomeController.java
        │               │
        │               ├── model
        │               │   ├── ReservaSolicitud.java
        │               │   ├── Suite.java
        │               │   └── HuespedResumen.java
        │               │
        │               ├── repository
        │               │   ├── ReservaSolicitudRepository.java
        │               │   └── SuiteRepository.java
        │               │
        │               └── service
        │                   ├── ReservaService.java
        │                   └── SuiteService.java
        │
        └── resources
            ├── application.properties
            │
            ├── static
            │   ├── css
            │   │   └── styles.css
            │   │
            │   ├── img
            │   │   ├── luxus.png
            │   │   ├── suite.png
            │   │   ├── breakfast.png
            │   │   └── spa1.png
            │   │
            │   └── js
            │       └── theme.js
            │
            └── templates
                ├── index.html
                ├── admin.html
                ├── reserva-confirmacion.html
                ├── reserva-editar.html
                ├── reserva-nueva-admin.html
                ├── suite-editar.html
                └── suite-nueva.html
```

---

## Cómo ejecutar el proyecto

### Requisitos previos

Es necesario tener instalado o configurado:

- Java 17.
- Git.
- Navegador web.
- IntelliJ IDEA, VS Code u otro IDE compatible.

No es necesario tener Maven instalado globalmente porque el proyecto usa **Maven Wrapper**.

### 1. Clonar el repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

### 2. Ingresar a la carpeta del proyecto

```bash
cd luxus-suites-management
```

### 3. Verificar Java

```bash
java -version
```

Debe mostrarse una versión de Java 17.

### 4. Configurar JAVA_HOME en Windows si fuera necesario

Si aparece un error como:

```text
The JAVA_HOME environment variable is not defined correctly
```

configurar temporalmente en la terminal:

```cmd
set JAVA_HOME=C:\Users\Federico\.jdks\temurin-17.0.19
set PATH=%JAVA_HOME%\bin;%PATH%
```

Luego verificar:

```cmd
java -version
```

### 5. Compilar

En Windows:

```cmd
mvnw.cmd clean compile
```

En Linux/macOS:

```bash
./mvnw clean compile
```

Resultado esperado:

```text
BUILD SUCCESS
```

### 6. Ejecutar

En Windows:

```cmd
mvnw.cmd spring-boot:run
```

En Linux/macOS:

```bash
./mvnw spring-boot:run
```

Cuando la aplicación inicia correctamente se puede ver un mensaje similar a:

```text
Started LuxusSuitesManagementApplication
```

---

## Acceso a la aplicación

Sitio público:

```text
http://localhost:8081/
```

Panel administrativo:

```text
http://localhost:8081/admin
```

Consola H2:

```text
http://localhost:8081/h2-console
```

---

## Acceso a H2 Console

Datos de conexión:

```text
JDBC URL: jdbc:h2:file:./data/luxusdb
User Name: sa
Password: dejar vacío
```

Luego presionar **Connect**.

---

## Consultas útiles de base de datos

Ver reservas:

```sql
SELECT * FROM RESERVAS_SOLICITUDES;
```

Ver suites:

```sql
SELECT * FROM SUITES;
```

Ver reservas confirmadas:

```sql
SELECT * FROM RESERVAS_SOLICITUDES
WHERE ESTADO = 'Confirmada';
```

Ver reservas pendientes:

```sql
SELECT * FROM RESERVAS_SOLICITUDES
WHERE ESTADO = 'Pendiente';
```

Ver suites disponibles:

```sql
SELECT * FROM SUITES
WHERE DISPONIBLE = TRUE;
```

---

## Modo claro y oscuro

El proyecto incluye alternancia entre modo oscuro y modo claro.

El botón de cambio de tema está disponible en:

- Sitio público.
- Panel administrativo.

El manejo se realiza desde:

```text
src/main/resources/static/js/theme.js
```

El estado visual se aplica mediante clases CSS.

---

## Responsive design

El proyecto fue ajustado para distintos tamaños de pantalla:

| Tamaño | Estado |
|---|---|
| Desktop | Optimizado |
| Notebook | Optimizado |
| Tablet | Optimizado |
| Mobile 320px | Optimizado |

Se ajustaron especialmente:

- Landing pública.
- Carrusel de suites.
- Cards internas.
- Imágenes editoriales.
- Captions sobre imágenes.
- Formulario de reserva.
- Contacto.
- FAQ.
- Panel administrativo mobile.
- Botones de acciones.
- Cards de reservas.
- Cards de suites.
- Resumen de huéspedes.

---

## Decisiones técnicas

### Uso de H2

Se eligió H2 en archivo local para facilitar el desarrollo, las pruebas y la persistencia sin requerir instalación de un motor externo.

### Uso de Thymeleaf

Thymeleaf permite renderizar vistas HTML del lado del servidor y trabajar de forma directa con los modelos enviados desde Spring MVC.

### Uso de JPA

JPA permite persistir las entidades principales sin escribir SQL manual para las operaciones básicas.

### Huéspedes como resumen

La sección de huéspedes se resolvió mediante una clase auxiliar y agrupación desde reservas existentes.

Esta decisión evita crear una tabla adicional antes de que el dominio lo requiera realmente, pero permite ofrecer una vista administrativa útil.

### Validación de superposición

La validación de reservas superpuestas se realiza en la capa de servicio para mantener la lógica de negocio centralizada.

### CSS central

El proyecto utiliza un único archivo CSS central:

```text
src/main/resources/static/css/styles.css
```

El archivo está organizado por secciones comentadas para facilitar mantenimiento.

---

## Flujo básico de uso

### Flujo público

1. El visitante ingresa al sitio.
2. Revisa suites y servicios.
3. Completa el formulario de reserva.
4. El sistema valida los datos.
5. Si los datos son correctos, se registra una solicitud pendiente.
6. Se muestra una confirmación.

### Flujo administrativo

1. El staff ingresa al panel.
2. Revisa métricas del dashboard.
3. Gestiona suites disponibles.
4. Revisa solicitudes de reserva.
5. Confirma, cancela, reabre o edita reservas.
6. Consulta el resumen automático de huéspedes.

---

## Estado actual

El proyecto se encuentra en una versión funcional que incluye:

- Sitio público completo.
- Panel administrativo operativo.
- Persistencia en H2.
- Gestión de suites.
- Gestión de reservas.
- Gestión de estados.
- Validaciones principales.
- Control de superposición de reservas.
- Resumen de huéspedes.
- Modo claro y oscuro.
- Responsive para desktop, tablet y mobile.
- CSS central organizado y comentado.

---

## Posibles mejoras futuras

Algunas mejoras posibles para futuras versiones:

- Login para proteger el panel administrativo.
- Entidad propia de huésped.
- Historial detallado de cambios de estado.
- Confirmación por email.
- Exportación de reservas a PDF o Excel.
- Calendario visual de ocupación.
- Gestión de pagos.
- Roles de usuario.
- Filtros avanzados por fecha.
- Reportes mensuales.
- Dashboard con gráficos.
- Integración con una base de datos externa como PostgreSQL o MySQL.
- Tests automatizados de servicios y controladores.
- Deploy en la nube.

---

## Notas de desarrollo

Durante el desarrollo se trabajó especialmente en:

- Separar la experiencia pública de la experiencia administrativa.
- Evitar mostrar textos técnicos en la landing.
- Mantener una apariencia de hotel real.
- Mejorar el responsive mobile.
- Mantener un único CSS central, pero ordenado y comentado.
- Guardar cambios en commits progresivos.

---

## Autor

Proyecto desarrollado por:

**Leandro Federico Sosa**

---

## Licencia

Proyecto desarrollado con fin profesional.
