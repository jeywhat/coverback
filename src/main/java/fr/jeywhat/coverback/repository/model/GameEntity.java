package fr.jeywhat.coverback.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cover")
public class GameEntity implements Serializable {

    @Id
    private String title;

    @Column(name="release_date")
    private String releaseDate;

    private String description;

    private String genre;

    private Integer score;

    private String developer;

    private String rating;

    private String fullpath;

    private String namefile;

    private String extension;

    private BigDecimal size;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;

    @Temporal(value= TemporalType.TIMESTAMP)
    @Column(name="created_on")
    private Date createOn;

}
