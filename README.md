# jsonbox
A Java / JVM wrapper for jsonbox.io

## Example
```java
import io.jsonbox.JsonBoxStorage;

JsonBoxStorage storage = new JsonBoxStorage("box30c83d77bab82ce487665");

String result = storage.create("{ \"name\": \"john\" }");
```

As we can see, this library is unopinated about JSON parsing, so you can choose your prefered JSON library to work with classes instead plain JSON text. Our recommendation is [moshi](https://github.com/square/moshi#moshi).

## Install via Maven Repository
```xml
<dependency>
  <groupId>io.jsonbox</groupId>
  <artifactId>jsonbox</artifactId>
  <version>0.0.1</version>
</dependency>
```
Note: we are waiting for publishing approval, so installing via Maven is not working yet.
## Documentation

#### Constructors

| Name | Summary |
|-----|---|
|`JsonBoxStorage(String boxId)`                       | Instance for a jsonbox.io box |
|`JsonBoxStorage(String boxId, String collectionId)`  | Instance for a jsonbox.io collection inside a box. |

#### Functions

| Name | Summary |
|-----|---|
|`create(String json)`                       | Create a record inside a box / collection. |
|`read()`  | Read all records inside a box / collection. |
|`read(String sort, int skip, int limit, String query)`  | Read records in a box / collection with given filter parameters. |
|`updateByRecordId(String recordId, String json)`  | Update a record in a box / collection. |
|`deleteByRecordId(String recordId)` | Delete a single record from a box / collection. |
|`deleteByQuery(String query)` | Delete multiple records from a box / collection using a query string to filter. |

Query string filter: https://github.com/vasanthv/jsonbox#filtering


## Deploy

[Deploy via Maven](https://github.com/leonardiwagner/jsonbox/wiki/Maven-Deploy)

## Contribution

Feel free to contribute. Just open an issue to discuss something before creating a PR.

## License

[Apache 2.0][apache-license]

[apache-license]:./LICENSE
