package com.sample.springboot.station.controller;

import com.sample.springboot.station.exception.StationNotFoundException;
import com.sample.springboot.station.model.Station;
import com.sample.springboot.station.repository.StationRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Create Station",
            nickname = "CreateStation",
            notes = "Creates the station record in the system")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station Id of the created record") })
    @PostMapping("/create")
    public String create(@RequestBody Station station){
         repository.save(station);
        return station.getStationId();
    }

    @ApiOperation(value = "Update Station",
            nickname = "UpdateStation",
            notes = "Update the existing station record in the system. Return 404 if record not found")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station Id of the updated record") })
    @PostMapping("/update")
    public String update(@RequestBody Station station){
        Optional<Station> optionalStation = repository.findById(station.getStationId());
        if(optionalStation.isPresent()){
            repository.save(station);
        }else{
            throw new StationNotFoundException("id="+station.getStationId());
        }

        return station.getStationId();
    }

    @ApiOperation(value = "Remove Station",
            nickname = "RemoveStation",
            notes = "Remove the existing station identified by stationId parameter. Return 404 if record not found")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Station Id of the deleted record") })
    @DeleteMapping("/remove")
    public String remove(
            @ApiParam(value = "Station identifier", required = true)
            @RequestParam(value = "stationId") String stationId){
        Optional<Station> optionalStation = repository.findById(stationId);
        if(optionalStation.isPresent()){
            repository.delete(optionalStation.get());
        }else{
            throw new StationNotFoundException("id="+stationId);
        }
        return stationId;
    }

    @ApiOperation(value = "Find Station By station id",
            nickname = "FindStationByID",
            notes = "Finds the existing station identified by stationId parameter. Return 404 if record not found")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the station record") })
    @GetMapping("/findById")
    public Station findStationById(
            @ApiParam(value = "Station identifier", required = true)
            @RequestParam(value = "stationId") String stationId){
        Optional<Station> optionalStation = repository.findById(stationId);
        if(optionalStation.isPresent()){
             return  optionalStation.get();
        }else{
            throw new StationNotFoundException("id="+stationId);
        }
     }

    @ApiOperation(value = "Find Stations By name",
            nickname = "FindStationsByName",
            notes = "Finds the existing stations matching the name parameter.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the matching station records") })
    @GetMapping("/findByName")
    public List<Station> findStationsByName(
            @ApiParam(value = "Station name", required = true)
            @RequestParam(value = "name", required = true) String name){
        return repository.findByName(name);
    }

    @ApiOperation(value = "Find Station By HdEnabled",
            nickname = "FindStationsByHdEnabled",
            notes = "Finds the existing station matching the hd enabled parameter.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Return the matching station records") })
    @GetMapping("/findHdEnabled")
    public List<Station> findHdEnabledStations(
            @ApiParam(value = "Hd Enabled flag", required = true)
            @RequestParam(value = "enabled") Boolean hdEnabled){
        return repository.findByHdEnabled(hdEnabled);
    }

}
