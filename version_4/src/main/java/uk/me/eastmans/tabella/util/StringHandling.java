package uk.me.eastmans.tabella.util;

import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meastman on 07/12/15.
 */
public class StringHandling
{
    public static Map<String,List<String>> extractRequestParams(HttpServletRequest request)
    {
        Map<String,List<String>> params = new HashMap<>();
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            // Values are in key=value&
            String[] tokens = URLDecoder.decode(jb.toString(),"UTF8").split("&");
            for (String token : tokens)
            {
               // Now key = value
                String[] mapping = token.split("=");
                // Do we have the mapping already
                if (!params.containsKey(mapping[0]))
                    params.put(mapping[0],new ArrayList<String>());
                if (mapping.length > 1 && mapping[1] != null) {
                    List<String> values = params.get(mapping[0]);
                    values.add(mapping[1]);
                }
            }
        } catch (Exception e) { /*report an error*/ }
        return params;
    }
}
