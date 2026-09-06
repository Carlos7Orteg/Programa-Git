# EV01 — Codificación del módulo CRUD con Java, JDBC y MySQL

## 1. Descripción

La EV01 corresponde a la codificación del módulo del proyecto **CulturaTech Bogotá**, teniendo en cuenta las características del software a desarrollar y realizando la conexión con una base de datos mediante **JDBC (Java Database Connectivity)**, de acuerdo con lo visto en el componente formativo.

El desarrollo de esta evidencia permitió construir un módulo funcional para la gestión de eventos culturales mediante las operaciones fundamentales de un CRUD:

- **Create:** inserción de registros.
- **Read:** consulta de registros.
- **Update:** actualización de registros.
- **Delete:** eliminación de registros.

El módulo fue desarrollado en Java y conectado a la base de datos MySQL mediante JDBC. La implementación se organizó utilizando clases y paquetes con responsabilidades diferenciadas y posteriormente se realizaron pruebas para comprobar el funcionamiento de cada operación.

---

## 2. Referencia a los artefactos del ciclo del software

La codificación del módulo se realizó tomando como referencia los artefactos del ciclo de desarrollo del software elaborados previamente para el proyecto, entre ellos:

- Diagrama de clases.
- Diagramas de casos de uso.
- Historias de usuario.
- Diseños de las interfaces.
- Prototipos.
- Informe técnico del plan de trabajo para la construcción del software.
- Tecnologías seleccionadas para el desarrollo.

Estos artefactos sirven como referencia para mantener la correspondencia entre las necesidades identificadas, el diseño planteado y la implementación del módulo.

La etapa de codificación de la EV01 representa la materialización técnica de las definiciones realizadas durante las etapas anteriores del proyecto.

---

## 3. Tecnologías y herramientas utilizadas

Para el desarrollo, conexión y pruebas del módulo se utilizaron:

- **Java / JDK 21.0.12 LTS**
- **Apache NetBeans 31**
- **MySQL**
- **JDBC**
- **MySQL Connector/J 26.7.0**
- **Git** para el control de versiones

La aplicación utiliza JDBC como mecanismo de comunicación entre el código Java y la base de datos MySQL.

---

## 4. Estructura del proyecto

El proyecto de la EV01 fue organizado mediante paquetes de acuerdo con la responsabilidad de cada componente:

```text
CulturaTechBgCRUD
├── build.xml
├── manifest.mf
├── nbproject
└── src
    ├── culturatechbgcrud
    │   └── CulturaTechBgCRUD.java
    ├── dao
    │   └── EventoDAO.java
    ├── model
    │   └── Evento.java
    └── util
        └── ConexionBD.java
```

### Paquete `model`

Contiene las clases que representan la información manejada por el sistema.

#### `Evento.java`

Representa un evento cultural y contiene sus atributos, constructores, métodos de acceso y métodos de modificación.

Entre los datos manejados se encuentran:

- Identificador del evento.
- Título.
- Descripción.
- Fecha y hora.
- Costo.
- Imagen.
- Estado.
- Identificador de categoría.
- Identificador de lugar.
- Nombre de la categoría.
- Nombre del lugar.
- Localidad.
- Dirección.

La clase `Evento` permite transportar la información entre la aplicación y la capa de acceso a datos.

### Paquete `dao`

Contiene la lógica relacionada con el acceso a la base de datos.

#### `EventoDAO.java`

Es la clase responsable de ejecutar las operaciones CRUD sobre la tabla `evento`.

Los métodos principales implementados son:

```text
insertar()
consultarTodas()
actualizar()
eliminar()
```

La separación del acceso a datos en una clase DAO permite mantener organizada la lógica de persistencia y evitar que las operaciones SQL se encuentren directamente mezcladas con la clase principal.

### Paquete `util`

Contiene las clases de utilidad del proyecto.

#### `ConexionBD.java`

Centraliza la configuración y creación de la conexión con MySQL mediante JDBC.

