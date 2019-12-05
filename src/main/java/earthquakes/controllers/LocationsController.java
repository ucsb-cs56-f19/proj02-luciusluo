package earthquakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Map;
import java.util.HashMap;

import com.nimbusds.oauth2.sdk.client.ClientReadRequest;

import earthquakes.osm.Place;
import earthquakes.services.LocationQueryService;
import earthquakes.searches.LocSearch;
import earthquakes.entities.Location;
import earthquakes.repositories.LocationRepository;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class LocationsController {

    @Autowired
       private LocationRepository locationRepository;
    
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    @GetMapping("/locations/search")
    public String getLocationsSearch(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            LocSearch locSearch) {
        return "locations/search";
    }
    

     @GetMapping("/locations/results")
    public String getLocationsResults(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            LocSearch locSearch) {

        LocationQueryService e =
           new LocationQueryService();

        model.addAttribute("locSearch", locSearch);
        // TODO: Actually do the search here and add results to the model
        String json = e.getJSON(locSearch.getLocation());
        model.addAttribute("json", json);
        List<Place> place = Place.listFromJSON(json);
        model.addAttribute("place",place);
        return "locations/results";
    }
    
    
    @GetMapping("/locations")
    public String index(Model model, OAuth2AuthenticationToken token) {
        String uid = token.getPrincipal().getAttributes().get("id").toString();
        Iterable<Location> locations= locationRepository.findByUid(uid);
        model.addAttribute("locations", locations);
        return "locations/index";
    }
    
    
    @PostMapping("/locations/add")
      public String add(Location location, Model model, OAuth2AuthenticationToken token) {
        String uid = token.getPrincipal().getAttributes().get("id").toString();
        location.setUid(uid);
        locationRepository.save(location);
        model.addAttribute("locations", locationRepository.findAll());
        return "locations/index";
      }
    
    @DeleteMapping("/locations/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid courseoffering Id:" + id));
        locationRepository.delete(location);
        model.addAttribute("locations", locationRepository.findAll());
        return "locations/index";
    }
    
}
