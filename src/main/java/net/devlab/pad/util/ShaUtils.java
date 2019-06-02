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

    private static MessageDigest sha1Digest;
    private static MessageDigest sha256Digest;
    static {
        try {
            sha1Digest = MessageDigest.getInstance("SHA-1");
            sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.catching(e);
        }
    }

    private ShaUtils() {
    }

    public static String sha1Sum(final Pad pad) {
        sha1Digest.update(pad.getAuthor().getBytes());
        if (PadUtils.isTitled(pad)) {
            sha1Digest.update(pad.getTitle().getBytes());
        }
        sha1Digest.update(pad.getCreationDate().toString().getBytes());
        if (PadUtils.expires(pad)) {
            sha1Digest.update(pad.getExpirationDate().toString().getBytes());
        }
        sha1Digest.update(pad.getHighlight().getBytes());
        sha1Digest.update(pad.getContent().getBytes());
        return Hex.encodeHexString(sha1Digest.digest());
    }
}