La conexión utiliza el esquema:

```text
jdbc:mysql://localhost:3306/culturatechbg
```

También se utiliza el controlador:

```text
com.mysql.cj.jdbc.Driver
```

La contraseña de la base de datos no se incluye en este documento ni debe almacenarse en el repositorio.

### Paquete `culturatechbgcrud`

Contiene la clase principal de ejecución.

#### `CulturaTechBgCRUD.java`

Permite ejecutar la aplicación Java y realizar la comprobación de la consulta del módulo.

---

# 5. Implementación del CRUD

## 5.1 Create — Inserción

La operación de inserción fue implementada mediante el método:

```text
insertar()
```

Su objetivo es registrar un nuevo evento en la tabla `evento`.

La operación recibe un objeto `Evento` y utiliza una sentencia SQL parametrizada mediante `PreparedStatement`.

Los datos que pueden ser registrados incluyen:

```text
titulo
descripcion
fecha_hora
costo
imagen
estado
id_categoria
id_lugar
```

El uso de `PreparedStatement` permite separar la sentencia SQL de los valores enviados y evita construir consultas mediante concatenación directa de datos.

### Proceso de inserción

El proceso realizado fue:

1. Crear y preparar el objeto `Evento`.
2. Establecer los datos correspondientes al evento.
3. Abrir una conexión mediante `ConexionBD`.
4. Ejecutar el método `insertar()` de `EventoDAO`.
5. Ejecutar la sentencia `INSERT` mediante JDBC.
6. Confirmar que MySQL generó el registro correctamente.
7. Consultar posteriormente el registro para comprobar la inserción.

La inserción fue probada utilizando identificadores válidos de categoría y lugar para respetar las relaciones establecidas en la base de datos.

---

## 5.2 Read — Consulta

La operación de consulta fue implementada mediante:

```text
consultarTodas()
```

Su función es recuperar los eventos almacenados en la base de datos y convertir cada resultado en un objeto `Evento`.

La consulta utiliza `SELECT` y obtiene información de la tabla `evento`.

Además, se utilizaron relaciones entre las tablas para recuperar información descriptiva de la categoría y del lugar asociado al evento.

La consulta utiliza:

```text
INNER JOIN categoria
INNER JOIN lugar
```

De esta manera, el resultado puede incluir:

- Datos del evento.
- Nombre de la categoría.
- Nombre del lugar.
- Dirección.
- Localidad.

### Proceso de consulta

1. Se establece la conexión con MySQL.
2. Se prepara la sentencia `SELECT`.
3. Se ejecuta la consulta mediante JDBC.
4. Se recorre el `ResultSet`.
5. Cada registro se transforma en un objeto `Evento`.
6. Los objetos obtenidos se devuelven al programa.
7. Se muestran o utilizan los resultados para comprobar la información almacenada.

La consulta fue comprobada durante las pruebas del módulo y permitió verificar los registros existentes en la base de datos.

---

## 5.3 Update — Actualización

La actualización fue implementada mediante:

```text
actualizar()
```

Su objetivo es modificar la información de un evento existente.

El registro se identifica mediante:

```text
id_evento
```

La operación utiliza una sentencia SQL `UPDATE` parametrizada mediante `PreparedStatement`.

Los datos del evento pueden ser modificados de acuerdo con los campos contemplados por el modelo.

### Proceso de actualización

1. Identificar el evento mediante su `id_evento`.
2. Crear o modificar el objeto `Evento`.
3. Establecer los nuevos valores.
4. Abrir la conexión con la base de datos.
5. Ejecutar el método `actualizar()`.
6. Ejecutar la sentencia `UPDATE`.
7. Consultar nuevamente el registro.
8. Verificar que los nuevos valores hayan sido almacenados.

### Prueba realizada

Durante la prueba funcional se utilizó un registro de evento de prueba.

Se verificó la modificación de datos como:

- Título.
- Descripción.
- Fecha y hora.
- Costo.

