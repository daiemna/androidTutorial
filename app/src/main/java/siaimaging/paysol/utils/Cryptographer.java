package siaimaging.paysol.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dna on 11/20/15.
 */
public class Cryptographer {
    private static Cryptographer ourInstance = new Cryptographer();
    private static final String MD5 = "MD5";
    public static Cryptographer getInstance() {
        return ourInstance;
    }

    private Cryptographer() {
    }

    public static final String getHash(final String s) {

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
