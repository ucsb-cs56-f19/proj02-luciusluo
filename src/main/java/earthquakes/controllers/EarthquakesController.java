package earthquakes.controllers;

import earthquakes.services.EarthquakeQueryService;
import earthquakes.searches.EqSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;
import java.util.HashMap;

import com.nimbusds.oauth2.sdk.client.ClientReadRequest;

import earthquakes.geojson.FeatureCollection;


@Controller
public class EarthquakesController {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    @GetMapping("/earthquakes/search")
    public String getEarthquakesSearch(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            EqSearch eqSearch) {
        return "earthquakes/search";
    }
    

     @GetMapping("/earthquakes/results")
    public String getEarthquakesResults(Model model, OAuth2AuthenticationToken oAuth2AuthenticationToken,
            EqSearch eqSearch) {

        EarthquakeQueryService e =
           new EarthquakeQueryService();

        model.addAttribute("eqSearch", eqSearch);
        // TODO: Actually do the search here and add results to the model
        String json = e.getJSON(eqSearch.getDistance(), eqSearch.getMinmag(), eqSearch.getLat(), eqSearch.getLon(), eqSearch.getLocation());
        model.addAttribute("json", json);
	FeatureCollection featureCollection = FeatureCollection.fromJSON(json);
        model.addAttribute("featureCollection",featureCollection);
        return "earthquakes/results";
    }

}
