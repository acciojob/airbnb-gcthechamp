package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {


    HotelManagementRepository repositoryObj = new HotelManagementRepository();

    public String addHotel(Hotel hotel)
    {
        String isHotelAdded = repositoryObj.addHotel(hotel);
        return isHotelAdded;
    }

    public Integer addUser(User user)
    {
        int aadharCardNo = repositoryObj.addUser(user);
        return aadharCardNo;
    }

    public String getHotelWithMostFacilities() {
        return repositoryObj.getHotelWithMostFacilities();

    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        booking.setBookingId(uuid.toString());
        return repositoryObj.bookARoom(booking);
    }

    public int getBookings(int aadharCard) {
        return repositoryObj.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return repositoryObj.updateFacilities(newFacilities, hotelName);
    }
}
