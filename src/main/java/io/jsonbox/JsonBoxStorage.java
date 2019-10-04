package io.jsonbox;

import java.io.IOException;

import java.util.HashMap;

public class JsonBoxStorage {
    public final Http http = new Http();
    private String id = "";
    
    public JsonBoxStorage(String id) {
        this.id = id;
    }

    /**
     * Create a record (HTTP POST)
     * @param  json a JSON string record to be created on jsonbox.io
     * @return      a JSON string response from jsonbox.io
     */
    public String create(String json) throws IOException {
        return http.request(id, null, "POST", new HashMap<String, String>(), json);
    }

    /**
     * Read all records (HTTP GET)
     * @return a JSON string response from jsonbox.io
     */
    public String read() throws IOException {
        return http.request(id, null, "GET",  new HashMap<String, String>(), null);
    }

    /**
     * Read records with given filter parameters (HTTP GET)
     * @param  sort sort the result set by the specific field. Add a prefix "-" to sort in reverse order. (default: "-_createdOn")
     * @param  skip skip certain number of records, it can be used for pagination. (default: 0)
     * @param  limit limit the results to a certain number of records, it can be used for pagination. (default: 20, max: 1000)
     * @param  query query for filtering values, check format at: https://github.com/vasanthv/jsonbox#filtering
     * @return      a JSON string response from jsonbox.io
     */
    public String read(String sort, int skip, int limit, String query) throws IOException {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sort", sort);
        parameters.put("skip", Integer.toString(skip));
        parameters.put("limit", Integer.toString(limit));
        parameters.put("query", query);

        return http.request(id, null, "GET", parameters, null);
    }

    /**
     * Update a record (HTTP PUT)
     * @param  recordId the record id (property "_id" generated by jsonbox.io on creation) to be updated.
     * @param  json     a JSON string record to be updated. Please note that this will not patch the record, it is a full update. A bulk update is not supported yet.
     * @return          a JSON string response from jsonbox.io
     */
    public String updateByRecordId(String recordId, String json) throws IOException {
        return http.request(id, recordId, "PUT",  new HashMap<String, String>(), json);
    }

    /**
     * Delete a single record (HTTP DELETE)
     * @param  recordId the record id (property "_id" generated by jsonbox.io on creation) to be deleted.
     * @return          a JSON string response from jsonbox.io
     */
    public String deleteByRecordId(String recordId) throws IOException {
        return http.request(id, recordId, "DELETE",  new HashMap<String, String>(), null);
    }

    /**
     * Delete multiple records (HTTP DELETE)
     * @param  query query for filtering values, check format at: https://github.com/vasanthv/jsonbox#filtering
     * @return       a JSON string response from jsonbox.io
     */
    public String deleteByQuery(String query) throws IOException {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("query", query);

        return http.request(id, null, "DELETE", parameters, null);
    }
}
