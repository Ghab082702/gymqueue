package com.gymqueue.app.repositories;


import com.gymqueue.app.entities.Booking;
import com.gymqueue.app.entities.BookingStatus;
import com.gymqueue.app.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {




}
