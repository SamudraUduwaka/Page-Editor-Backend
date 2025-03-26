package com.example;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class ComponentEngine {
    // Example component store (can be file reading or DB lookup in real)
    private static final Map<String, String> componentStore = new HashMap<>();

    static {
        // componentStore.put("mainsection", """
        //     <div class="main-content">
        //         <h1>Welcome to the Main Section</h1>
        //         <p>This content is loaded from the component.</p>
        //     </div>
        // """);
        componentStore.put("MainSection", "<div>The content of Main Section</div>");
    }

    public static String resolveComponents(String layoutContent) throws Exception {

        StringWriter finalOutput = new StringWriter();
        BufferedReader reader = new BufferedReader(new StringReader(layoutContent));
        String line;

        while ((line = reader.readLine()) != null) {
            
            String resolvedLine = line;
            // Detect and replace any {{{component}}} tag
            while (resolvedLine.contains("{{{")) {
                int start = resolvedLine.indexOf("{{{");
                int end = resolvedLine.indexOf("}}}", start);
                if (end == -1) break; // Unmatched, skip

                String componentName = resolvedLine.substring(start + 3, end).trim();
                String componentContent = componentStore.getOrDefault(componentName, "<!-- Missing Component: " + componentName + " : Try Built-in componet names insted -->");

                // Replace the placeholder with actual component content
                resolvedLine = resolvedLine.substring(0, start) + componentContent + resolvedLine.substring(end + 3);
            }
            finalOutput.append(resolvedLine).append("\n");
        }
        return finalOutput.toString();
    }
}
