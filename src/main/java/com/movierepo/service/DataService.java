package com.movierepo.service;

import com.movierepo.config.JwtService;
import com.movierepo.entity.Data;
import com.movierepo.entity.User;
import com.movierepo.repository.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {
    @Autowired private DataRepo datarepo;
    @Autowired private JwtService jwtService;

    @Transactional
    public ResponseEntity<String> addData(String name, String description, Timestamp watchedon, float rating, String type, MultipartFile photo, String trailer, String Authorization) throws IOException {
        String token = Authorization.substring("Bearer ".length());

        // Create a new Data instance for each request
        Data data = new Data();

        // Set user information
        User user = new User();
        user.setUsername(jwtService.extractUsername(token));
        data.setUser(user);

        // Set other data fields
        data.setName(name);
        data.setDescription(description);
        data.setWatchedon(watchedon);
        data.setRating(rating);
        data.setType(type);


        if(trailer != null && !trailer.isEmpty())
        {
            data.setTrailer(trailer);
        }

        if (photo != null && !photo.isEmpty()) {
            byte[] bytes = photo.getBytes();
            data.setPhoto(bytes);
        }
        datarepo.save(data);

        return ResponseEntity.ok("Data added successfully!!");
    }

    @Cacheable(cacheNames = "datacache", key = "#dataid")
    public ResponseEntity<Data> getDataById(int dataid) {
        System.out.println("Getting data from data repository !!");
        Optional<Data> existingData = datarepo.findByDataid(dataid);
        if (existingData.isPresent()) {
            return ResponseEntity.ok(existingData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @CachePut(cacheNames = "datacache", key = "#dataid")
    public ResponseEntity<Data> findByDataidAndUpdate(int dataid,
                                                      String name,
                                                      String description,
                                                      Timestamp watchedon,
                                                      float rating,
                                                      String type,
                                                      MultipartFile photo,
                                                      String trailer,
                                                      String user) throws IOException {

        Optional<Data> optionalEntity = datarepo.findByDataid(dataid);

        if (optionalEntity.isPresent()) {
            Data existingEntity = optionalEntity.get();

            // Update the fields of the existing entity with values from the updated entity
            existingEntity.setName(name);
            existingEntity.setDescription(description);
            existingEntity.setWatchedon(watchedon);
            existingEntity.setRating(rating);
            existingEntity.setType(type);

            if (trailer != null && !trailer.isEmpty()) {
                existingEntity.setTrailer(trailer);
            } else {
                if (existingEntity.getTrailer() != null && !existingEntity.getTrailer().isEmpty()) {
                    existingEntity.setTrailer(null);
                }
            }
            if (photo != null && !photo.isEmpty()) {
                byte[] bytes = photo.getBytes();
                existingEntity.setPhoto(bytes);
            } else {
                if (existingEntity.getPhoto() != null) {
                    existingEntity.setPhoto(null);
                }
            }

            // Save the updated entity
            Data savedEntity = datarepo.save(existingEntity);

            return ResponseEntity.ok(savedEntity);
        } else {
            // Handle the case where the entity with the given ID is not found
            return ResponseEntity.notFound().build();
        }
    }


    @CacheEvict(cacheNames = "datacache", key = "#dataid")
    public ResponseEntity<Data> deleteData(int dataid) {
        Optional<Data> optionalEntity = datarepo.findByDataid(dataid);
        if (optionalEntity.isPresent()) {
            Data deletedData = optionalEntity.get();
            datarepo.deleteById(dataid);
            return ResponseEntity.ok(deletedData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public List<Data> getAllDataByUser(String Authorization) {
        String token = Authorization.substring("Bearer ".length());
        return datarepo.findByUserUsername(jwtService.extractUsername(token));
    }

    public List<Data> filterData(String Authorization, Date startDate, Date endDate) {
        String token = Authorization.substring("Bearer ".length());
        String username = jwtService.extractUsername(token);
        return datarepo.findByUserUsernameAndWatchedonGreaterThanAndWatchedonLessThan(username,startDate,endDate);
    }
}
