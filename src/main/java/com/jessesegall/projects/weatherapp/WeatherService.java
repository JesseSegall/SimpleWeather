package com.jessesegall.projects.weatherapp;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherService {
    private final OkHttpClient client = new OkHttpClient();
    private final  ObjectMapper objectMapper = new ObjectMapper();

    public WeatherData getWeatherData(String city) throws IOException {
        String jsonData = getWeatherJson(city);
        return  objectMapper.readValue(jsonData, WeatherData.class);
    }

    public String getWeatherJson(String city) throws IOException {
        Request request = new Request.Builder()
                .url("https://open-weather13.p.rapidapi.com/city/" + city)
                .get()
                .addHeader("X-RapidAPI-Key", "c2bd33f4bbmsh12420e5192b0c4dp1e5f55jsna7568b6e1a9e")
                .addHeader("X-RapidAPI-Host", "open-weather13.p.rapidapi.com")
                .build();
        try (Response response = client.newCall(request).execute();){
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            assert response.body() != null;
            return response.body().string();
        }
    }




    public WeatherService() throws IOException {
    }
}
