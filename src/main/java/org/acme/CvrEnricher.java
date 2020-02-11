/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
public class CvrEnricher {

    public String getJsonData(String url) throws MalformedURLException, IOException {
        URL getUrl = new URL(url);
        
        Scanner scanner = new Scanner(getUrl.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();

        return response;
    }

    @Incoming("cvr-enrichment")
    public void enrichCvr(String content) throws IOException, Exception {
        Gson gson = new Gson();
        Message msg = gson.fromJson(content, Message.class);
        msg.startLog("cvr-enrichment");
        String url = "https://cvrapi.dk/api?country=dk&search=";
        
        
        String cvr = "convergens";
        System.out.println("CVR___________: \n" + cvr + "\n");
        

        String cvrString = getJsonData(url + cvr);
        Map cvrJson = gson.fromJson(cvrString, Map.class);
        
        
        JsonObject companyInfo = new JsonObject();
        companyInfo.add("name", gson.toJsonTree(cvrJson.get("name")));
        companyInfo.add("address", gson.toJsonTree(cvrJson.get("address")));
        companyInfo.add("zipcode", gson.toJsonTree(cvrJson.get("zipcode")));
        companyInfo.add("city", gson.toJsonTree(cvrJson.get("city")));
        companyInfo.add("phone", gson.toJsonTree(cvrJson.get("phone")));
        

        System.out.println("COMPANY INFO___________: \n" + companyInfo + "\n");
        msg.appendJson("cvrInfo", companyInfo);

        
        msg.sendToKafkaQue();
    }
}
