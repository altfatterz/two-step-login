package com.backbase.progfun.code;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;

public class TimeBasedOneTimePassword {

    private static final String SHARED_SECRET = "6YFX5HZT76OHHNMS";
    private static final int VARIANCE = 5;

    public static boolean isVerificationCodeValid(int code) throws VerificationCodeException {
        boolean result = false;
        try {
            result = verifyCode(SHARED_SECRET, code, getTimeIndex(), VARIANCE);
        } catch (NoSuchAlgorithmException e) {
            throw new VerificationCodeException("invalid algorithm");
        } catch (InvalidKeyException e) {
            throw new VerificationCodeException("invalid key");
        }

        return result;
    }

    private static boolean verifyCode(String secret, int code, long timeIndex, int variance)
            throws NoSuchAlgorithmException, InvalidKeyException {
        for (int i = -variance; i <= variance; i++) {
            if (getCode(secret, timeIndex + i) == code) {
                return true;
            }
        }
        return false;
    }

    private static long getCode(String secret, long timeIndex) throws InvalidKeyException, NoSuchAlgorithmException {
        return getCode(new Base32().decode(secret), timeIndex);
    }

    private static long getCode(byte[] secret, long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec signKey = new SecretKeySpec(secret, "HmacSHA1");
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(timeIndex);
        byte[] timeBytes = buffer.array();
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(timeBytes);
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return (truncatedHash %= 1000000);
    }

    private static long getTimeIndex() {
        return System.currentTimeMillis() / 1000 / 30;
    }

}
