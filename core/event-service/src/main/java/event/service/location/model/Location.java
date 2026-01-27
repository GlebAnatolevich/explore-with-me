package event.service.location.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Check;

@Getter
@Setter
@Entity
@Table(name = "location")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Check(constraints = "lat BETWEEN -90.0 AND 90.0")
@Check(constraints = "lon BETWEEN -180.0 AND 180.0")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "lat", nullable = false)
    Double lat;

    @Column(name = "lon", nullable = false)
    Double lon;
}
