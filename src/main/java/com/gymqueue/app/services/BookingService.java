package com.gymqueue.app.services;


import com.gymqueue.app.entities.Booking;
import com.gymqueue.app.entities.BookingStatus;
import com.gymqueue.app.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(Long id, Booking booking){
        booking.setId(id);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }

    public Booking completeBooking(Long id, Booking booking){
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByEquipment(Long equipmentId){
        return bookingRepository.findByEquipmentId(equipmentId);
    }

    public List<Booking> getBookingsByStatus(BookingStatus status){
        return bookingRepository.findByStatus(status);
    }
}
