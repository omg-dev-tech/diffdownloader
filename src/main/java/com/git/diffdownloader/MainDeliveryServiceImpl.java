/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.git.diffdownloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author codespace
 */
@Service(value = "mainDeliveryService")
public class MainDeliveryServiceImpl implements DeliveryService {

	private final RestTemplate restTemplate;

	@Value("${gitlab.project.id}")
	private String[] projectIds;

	@Value("${gitlab.api.url}")
	String gitlabApiUrl;

	@Value("${gitlab.token}")
	String gitlabToken;

	public MainDeliveryServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public void makeZip() {

		List<String> latestTags = new ArrayList<String>();
		// get current tag
		for (String projectId : projectIds) {
			String lastestTag = getLatestTags(projectId);
			if (lastestTag != null) {
				latestTags.add(getLatestTags(projectId));
			}
		}

		System.out.println(Arrays.toString(latestTags.toArray()));

		// compare

		// download
	}

	private String getLatestTags(String projectId) {

		String url = String.format("%s/projects/%s/repository/tags", gitlabApiUrl, projectId);
		HttpHeaders headers = new HttpHeaders();
		headers.set("PRIVATE-TOKEN", gitlabToken);
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<Map[]> response = restTemplate.exchange(
	            url, 
	            HttpMethod.GET, 
	            entity, 
	            Map[].class, 
	            projectId
	        );
		
		List<Map> tags = Arrays.asList(response.getBody());
		return tags.isEmpty() ? "No tags found" : (String) tags.get(0).get("name");

	}
}
