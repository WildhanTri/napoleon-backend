package com.example.napoleon.util;

import java.util.Date;

import org.springframework.data.domain.Sort;

public class AppUtil {
    public static String tokenCutter(String token) {
        return token.replace("Bearer ", token);
    }

    public static String randomOtp() {
        return String.valueOf(Math.round(Math.random() * 1000000));
    }

    public static Boolean isExpired(Date date) {
        return date.before(new Date());
    }

    public static Date addMinutes(Date date, int minutes) {
        date.setTime(date.getTime() + (minutes * 60 * 1000));
        return date;
    }

    public static Sort sortType(String by, String type) {
        Sort sort = Sort.by(by == null ? "id" : by);
        if(type.equals("asc")){
            return sort.ascending();
        } else if(type.equals("desc")) {
            return sort.descending();
        } else {
            return sort.ascending();
        }
    }
}
