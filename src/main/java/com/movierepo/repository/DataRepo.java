package com.movierepo.repository;

import com.movierepo.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.List;

@Repository
public interface DataRepo extends JpaRepository<Data,Integer> {
    Optional<Data> findByDataid(int dataid);
    List<Data> findByUserUsername(String username);

    List<Data> findByUserUsernameAndWatchedonGreaterThanAndWatchedonLessThan(String username, Date startDate, Date endDate);
}
