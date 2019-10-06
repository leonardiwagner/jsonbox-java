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


    @BeforeClass
    public static void initialize() {
        storage = new JsonBoxStorage("box_0c83d77bab82ce487665");
    }

    @Before
    public void cleanRecords() throws IOException {
        storage.deleteByQuery("name:*");
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
    public void shouldReadWithFilter() throws IOException {
        storage.create(adapter.toJson(new Record("", "william", 18, "")));
        storage.create(adapter.toJson(new Record("", "lisa", 21, "")));
        storage.create(adapter.toJson(new Record("", "patricia", 52, "")));
        storage.create(adapter.toJson(new Record("", "james", 38, "")));
        storage.create(adapter.toJson(new Record("", "jennifer", 31, "")));
        storage.create(adapter.toJson(new Record("", "thomas", 22, "")));
        storage.create(adapter.toJson(new Record("", "david", 30, "")));
        storage.create(adapter.toJson(new Record("", "elizabeth", 30, "")));

        String jsonResultString = storage.read("-name", 1, 2, "age:<35,name:*i*");

        List<Record> resultRecords = listAdapter.fromJson(jsonResultString);

        assertEquals(resultRecords.size(), 2);
        assertEquals(resultRecords.get(0).getName(), "lisa");
        assertEquals(resultRecords.get(1).getName(), "jennifer");
    }

    @Test
    public void shouldUpdate() throws IOException {
        String jsonCreateResultString = storage.create(adapter.toJson(new Record("", "william", 18, "")));
        Record createResultRecord = adapter.fromJson(jsonCreateResultString);


        String result = storage.updateByRecordId(createResultRecord.getId(), adapter.toJson(new Record("", "william", 19, "")));
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "Record updated.");
    }

    @Test
    public void shouldDelete() throws IOException {
        String jsonCreateResultString = storage.create(adapter.toJson(new Record("", "william", 18, "")));
        Record createResultRecord = adapter.fromJson(jsonCreateResultString);

        String result = storage.deleteByRecordId(createResultRecord.getId());
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "Record removed.");
    }

    @Test
    public void shouldDeleteByQuery() throws IOException {
        storage.create(adapter.toJson(new Record("", "william", 18, "")));
        storage.create(adapter.toJson(new Record("", "mile", 18, "")));
        storage.create(adapter.toJson(new Record("", "amy", 18, "")));

        String result = storage.deleteByQuery("name:*a*");
        Record resultMessage = adapter.fromJson(result);

        assertEquals(resultMessage.getMessage(), "2 Records removed.");
    }

}
