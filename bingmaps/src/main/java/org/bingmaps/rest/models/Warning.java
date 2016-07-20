package org.bingmaps.rest.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Warning {
	public Warning()
    {
    }

    public Warning(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public Warning(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

    public String WarningType;

    public String Severity;

    public String Value;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.WarningType = obj.optString("warningType");
        this.Severity = obj.optString("severity");
        this.Value = obj.optString("value");
    }
}
