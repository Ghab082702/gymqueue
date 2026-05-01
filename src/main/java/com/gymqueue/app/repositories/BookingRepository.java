package com.gymqueue.app.repositories;


import com.gymqueue.app.entities.Booking;
import com.gymqueue.app.entities.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.equipmentId = :equipmentId")
    List<Booking> findByEquipmentId(Long equipmentId);

    @Query("SELECT b FROM Booking b WHERE b.status = :status")
    List<Booking> findByStatus(BookingStatus status);
}
