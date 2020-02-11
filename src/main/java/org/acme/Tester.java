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

/**
 *
 * @author Rasmu
 */
public class Tester {
    Gson gson = new Gson();

    public String getHttpData(String url) throws MalformedURLException, IOException {
        URL getUrl = new URL(url);
        Scanner scanner = new Scanner(getUrl.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();

        return response;
    }

    public void tester() throws IOException {
        String cvr = "convergens";
        String url = "https://cvrapi.dk/api?country=dk&search=";

        String cvrString = getHttpData(url + cvr);

        Map cvrJson = gson.fromJson(cvrString, Map.class);
        
        JsonObject companyInfo = new JsonObject();
        companyInfo.add("name", gson.toJsonTree(cvrJson.get("name")));
        companyInfo.add("address", gson.toJsonTree(cvrJson.get("address")));
        companyInfo.add("zipcode", gson.toJsonTree(cvrJson.get("zipcode")));
        companyInfo.add("city", gson.toJsonTree(cvrJson.get("city")));
        companyInfo.add("phone", gson.toJsonTree(cvrJson.get("phone")));
        companyInfo.add("email", gson.toJsonTree(cvrJson.get("email")));
        
        
        System.out.println(companyInfo);

    }

    public static void main(String[] args) throws IOException {
        Tester test = new Tester();
        test.tester();

    }
    
}
