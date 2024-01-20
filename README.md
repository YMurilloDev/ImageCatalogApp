# Flickr Timeline Viewer

Este repositorio contiene mi solución a la prueba técnica de desarrollo de una aplicación móvil Android para visualizar imágenes de Flickr en un timeline.

## Desarrollador

- Nombre: Yordy Murillo
- Correo Electrónico: Yordy.murillo.clbdf@gmail.com

## Requerimientos

La prueba técnica incluyó los siguientes requerimientos y características que fueron implementados:

- El usuario puede obtener nuevos elementos en el timeline de manera infinita usando la estrategia "pull from bottom".
- El usuario puede seleccionar un elemento del timeline para verlo en pantalla completa.
- Cada elemento en el timeline consta de una imagen y un título.
- Implementación de cache para visualizar el timeline cuando la aplicación está offline.
- Indicación visual de momentos de carga utilizando la estrategia preferida de loading.

## Implementación

- Se utilizaron patrones de arquitectura y de diseño (MVVM ,Inyección de dependencias, SRP, SOLID, Builder, Singleton ).
- El código está escrito en Kotlin en su totalidad.
- Mínima versión soportada: Android 11 (Api 30).
- Versión objetivo y de compilación: Android 14 (Api 34).
- Se buscó una solución simple pero robusta para que la app sea escalable.
- Se realizaron pruebas unitarias a los viewModel.

## Liberías

Se implementaron las siguientes librerías para la contrucción del proyecto:

- Retrofit
- Room
- KotlinCorutines
- OkHttp3
- Coil

## Información Adicional

- Se utilizó la API oficial de Flickr: [Flickr API](https://www.flickr.com/services/api/)
- Credenciales de la API de Flickr:
  - API Key: 40bd373bb6a19a078023b06af055d03c

## Instrucciones para Ejecutar

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Ejecuta la aplicación en un emulador o dispositivo Android.

## Estructura del Proyecto

- `app/`: Contiene el código fuente de la aplicación.
- `tests/`: Contiene las pruebas unitarias.


**¡Gracias por revisar mi prueba técnica! Si tienes alguna pregunta o sugerencia, no dudes en contactarme.**
