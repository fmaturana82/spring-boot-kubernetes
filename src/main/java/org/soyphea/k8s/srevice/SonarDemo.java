package org.soyphea.k8s.srevice;
import java.util.*;
import java.io.PrintStream;
import java.nio.file.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import javax.naming.*;

public class SonarDemo {
    public static void main(String[] args) {
        PrintStream o = System.out; //NOSONAR

        String pass = args[0];//request.getParameter("pass");
        String user = args[1];
        String query = "SELECT * FROM users WHERE user = '" + user + "' AND pass = '" + pass + "'"; // Unsafe
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", pass);
        java.sql.Connection connection = null;
        try {
            connection = java.sql.DriverManager.getConnection("jdbc:localhost:sql1;create=true",connectionProps);
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery(query);
            Files.exists(Paths.get("/home/", user));

            String filter = "(&(uid=" + user + ")(userPassword=" + pass + "))"; // Unsafe

            LdapContext ctx = new InitialLdapContext();
            NamingEnumeration<SearchResult> results = ctx.search("ou=system", filter, new SearchControls());

        } catch (Exception e){
            o.println("Exception");
        }

    }
}
