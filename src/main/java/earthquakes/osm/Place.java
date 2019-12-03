package earthquakes.osm;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class Place {
  public long place_id;
  public double lat;
  public double lon;
  public String display_name;
  public String type;
  private static Logger logger = LoggerFactory.getLogger(Place.class);


  /**
     * Create a FeatureCollection object from json representation
     * 
     * @param json String of json returned by API endpoint {@code /classes/search}
     * @return a new FeatureCollection object
     * @see <a href=
     *      "https://tools.ietf.org/html/rfc7946">https://tools.ietf.org/html/rfc7946</a>
     */

    public static List<Place> listFromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // FeatureCollection featureCollection = objectMapper.readValue(json, FeatureCollection.class);
            // List<Place> place = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, Place.class));
            List<Place> place = objectMapper.readValue(json, new TypeReference<List<Place>>(){});
            return place;
        } catch (JsonProcessingException jpe) {
            logger.error("JsonProcessingException:" + jpe);
            return null;
        } catch (Exception e) {
            logger.error("Exception:" + e);
            return null;
        }
    }
}