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
import org.json.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

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
}
