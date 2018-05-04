package org.aist.formexpert.services;

import com.smartystreets.api.StaticCredentials;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.ClientBuilder;
import com.smartystreets.api.us_street.Lookup;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressValidatorService {
    private static final Logger logger = LoggerFactory.getLogger(AddressValidatorService.class);
    private static String authId = System.getenv("SMART_AUTH_ID");
    private static String authToken = System.getenv("SMART_AUTH_TOKEN");
    private static StaticCredentials credentials = new StaticCredentials(authId, authToken);
    private static Client client = new ClientBuilder(credentials).build();

    public boolean validateAddress(String address) {
        Lookup addressLookup = new Lookup(address);
        try {
            logger.info("Trying to lookup " + address);
            client.send(addressLookup);
        } catch (SmartyException ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            logger.error("IOException happened.");
            ex.printStackTrace();
        }

        return !addressLookup.getResult().isEmpty();
    }
}