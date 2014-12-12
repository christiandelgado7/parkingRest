package com.christancho.parking;

import java.util.Calendar;

/**
 * Enum that represents all the Week Days
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12/10/14
 */
public enum WeekDay {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    /**
     * Obtains the valid WeekDay for a specified date
     *
     * @param calendar the day to evaluate
     * @return the WeekDay for the specified date
     */
    public static WeekDay getDayFromCalendar(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return MONDAY;
            case Calendar.TUESDAY:
                return TUESDAY;
            case Calendar.WEDNESDAY:
                return WEDNESDAY;
            case Calendar.THURSDAY:
                return THURSDAY;
            case Calendar.FRIDAY:
                return FRIDAY;
            case Calendar.SATURDAY:
                return SATURDAY;
            case Calendar.SUNDAY:
                return SUNDAY;
        }
        return null;
    }

}
