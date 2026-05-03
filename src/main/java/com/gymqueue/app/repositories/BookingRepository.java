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

    @Query("SELECT MAX(b.queuePosition) FROM Booking b WHERE b.equipmentId = :equipmentId")
    Integer findMaxQueuePositionByEquipment(Long equipmentId);

    @Query("SELECT b FROM Booking b WHERE b.equipmentId = :equipmentId AND b.status = :status ORDER BY b.queuePosition ASC")
    List<Booking> findBookingsByEquipmentAndStatus(Long equipmentId, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.equipmentId = :equipmentId AND b.status = 'ACTIVE'")
    List<Booking> findActiveBookingsByEquipment(Long equipmentId);

    @Query("SELECT b FROM Booking b WHERE b.userName = :userName AND b.equipmentId = :equipmentId AND b.status IN ('QUEUED', 'ACTIVE')")
    List<Booking> findExistingBookingByUserAndEquipment(String userName, Long equipmentId);
}
