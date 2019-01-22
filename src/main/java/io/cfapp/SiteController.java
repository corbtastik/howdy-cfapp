package io.cfapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SiteController {

    @Value("${site.title}")
    private String title = "";

    @Value("${site.subtitle}")
    private String subtitle = "";

    @Value("${VCAP_APPLICATION:{}}")
    private String appInfo;

    @Value("${VCAP_SERVICES:{}}")
    private String serviceInfo;

    private final ObjectMapper json;

    @Autowired
    public SiteController(ObjectMapper json) {
        this.json = json;
    }

    @GetMapping("/")
    public String index(Map<String, Object> model) throws IOException {
        model.put("title", title);
        model.put("subtitle", subtitle);
        model.put("cfapp", json.readValue(appInfo, LinkedHashMap.class));
        model.put("cfservices", json.readValue(serviceInfo, LinkedHashMap.class));
        return "site";
    }
}