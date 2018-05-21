package com.practice;

import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

public class SpringBootRestTestClient {
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest/curd/api";

//    GET
    public static void listAllUsers(){

        RestTemplate template = new RestTemplate();
        List<LinkedHashMap<String, Object>> userMap = template.getForObject(REST_SERVICE_URI+"/user/", List.class);

        if(userMap != null){
            for(LinkedHashMap<String, Object> map : userMap ) {
                System.out.println("User : id ="+map.get("id")+", Name : name ="+map.get("name")+", Age : age ="+map.get("age")+",Salary : salary ="+map.get("salary"));
            }
        }
        else System.out.println("No user exists...........");
    }
}
