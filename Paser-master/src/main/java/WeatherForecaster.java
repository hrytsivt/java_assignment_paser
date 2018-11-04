import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import java.security.Key;
/**
 * Created by Victor on 04.10.2018.
 */
public class WeatherForecaster {

    public String forecast(City city){
        String lastUpdate;
        float celTemperature;
        float celFeelsTemperature;
        float windSpeed;
        int windDirection;
        int humidity;
        int cloudCover;
        Float lat = city.getCoordinates().getLat();
        Float lon = city.getCoordinates().getLon();
        if (lat == null || lon == null) {
            return "This city is not supported";
        }
        JSONObject forecastInfo;
        String forecastUrl = "http://api.apixu.com/v1/current.json";
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(forecastUrl)
                    .queryString("key", "aa060e1a877b406ca6b152642181510")
                    .queryString("q", String.format("%f, %f", lat, lon))
                    .asJson();
            forecastInfo = jsonResponse.getBody().getObject();
        } catch (UnirestException ue) {
            return "Something went wrong";
        }
        if (forecastInfo == null) {
            return "Something went wrong";
        }
        JSONObject current_json = forecastInfo.getJSONObject("current");
        lastUpdate = current_json.getString("last_updated");
        celTemperature = (float) current_json.getDouble("temp_c");
        celFeelsTemperature = (float) current_json.getDouble("feelslike_c");
        windSpeed = (float) current_json.getDouble("wind_kph");
        windDirection = current_json.getInt("wind_degree");
        humidity = current_json.getInt("humidity");
        cloudCover = current_json.getInt("cloud");

        return String.format("\nThe last update was: %s\n" +
                        "Temperature: %f feels like %f\n" +
                        "Wind speed: %f Wind direction: %d\n" +
                        "Humidity: %d\n" +
                        "Clouds : %d", lastUpdate, celTemperature, celFeelsTemperature, windSpeed, windDirection,
                humidity, cloudCover);

    }

}
