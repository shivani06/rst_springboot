package com.springboot;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@RestController
@RequestMapping(value="/myservice")
public class WelcomeController {
	
		

	@PostMapping
	public String welcome(@RequestHeader HttpHeaders headers,@RequestBody Object request) throws JsonProcessingException, IOException, ParseException {
		

	    RestTemplate restTemplate = new RestTemplate();
	    
	    //API call for Access Token
	    
	    String accessURL = "https://test.salesforce.com/services/oauth2/authorize?response_type=code&client_id=3MVG94DzwlYDSHS6jPRjWKdLh4d9j0TEpkFkvWilr1jqtUNsYTpet2VJtNdeg7SoGDhXLnOvJm_PWoQ3.jhCf&redirect_uri=https://localhost:8080/callback&state=mystate";
	    
	    
//	    String accessURL = "https://httpbin.org/post?grant_type='code'&code='some value'&client_id='client_ID'&client_secret='4056016813500571243'&redirect_uri='Some uri'";    
	    HttpHeaders myHeaders = new HttpHeaders(); 
	    HttpEntity<Object> entity = new HttpEntity<Object>(request,myHeaders);
	    ResponseEntity<JsonNode> response = restTemplate.exchange(accessURL, HttpMethod.POST,entity,JsonNode.class);
	    String value = response.getBody().path("data").asText();
	    ObjectMapper mapper = new ObjectMapper();
	    AccessTokenResponse accessTokenResponse = mapper.readValue(value, AccessTokenResponse.class);

	     
	    String accessToken =  accessTokenResponse.access_token;

	    //API call for Actual Resource
	    String resourceURL = "https://httpbin.org/post";
	    HttpHeaders resourceHeaders = new HttpHeaders();
	    resourceHeaders.set("Authorization","Bearer "+accessToken);
	    
	    HttpEntity<Object> entity1 = new HttpEntity<Object>(request,myHeaders);
	    ResponseEntity<String> responseData = restTemplate.exchange(resourceURL, HttpMethod.POST,entity1,String.class);
	    System.out.println(resourceHeaders);
	    
		return "Success";
	}
	

	@GetMapping
	@ResponseBody
	public String welcome1(@RequestHeader HttpHeaders headers) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		
		String accessURL = "https://test.salesforce.com/services/oauth2/authorize?response_type=code&client_id=3MVG94DzwlYDSHS6jPRjWKdLh4d9j0TEpkFkvWilr1jqtUNsYTpet2VJtNdeg7SoGDhXLnOvJm_PWoQ3.jhCf&https://vpscx.my.salesforce.com/services/oauth2/success&state=mystate";
		HttpHeaders myHeaders = new HttpHeaders(); 
	    HttpEntity<Object> entity = new HttpEntity<Object>(myHeaders);
	    ResponseEntity<JsonNode> response = restTemplate.exchange(accessURL, HttpMethod.POST,entity,JsonNode.class);
		return "Swayaan";
	}
	
	
}