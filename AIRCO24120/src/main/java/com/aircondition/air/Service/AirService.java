package com.aircondition.air.Service;

import com.aircondition.air.Model.AirCon;
import com.aircondition.air.Repository.AirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirService {


    @Autowired
    AirRepository airRepository;

    public void save(AirCon refrigeration) {
        airRepository.save(refrigeration);
    }

    public List<AirCon> listAll() {
        return airRepository.findAll();
    }

    public Optional<AirCon> findClientById(Long id) {
        return airRepository.findById(id);
    }

}
