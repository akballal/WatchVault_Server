package com.movierepo.service;

import com.movierepo.config.JwtService;
import com.movierepo.entity.Data;
import com.movierepo.entity.User;
import com.movierepo.repository.DataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepo datarepo;
    private final JwtService jwtService;
    private final User user;


    public ResponseEntity<String> addData(Data data,String Authorization) {
        String token = Authorization.substring("Bearer ".length());
        user.setUsername(jwtService.extractUsername(token));
        data.setUser(user);
        datarepo.save(data);
        return ResponseEntity.ok("Data added successfully!!");
    }

    public ResponseEntity<Optional<Data>> getDataById(int dataid) {
        Optional<Data> ExistingData = datarepo.findByDataid(dataid);
        if (ExistingData.isPresent()) {
            return ResponseEntity.ok(ExistingData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Optional<Data> findByDataidAndUpdate(Data updatedEntity) {
        int dataid = updatedEntity.getDataid();
        Optional<Data> optionalEntity = datarepo.findByDataid(dataid);

        if (optionalEntity.isPresent()) {
            Data existingEntity = optionalEntity.get();

            // Update the fields of the existing entity with values from the updated entity
            existingEntity.setName(updatedEntity.getName());
            existingEntity.setDescription(updatedEntity.getDescription());
            existingEntity.setWatchedon(updatedEntity.getWatchedon());
            existingEntity.setRating(updatedEntity.getRating());
            existingEntity.setType(updatedEntity.getType());


            // Save the updated entity
            datarepo.save(existingEntity);

            return Optional.of(existingEntity);
        } else {
            // Handle the case where the entity with the given ID is not found
            return Optional.empty();
        }
    }

    public ResponseEntity<String> deleteData(int dataid) {
        Optional<Data> optionalEntity = datarepo.findByDataid(dataid);
        if (optionalEntity.isPresent()) {
            datarepo.deleteById(dataid);
            return ResponseEntity.ok("Data deleted successfully!");
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
