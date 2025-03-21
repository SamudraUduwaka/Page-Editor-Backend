package com.example;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.config.mapper.ConfigParserException;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;

public class LayoutParser {

    private static final Log log = LogFactory.getLog(LayoutParser.class);

    public static String parse(Map<String, Object> data, String templateFilePath, String templateFilePathDefault) throws ConfigParserException {

        JinjavaConfig configurator = JinjavaConfig.newBuilder()
                .withLstripBlocks(true)
                .withTrimBlocks(true)
                .build();
        Jinjava jinjava = new Jinjava(configurator);

        String templateContent = null;

        try {
            // 1. Try loading from CDN / URL first
            try (InputStream input = new URL(templateFilePath).openStream();
                 Scanner scanner = new Scanner(input, "UTF-8")) {
        
                log.info("Loading template from URL: " + templateFilePath);
                templateContent = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        
            } catch (Exception urlEx) {
                log.warn("Failed to load template from Your Edited Content --> Switching to default template");
        
                // 2. Try loading the default template file 
                if (Files.exists(Paths.get(templateFilePathDefault))) {
                    log.info("Loading template from local file path: " + templateFilePathDefault);
                    templateContent = new String(Files.readAllBytes(Paths.get(templateFilePathDefault)));
        
                } else {
                    // 3. Fallback to classpath resource
                    InputStream resourceStream = LayoutParser.class.getClassLoader()
                            .getResourceAsStream(templateFilePathDefault);
                    if (resourceStream != null) {
                        log.info("Loading template from classpath resource: " + templateFilePathDefault);
                        try (Scanner scanner = new Scanner(resourceStream, "UTF-8")) {
                            templateContent = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        }
                    }
                }
            }
        
        } catch (Exception e) {
            throw new ConfigParserException("Failed to load template from any source: " +  e);
        }
        
        // 3. Render the template
        try {
            return jinjava.render(templateContent, data);
        } catch (Exception e) {
            log.error("Error while parsing Jinja template", e);
            throw new ConfigParserException("Template rendering failed", e);
        }
    }
}
