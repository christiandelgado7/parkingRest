package com.christancho.parking;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Class used to parsing the Parking class into a JsonObject
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public class ParkingSerializer implements JsonSerializer<Parking>, JsonDeserializer<Parking> {


    @Override
    public JsonElement serialize(Parking parking, Type type, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", parking.getId());
        jsonObject.addProperty("name", parking.getName());
        jsonObject.addProperty("openHour", parking.getOpenHour());
        jsonObject.addProperty("closeHour", parking.getCloseHour());
        jsonObject.addProperty("totalPlaces", parking.getTotalPlaces());
        jsonObject.addProperty("freePlaces", parking.getFreePlaces());
        jsonObject.addProperty("latitude", parking.getLatitude());
        jsonObject.addProperty("longitude", parking.getLongitude());
        final JsonArray daysJson = new JsonArray();
        if (parking.getDaysOpen() != null) {
            for (WeekDay weekDay : parking.getDaysOpen()) {
                daysJson.add(new JsonPrimitive(weekDay + ""));
            }
        }
        jsonObject.add("daysOpen", daysJson);
        return jsonObject;
    }

    @Override
    public Parking deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        final Parking parking = new Parking();
        final JsonObject jsonObject;
        try {
            jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get("id") != null && !jsonObject.get("id").isJsonNull())
                parking.setId(jsonObject.get("id").getAsInt());
            if (jsonObject.get("name") != null && !jsonObject.get("name").isJsonNull())
                parking.setName(jsonObject.get("name").getAsString());
            if (jsonObject.get("openHour") != null && !jsonObject.get("openHour").isJsonNull())
                parking.setOpenHour(jsonObject.get("openHour").getAsInt());
            if (jsonObject.get("closeHour") != null && !jsonObject.get("closeHour").isJsonNull())
                parking.setCloseHour(jsonObject.get("closeHour").getAsInt());
            if (jsonObject.get("totalPlaces") != null && !jsonObject.get("totalPlaces").isJsonNull())
                parking.setTotalPlaces(jsonObject.get("totalPlaces").getAsInt());
            if (jsonObject.get("freePlaces") != null && !jsonObject.get("freePlaces").isJsonNull())
                parking.setFreePlaces(jsonObject.get("freePlaces").getAsInt());
            if (jsonObject.get("latitude") != null && !jsonObject.get("latitude").isJsonNull())
                parking.setLatitude(jsonObject.get("latitude").getAsDouble());
            if (jsonObject.get("longitude") != null && !jsonObject.get("longitude").isJsonNull())
                parking.setLongitude(jsonObject.get("longitude").getAsDouble());
        } catch (Exception ex) {
            throw new ParkingException(ex, ParkingException.CODE_PARSING_JSON,
                    "Error Parsing Parking JsonObject");
        }
        if (jsonObject.get("daysOpen") != null && !jsonObject.get("daysOpen").isJsonNull()) {
            JsonArray daysJson = jsonObject.get("daysOpen").getAsJsonArray();
            for (JsonElement element : daysJson) {
                try {
                    if (element != null && !element.isJsonNull())
                        parking.addDay(WeekDay.valueOf(element.getAsString().toUpperCase()));
                } catch (IllegalArgumentException ex) {
                    throw new ParkingException(ParkingException.CODE_PARSING_JSON,
                            ParkingException.DESC_INVALID_VALUE,
                            "Invalid Week Day '" + element.getAsString() + "'");
                }
            }
        } else {
            parking.setDaysOpen(null);
        }
        return parking;
    }
}
