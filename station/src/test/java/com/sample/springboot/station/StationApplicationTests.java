package com.sample.springboot.station;

import com.sample.springboot.station.model.Station;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;


import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"local.management.port=0"})
public class StationApplicationTests {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void contextLoads() {
	}

	private void createSampleRecord(Station station){
		ResponseEntity<String> entity = this.testRestTemplate.postForEntity(
				"http://localhost:" + this.port + "/api/station/create",station, String.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		then(entity.getBody().equals("STR-1"));
	}

	@Test
	public void Return404WhenStationNotFound() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/api/station/findById?stationId=abc123", String.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void shouldCreateNewStation() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);
	}

	@Test
	public void shouldDeleteStation() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);
 		this.testRestTemplate.delete("http://localhost:" + this.port + "/api/station/remove?stationId=STR-1",String.class);

 		ResponseEntity<String> getResponse =
 		this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/api/station/findById?stationId=STR-1",String.class);
		then(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  	}

	@Test
	public void shouldUpdateStation() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);
		Station updatedStation = new Station();
		updatedStation.setStationId("STR-1");
		updatedStation.setCallSign("STR-CALL-SIGN-X");
		updatedStation.setHdEnabled(Boolean.FALSE);
		updatedStation.setName("STR-1-Name-X");

		this.testRestTemplate.postForEntity("http://localhost:" + this.port + "/api/station/update",updatedStation,String.class);

		ResponseEntity<Station> getResponse =
				this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/api/station/findById?stationId=STR-1",Station.class);
		then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		Station updatedCopy = getResponse.getBody();
		Assert.assertEquals(updatedCopy.getCallSign(),updatedStation.getCallSign());
		Assert.assertEquals(updatedCopy.getName(),updatedStation.getName());
		Assert.assertEquals(updatedCopy.getHdEnabled(),updatedStation.getHdEnabled());

	}


	@Test
	public void shouldFindStationById() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);

		ResponseEntity<Station> getResponse =
				this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/api/station/findById?stationId=STR-1",Station.class);
		then(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		Station updatedCopy = getResponse.getBody();
		Assert.assertEquals(updatedCopy.getCallSign(),"STR-CALL-SIGN-1");
		Assert.assertEquals(updatedCopy.getName(),"STR-1-Name");
		Assert.assertEquals(updatedCopy.getHdEnabled(),Boolean.TRUE);

	}

	@Test
	public void shouldFindStationByName() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);


		ResponseEntity<List<Station>> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/station/findByName?name=STR-1-Name",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Station>>(){});

		List<Station> stations = response.getBody();
		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assert.assertEquals(stations.size(),1);

	}

	@Test
	public void shouldFindStationByHDEnabled() throws Exception {
		Station station = new Station("STR-1","STR-1-Name",Boolean.TRUE,"STR-CALL-SIGN-1");
		createSampleRecord(station);

		ResponseEntity<List<Station>> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/station/findHdEnabled?enabled=true",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Station>>(){});

		List<Station> stations = response.getBody();
		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assert.assertEquals(stations.size(),1);

	}





}
