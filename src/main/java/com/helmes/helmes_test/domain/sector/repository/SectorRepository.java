package com.helmes.helmes_test.domain.sector.repository;

import com.helmes.helmes_test.domain.sector.model.Sector;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    List<Sector> findAllByParent(Sector parent);
}
