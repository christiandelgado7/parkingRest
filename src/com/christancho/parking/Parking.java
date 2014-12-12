package com.christancho.parking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Class that represents the model Parking
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public class Parking implements Serializable {

    /**
     * Parking Id
     */
    private Integer id;
    /**
     * Parking name
     */
    private String name;
    /**
     * Hour of the day when the Parking open
     */
    private Integer openHour;
    /**
     * Hour of the day when the Parking close
     */
    private Integer closeHour;
    /**
     * Total number of cars that can occupy a place in the Parking
     */
    private Integer totalPlaces;
    /**
     * Actual number of free places in the Parking
     */
    private Integer freePlaces;
    /**
     * List with the Week days when the Parking is open
     */
    private List<WeekDay> daysOpen;
    /**
     * Latitude coordinate of the Parking.
     */
    private Double latitude;
    /**
     * Longitude coordinate of the Parking.
     */
    private Double longitude;


    /**
     * Creates a new instance of Parking
     */
    public Parking() {
        daysOpen = new ArrayList<WeekDay>();
    }

    /**
     * Creates a new instance of Parking
     *
     * @param id
     * @param name
     * @param openHour
     * @param closeHour
     * @param totalPlaces
     * @param freePlaces
     * @param daysOpen
     * @param latitude
     * @param longitude
     */
    public Parking(Integer id, String name, Integer openHour, Integer closeHour, Integer totalPlaces,
                   Integer freePlaces, List<WeekDay> daysOpen, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.totalPlaces = totalPlaces;
        this.freePlaces = freePlaces;
        this.daysOpen = daysOpen;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Get the value of Id
     *
     * @return the value of Id
     */
    public Integer getId() {
        return id;
    }


    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of openHour
     *
     * @return the value of openHour
     */
    public Integer getOpenHour() {
        return openHour;
    }

    /**
     * Set the value of openHour
     *
     * @param openHour new value of openHour
     */
    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    /**
     * Get the value of closeHour
     *
     * @return the value of closeHour
     */
    public Integer getCloseHour() {
        return closeHour;
    }

    /**
     * Set the value of closeHour
     *
     * @param closeHour new value of closeHour
     */
    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }

    /**
     * Get the value of totalPlaces
     *
     * @return the value of totalPlaces
     */
    public Integer getTotalPlaces() {
        return totalPlaces;
    }

    /**
     * Set the value of totalPlaces
     *
     * @param totalPlaces new value of totalPlaces
     */
    public void setTotalPlaces(Integer totalPlaces) {
        this.totalPlaces = totalPlaces;
    }

    /**
     * Get the value of freePlaces
     *
     * @return the value of freePlaces
     */
    public Integer getFreePlaces() {
        return freePlaces;
    }

    /**
     * Set the value of freePlaces
     *
     * @param freePlaces new value of freePlaces
     */
    public void setFreePlaces(Integer freePlaces) {
        this.freePlaces = freePlaces;
    }

    /**
     * Get the value of daysOpen
     *
     * @return the value of daysOpen
     */
    public List<WeekDay> getDaysOpen() {
        return daysOpen;
    }

    /**
     * Set the value of daysOpen
     *
     * @param daysOpen new value of daysOpen
     */
    public void setDaysOpen(List<WeekDay> daysOpen) {
        this.daysOpen = daysOpen;
    }

    /**
     * add a new WeekDay to the DaysOpen list
     * @param weekDay day to add
     */
    public void addDay(WeekDay weekDay) {
        if (daysOpen == null && weekDay != null)
            daysOpen = new ArrayList<WeekDay>();
        if (weekDay != null && !daysOpen.contains(weekDay))
            daysOpen.add(weekDay);
    }

    /**
     * Get the value of latitude
     *
     * @return the value of latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Set the value of latitude
     *
     * @param latitude new value of latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the value of longitude
     *
     * @return the value of longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Set the value of longitude
     *
     * @param longitude new value of longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Ask if the Parking is full
     *
     * @return true if the parking is full
     */
    public boolean isFull() {
        return totalPlaces <= freePlaces;
    }

    /**
     * Ask if the Parking is open at a selected hour
     * @param hour hour to evaluate
     * @return true if the parking is open at the selected hour
     */
    public boolean isOpenInHour(int hour) {
        return openHour <= hour && hour < closeHour;
    }

    /**
     * Ask if the Parking is open at a selected Date
     * @param calendar calendaar to evaluate
     * @return true if the parking is open at the selected Date
     */
    public boolean isOpenInDay(Calendar calendar) {
        return daysOpen.contains(WeekDay.getDayFromCalendar(calendar));
    }

    /**
     * Fill one place
     */
    public void takePlace() {
        if (freePlaces < 1)
            throw new ParkingException(ParkingException.CODE_INVALID_ACTION,
                    ParkingException.DESC_INVALID_ACTION,
                    "The Parking is full");
        freePlaces--;
    }

    /**
     * Free one place
     */
    public void releasePlace() {
        if (freePlaces >= totalPlaces)
            throw new ParkingException(ParkingException.CODE_INVALID_ACTION,
                    ParkingException.DESC_INVALID_ACTION,
                    "The Parking is Empty");
        freePlaces++;
    }

    /**
     * Clone the values from the newParking, except the ID
     * @param newParking the Parking to copy
     */
    public void copyParkingValues(Parking newParking) {
        if (newParking.name != null)
            name = newParking.name;
        if (newParking.openHour != null)
            openHour = newParking.openHour;
        if (newParking.closeHour != null)
            closeHour = newParking.closeHour;
        if (newParking.totalPlaces != null)
            totalPlaces = newParking.totalPlaces;
        if (newParking.freePlaces != null)
            freePlaces.equals(newParking.freePlaces);
        if (newParking.daysOpen != null)
            daysOpen = newParking.daysOpen;
        if (newParking.latitude != null)
            latitude = newParking.latitude;
        if (newParking.longitude != null)
            longitude = newParking.longitude;
    }

    /**
     * Ask if the Parking is located in a selected radius
     * @param latCenter latitude coordinate for the center of the radius
     * @param lonCenter longitude coordinate for the center of the radius
     * @param radiusKms size for the radius in Kilometers
     * @return true if the parking is located in the selected Radius
     */
    public boolean isInsideRadius(double latCenter, double lonCenter, double radiusKms) {
        //Haversine Formula
        double radius = Math.acos(Math.sin(Math.toRadians(latCenter)) * Math.sin(Math.toRadians(latitude))
                + Math.cos(Math.toRadians(latCenter)) * Math.cos(Math.toRadians(latitude))
                * Math.cos(Math.toRadians(longitude) - Math.toRadians(lonCenter)));
        //earth radius in kilometers = 6373
        radius = radius * 6373;
        return (radius <= radiusKms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parking)) return false;

        Parking parking = (Parking) o;

        if (closeHour != null ? !closeHour.equals(parking.closeHour) : parking.closeHour != null)
            return false;
        if (daysOpen != null ? !daysOpen.equals(parking.daysOpen) : parking.daysOpen != null) return false;
        if (freePlaces != null ? !freePlaces.equals(parking.freePlaces) : parking.freePlaces != null)
            return false;
        if (!id.equals(parking.id)) return false;
        if (latitude != null ? !latitude.equals(parking.latitude) : parking.latitude != null) return false;
        if (longitude != null ? !longitude.equals(parking.longitude) : parking.longitude != null)
            return false;
        if (name != null ? !name.equals(parking.name) : parking.name != null) return false;
        if (openHour != null ? !openHour.equals(parking.openHour) : parking.openHour != null) return false;
        if (totalPlaces != null ? !totalPlaces.equals(parking.totalPlaces) : parking.totalPlaces != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (openHour != null ? openHour.hashCode() : 0);
        result = 31 * result + (closeHour != null ? closeHour.hashCode() : 0);
        result = 31 * result + (totalPlaces != null ? totalPlaces.hashCode() : 0);
        result = 31 * result + (freePlaces != null ? freePlaces.hashCode() : 0);
        result = 31 * result + (daysOpen != null ? daysOpen.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }
}
