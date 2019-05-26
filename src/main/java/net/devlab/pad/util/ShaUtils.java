package net.devlab.pad.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import lombok.extern.log4j.Log4j2;
import net.devlab.pad.model.Pad;

/**
 * 
 * @author dj0n1
 *
 */
@Log4j2
public class ShaUtils {

    private static MessageDigest messageDigest;
    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.catching(e);
        }
    }

    private ShaUtils() {
    }

    public static String sha256Sum(final Pad pad) {
        messageDigest.update(pad.getAuthor().getBytes());
        if (pad.isTitled()) {
            messageDigest.update(pad.getTitle().getBytes());
        }
        messageDigest.update(pad.getCreationDate().toString().getBytes());
        if (pad.expires()) {
            messageDigest.update(pad.getExpirationDate().toString().getBytes());
        }
        messageDigest.update(pad.getHighlight().getBytes());
        messageDigest.update(pad.getContent().getBytes());
        return Hex.encodeHexString(messageDigest.digest()).substring(0, 8);
    }
}
