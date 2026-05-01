package com.gymqueue.app.services;


import com.gymqueue.app.entities.Equipment;
import com.gymqueue.app.repositories.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public Equipment createEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }
    
    public List<Equipment> getAllEquipment(){
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getEquipmentById(Long id){
        return equipmentRepository.findById(id);
    }

    public Equipment updateEquipment(Long id, Equipment equipment){
        equipment.setId(id);
        return equipmentRepository.save(equipment);
    }

    public void deleteEquipment(Long id){
        equipmentRepository.deleteById(id);
    }
}
