package com.example;

import java.util.*;
import org.wso2.config.mapper.ConfigParserException;

public class App {

    public static void main(String[] args) {

        // Sample data for the context
        Map<String, Object> data = new HashMap<>();
        List<Map<String, String>> usersList = new ArrayList<>();
        usersList.add(Map.of("name", "Alice"));
        usersList.add(Map.of("name", "Bob"));
        data.put("users", usersList);

        // File path of the Jinja template
        String filePath = "https://cd.statically.io/gh/SamudraUduwaka/testFilesForCustomPageEditor/24012874b876ae3535ef0ca46e1e0e1c3dbfc37b/test.html.j2";
        // Default file path of the Default template WSO2 provides
        String filePathDefault = "templates/template.html.j2";

        // Parse Jinja templates 
        try {
            String parsedTemplate = LayoutParser.parse(data, filePath, filePathDefault);
            System.out.println(parsedTemplate);
        } catch (ConfigParserException e) {
            e.printStackTrace();
        }

    }

}
