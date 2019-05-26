package net.devlab.pad.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import lombok.Data;
import net.devlab.pad.util.ShaUtils;

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
    private String shaSum;
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
        setHighlight("txt");
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getShaSum() {
        if (StringUtils.isEmpty(shaSum)) {
            shaSum = ShaUtils.sha256Sum(this);
        }
        return shaSum;
    }

    public boolean isTitled() {
        return StringUtils.isNotBlank(title);
    }

    public String getTitle() {
        if (StringUtils.isBlank(title)) {
            return "Pad #" + getShaSum();
        }
        return title;
    }

    public boolean expires() {
        return expirationDate != null;
    }
}
