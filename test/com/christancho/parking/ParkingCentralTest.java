package com.christancho.parking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Class used to Test the ParkingCentral Methods
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public class ParkingCentralTest {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Parking.class, new ParkingSerializer())
            .create();
    ParkingCentral parkingCentral;

    @Before
    public void setUp() throws Exception {
        parkingCentral = ParkingCentral.getInstance();
    }

    @Test
    public void testAddParking() throws Exception {
        Parking parking = new Parking(null, "Parking Total Free", 9, 18, 50, 0, null, 41.3808,
                2.1676);
        int id = parkingCentral.addParking(parking);
        parking = parkingCentral.getParking(id);
        System.out.println("parking = " + gson.toJson(parking));
    }

    @Test
    public void testEditParking() throws Exception {
        Parking parking = parkingCentral.getParking(1);
        System.out.println("parking = " + gson.toJson(parking));
        parking.setName("new Name");
        parking = parkingCentral.editParking(parking);
        System.out.println("newParking = " + gson.toJson(parking));
    }


    @Test
    public void testGetParking() throws Exception {
        Parking parking = parkingCentral.getParking(0);
        System.out.println("parking = " + gson.toJson(parking));
        Parking parking2 = parkingCentral.getParking(2);
        System.out.println("parking = " + gson.toJson(parking2));
        Parking parking1 = parkingCentral.getParking(1);
        System.out.println("parking = " + gson.toJson(parking1));
        System.out.println("distance = " + parking2.isInsideRadius(parking.getLatitude(),
                parking.getLongitude(), 1.043));
        System.out.println("distance = " + parking1.isInsideRadius(parking.getLatitude(),
                parking.getLongitude(), 0.296));
        System.out.println("distance = " + parking1.isInsideRadius(parking2.getLatitude(),
                parking2.getLongitude(), 0.752));

    }

    @Test
    public void testSearchParkings() throws Exception {
        List<Parking> parkingList;
        parkingList = parkingCentral.searchParkings(null, null, null, null, null);
        System.out.println("parking all= " + gson.toJson(parkingList));
        parkingList = parkingCentral.searchParkings(true, null, null, null, null);
        System.out.println("parking complete= " + gson.toJson(parkingList));
        parkingList = parkingCentral.searchParkings(false, null, null, null, null);
        System.out.println("parking free= " + gson.toJson(parkingList));
        parkingList = parkingCentral.searchParkings(null, "07.12.2014#20", null, null, null);
        System.out.println("parking date= " + gson.toJson(parkingList));
        parkingList = parkingCentral.searchParkings(null, null, 41.39, 2.17, null);
        System.out.println("parking Location= " + gson.toJson(parkingList));
        parkingList = parkingCentral.searchParkings(null, null, 41.385, 2.165, 0.6);
        System.out.println("parking Location.distance= " + gson.toJson(parkingList));
        try {
            parkingList = parkingCentral.searchParkings(null, "07.12.2014.20", null, null, null);
            System.out.println("parking wrong date= " + gson.toJson(parkingList));
        } catch (Exception ex) {
            JsonObject exceptionJson = gson.toJsonTree(ex).getAsJsonObject();
            exceptionJson.remove("stackTrace");
            System.out.println("Exception= " + gson.toJson(exceptionJson));
        }

    }
}