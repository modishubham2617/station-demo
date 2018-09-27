package com.sample.springboot.station.repository;

import com.sample.springboot.station.model.Station;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StationRepository extends CrudRepository<Station,String> {

    /**
     * Find Station - based on the hdEnabled property.
     * @param hdEnabled Indicates whether station is hdEnabled.
     * @return Collection of stations matching the hdEnabled property
     */
    List<Station> findByHdEnabled(Boolean hdEnabled);

    /**
     * Find Station - based on the name property
     * @param name Holds the filter value for name property
     * @return Collection of stations matching the name
     */
    List<Station> findByName(String name);


}
