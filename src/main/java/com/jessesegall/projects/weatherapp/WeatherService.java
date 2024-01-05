package com.jessesegall.projects.weatherapp;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

public class WeatherService {
    private final OkHttpClient client = new OkHttpClient();
    private final  ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey;

    public WeatherData getWeatherData(String city) throws IOException {
        String jsonData = getWeatherJson(city);
        return  objectMapper.readValue(jsonData, WeatherData.class);
    }



    public WeatherService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("RAPIDAPI_KEY");
    }


    public String getWeatherJson(String city) throws IOException {
        Request request = new Request.Builder()
                .url("https://open-weather13.p.rapidapi.com/city/" + city)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", "open-weather13.p.rapidapi.com")
                .build();
        try (Response response = client.newCall(request).execute();){
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            assert response.body() != null;
            return response.body().string();
        }
    }




    public WeatherService(String apiKey) throws IOException {
        this.apiKey = apiKey;
    }
}
