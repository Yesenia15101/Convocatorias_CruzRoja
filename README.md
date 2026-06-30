#Cruz Roja Peruana Filial Arequipa

Sistema de gestión de voluntarios, convocatorias e inscripciones desarrollado para la Cruz Roja Peruana Filial Arequipa, utilizando principios de Domain Driven Design (DDD) y arquitectura por capas.

---

📋 Descripción

El sistema tiene como objetivo gestionar el proceso de incorporación y participación de voluntarios dentro de la institución, permitiendo administrar:

- Registro y gestión de voluntarios.
- Gestión de postulaciones.
- Creación y administración de convocatorias.
- Inscripción de voluntarios a convocatorias.
- Seguimiento del estado de postulaciones e inscripciones.

La arquitectura fue diseñada siguiendo los principios de Domain Driven Design (DDD), separando el dominio del negocio de los detalles de infraestructura para facilitar el mantenimiento y la escalabilidad del sistema.

---

🏗️ Arquitectura

El proyecto se encuentra organizado mediante Bounded Contexts (BC) y una arquitectura por capas.

Bounded Contexts

Contexto| Responsabilidad

BC1 - Usuarios| Gestión de voluntarios y postulaciones

BC2 - Convocatorias| Administración de convocatorias y requisitos

BC3 - Inscripciones| Gestión de inscripciones a convocatorias

Capas

- Presentación: Controladores y puntos de acceso al sistema.
- Aplicación: Casos de uso y orquestación de procesos.
- Dominio: Reglas de negocio, entidades y agregados.
- Infraestructura: Persistencia y acceso a datos.

---

🧩 Patrones DDD utilizados

El sistema implementa diversos patrones de Domain Driven Design:

- Entities
- Value Objects
- Aggregate Roots
- Factories
- Repositories
- Application Services
- Bounded Contexts

Ejemplos implementados

- Postulación → Aggregate Root.
- Convocatoria → Aggregate Root.
- Inscripción → Aggregate Root.
- Perfil, Horario y Ubicación → Value Objects.
- PostulacionFabrica, ConvocatoriaFabrica e InscripcionFabrica → Factories.

---

🔄 Flujo general del sistema

Cliente

    ↓
Controller

    ↓
Application Service

    ↓
Domain

    ↓
Repository

    ↓
Infrastructure

    ↓
Base de Datos

Ejemplo de proceso

1. El voluntario realiza una inscripción.
2. El controlador recibe la solicitud.
3. El servicio de aplicación coordina el caso de uso.
4. El dominio valida las reglas de negocio.
5. El repositorio persiste la información.

---

🛠️ Tecnologías utilizadas

- Java
- Domain Driven Design (DDD)
- Arquitectura por capas

---

🚀 Instalación

Clonar el repositorio

git clone https://github.com/usuario/cruz-roja-peruana.git
cd cruz-roja-peruana

Compilar el proyecto

mvn clean install

Ejecutar la aplicación

mvn spring-boot:run

---

🎯 Objetivos del proyecto

- Aplicar principios de Domain Driven Design.
- Implementar separación de responsabilidades.
- Diseñar un sistema mantenible y escalable.
- Modelar correctamente los procesos de negocio de la Cruz Roja Peruana.

---

👥 Autores

Proyecto académico desarrollado para la gestión de la Cruz Roja Peruana Filial Arequipa.

---

📄 Licencia

Este proyecto fue desarrollado con fines académicos y educativos.