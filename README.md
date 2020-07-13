# HTTP Handler

Use an AsyncTask to execute requests to the server, avoid all the boilerplate and use only some methods

## How to use

In the file `app/build.gradle` add:

```gradle
repositories{
    maven { url "https://jitpack.io" }
}
```

Then, in the dependencies add:
```gradle
dependencies {
    implementation 'com.github.G3rcar:HttpHandler:2.0.0'
}
```

## Example code
[sample/MainActivity.java](app/src/main/java/com/g3rcar/httphandler/sample/MainActivity.java)