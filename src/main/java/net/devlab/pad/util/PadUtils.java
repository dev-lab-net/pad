package net.devlab.pad.util;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import net.devlab.pad.model.Pad;

/**
 * 
 * @author dj0n1
 *
 */
public class PadUtils {

    private PadUtils() {
    }

    public static boolean isTitled(Pad pad) {
        return StringUtils.isNotBlank(pad.getTitle());
    }

    public static boolean expires(Pad pad) {
        return pad.getExpirationDate() != null;
    }

    public static boolean isExpired(Pad pad) {
        if (!expires(pad)) {
            return false;
        }
        final LocalDateTime now = LocalDateTime.now();
        return now.isAfter(pad.getExpirationDate());
    }
}
