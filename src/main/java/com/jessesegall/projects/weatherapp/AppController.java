package com.jessesegall.projects.weatherapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppController {

    @FXML
    private TextField cityTextField;

    @FXML
    private Label weatherDataLabel;

    private WeatherService weatherService = new WeatherService();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public AppController() throws IOException {
    }

    @FXML
    protected void onGetWeatherButtonClick() {
        String city = cityTextField.getText().trim();
        if (!city.isEmpty()) {
            executorService.submit(() -> {
            try {
                WeatherData weatherData = weatherService.getWeatherData(city);
                Platform.runLater(() -> {
                    WeatherData.Main mainData = weatherData.getMain();
                    weatherDataLabel.setText(
                            "Temperature: " + mainData.getTemperature() + "°F\n" +
                                    "Feels Like: " + mainData.getFeelsLike() + "°F\n" +
                                    "Max Temperature: " + mainData.getMaxTemp() + "°F\n" +
                                    "Min Temperature: " + mainData.getMinTemp() + "°F\n" +
                                    "Humidity: " + mainData.getHumidity() + " \n" +
                                    "Pressure: " + mainData.getPressure()
                    );
                });
            } catch (IOException e) {
                e.printStackTrace(); // Handle this exception more gracefully in production code
            }
        });
    } else {
        weatherDataLabel.setText("Please enter a city name.");
    }
}

public void shutdown() {
    executorService.shutdownNow(); // Shutdown the executor service when the app is closed
}
}
