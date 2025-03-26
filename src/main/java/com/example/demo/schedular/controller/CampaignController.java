package com.example.demo.schedular.controller;

import com.example.demo.schedular.model.Campaign;
import com.example.demo.schedular.repository.ProductRepository;
import com.example.demo.schedular.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("campaign")
public class CampaignController {
    @Autowired
    CampaignService campaignService;

    @Autowired
    ProductRepository repository;

    @PostMapping("create")
    public ResponseEntity<?> addCampaign(@RequestBody Campaign campaign) {
        try {
            Campaign savedCampaign = campaignService.save(campaign);
            return new ResponseEntity<>(savedCampaign, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add campaign", HttpStatus.BAD_REQUEST);
        }
    }
}
