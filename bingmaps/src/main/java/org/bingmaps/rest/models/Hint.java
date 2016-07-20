package org.bingmaps.rest.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Hint {
	public Hint()
    {
    }

    public Hint(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public Hint(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

    public String HintType;

    public String Value;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.HintType = obj.optString("hintType");
        this.Value = obj.optString("value");
    }
}
