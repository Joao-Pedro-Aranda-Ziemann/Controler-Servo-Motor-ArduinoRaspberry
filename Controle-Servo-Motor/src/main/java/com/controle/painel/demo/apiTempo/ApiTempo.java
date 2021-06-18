package com.controle.painel.demo.apiTempo;

import com.controle.painel.demo.exceptions.HttpException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApiTempo {

    // TODO: Criar arquivo de configuração para buscar pela cidade configurada
    private final static String localBraganca = "https://api.hgbrasil.com/weather?woeid=456859";

    private JSONObject searchApiData() throws Exception {

        try {

            HttpURLConnection conectionApiHgBrasil = (HttpURLConnection) new URL(localBraganca).openConnection();
            conectionApiHgBrasil.setRequestMethod("GET");

            if (conectionApiHgBrasil.getResponseCode() != 200)
                throw new HttpException("Erro " + conectionApiHgBrasil.getResponseCode() + " ao conectar  no site");

            BufferedReader bufferResultApi = new BufferedReader(new InputStreamReader((conectionApiHgBrasil.getInputStream())));

            StringBuffer jsonStr = new StringBuffer();
            bufferResultApi.readLine().lines().forEach(jsonStr::append);
            JSONObject json = new JSONObject(jsonStr.toString());
            return json;

        } catch (HttpException httEx) {
            throw httEx;
        } catch (UnknownHostException ue) {
            throw ue;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public JSONObject informationAboutApi() throws Exception {

        JSONObject roughJson = this.searchApiData();
        JSONObject refinedJson = new JSONObject();

        refinedJson.put("date", roughJson.getJSONObject("results").get("date"));
        refinedJson.put("time", roughJson.getJSONObject("results").get("time"));
        refinedJson.put("city_name", roughJson.getJSONObject("results").get("city_name"));
        refinedJson.put("description", roughJson.getJSONObject("results").get("description"));
        refinedJson.put("humidity", roughJson.getJSONObject("results").get("humidity"));
        refinedJson.put("currently", roughJson.getJSONObject("results").get("currently"));

        JSONObject forecastJson = (JSONObject) roughJson.getJSONObject("results").getJSONArray("forecast").get(0);
        refinedJson.put("max", forecastJson.get("max"));
        refinedJson.put("min", forecastJson.get("min"));

        return refinedJson;
    }

    public int timeH() {

        try {

            JSONObject json = this.searchApiData();
            String time = json.getJSONObject("results").get("time").toString();
            return Integer.parseInt(time.split(":")[0]);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
