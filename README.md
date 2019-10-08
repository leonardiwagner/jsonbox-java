# jsonbox-java ![Travis (.org)](https://img.shields.io/travis/leonardiwagner/jsonbox-java?style=flat-square) ![Coveralls github](https://img.shields.io/coveralls/github/leonardiwagner/jsonbox-java?style=flat-square) ![Maven Central](https://img.shields.io/maven-central/v/io.jsonbox/jsonbox?style=flat-square)

JVM library for [jsonbox.io](https://jsonbox.io): A HTTP based JSON storage. It lets you store, read & modify JSON data over HTTP APIs for FREE. Ideal for small projects, prototypes or hackathons, where you don't have to spin up your own data store.

Demo example:
```java
//Create a storage
JsonBoxStorage storage = new JsonBoxStorage("box30c83d77bab82ce487665");
//Create records
storage.create(new Record("john", 25));
//Read records
List<Record> records = jsonAdapter.fromJson(storage.read());
```
## Starting Guide
1. [Installation](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples#1-add-jsonbox-library-in-your-project-dependencies-if-you-are-using-gradle-scala-or-kotlin-see-their-dependency-script-here-for-clojure-we-have--an-exclusive-guide-here)
2. [Basic examples](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples#4-now-you-can-use-any-function-from-the-documentation-to-create-read-update-and-delete-records-in-your-store-or-collection-see-examples)
3. [Advanced examples with JSON parsing](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples-with-JSON-parsing)

## Documentation

### Constructors

| Name | Summary |
|-----|---|
|`JsonBoxStorage()`                       | Instance for a jsonbox.io box, a random box id will be generated, you can use `getBoxId()` to retrieve generated box id. |
|`JsonBoxStorage(String boxId)`                       | Instance for a jsonbox.io box. |
|`JsonBoxStorage(String boxId, String collectionId)`  | Instance for a jsonbox.io collection inside a box. |

### Functions

| Name | Summary |
|-----|---|
|`create(String json)`                       | Create a record inside a box / collection. |
|`read()`  | Read all records inside a box / collection. |
|`read(int skip, int limit, String filter, String sort)` | Read records in a box / collection with given filter parameters. `skip` and `limit` are pagination parametrs, `filter` usage [here](https://github.com/vasanthv/jsonbox#filtering), `sort` by a json property (use `-` for descending sorting, example: `"-name"`. |
|`update(String recordId, String json)`  | Update a record in a box / collection. |
|`delete(String recordId)` | Delete a single record from a box / collection. |
|`deleteFiltering(String filter)` | Delete multiple records from a box / collection using a filter. `filter` usage [here](https://github.com/vasanthv/jsonbox#filtering).|

#### Getters
| Name | Summary |
|-----|---|
|`getBoxId()` | current box id. |
|`getCollectionId()` | current collection id. returns `null` if you are not using a collection. |

#### Helper Functions for reading
| Name | Summary |
|-----|---|
|`read(int skip, int limit)`  | Read with pagination parameters only. |
|`read(int skip, int limit, String filter)` | Read with pagination and filter paramters only. |
|`readFiltering(String filter)`  | Read with filter parameter only. |
|`readSorting(String sort)` | Read with sort paramter only.|

## Contribution

Feel free to contribute. Just create an issue before sending a Pull Request.

## License

[Apache 2.0][apache-license]

[apache-license]:./LICENSE
