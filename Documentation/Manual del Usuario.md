# Manual del usuario

En esta guía encontrarás una guía rápida de utilización de la aplicación móvil **imageTOtext**.

 1. [Instalación](#instalación)
 2.  [Autenticación](#autenticación)
 3. [Pasos para la traducción de texto](#pasos-para-la-traducci%C3%B3n-de-texto)
 4. [Guardar traducción en el historial](#guardar-traducci%C3%B3n-en-el-historial)
 
## Instalación

Primero descarga el archivo .apk más reciente de la sección de releases en el repositorio del proyecto.
Cuando hayas bajado la aplicación tendrás una notificación, y sólo tendrás que abrir el panel de notificaciones y **pulsar sobre el nombre de la apk descargada**. Si has metido la apk en una tarjeta microSD o carpeta concreta del móvil, tendrás que utilizar un navegador de archivos para acceder a esta dirección y luego pulsar sobre ella. Exactamente igual que en Windows.
En la mayoría de los casos Android tiene deshabilitada por ejemplo la opción de instalar aplicaciones de orígenes desconocidos, que son todas las que no se instalan a través de Google Play. Para ello ve a los _Ajustes_ de Android, y una vez en ellos *pulsa sobre la opción *Seguridad***.
Una vez dentro, **pulsa sobre el interruptor que tienes al lado de la opción  _Orígenes desconocidos_** para activarla.

## Autenticación

<img src="https://user-images.githubusercontent.com/54408047/177633236-a2896f15-5ae5-4652-a829-cfa239daf137.jpeg" width="200">

La aplicación cuenta con un sistema de autenticación, implementado con el único objetivo de tener una referencia de usuario para poder almacenar en la nube un historial de traducciones y que este pueda acceder a ellos fácilmente.

### Registrarse 
Para registrarse, ingresar un correo electrónico y una contraseña en los campos solicitados y luego presionar el botón **CREAR USUARIO**.

### Iniciar sesión
Una vez hayas creado un usuario en la aplicación, podrás iniciar sesión al ingresar tus credenciales en los campos indicados y luego presionando el botón **INICIAR SESIÓN**.

## Pasos para la traducción de texto

<img src="https://user-images.githubusercontent.com/54408047/177633921-25af9b4b-7073-4050-9e5d-449e78d91171.jpeg" width="200">

Se podría decir que la aplicación se encuentra en una fase ALPHA de desarrollo por lo que si no sigues los pasos en el orden detallado, el funcionamiento podría ser diferente al esperado.

 - PASO 1: Oprimir el botón **TAKE PIC** para entrar a la cámara del dispositivo y brindar los permisos necesarios en caso de que se despliegue el dialogo para conceder permisos de Android 
 - PASO 2: Tomar una foto del texto que buscas traducir una vez se haya desplegado la cámara de tu dispositivo 
 - PASO 3: Ahora que tienes la vista previa de la imagen capturada, presiona el chequesito si estás conforme con la imagen o presiona el botón borrar si deseas retomar la imagen
 - PASO 4: Debajo de la imagen procesada aparecerá el texto analizado, si te parece que concuerda con el texto de la imagen puedes proceder a seleccionar el lenguaje al que corresponde en el botón dropdown a la derecha de este campo
 - PASO 5: Una vez seleccionado el lenguaje del texto, debes seleccionar el botón dropdown inferior que se encuentra debajo del botón **TRANSLATE** uno de los cuatro idiomas disponibles al cual desees traducir el texto
 - PASO 6: Presionar el botón **TRANSLATE** para ver el resultado en el campo debajo

## Guardar traducción en el historial

Si has completado los pasos anteriores y quieres guardar el resultado actual de la traducción, puedes oprimir en el botón **SAVE TRANSLATION** para guardar el resultado en la nube bajo tu usuario.

El historial se vuelve visible al presionar el botón **REMEMBER**.

## Export a file

You can export the current file by clicking **Export to disk** in the menu. You can choose to export the file as plain Markdown, as HTML using a Handlebars template or as a PDF.
