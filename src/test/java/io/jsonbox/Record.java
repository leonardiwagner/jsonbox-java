package io.jsonbox;

class Record {
  private final String _id;
  private final String name;
  private String message;
  private final int age;
  private final String _createdOn;

  public Record(String _id, String name, int age, String _createdOn) {
    this._id = _id;
    this.name = name;
    this.age = age;
    this._createdOn = _createdOn;
  }

  public String getId() {
    return _id;
  }

  public String getName() {
    return name;
  }

  public String getMessage() {
    return message;
  }

  public int getAge() {
    return age;
  }

  public String getCreatedOn() {
    return _createdOn;
  }

  

  
}