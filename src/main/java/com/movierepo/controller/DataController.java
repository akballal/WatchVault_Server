package com.movierepo.controller;

import com.movierepo.entity.Data;
import com.movierepo.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private DataService dataservice;

    @PostMapping("/adddata")
    private ResponseEntity<String> addData(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("watchedon") Timestamp watchedon,
            @RequestParam("rating") float rating,
            @RequestParam("type") String type,
            @RequestPart(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "trailer", required = false) String trailer,
            @RequestHeader String Authorization) throws IOException {

        return dataservice.addData(name,description,watchedon,rating,type,photo,trailer,Authorization);
    }

    @GetMapping("/getdatabyid")
    private ResponseEntity<Data> getDataById(@RequestHeader("id") int dataid) {
        logger.info(String.valueOf(dataid));
        return dataservice.getDataById(dataid);
    }

    @PutMapping("/updatedata")
    public ResponseEntity<String> updateEntity(@RequestParam("dataid") int dataid,
                                               @RequestParam("name") String name,
                                               @RequestParam("description") String description,
                                               @RequestParam("watchedon") Timestamp watchedon,
                                               @RequestParam("rating") float rating,
                                               @RequestParam("type") String type,
                                               @RequestPart(value = "photo", required = false) MultipartFile photo,
                                               @RequestParam(value = "trailer", required = false) String trailer,
                                               @RequestParam(value = "username") String user) throws IOException {
        ResponseEntity<Data> updatedEntityResponse = dataservice.findByDataidAndUpdate(dataid, name, description, watchedon, rating, type, photo, trailer, user);

        if (updatedEntityResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Data updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/deletedata")
    public ResponseEntity<String> deleteEntity(@RequestHeader("id") int dataid) {
        ResponseEntity<Data> deleteEntityResponse = dataservice.deleteData(dataid);

        if (deleteEntityResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Data deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getalldata")
    public List<Data> getAllData(@RequestHeader String Authorization) {
        return dataservice.getAllDataByUser(Authorization);
    }

    @GetMapping("/filterdata")
    public List<Data> filterData(@RequestHeader String Authorization, @RequestHeader("startDateObject") Date startDate, @RequestHeader("endDateObject") Date endDate) {
        return dataservice.filterData(Authorization,startDate,endDate);
    }
}
