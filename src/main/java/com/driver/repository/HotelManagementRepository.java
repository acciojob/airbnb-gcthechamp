package com.driver.repository;

import com.driver.model.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    HashMap<String,Hotel> hotelDb = new HashMap<>();
    HashMap<Integer,User> userDb = new HashMap<>();

    HashMap<String, Booking> bookings = new HashMap<>();

    HashMap<Integer,List<Booking>> personBookings = new HashMap<>();
    public String addHotel(Hotel hotel)
    {
        if(hotel == null || hotel.getHotelName() == null || hotelDb.containsKey(hotel.getHotelName()))
        {
            return "FAILURE";
        }
        else
        {
            hotelDb.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
    }

    public int addUser(User user)
    {
        userDb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }


    public String getHotelWithMostFacilities() {
        int maxFacilities = 0;
        String maxFacilitiesHotel = "";
        TreeMap<String,Hotel> sorted = new TreeMap<>();
        sorted.putAll(hotelDb);

        for(String hotelName : sorted.keySet())
        {
            int numOfFacilities = hotelDb.get(hotelName).getFacilities().size();
            if(maxFacilities < numOfFacilities)
            {
                maxFacilities = numOfFacilities;
                maxFacilitiesHotel = hotelName;
            }
        }
        return maxFacilitiesHotel;
    }

    public int bookARoom(Booking booking) {

        String primaryKey = booking.getBookingId();
        bookings.put(primaryKey,booking);

        int availableRooms = hotelDb.get(booking.getHotelName()).getAvailableRooms();
        if(availableRooms < booking.getNoOfRooms())
            return -1;
        else
        {
            List<Booking> bookingsTillNow = personBookings.getOrDefault(booking.getBookingAadharCard(),new ArrayList<>());
            bookingsTillNow.add(booking);
            personBookings.put(booking.getBookingAadharCard(),bookingsTillNow);
            int priceToBePaid = booking.getNoOfRooms() * hotelDb.get(booking.getHotelName()).getPricePerNight();
            booking.setAmountToBePaid(priceToBePaid);
            return priceToBePaid;
        }

    }

    public int getBookings(int aadharCard) {
        List<Booking> bookingList = personBookings.get(aadharCard);
        return bookingList.size();
    }


    public boolean containsAlready(List<Facility> facilities, Facility newFacility)
    {
        for(Facility f : facilities)
        {
            if(f == newFacility)
                return true;
        }
        return false;
    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);
        List<Facility> availableFacilities = hotel.getFacilities();
        for(Facility f : newFacilities)
        {
            if(!containsAlready(availableFacilities, f))
                availableFacilities.add(f);
        }
        hotel.setFacilities(availableFacilities);
        return hotel;
    }
}
