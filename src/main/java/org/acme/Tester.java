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

    public static void main(String[] args) throws IOException {
        String cvr = "convergens";
        String url = "https://cvrapi.dk/api?country=dk&search=";

        Tester test = new Tester();
        String json = test.getHttpData(url + cvr);

        System.out.println(json);

    }
}
