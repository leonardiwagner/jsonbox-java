package io.jsonbox;

import io.jsonbox.JsonBoxStorage;
import com.squareup.moshi.*;

import java.util.*;
import java.io.IOException;
import org.junit.*;
import static org.junit.Assert.*;

public class AppTest 
{
    private static JsonBoxStorage storage = null;
    private Moshi moshi = new Moshi.Builder().build();
    private JsonAdapter<Record> adapter = moshi.adapter(Record.class);
    private JsonAdapter<List<Record>> listAdapter = moshi.adapter(Types.newParameterizedType(List.class, Record.class));
    private static String boxId = "box00000001111111111";
    private static String collectionId = "collection01";

    @BeforeClass
    public static void initialize() {
        storage = new JsonBoxStorage(boxId, collectionId);
    }

    @Before
    public void cleanRecords() throws IOException {
        storage.deleteFiltering("name:*");
    }

    @Test
    public void shouldCreate() throws IOException {        
        Record requestRecord = new Record("id", "name", 5, "");
        String jsonRequestString = adapter.toJson(requestRecord);

        String jsonResultString = storage.create(jsonRequestString);
        Record resultRecord = adapter.fromJson(jsonResultString);

        assertEquals(requestRecord.getName(), resultRecord.getName());
        assertNotEquals(requestRecord.getCreatedOn(), resultRecord.getCreatedOn());
    }

    @Test
    public void shouldRead() throws IOException {
        storage.create(adapter.toJson(new Record("", "frank", 0, "")));
        storage.create(adapter.toJson(new Record("", "amy", 0, "")));
        storage.create(adapter.toJson(new Record("", "dalila", 0, "")));

        String jsonResultString = storage.read();
        
        List<Record> resultRecords = listAdapter.fromJson(jsonResultString);
        List<Record> expectedRecords = new ArrayList<Record>();

        for(Record r : resultRecords) {
            if(r.getName().equals("frank") || r.getName().equals("amy") || r.getName().equals("dalila")) {
                expectedRecords.add(r);
            }
        }

        assertEquals(expectedRecords.size(), resultRecords.size());
    }

    @Test
    public void shouldReadWithFilters() throws IOException {
        storage.create(adapter.toJson(new Record("", "thomas", 22, "")));
        storage.create(adapter.toJson(new Record("", "james", 38, "")));
        storage.create(adapter.toJson(new Record("", "patricia", 52, "")));
        storage.create(adapter.toJson(new Record("", "william", 18, "")));
        storage.create(adapter.toJson(new Record("", "jennifer", 31, "")));
        storage.create(adapter.toJson(new Record("", "vivian", 21, "")));
        storage.create(adapter.toJson(new Record("", "david", 30, "")));
        storage.create(adapter.toJson(new Record("", "elizabeth", 30, "")));

        //full read
        String jsonResultString = storage.read(1, 2, "age:<35,name:*i*", "-name");
        List<Record> resultRecords = listAdapter.fromJson(jsonResultString);

        assertEquals(resultRecords.size(), 2);
        assertEquals(resultRecords.get(0).getName(), "vivian");
        assertEquals(resultRecords.get(1).getName(), "jennifer");

        //helpers read
        jsonResultString = storage.read(2, 3);
        resultRecords = listAdapter.fromJson(jsonResultString);
        assertEquals(resultRecords.size(), 3);
        assertEquals(resultRecords.get(0).getName(), "vivian");
        assertEquals(resultRecords.get(1).getName(), "jennifer");
        assertEquals(resultRecords.get(2).getName(), "william");

        jsonResultString = storage.read(0, 2, "age:<35,name:*e*");
        resultRecords = listAdapter.fromJson(jsonResultString);
        assertEquals(resultRecords.size(), 2);
        assertEquals(resultRecords.get(0).getName(), "elizabeth");
        assertEquals(resultRecords.get(1).getName(), "jennifer");

        jsonResultString = storage.readFiltering("age:<35,name:*e*");
        resultRecords = listAdapter.fromJson(jsonResultString);
        assertEquals(resultRecords.size(), 2);
        assertEquals(resultRecords.get(0).getName(), "elizabeth");
        assertEquals(resultRecords.get(1).getName(), "jennifer");

        jsonResultString = storage.readSorting("name");
        resultRecords = listAdapter.fromJson(jsonResultString);
        assertEquals(resultRecords.size(), 8);
        assertEquals(resultRecords.get(0).getName(), "david");
        assertEquals(resultRecords.get(1).getName(), "elizabeth");
        assertEquals(resultRecords.get(2).getName(), "james");
        assertEquals(resultRecords.get(3).getName(), "jennifer");
        assertEquals(resultRecords.get(4).getName(), "patricia");
        assertEquals(resultRecords.get(5).getName(), "thomas");
        assertEquals(resultRecords.get(6).getName(), "vivian");
        assertEquals(resultRecords.get(7).getName(), "william");
    }

    @Test
    public void shouldUpdate() throws IOException {
        String jsonCreateResultString = storage.create(adapter.toJson(new Record("william", 18)));
        Record createResultRecord = adapter.fromJson(jsonCreateResultString);


        String result = storage.update(createResultRecord.getId(), adapter.toJson(new Record("william", 19)));
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "Record updated.");
    }

    @Test
    public void shouldDelete() throws IOException {
        String jsonCreateResultString = storage.create(adapter.toJson(new Record("william", 18)));
        Record createResultRecord = adapter.fromJson(jsonCreateResultString);

        String result = storage.delete(createResultRecord.getId());
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "Record removed.");
    }

    @Test
    public void shouldDeleteByQuery() throws IOException {
        storage.create(adapter.toJson(new Record("", "william", 18, "")));
        storage.create(adapter.toJson(new Record("", "mile", 18, "")));
        storage.create(adapter.toJson(new Record("", "amy", 18, "")));

        String result = storage.deleteFiltering("name:*a*");
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "2 Records removed.");
    }

    @Test
    public void shouldReturnBoxId() {
        assertEquals(storage.getBoxId(), boxId);
    }

    @Test
    public void shouldReturnCollectionId() {
        assertEquals(storage.getCollectionId(), collectionId);
    }

    @Test
    public void shouldGenerateBoxId() {
        JsonBoxStorage autoGeneratedStorage = new JsonBoxStorage();
        assertEquals(autoGeneratedStorage.getBoxId().length(), 32);
        assertEquals(autoGeneratedStorage.getCollectionId(), null);
    }

}
