package com.btasdemir.loganalyzer.repository;

import com.btasdemir.loganalyzer.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
