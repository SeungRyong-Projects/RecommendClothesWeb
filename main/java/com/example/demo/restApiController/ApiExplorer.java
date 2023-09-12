package com.example.demo.restApiController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class ApiExplorer {
    public String apiExplorer(Model model) throws JsonProcessingException {

        LocalDateTime now = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();

        try {
            StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?lat=37.1933&lon=127.0225&appid="); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "c98c224d3fbfa30ae683d954eaef36c7"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("date", "UTF-8") + "now"); /*현재시간*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(sb.toString());
        ObjectNode obj = (ObjectNode) node;

        JsonNode json = obj.get("weather");
        for(JsonNode jsonNode : json) {
            model.addAttribute("lat", jsonNode.get("lat").toString());
            model.addAttribute("lon", jsonNode.get("lon").toString());
            model.addAttribute("temp_min", jsonNode.get("temp_min").toString());
            model.addAttribute("temp_max", jsonNode.get("temp_max").toString());
            model.addAttribute("description", jsonNode.get("description").toString());
            model.addAttribute("main", jsonNode.get("main").toString());
        }
        return "/main/mainPage";
    }
}