Después de ejecutar la actualización, se realizó una consulta en MySQL para confirmar que los cambios se hubieran aplicado correctamente.

---

## 5.4 Delete — Eliminación

La eliminación fue implementada mediante:

```text
eliminar()
```

Su función es eliminar un evento existente de la tabla `evento`.

El registro se identifica mediante su:

```text
id_evento
```

La operación utiliza una sentencia SQL `DELETE` parametrizada.

### Proceso de eliminación

1. Identificar el registro que será eliminado.
2. Obtener su `id_evento`.
3. Abrir la conexión con MySQL.
4. Ejecutar el método `eliminar()`.
5. Ejecutar la sentencia `DELETE`.
6. Realizar nuevamente una consulta.
7. Comprobar que el registro ya no se encuentre en la tabla.

### Prueba realizada

El registro utilizado para las pruebas de inserción y actualización fue posteriormente eliminado.

Después de ejecutar la eliminación se verificó en MySQL que el registro de prueba ya no estuviera disponible.

---

# 6. Flujo general del CRUD

El funcionamiento del módulo puede representarse de la siguiente manera:

```text
Aplicación Java
      │
      ▼
ConexionBD
      │
      ▼
    JDBC
      │
      ▼
   EventoDAO
      │
      ├── insertar()
      ├── consultarTodas()
      ├── actualizar()
      └── eliminar()
      │
      ▼
   Base de datos
    culturatechbg
      │
      ▼
     evento
```

La clase `Evento` representa los datos mientras que `EventoDAO` concentra las operaciones sobre la base de datos.

---

# 7. Relaciones utilizadas en la base de datos

El módulo trabaja principalmente con la entidad `evento` y sus relaciones con:

```text
evento → categoria
evento → lugar
```

Los identificadores:

```text
id_categoria
id_lugar
```

permiten relacionar cada evento con una categoría y un lugar existentes.

Durante las pruebas de inserción se utilizaron identificadores válidos para cumplir las restricciones de integridad referencial establecidas en la base de datos.

---

# 8. Uso de PreparedStatement

Las operaciones de acceso a datos utilizan `PreparedStatement`.

Esto permite:

- Parametrizar los valores enviados a las consultas.
- Evitar concatenar directamente los datos dentro de las sentencias SQL.
- Mantener las operaciones SQL organizadas.
- Mejorar la seguridad y el manejo de los parámetros.

El patrón utilizado en las operaciones CRUD consiste en preparar la sentencia y posteriormente establecer los valores correspondientes mediante parámetros.

---

# 9. Estándares de codificación

La implementación tuvo en cuenta los estándares de nombramiento solicitados para la evidencia.

## 9.1 Clases — PascalCase

Las clases utilizan **PascalCase**:

```text
Evento
EventoDAO
ConexionBD
CulturaTechBgCRUD
```

Cada palabra comienza con mayúscula.

## 9.2 Métodos — camelCase

Los métodos utilizan **camelCase**:

```text
insertar()
consultarTodas()
actualizar()
eliminar()
conectar()
```

La primera palabra comienza en minúscula y las palabras posteriores comienzan con mayúscula.

## 9.3 Variables — camelCase

Las variables utilizan nombres descriptivos en camelCase:

```text
evento
conexion
resultado
sentencia
idEvento
fechaHora
```

## 9.4 Paquetes — minúsculas

Los paquetes fueron nombrados utilizando minúsculas:

```text
dao
model
util
culturatechbgcrud
```

## 9.5 Uso de mayúsculas y minúsculas

Se aplicaron las convenciones de Java solicitadas:

| Elemento | Convención | Ejemplo |
|---|---|---|
| Clases | PascalCase | `EventoDAO` |
| Métodos | camelCase | `consultarTodas()` |
| Variables | camelCase | `fechaHora` |
| Paquetes | minúsculas | `dao` |
| Constantes | mayúsculas | `URL` |

La aplicación de estas convenciones facilita la lectura, mantenimiento y organización del código.

---

