# jsonbox-java 
[![Travis (.org)](https://img.shields.io/travis/leonardiwagner/jsonbox-java?style=flat-square&logo=travis)](https://travis-ci.org/leonardiwagner/jsonbox-java)
[![Coveralls github](https://img.shields.io/coveralls/github/leonardiwagner/jsonbox-java?style=flat-square)](https://coveralls.io/github/leonardiwagner/jsonbox-java)
[![Maven Central](https://img.shields.io/maven-central/v/io.jsonbox/jsonbox?color=light-green&logo=java&style=flat-square)](https://search.maven.org/artifact/io.jsonbox/jsonbox)

JVM library for [jsonbox.io](https://jsonbox.io): A HTTP based JSON storage. It lets you store, read & modify JSON data over HTTP APIs for FREE. Ideal for small projects, prototypes or hackathons, where you don't have to spin up your own data store.

Demo example:
```java
JsonBoxStorage storage = new JsonBoxStorage("box30c83d77bab82ce487665");
// create a new record
storage.create(adapter.toJson(new Record("john")));
// read records
List<Record> records = adapter.fromJson(storage.read());
```
## Starting Guide
1. [Installation](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples#1-add-jsonbox-library-in-your-project-dependencies-if-you-are-using-gradle-scala-or-kotlin-see-their-dependency-script-here-for-clojure-we-have--an-exclusive-guide-here)
2. [Basic examples](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples#4-now-you-can-use-any-function-from-the-documentation-to-create-read-update-and-delete-records-in-your-store-or-collection-see-examples)
3. [Advanced examples with JSON parsing](https://github.com/leonardiwagner/jsonbox-java/wiki/Usage-examples-with-JSON-parsing)

## Documentation

### Constructors

| Name | Summary |
|-----|---|
|`JsonBoxStorage()`                       | Instance for a jsonbox.io box, a random box id will be generated, you can use `getBoxId()` to retrieve the generated box id |
|`JsonBoxStorage(String boxId)`                       | Instance for a jsonbox.io box |
|`JsonBoxStorage(String boxId, String collectionId)`  | Instance for a jsonbox.io collection inside a box |

### Functions

| Name | Summary |
|-----|---|
|`create(String json)`                       | Create a new record |
|`read()`  | Read all records |
|`read(int skip, int limit, String filter, String sort)` | Read records with pagination, filtering and sorting* |
|`update(String recordId, String json)`  | Update a record by a given record id |
|`delete(String recordId)` | Delete a single record by a given record id |
|`deleteFiltering(String filter)` | Delete multiple records using filtering*|

*filtering and sorting:
- `filter` usage [here](https://github.com/vasanthv/jsonbox#filtering)
- `sort` by a json property, use `-` for descending sorting, example: `"-name"`.

#### Getters
| Name | Summary |
|-----|---|
|`getBoxId()` | current box id |
|`getCollectionId()` | current collection id (returns `null` if you are not using a collection) |

#### Helper Functions for reading
| Name | Summary |
|-----|---|
|`readFiltering(String filter)`  | Read applying only filtering |
|`readSorting(String sort)` | Read applying only sorting |
|`read(int skip, int limit)`  | Read applying only pagination |
|`read(int skip, int limit, String filter)` | Read applying only pagination and filtering |

## Contribution

Feel free to contribute. Just create an issue before sending a Pull Request.

## License

[Apache 2.0][apache-license]

[apache-license]:./LICENSE
