package com.christancho.parking.service;

import com.christancho.parking.Parking;
import com.christancho.parking.ParkingCentral;
import com.christancho.parking.ParkingException;
import com.christancho.parking.ParkingSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Class where defines all the ParkingCentral methods as a REST API
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/11/14
 */
@Path("/api")
public class ParkingAPI {

    /**
     * Class used to parse all the objects as JsonObject
     */
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(Parking.class, new ParkingSerializer())
            .registerTypeAdapter(ParkingException.class, new ParkingException.ParkingExceptionSerializer())
            .create();

    /**
     * Service GET to obtain an specified Parking
     *
     * @param parkingId the id for the Parking selected
     * @return the String with the Json representation of the Parking selected
     */
    @GET
    @Produces("application/json")
    @Path("/parking/{parkingId}")
    public String get(@PathParam("parkingId") Integer parkingId) {
        try {
            Parking parking = ParkingCentral.getInstance().getParking(parkingId);
            return gson.toJson(parking);
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }

    /**
     * Service POST to add a new Parking in the Central
     *
     * @param parkingJson the new parking to add
     * @return the String with the Json representation of the Parking added
     */
    @POST
    @Produces("application/json")
    @Path("/parking")
    public String add(String parkingJson) {
        try {
            if (parkingJson == null || parkingJson.trim().isEmpty())
                throw new ParkingException(ParkingException.CODE_PARSING_JSON,
                        ParkingException.DESC_INVALID_VALUE,
                        "The Parking can't be null");
            Parking parking = gson.fromJson(parkingJson, Parking.class);
            int parkingId = ParkingCentral.getInstance().addParking(parking);
            parking = ParkingCentral.getInstance().getParking(parkingId);
            return gson.toJson(parking);
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (JsonSyntaxException ex) {
            return gson.toJson(new ParkingException(ex, ParkingException.CODE_PARSING_JSON,
                    "Error Parsing Parking JsonObject"));
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }

    /**
     * Service PUT to edit an specified Parking
     *
     * @param parkingJson the parking to edit
     * @return the String with the Json representation of the Parking edited
     */
    @PUT
    @Produces("application/json")
    @Path("/parking")
    public String edit(String parkingJson) {
        return edit(parkingJson, null);
    }


    /**
     * Service PUT to edit an specified Parking Id
     *
     * @param parkingJson the parking to edit
     * @param parkingId   the id for the Parking selected
     * @return the String with the Json representation of the Parking edited
     */
    @PUT
    @Produces("application/json")
    @Path("/parking/{parkingId}")
    public String edit(String parkingJson, @PathParam("parkingId") Integer parkingId) {
        try {
            if (parkingJson == null || parkingJson.trim().isEmpty())
                throw new ParkingException(ParkingException.CODE_PARSING_JSON,
                        ParkingException.DESC_INVALID_VALUE,
                        "The Parking can't be null");
            Parking parking = gson.fromJson(parkingJson, Parking.class);
            if (parkingId != null)
                parking.setId(parkingId);
            parking = ParkingCentral.getInstance().editParking(parking);
            return gson.toJson(parking);
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (JsonSyntaxException ex) {
            return gson.toJson(new ParkingException(ex, ParkingException.CODE_PARSING_JSON,
                    "Error Parsing Parking JsonObject"));
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }


    /**
     * Service DELETE to remove an specified Parking
     *
     * @param parkingId the id for the Parking to remove
     * @return the String with the Json representation of the Parking removed
     */
    @DELETE
    @Produces("application/json")
    @Path("/parking/{parkingId}")
    public String remove(@PathParam("parkingId") Integer parkingId) {
        try {
            Parking parking = ParkingCentral.getInstance().removeParking(parkingId);
            return gson.toJson(parking);
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }

    /**
     * Service GET to obtain an specified Parking List
     *
     * @param complete
     * @param date
     * @param latitude
     * @param longitude
     * @param distance
     * @return the String with the Json representation of the Parking List selected
     */
    @GET
    @Produces("application/json")
    @Path("/parking/search")
    public String searchParkings(@QueryParam("complete") Boolean complete,
                                 @QueryParam("date") String date,
                                 @QueryParam("latitude") Double latitude,
                                 @QueryParam("longitude") Double longitude,
                                 @QueryParam("distance") Double distance) {
        try {
            return gson.toJson(ParkingCentral.getInstance().searchParkings(complete, date, latitude,
                    longitude, distance));
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }


    /**
     * Service GET to free a place in the Parking for the specified id.
     *
     * @param parkingId the id for the Parking selected
     * @return the String of a JsonObject with the number of free places in the Parking
     */
    @GET
    @Produces("application/json")
    @Path("/parking/{parkingId}/releasePlace")
    public String releasePlace(@PathParam("parkingId") Integer parkingId) {
        try {
            JsonObject response = new JsonObject();
            response.addProperty("freePlaces", ParkingCentral.getInstance().releasePlace(parkingId));
            return response.toString();
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }

    /**
     * Service GET to fill a place in the Parking for the specified id.
     *
     * @param parkingId the id for the Parking selected
     * @return the String of a JsonObject with the number of free places in the Parking
     */
    @GET
    @Produces("application/json")
    @Path("/parking/{parkingId}/takePlace")
    public String takePlace(@PathParam("parkingId") Integer parkingId) {
        try {
            JsonObject response = new JsonObject();
            response.addProperty("freePlaces", ParkingCentral.getInstance().takePlace(parkingId));
            return response.toString();
        } catch (ParkingException ex) {
            return gson.toJson(ex);
        } catch (Exception ex) {
            return gson.toJson(new ParkingException(ex));
        }
    }
}
