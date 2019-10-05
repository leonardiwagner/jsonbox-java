package io.jsonbox;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.jsonbox.JsonBoxStorage;

import java.io.IOException;

class AppTest 
{
    @Test
    public void shouldAssertPost() throws IOException {
        JsonBoxStorage storage = new JsonBoxStorage("box_0c83d77bab82ce487665");

        String result = storage.create("{ \"a\": \"6\", \"name\": \"john\" }");

        assertEquals("ae", result);
    }

    @Test
    public void shouldAssertGet() throws IOException {
        JsonBoxStorage storage = new JsonBoxStorage("box_0c83d77bab82ce487665");

        String result = storage.read();

        assertEquals("ae", result);
    }

    @Test
    public void shouldAssertGet2() throws IOException {
        JsonBoxStorage storage = new JsonBoxStorage("box_0c83d77bab82ce487665");

        String result = storage.read("", 0, 0, "");

        assertEquals("ae", result);
    }

    @Test
    public void shouldUpdate() throws IOException {
        JsonBoxStorage storage = new JsonBoxStorage("box_0c83d77bab82ce487665");

        String result = storage.updateByRecordId("5d974b13f41b240017e71846", "{ \"a\": \"5\", \"name\": \"mariaahn\" }");

        assertEquals("ae", result);
    }

    @Test
    public void shouldDelete() throws IOException {
        JsonBoxStorage storage = new JsonBoxStorage("box_0c83d77bab82ce487665");

        String result = storage.deleteByRecordId("5d974b13f41b240017e71846");

        assertEquals("ae", result);
    }
}
