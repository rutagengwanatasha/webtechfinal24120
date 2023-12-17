package com.aircondition.air.Repository;

import com.aircondition.air.Model.AirCon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirRepository extends JpaRepository<AirCon, Long> {


}
