package helper;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KseniaB on 12/21/2016.
 */
public class JsonParser {

    /**
     * Method deletes an empty new line character at the beginning of json and returns choosen value from json.
     */
    @Nullable
    public static String getValueFromJsonBy(String json, String name) throws JSONException {
        try {
            int i = json.indexOf("{");
            json = json.substring(i);
            JSONObject jsonObject = new JSONObject(json.trim());
            return jsonObject.getString(name);
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
