# HurtadoPJRegistro - Aplicación Web de Registro de Usuarios

## Descripción
Aplicación web desarrollada con patrón MVC, DAO y Factory que permite el acceso y registro de usuarios. Utiliza JSTL para las vistas y MySQL como base de datos.

## Características
- **Login de usuarios** con opción de recordar usuario (cookie)
- **Registro de nuevos usuarios** con validación de username único
- **Dos tipos de usuarios**: Administrador y Normal
- **Auditoría de sesiones** para usuarios normales (registro de entrada/salida)
- **Gestión de usuarios** (solo admin): listar y eliminar usuarios
- **Modificación de perfil** para usuarios normales
- **Encriptación de contraseñas** con MD5
- **Capitalización automática** de nombres y apellidos

## Estructura de la Base de Datos

### Tabla `usuarios`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| idusuario | INT | Clave primaria autoincremental |
| nombre | VARCHAR(50) | Nombre del usuario (capitalizado) |
| apellidos | VARCHAR(100) | Apellidos del usuario (capitalizado) |
| genero | CHAR(1) | M=Mujer, H=Hombre, O=Otro |
| username | VARCHAR(50) | Nombre de usuario (UNIQUE) |
| password | VARCHAR(32) | Contraseña en MD5 |
| ultimoAcceso | DATETIME | Fecha/hora del último acceso |
| rol | VARCHAR(10) | 'admin' o 'normal' |

### Tabla `auditoria`
| Campo | Tipo | Descripción |
|-------|------|-------------|
| idauditoria | INT | Clave primaria autoincremental |
| idusuario | INT | FK a usuarios |
| fechaEntrada | DATETIME | Fecha/hora de entrada |
| fechaSalida | DATETIME | Fecha/hora de salida (puede ser NULL) |

## Usuarios de Base de Datos
1. **adminregistro** (@localhost): Permisos completos sobre `registro`. Contraseña: `Java*2025`
2. **java2026** (@localhost): SELECT, INSERT, UPDATE sobre tabla `usuarios`. Contraseña: `Java*2026`

## Instalación

1. Ejecutar el script SQL en MySQL:
   ```sql
   source sql/create_database.sql
   ```

2. Configurar Tomcat con los DataSources definidos en `META-INF/context.xml`

3. Desplegar la aplicación en Tomcat

4. Acceder a: `http://localhost:8084/HurtadoPJRegistro/`

## Usuario Administrador
- **Username**: admin
- **Password**: admin123

## Flujo de la Aplicación

### Usuario Normal
1. Login → Menú Normal → Modificar datos / Salir
2. Al hacer login se registra entrada en auditoría
3. Al cerrar sesión se registra salida en auditoría

### Usuario Admin
1. Login → Menú Admin → Auditoría / Usuarios / Salir
2. En Auditoría: filtrar por usuario y fecha
3. En Usuarios: ver listado y eliminar usuarios

## Tecnologías
- Java EE 7
- Servlets 3.1
- JSP con JSTL
- MySQL 8.0
- Apache Tomcat
- Maven
- Apache Commons BeanUtils

## Autor
Juan Fco Hurtado Perez - DWES 2026
