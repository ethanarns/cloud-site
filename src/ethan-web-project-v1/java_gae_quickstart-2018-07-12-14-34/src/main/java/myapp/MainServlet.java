/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package myapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// From Maven (pom.xml) dependencies
import org.json.*;
import java.sql.*;
import com.google.apphosting.api.ApiProxy;

// https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html
public class MainServlet extends HttpServlet {
    
    // Keep false until deploying
    public final boolean SQL_ENABLED = false;
    
    // SQL Connection, created when server first accessed
    Connection conn;
    
    //String header_user = "null";
    
    // Fires every time a user visits a page, most of the time via hyperlink or refresh
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    
        String ip = "" + req.getRemoteAddr();
        
        // Browser detecter
        String header_user = req.getHeader("User-Agent").toLowerCase();
        if(header_user.indexOf("firefox") != -1)
            header_user = "Firefox";
        else if (header_user.indexOf("chrome") != -1)
            header_user = "Chrome";
        else if (header_user.indexOf("opera") != -1)
            header_user = "Opera";
        else if (header_user.indexOf("safari") != -1)
            header_user = "Safari";
        else if (header_user.indexOf("ie") != -1)
            header_user = "Internet Explorer";
        else
            header_user = "Unknown";
        
        // Build JSON output
        HashMap<String, String> dict = new HashMap<String, String>();
        dict.put("name","Ethan Arns");
        dict.put("ip", ip);
        dict.put("browser", header_user);
        
        // Send JSON response
        resp.setContentType("text/plain");
        resp.getWriter().println(new JSONObject(dict));
    }
    
    // Called at very start of server, 'conn' will last until server closes
    @Override
    public void init() throws ServletException {
        // NOTE: SQL ONLY WORKS ON DEPLOYED SERVERS
        if(SQL_ENABLED) {
            try {
                conn = DriverManager.getConnection(System.getProperty("cloudsql"));
                //header_user = "SQL works!";
            } catch (SQLException e) {
                log("ERROR at init(): 'Unable to connect to Cloud SQL'");
                //header_user = "SQL epic fails!";
                // Disable below command until deploying final production server
                //throw new ServletException("Unable to connect to Cloud SQL", e);
            }
        }

    }
}
