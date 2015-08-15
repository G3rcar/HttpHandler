# HTTP Handler

Hace uso de un AsyncTask para realizar las peticiones al servidor

## Uso

En el archivo `app/build.gradle` agregaremos

```gradle
repositories{
    maven { url "https://jitpack.io" }
}
```

Luego, en las dependiencias usaremos

```gradle
dependencies {
    compile 'com.github.G3rcar:HttpHandler:1.0.2'
}
```