package com.sample.springboot.station.model;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//column length ?
@Data
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="station")
public class Station implements Serializable{

    @Id
    @Column(name="stationId", nullable = false)
    private @NotNull String stationId;
    @Column(name="name", nullable = false)
    private @NotNull  String name;
    @Column(name="hdEnabled", nullable = false)
    private @NotNull Boolean hdEnabled;
    @Column(name="callSign", nullable = false)
    private @NotNull String callSign;

}
