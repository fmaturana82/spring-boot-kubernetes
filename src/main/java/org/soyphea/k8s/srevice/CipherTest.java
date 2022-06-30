package org.soyphea.k8s.srevice;

import ch.qos.logback.core.db.dialect.MsSQLDialect;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.NullCipher;
import javax.sql.DataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CipherTest {
    public static void main(String[] args) {
        try {
            Cipher c1 = Cipher.getInstance("DES"); // Noncompliant: DES works with 56-bit keys allow attacks via exhaustive search
            Cipher c7 = Cipher.getInstance("DESede"); // Noncompliant: Triple DES is vulnerable to meet-in-the-middle attack
            Cipher c13 = Cipher.getInstance("RC2"); // Noncompliant: RC2 is vulnerable to a related-key attack
            Cipher c19 = Cipher.getInstance("RC4"); // Noncompliant: vulnerable to several attacks (see https://en.wikipedia.org/wiki/RC4#Security)
            Cipher c25 = Cipher.getInstance("Blowfish"); // Noncompliant: Blowfish use a 64-bit block size makes it vulnerable to birthday attacks

            NullCipher nc = new NullCipher(); // Noncompliant: the NullCipher class provides an "identity cipher" one that does not transform or encrypt the plaintext in any way.
        }
        catch(NoSuchAlgorithmException|NoSuchPaddingException e)   {
        }
    }
    private static DataSource dataSource;
    public List<Object> unsafeFindAccountsByCustomerId(String customerId) throws SQLException {
        // UNSAFE !!! DON'T DO THIS !!!
        List<Object> r = new ArrayList<>();
        String sql = "select "
                + "customer_id,acc_number,branch_id,balance "
                + "from Accounts where customer_id = '"
                + customerId
                + "'";
        Connection c = dataSource.getConnection();
        ResultSet rs = c.createStatement().executeQuery(sql);
       return r;
    }
}
