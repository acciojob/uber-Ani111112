package com.driver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.driver.model.Driver;
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer>{
    @Modifying
    @Query("delete from driver d where d.driver_id = :id")
    void deleteId(@Param("id") int id);
}
