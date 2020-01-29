/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 *
 * @author Rasmu
 */
@ApplicationScoped
public class EntryConsumer {

    public String getHttpData(String url) throws MalformedURLException, IOException {
        URL getUrl = new URL(url);
        Scanner scanner = new Scanner(getUrl.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();

        return response;
    }

    @Incoming("cvr-enrichment")
    public void consumeEntry(String msg) throws IOException, Exception {
        Gson gson = new Gson();
        System.out.println("Recieved data_____________: \n" + msg + "\n");

        Map map = gson.fromJson(msg, Map.class);
        //String cvr = (String) map.get("cvr"); 
        String cvr = "convergens";
        System.out.println("CVR___________: \n" + cvr + "\n");
        
        String url = "https://cvrapi.dk/api?country=dk&search=";

        String json = getHttpData(url + cvr);

        System.out.println("JSON_____________________: \n" + json + "\n");

    }
}
