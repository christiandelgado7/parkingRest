package com.christancho.parking;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Exception used in all the Parking Application.
 * <p/>
 * Here can find several Error Codes, to easily indicate what kind of problem we are having
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public class ParkingException extends RuntimeException implements Serializable {


    //Error Codes
    public static String CODE_UNKNOWN = "00";
    public static String CODE_SERVER_HTTP = "01";
    public static String CODE_PARSING_JSON = "02";
    public static String CODE_INVALID_ID = "03";
    public static String CODE_NOT_FOUND = "04";
    public static String CODE_FORMAT_DATE = "05";
    public static String CODE_INVALID_VALUE = "06";
    public static String CODE_INVALID_ACTION = "07";
    //Error Messages
    public static String DESC_INVALID_ID = "Error in the parkingId";
    public static String DESC_NOT_FOUND = "Parking Not Found";
    public static String DESC_INVALID_VALUE = "Invalid Value";
    public static String DESC_INVALID_ACTION = "Invalid Action";
    /**
     * Code that indicate the type of Error
     */
    private String code;
    /**
     * Message related to the error
     */
    private String description;

    /**
     * Creates a new instance of ParkingException
     *
     * @param code
     * @param description
     * @param detailMessage
     */
    public ParkingException(String code, String description, String detailMessage) {
        super(detailMessage);
        this.code = code;
        this.description = description;
    }

    /**
     * Creates a new instance of ParkingException from an existed Exception
     *
     * @param ex
     */
    public ParkingException(Exception ex) {
        super(ex.getMessage(), ex.getCause());
        this.setStackTrace(ex.getStackTrace());
        this.code = CODE_UNKNOWN;
        this.description = ex.getClass() + "";
    }

    /**
     * Creates a new instance of ParkingException from an existed Exception
     *
     * @param ex
     * @param code
     * @param description
     */
    public ParkingException(Exception ex, String code, String description) {
        super(ex.getMessage(), ex.getCause());
        this.setStackTrace(ex.getStackTrace());
        this.code = code;
        this.description = description;
    }

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ParkingException: [" + this.code + "]: " +
                (this.description.equals(this.getMessage()) ? this.description
                        : this.description + ": " + this.getMessage());
    }

    /**
     * Class used to parsing the ParkingException class into a JsonObject
     */
    public static class ParkingExceptionSerializer implements JsonSerializer<ParkingException> {
        @Override
        public JsonElement serialize(ParkingException exception, Type type,
                                     JsonSerializationContext context) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code", exception.getCode());
            if (exception.getMessage() != null)
                jsonObject.addProperty("description", exception.getDescription().replaceAll("\"", "'"));
            if (exception.getMessage() != null)
                jsonObject.addProperty("detailMessage", exception.getMessage().replaceAll("\"", "'"));
            return jsonObject;
        }
    }
}
