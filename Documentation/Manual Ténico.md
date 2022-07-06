# Manual técnico

 1. [Definición del proyecto](#definición-del-proyecto)
 2.  [Datos técnicos](#datos-técnicos)
 3. [Instrucciones para utilizar el código](#instrucciones-para-utilizar-el-código)
 4. [Herramientas utilizadas](#herramientas-utilizadas)
 
## Definición del proyecto

### Tipos de usuario
La aplicación fue concebida para cualquier tipo de usuario final en una plataforma móvil

### Uso previsto
El uso previsto de la aplicación es traducir de manera fácil el texto escrito encontrado por el usuario en su día a día. Se propone su utilización sobre todo para los casos en que el usuario se encuentre en el extranjero y necesite traducir algo cuyos caracteres difieran de los de su lenguaje propio, caso en el que los traductores tradicionales resultan más difíciles de utilizar.

## Datos técnicos

### Hardware requerido
La aplicación está construida para ser compatible con cualquier dispositivo Android 6.0 o superior.
El uso de todas las funcionalidades de la aplicación requiere del uso de cámara integrada y de una conexión a internet.

### Permisos
La aplicación require solamente de permisos por parte del usuario para utilizar la cámara integrada del dispositivo móvil.

### Especificaciones
El código fuente es un archivo de tipo proyecto Android Studio. 
El lenguaje de codificación de las páginas es java.

## Instrucciones para utilizar el código

Solo debes clonar este repositorio y abrir la carpeta *imageTOtext* utilizando Android Studio. El IDE se encargará de descargar todas las dependencias.
La utilización del sistema de autenticación require de una conexión a una cuenta de firebase.

## Herramientas utilizadas

Se utilizaron librerías pertenecientes al kit bajo licencia Apache de Google: **ml-kit**

 - ml-vision para reconocer el texto captado en la imagen
 - mlkit:language para identificar el lenguaje y traducirlo
