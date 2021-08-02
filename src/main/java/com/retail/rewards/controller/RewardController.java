package com.retail.rewards.controller;

import com.retail.rewards.CSVHelper;
import com.retail.rewards.modal.ConsumerPoints;
import com.retail.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/consumerTransactions")
public class RewardController {

    @Autowired
    private RewardsService rewardsService;

    @PostMapping("/upload")
    public ResponseEntity uploadNewData(@RequestParam("file") MultipartFile file) {

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                rewardsService.save(file);
                return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + file.getOriginalFilename());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a csv file!");
    }

    @GetMapping("/consumerPoints")
    public ResponseEntity getConsumerPointsForAllConsumers() {

        List<ConsumerPoints> consumerPoints =  rewardsService.getConsumerPointsForLastThreeMonths();
        return ResponseEntity.status(HttpStatus.OK).body(consumerPoints);

    }
}
