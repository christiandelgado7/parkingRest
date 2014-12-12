package com.christancho.parking.service;

import com.christancho.parking.ParkingException;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.logging.LogManager;

/**
 * Class that generate a HttpServer where the ParkingAPI is deployed
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/11/14
 */
public class ParkingHttpServer {

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().reset();
            System.out.println("\n\n   Starting HTTPServer.");
            ResourceConfig resourceConfig = new PackagesResourceConfig("com.christancho.parking.service");
            String hostName = "localhost";
            try {
                hostName = InetAddress.getLocalHost().getCanonicalHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            URI uri = UriBuilder.fromUri("http://" + hostName + "/").port(8085).build();

            HttpServer httpServer = HttpServerFactory.create(uri, resourceConfig);

            httpServer.start();
            System.out.println("   HTTPServer started with WADL available at http://"
                    + hostName + ":8085/application.wadl");
            System.out.println("   Started HTTPServer Successfully!");
        } catch (Exception e) {
            throw new ParkingException(e, ParkingException.CODE_SERVER_HTTP, "Error starting the HTTPServer");
        }
    }
}
