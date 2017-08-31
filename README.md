# RightNow: Getting Started -- Java JAX-WS

Oracle has been publishing the [Getting Started](http://documentation.custhelp.com/euf/assets/devdocs/august2017/Connect_Web_Services_for_SOAP/Content/Getting%20Started/Getting%20Started%20--%20Java.htm) for Java Axis2.  
However, there is not `Getting Started` for Java JAX-WS.  
I introduce to `Getting Started` for Java JAX-WS.

## Prerequisites

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Fix the Config.java

Fix the `src/main/java/app/Config.java` by your RightNow connect information.

```
    public static final String USERNAME = "__REPLACE_YOUR_USERNAME__";
    public static final String PASSWORD = "__REPLACE_YOUR_PASSWORD__";
    public static final String APP_ID = "__REPLACE_YOUR_APP_ID__";
    public static final String WSDL_URL = "__REPLACE__"
```

## Gnerate client code

Execute following command.
```
$ ./gradlew wsImport
```

## Run

```
$ ./gradlew run
```

That's all !



