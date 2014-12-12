package com.christancho.parking;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Singleton Class used as BackEnd where store all the Parkings for the app
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public class ParkingCentral implements Serializable {

    /**
     * Singleton instance
     */
    public volatile static ParkingCentral instance;

    /**
     * Map with all the Parkings, the key is the ParkingId
     */
    private HashMap<Integer, Parking> parkingMap;

    /**
     * Creates a new instance of Parking
     */
    public ParkingCentral() {
        this.parkingMap = new HashMap<Integer, Parking>();
        initialize();
    }


    /**
     * Obtain the instance for the singleton
     *
     * @return the instance initialized
     */
    public static ParkingCentral getInstance() {
        if (instance == null) {
            synchronized (ParkingCentral.class) {
                if (instance == null) {
                    instance = new ParkingCentral();
                }
            }
        }
        return instance;
    }

    /**
     * Method that fills the Map with 3 Parkings, for testing propose
     */
    private void initialize() {
        ArrayList<WeekDay> days = new ArrayList<WeekDay>();
        for (WeekDay daysArray : WeekDay.values()) {
            days.add(daysArray);
        }
        Parking parking1 = new Parking(null, "Parking Total Free", 9, 18, 50, 0, (List) days.clone(), 41.3808,
                2.1676);
        Parking parking3 = new Parking(null, "Parking Full", 8, 23, 50, 50, (List) days.clone(), 41.39, 2.17);
        days.remove(6);
        days.remove(0);
        Parking parking2 = new Parking(null, "Parking Half Free", 7, 20, 50, 25, (List) days.clone(), 41.3833,
                2.1688);
        addParking(parking1);
        addParking(parking2);
        addParking(parking3);
    }

    /**
     * Generate a new unique Id
     *
     * @return a valid Id
     */
    private int getNewParkingID() {
        int id = parkingMap.size();
        while (parkingMap.get(id) != null) {
            Random random = new Random();
            id = random.nextInt();
        }
        return id;
    }

    /**
     * Gets the Parking for the specified id from the Map.
     *
     * @param id the id for the Parking selected
     * @return the Parking selected
     */
    public Parking getParking(Integer id) {
        if (id == null) {
            throw new ParkingException(ParkingException.CODE_INVALID_ID,
                    ParkingException.DESC_INVALID_ID,
                    "The ID can't be null");
        }
        Parking parking = parkingMap.get(id);
        if (parking == null) {
            throw new ParkingException(ParkingException.CODE_NOT_FOUND,
                    ParkingException.DESC_NOT_FOUND,
                    "Parking id='" + id + "' Not Found");
        }
        return parking;
    }

    /**
     * Adds a new Parking to the Map
     *
     * @param parking the new parking to add
     * @return the id for the new parking
     */
    public int addParking(Parking parking) {
        if (parking == null)
            throw new ParkingException(ParkingException.CODE_INVALID_VALUE,
                    ParkingException.DESC_INVALID_VALUE,
                    "The Parking can't be null");
        if (parking.getId() != null) {
            throw new ParkingException(ParkingException.CODE_INVALID_ID,
                    ParkingException.DESC_INVALID_ID,
                    "The ID has to be null");
        }
        if (parking.getName() == null) {
            throw new ParkingException(ParkingException.CODE_INVALID_VALUE,
                    ParkingException.DESC_INVALID_VALUE,
                    "The Name can't be null");
        }
        parking.setId(getNewParkingID());
        parkingMap.put(parking.getId(), parking);
        return parking.getId();
    }

    /**
     * Edits a parking in the Map
     *
     * @param parking the new values to edit
     * @return the parking edited
     */
    public Parking editParking(Parking parking) {
        Parking oldParking = getParking(parking.getId());
        oldParking.copyParkingValues(parking);
        return oldParking;
    }

    /**
     * Removes the Parking for the specified id from the Map.
     *
     * @param id the id for the Parking to remove
     * @return the Parking removed
     */
    public Parking removeParking(Integer id) {
        if (id == null) {
            throw new ParkingException(ParkingException.CODE_INVALID_ID,
                    ParkingException.DESC_INVALID_ID,
                    "The ID can't be null");
        }
        Parking parking = parkingMap.remove(id);
        if (parking == null) {
            throw new ParkingException(ParkingException.CODE_NOT_FOUND,
                    ParkingException.DESC_NOT_FOUND,
                    "Parking id='" + id + "' Not Found");
        }
        return parking;
    }

    /**
     * Fill a place in the Parking for the specified id.
     *
     * @param id the id for the Parking to fill the place
     * @return number of free places in the Parking
     */
    public int takePlace(Integer id) {
        Parking parking = getParking(id);
        parking.takePlace();
        return parking.getFreePlaces();
    }

    /**
     * Free a place in the Parking for the specified id.
     *
     * @param id the id for the Parking to free the place
     * @return number of free places in the Parking
     */
    public int releasePlace(Integer id) {
        Parking parking = getParking(id);
        parking.releasePlace();
        return parking.getFreePlaces();
    }

    /**
     * Gets a list of parkings in the Map, filter by multiple conditions. If all the conditions are null,
     * the method returns all the Parkings in the map.
     *
     * @param full      boolean that filter the Parkings with free places
     * @param date      String in the format DD.MM.YYYY#hh, that filter the Parking closed at this Date
     * @param latitude  latitude coordinate for a radius, that filter all the Parkings outside of it
     * @param longitude longitude coordinate for a radius, that filter all the Parkings outside of it
     * @param distance  distance in kilometers for a radius, that filter all the Parkings outside of it
     * @return the list with the Parking filters
     */
    public List<Parking> searchParkings(Boolean full, String date, Double latitude, Double longitude,
                                        Double distance) {
        List<Parking> parkingList = new ArrayList<Parking>();
        for (Parking parking : parkingMap.values()) {
            if (full != null) {
                if ((full && !parking.isFull()) || (!full && parking.isFull()))
                    continue;
            }
            if (date != null) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy#HH");
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateFormat.parse(date));
                    if (!parking.isOpenInDay(cal) || !parking.isOpenInHour(cal.get(Calendar.HOUR_OF_DAY)))
                        continue;
                } catch (Exception e) {
                    throw new ParkingException(e, ParkingException.CODE_FORMAT_DATE,
                            "Error in the format for the Date");
                }
            }
            if (latitude != null && longitude != null) {
                if (distance == null)
                    distance = 0.0;
                if (!parking.isInsideRadius(latitude, longitude, distance))
                    continue;
            } else if (latitude == null && longitude != null) {
                throw new ParkingException(ParkingException.CODE_INVALID_VALUE,
                        ParkingException.DESC_INVALID_VALUE,
                        "Invalid latitude parameter");
            } else if (latitude != null && longitude == null) {
                throw new ParkingException(ParkingException.CODE_INVALID_VALUE,
                        ParkingException.DESC_INVALID_VALUE,
                        "Invalid longitude parameter");
            }
            parkingList.add(parking);
        }
        return parkingList;
    }

}
