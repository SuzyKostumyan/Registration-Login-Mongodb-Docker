/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.app.adminmax.repositories;

import com.project.app.adminmax.domain.CustomLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author suzy
 */
@Transactional
@Repository
public interface CustomLogRepository extends MongoRepository<CustomLog, String> {

    // Page<CustomLog> findAllByOrderByDateDesc(Pageable pageable);
    
    Page<CustomLog> findAll(Pageable pageable);
    
    List<CustomLog> findTop10ByOrderByDateDesc();

}
