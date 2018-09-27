package com.sample.springboot.station.controller;

import com.sample.springboot.station.model.Station;
import com.sample.springboot.station.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/station")
public class StationController {

    @Autowired
    private StationRepository repository;

    @PostMapping("/create")
    public String create(@RequestBody Station station){
        //Specific create code 20X
        repository.save(station);
        return station.getStationId();
    }

    @PostMapping("/update")
    public String update(@RequestBody Station station){
        //Record Not found 404
        Optional<Station> optionalStation = repository.findById(station.getStationId());
        if(optionalStation.isPresent()){
            repository.save(station);
        }else{
            //Record Not found
        }

        return station.getStationId();
    }

    @DeleteMapping("/remove")
    public Boolean remove(@RequestParam("stationId") String stationId){
        Optional<Station> optionalStation = repository.findById(stationId);
        if(optionalStation.isPresent()){
            repository.delete(optionalStation.get());
        }else{
            //Record Not found
        }
        return Boolean.TRUE;
    }

    @GetMapping("/findById")
    public Station findStationById(@RequestParam("stationId") String stationId){
        Optional<Station> optionalStation = repository.findById(stationId);
        if(optionalStation.isPresent()){
             return  optionalStation.get();
        }else{
            //Record not found.
            return null;
        }
     }

    @GetMapping("/findByName")
    public List<Station> findStationsByName(@RequestParam("name") String name){
        return repository.findByName(name);
    }

    @GetMapping("/findHdEnabled")
    public List<Station> findHdEnabledStations(@NotNull @RequestParam("enabled") Boolean hdEnabled){
        return repository.findByHdEnabled(hdEnabled);
    }

}
