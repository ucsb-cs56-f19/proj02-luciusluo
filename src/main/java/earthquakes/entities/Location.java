package earthquakes.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String placeId;
    private String name;
    private double latitude;
    private double longitude;
    
    private String uid;

    public Long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPlaceId() { return placeId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }
   
    public String getName() { return name; }
    public void setName (String name) { this.name = name; }
    
    public double getLatitude() {return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public double getLongitude() { return longitude; }
    public void setLongitude( double longitude) { this.longitude = longitude; }
    
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
}

