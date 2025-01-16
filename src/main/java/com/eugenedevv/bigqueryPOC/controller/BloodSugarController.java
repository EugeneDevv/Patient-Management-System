package com.eugenedevv.bigqueryPOC.controller;

import com.eugenedevv.bigqueryPOC.model.BloodSugar;
import com.eugenedevv.bigqueryPOC.service.BloodSugarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/blood-sugar")
public class BloodSugarController {

    @Autowired
    private BloodSugarService bloodSugarService;

    private static final Logger logger = LoggerFactory.getLogger(BloodSugarController.class);

    @PostMapping
    public ResponseEntity<Void> createBloodSugarRecord(@RequestBody BloodSugar bloodSugar) {
        try {
            bloodSugarService.saveBloodSugar(bloodSugar);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Error creating blood sugar record", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BloodSugar>> getAllBloodSugarRecords() {
        try {
            List<BloodSugar> bloodSugarRecords = bloodSugarService.getAllBloodSugarRecords();
            return ResponseEntity.ok(bloodSugarRecords);
        } catch (Exception e) {
            logger.error("Error retrieving blood sugar records", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