# 10. Versionamiento mediante Git

El proyecto fue gestionado mediante Git para conservar un historial de los cambios realizados durante la codificación.

El historial correspondiente al desarrollo de la EV01 contiene:

```text
0886fd0  Añadido datos.
d97f5bd  Codificación módulo CRUD con JDBC
4b97d42  Ajuste ejecución principal del CRUD
```

El commit:

```text
4b97d42 Ajuste ejecución principal del CRUD
```

corresponde al estado final alcanzado en la implementación de la EV01.

El uso de Git permite conservar la trazabilidad del proceso de desarrollo y facilita la revisión de los cambios realizados.

---

# 11. Pruebas funcionales del CRUD

Para comprobar el funcionamiento del módulo se realizaron pruebas independientes para cada operación.

## Inserción

Se creó un registro de prueba utilizando valores válidos de categoría y lugar.

**Resultado:** el registro fue insertado correctamente y MySQL generó su identificador.

## Consulta

Se realizó una consulta para recuperar el registro insertado.

**Resultado:** los datos almacenados fueron recuperados correctamente.

## Actualización

Se modificó el registro de prueba, cambiando información como título, descripción, fecha, hora y costo.

**Resultado:** los cambios fueron almacenados correctamente y posteriormente verificados mediante consulta en MySQL.

## Eliminación

Finalmente se eliminó el registro utilizado durante las pruebas.

**Resultado:** el registro fue eliminado correctamente y se verificó que ya no apareciera en la base de datos.

---

# 12. Resultado obtenido

Al finalizar la EV01 se obtuvo un módulo CRUD funcional desarrollado en Java y conectado a MySQL mediante JDBC.

El módulo permite:

- Establecer conexión con la base de datos.
- Insertar eventos.
- Consultar eventos.
- Actualizar eventos.
- Eliminar eventos.
- Consultar información relacionada de categorías y lugares.
- Utilizar `PreparedStatement`.
- Mantener una separación de responsabilidades mediante `model`, `dao` y `util`.
- Aplicar convenciones de nomenclatura.
- Mantener el desarrollo bajo control de versiones mediante Git.

Las cuatro operaciones CRUD fueron ejecutadas y verificadas durante las pruebas funcionales.

---

# 13. Cumplimiento de los puntos solicitados

| Punto solicitado | Cumplimiento |
|---|---|
| Codificación del módulo del proyecto | Módulo CRUD de eventos desarrollado en Java |
| Conexión con base de datos mediante JDBC | Implementada mediante `ConexionBD` |
| Referencia a artefactos anteriores | Diagrama de clases, casos de uso, historias de usuario, diseños, prototipos e informe técnico considerados como referencia del desarrollo |
| Uso de herramienta de versionamiento | Git |
| Nombramiento de variables | camelCase |
| Nombramiento de métodos | camelCase |
| Nombramiento de clases | PascalCase |
| Nombramiento de paquetes | Minúsculas |
| Inserción | Implementada y probada |
| Consulta | Implementada y probada |
| Actualización | Implementada y probada |
| Eliminación | Implementada y probada |

---

# 14. Conclusión

La EV01 permitió llevar a la etapa de codificación el módulo definido para el proyecto CulturaTech Bogotá, utilizando Java, JDBC y MySQL.

La implementación se estructuró mediante un modelo de datos, una clase DAO para las operaciones de persistencia y una clase de conexión para administrar la comunicación con MySQL.

Las operaciones de **inserción, consulta, actualización y eliminación** fueron implementadas mediante JDBC y comprobadas funcionalmente. También se aplicaron las convenciones de nomenclatura solicitadas, utilizando **PascalCase para clases, camelCase para métodos y variables, y minúsculas para paquetes**.

Finalmente, el proyecto fue gestionado mediante Git, conservando el historial de cambios correspondiente al desarrollo de la EV01.

Este módulo constituye la base técnica sobre la cual continúa la evolución del proyecto en la EV02 hacia el entorno web.
