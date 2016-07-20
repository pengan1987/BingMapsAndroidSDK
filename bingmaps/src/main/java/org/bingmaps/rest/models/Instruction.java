package org.bingmaps.rest.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Instruction {
	public Instruction()
    {
    }

    public Instruction(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public Instruction(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

    public String ManeuverType;

    public String Text;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.ManeuverType = obj.optString("maneuverType");
        this.Text = obj.optString("text");
    }
}
