Parking Rest API Server
=======================

Esta prueba tiene como objetivo ver las habilidades de programación, se valoraran de más a menos los siguientes aspectos del código:
 
- Cumple los requisitos. 
- Estructura y mantenibilidad. 
- Eficiencia

Se considera más importante la calidad del código que implementar de toda la funcionalidad solicitada. El servicio debe ser usable y deberán asumirse comportamientos de la API en ciertos pasos, se debe documentar y justificar cada comportamiento que se salga de la especificación.

No se permite el uso de librerías externas salvo para parsear y generar JSON.

Se debe implementar usando Java 6, para publicar el servicio se recomienda el uso de:
com.sun.net.httpserver.HttpServer


Descripción del Ejercicio
=========================

Necesitamos una API REST para una aplicación móvil que proporcione información sobre parking. Los consumidores de este servicio serán dispositivos móviles de diferentes plataformas y por tanto el formato de los mensajes REST deberá estar en un formato homogéneo en toda la API.

En caso de error los dispositivos móviles deberían tener un mínimo de información para saber si la operación se ha realizado correctamente o no. No es necesario detalle del error, pero si un mínimo para que la aplicación móvil pueda decidir qué hacer. La persistencia se hará en memoria, no se permite el uso de disco en ninguna operación.

La API debe responder sobre el recurso parking con los siguientes datos:

- Identificador: Un entero único que identificará el parking
- Nombre: Nombre del parking
- Hora de apertura: Un entero que representa la hora de apertura del parking
- Hora de cierre: Un entero que representa la hora de cierre del parking 
- Plazas totales: Número de plazas totales del parking 
- Plazas libres: Número de plazas disponibles 
- Días de la semana que abre: Un listado de los días de la semana que abre (por ejemplo: Lunes, Martes, Miércoles, Jueves y Viernes)
- Latitud GPS
- Longitud GPS


Servicios
=========
Se implementaron servicios REST con el CRUD de la central de Parking, así como tambien otras las 
operaciones solicitadas en el ejercicio.


CRUD
----

###Create Parking
```
POST http://localhost:8085/api/parking
```
```js
raw:
    {
        "name": "Parking Test", 
        "openHour": 9,
        "closeHour": 18,
        "totalPlaces": 50,
        "freePlaces": 0,
        "latitude": 41.3808,
        "longitude": 2.1676,
        "daysOpen": [
          "MONDAY",
          "TUESDAY",
          "WEDNESDAY",
          "THURSDAY",
          "FRIDAY",
          "SATURDAY",
          "SUNDAY"
        ]
    }
```

Devuelve el Parking insertado


###Read Parking

```
GET http://localhost:8085/api/parking/{parkingId}
```

Devuelve el Parking con el Id solicitado

###Update Parking

```
PUT http://localhost:8085/api/parking/{parkingId}
```
```js
raw:
    {
        "name": "Parking New Name",
        "openHour": 9,
        "closeHour": 20,
        "daysOpen": [
          "MONDAY",
          "TUESDAY",
          "WEDNESDAY",
          "THURSDAY",
          "FRIDAY"
        ]
    }
```
Devuelve el Parking modificado

###Delete Parking

```
DELETE http://localhost:8085/api/parking/{parkingId}
```

Devuelve el Parking eliminado


Search Parking
--------------

###Parametros
- `complete`: Booleano que representa si se desea filtrar los Parkings llenos
- `date`: String con el formato de fecha `DD.MM.YYYY#hh`, filtra los Parkings que esten abiertos en ese 
momento. **Nota:** Dado que la llamada Http escapa el caracter *'#'*, la peticion debe llamarse con el 
encoding *%23*
- `latitude, longitude, distance`: valores doubles que representan un area circular, filtrará a los Parkings que se encuentren en el radio definido por la distancia (en kilometros) y las coordenadas GPS del centro del circulo.

```
GET http://localhost:8085/api/parking/search?complete=false&date=20.12.2014%2315&latitude=41.385&longitude=2.165&distance=0.6
```

Devuelve una lista de Parkings que cumplan con los filtros de busqueda


Modificar Plazas
---------------

###Liberar una plaza
```
GET http://localhost:8085/api/parking/{parkingId}/releasePlace
```
###Ocupar una plaza
```
GET http://localhost:8085/api/parking/{parkingId}/takePlace
```

Ambos metodos devuelven el número de plazas libres para el Parking solicitado


Requerimientos
============

+ Java JDK 1.6 or superior


Ejecución
=========

Dentro de la carpeta **/dist** se encuentra el compilado ```Parking.jar```. Se debe ejecutar el siguiente 
comando para arrancar el servicio.

>   java -jar Parking.jar

De entrada se cargan tres Parking en la Central como valores de pruebas.
