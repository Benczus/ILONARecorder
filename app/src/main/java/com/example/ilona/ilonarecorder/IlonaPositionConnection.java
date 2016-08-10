package com.example.ilona.ilonarecorder;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ilona on 2016.08.10..
 */
public class IlonaPositionConnection extends AsyncTask<String, String, String> {
    uni.miskolc.ips.ilona.measurement.model.measurement.Measurement measurement;

    public IlonaPositionConnection(uni.miskolc.ips.ilona.measurement.model.measurement.Measurement measurement) {
        this.measurement = measurement;
    }

    // Asycronous task that uses a thread in the background to do the network operations,
    // which makes the application more responsive.
    @Override
    protected String doInBackground(String... strings) {
        OutputStream out;
        try {
            // Connects to the server which is contained in the ServerURL variable
            String ServerURL = "http://grabowski.iit.uni-miskolc.hu:8080/ilona/getLocation";
            URL url = new URL(ServerURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            // Sets the connection type
            String ConnectionType = "Content-Type";
            String ConnectionPropety = "application/json;charset=UTF-8";
            urlConnection.setRequestProperty(ConnectionType, ConnectionPropety);
            Log.d("Connection watch", urlConnection.toString());
            // Connects to the server
            urlConnection.connect();
            out = urlConnection.getOutputStream();
            // Json object writer creates a standardized JSON output from the measurement.
            ObjectWriter objectMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = objectMapper.writeValueAsString(measurement);
            Log.d("JSON output", json);

            // Sends the measurement to the server
            out.write(json.getBytes());
            out.flush();

            //Receives the data sent by the server
            InputStream is = urlConnection.getInputStream();
            //Turns it into a readable string byte-by-byte
            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = is.read()) != -1) {
                sb.append((char) ch);
            }
            String zoneresult = sb.toString();
            Log.d("macska", zoneresult);
            // Returns the received data to the main activity
            out.close();
            return zoneresult;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "false";
    }
}
