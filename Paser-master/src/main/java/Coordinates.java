import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


@ToString
@Getter
@Setter
public class Coordinates {
    private Float lat, lon;

    @SneakyThrows
    public static Coordinates parseCoordinates(String url) {
        float latitude, longtitude;
        Coordinates coordinatess = new Coordinates();
        Document doc = Jsoup.connect(url).get();
        Elements cityCoords = doc.select(".geo");
        if (doc.select(".mw-kartographer-maplink").size() != 0) {
            coordinatess.setLat(Float.parseFloat(doc.select(".mw-kartographer-maplink").get(0).attr("data-lat")));
            coordinatess.setLon(Float.parseFloat(doc.select(".mw-kartographer-maplink").get(0).attr("data-lon")));
        }else if (cityCoords.size() != 0) {
            String strCoords[] = cityCoords.text().replaceAll(";", "").split(" ");
            latitude = Float.parseFloat(strCoords[0]);
            longtitude = Float.parseFloat(strCoords[1]);
            coordinatess.setLat(latitude);
            coordinatess.setLon(longtitude);
        }else {
            coordinatess.setLat(null);
            coordinatess.setLon(null);
        }
        return coordinatess;
    }
}
