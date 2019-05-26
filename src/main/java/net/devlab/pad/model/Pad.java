package net.devlab.pad.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import lombok.Data;

/**
 * 
 * @author dj0n1
 *
 */
@Data
@Entity
public class Pad implements Serializable {

    @Id
    private ObjectId id;
    @Property
    private String path;
    @Property
    private String author;
    @Property
    private String title;
    @Property
    private LocalDateTime creationDate;
    @Property
    private LocalDateTime expirationDate;
    @Property
    private int views;
    @Property
    private String highlight;
    @Property
    @NotBlank
    private String content;

    public Pad() {
        setId(new ObjectId());
    }

    public void setId(ObjectId id) {
        this.id = id;
        setPath(id.toHexString());
    }
}
