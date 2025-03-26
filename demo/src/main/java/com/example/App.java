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
        String filePath = "https://cdn.statically.io/gh/SamudraUduwaka/testFilesForCustomPageEditor/e68ed32674fdac72968a3de40df643b48c8c33cf/test.html.j2";
        // Default file path of the Default template WSO2 provides
        String filePathDefault = "templates/template.html.j2";

        // Parse Jinja templates
        try {
            String parsedTemplate = LayoutParser.parse(data, filePath, filePathDefault);
            System.out.println(parsedTemplate);

            try {

                String result = ComponentEngine.resolveComponents(parsedTemplate);
                System.out.println("Final Rendered Page:\n" + result);
            } catch (Exception e) {

                e.printStackTrace();
            }

        } catch (ConfigParserException e) {
            
            e.printStackTrace();
        }

    }

}
