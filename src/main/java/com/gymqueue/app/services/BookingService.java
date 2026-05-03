package com.gymqueue.app.services;


import com.gymqueue.app.entities.Booking;
import com.gymqueue.app.entities.BookingStatus;
import com.gymqueue.app.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking){
        // 1. Check if user already has QUEUED or ACTIVE booking
        List<Booking> existing = bookingRepository.findExistingBookingByUserAndEquipment(
                booking.getUserName(),
                booking.getEquipmentId()
        );
        if (!existing.isEmpty()){
            throw new IllegalArgumentException("User already has an active or queued booking for this equipment");
        }
        // 2. Get max queue position for this equipment
        Integer maxPosition = bookingRepository.findMaxQueuePositionByEquipment(booking.getEquipmentId());
        Integer newPosition = (maxPosition == null) ? 1: maxPosition + 1;

        // 3. Set booking details
        booking.setQueuePosition(newPosition);
        booking.setStatus(BookingStatus.QUEUED);
        booking.setBookedAt(LocalDateTime.now());

        //4. Save and return
        return bookingRepository.save(booking);
    }

    public Booking completeBooking(Long id) {
        // 1. Find the booking, if not found throw exception
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        // 2. Mark Booking as DONE
        booking.setStatus(BookingStatus.DONE);
        booking.setCompletedAt(LocalDateTime.now());

        // 3. Save the completed booking
        Booking completedBooking = bookingRepository.save(booking);

        // 4. Find new QUEUE booking for this equipment (ordered by position)
        List<Booking> nextQueue = bookingRepository.findBookingsByEquipmentAndStatus(
                booking.getEquipmentId(),
                BookingStatus.QUEUED
        );

        // 5. If there's a next person in queue, activate them
        if (!nextQueue.isEmpty()) {
            Booking nextBooking = nextQueue.getFirst();
            nextBooking.setStatus(BookingStatus.ACTIVE);
            nextBooking.setStartedAt(LocalDateTime.now());
            bookingRepository.save(nextBooking);
        }
        return completedBooking;
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


    public List<Booking> getBookingsByEquipment(Long equipmentId){
        return bookingRepository.findByEquipmentId(equipmentId);
    }

    public List<Booking> getBookingsByStatus(BookingStatus status){
        return bookingRepository.findByStatus(status);
    }


}
