package cmu.edu.assignment1;

import jakarta.ws.rs.SeBootstrap;

public class Main {
    public static void main(final String[] args) {
        SeBootstrap.Configuration configuration = SeBootstrap.Configuration.builder()
                .host("localhost")
                .port(8080)
                .protocol("http")
                .build();

        SeBootstrap.start(HelloApplication.class, configuration);
    }
}