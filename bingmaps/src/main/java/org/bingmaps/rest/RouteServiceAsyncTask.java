package org.bingmaps.rest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bingmaps.data.ServiceRequest;
import org.bingmaps.rest.models.Response;
import org.bingmaps.rest.models.Route;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class RouteServiceAsyncTask extends AsyncTask<ServiceRequest, Void, Route> {
    private Handler _callback;

    public RouteServiceAsyncTask(Handler callback) {
        _callback = callback;
    }

    protected Route doInBackground(ServiceRequest... params) {
        Route route = null;
        if (params != null && params.length > 0) {
            ServiceRequest request = params[0];
            String result = request.execute();
            if (TextUtils.isEmpty(result))
                return null;
            try {
                //remove all decimal places in excess of 5 places. no number in the response needs more than 5 decimal places
                Pattern p = Pattern.compile("(.[0-9]{5})([0-9]*)");
                Matcher m = p.matcher(result);
                result = m.replaceAll("$1");//.replaceAll("[0]+,", ",").replaceAll("[0]+\\]", "\\]");

                //for faster parsing of JSON separate route path coordinates from result string.
                String routePathString = null;
                int routePathIdx = result.indexOf("routePath");
                routePathIdx = result.indexOf("LineString", routePathIdx);

                p = Pattern.compile("\\[((\\[-?[0-9]+.?[0-9]*,-?[0-9]+.?[0-9]*\\]),?)*\\]");
                m = p.matcher(result);
                if (m.find(routePathIdx)) {
                    routePathString = m.group();
                }

                if (routePathString != null) {
                    result = result.replace(routePathString, "[]");
                }

                JSONObject obj = new JSONObject(result);
                Response response = new Response(obj);

                if (response.ResourceSets.length > 0 && response.ResourceSets[0].Resources.length > 0) {
                    route = new Route(response.ResourceSets[0].Resources[0]);
                }

                if (route != null && routePathString != null) {
                    //manually parse route path.
                    p = Pattern.compile("(\\[)(-?[0-9]+.?[0-9]*,-?[0-9]+.?[0-9]*)(\\])");
                    m = p.matcher(routePathString);
                    route.RoutePath = m.replaceAll("new MM.Location($2)");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return route;
    }

    @Override
    protected void onPostExecute(final Route route) {
        if (_callback != null) {
            Message response = new Message();
            response.obj = route;
            _callback.sendMessage(response);
        }
    }
}
