package net.devlab.pad.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import lombok.Builder;
import lombok.Data;
import net.devlab.pad.util.ShaUtils;

/**
 * 
 * @author dj0n1
 *
 */
@Data
@Builder
@Entity
public class Pad implements Serializable {

    @Id
    private ObjectId id;
    @Property
    private String hash;
    @Property
    private String partialHash;
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
    private String content;

    public Pad() {
        setId(new ObjectId());
    }

    private Pad(ObjectId id, String hash, String partialHash, String author, String title,
            LocalDateTime creationDate, LocalDateTime expirationDate, int views, String highlight, String content) {
        setId(id);
        setHash(hash);
        setPartialHash(partialHash);
        setAuthor(author);
        setTitle(title);
        setCreationDate(creationDate);
        setExpirationDate(expirationDate);
        setViews(views);
        setHighlight(highlight);
        setContent(content);
    }

    public void computeHash() {
        hash = ShaUtils.sha1Sum(this);
        partialHash = hash.substring(0, 8);
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getHash() {
        if (StringUtils.isBlank(hash)) {
            computeHash();
        }
        return hash;
    }

    public String getPartialHash() {
        if (StringUtils.isBlank(partialHash)) {
            computeHash();
        }
        return partialHash;
    }

    public String getTitle() {
        return title;
    }
}
