package com.flow.assignment.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flow.assignment.domain.extension.Extension;
import com.flow.assignment.domain.extension.ExtensionRepository;
import com.flow.assignment.utils.ResponseMessage;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExtensionApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExtensionRepository extensionRepository;

    @After
    public void tearDown() throws Exception {
        extensionRepository.deleteAll();
    }

    @Test
    public void saveTest() throws Exception {
        String name = "testExtension";
        ExtensionRequestDto extensionRequestDto = ExtensionRequestDto.builder().name(name).build();

        String url = "http://localhost:" + port + "/extension/v1/extensions";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, extensionRequestDto, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseJson.get("result").asBoolean()).isTrue();
        assertThat(responseJson.get("message").asText()).isEqualTo(ResponseMessage.SUCCESS_SAVE_MESSAGE);

        Long extensionId = Long.valueOf(responseJson.get("data").asText());

        assertThat(extensionId).isGreaterThan(0L);

        Extension extension = extensionRepository.findById(extensionId).orElseThrow(IllegalAccessError::new);
        assertThat(extension.getName()).isEqualTo(name);
    }

    @Test
    public void checkedTest() throws Exception {
        String name = "testExtension";
        ExtensionRequestDto extensionRequestDto = ExtensionRequestDto.builder().name(name).build();
        Long checkedId = extensionRepository.save(extensionRequestDto.toExtension()).getId();
        String url = "http://localhost:" + port + "/extension/v1/extensions/check/" + checkedId;

        HttpEntity<Boolean> requestEntity = new HttpEntity<>(true);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseJson.get("result").asBoolean()).isTrue();
        assertThat(responseJson.get("message").asText()).isEqualTo(ResponseMessage.SUCCESS_CHECKED_MESSAGE);

        Long extensionId = Long.valueOf(responseJson.get("data").asText());

        assertThat(extensionId).isGreaterThan(0L);

        Extension extension = extensionRepository.findById(extensionId).orElseThrow(IllegalAccessError::new);
        assertThat(extension.isChecked()).isEqualTo(true);
    }

    @Test
    public void deleteTest() throws Exception {
        String name = "testExtension";
        ExtensionRequestDto extensionRequestDto = ExtensionRequestDto.builder().name(name).build();
        Long deleteId = extensionRepository.save(extensionRequestDto.toExtension()).getId();
        String url = "http://localhost:" + port + "/extension/v1/extensions/" + deleteId;

        HttpEntity<Long> requestEntity = new HttpEntity<>(deleteId);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseJson.get("result").asBoolean()).isTrue();
        assertThat(responseJson.get("message").asText()).isEqualTo(ResponseMessage.SUCCESS_DELETED_MESSAGE);

        Long extensionId = Long.valueOf(responseJson.get("data").asText());

        assertThat(extensionId).isGreaterThan(0L);

        assertThat(extensionId).isEqualTo(deleteId);
    }

    @Test
    public void getListTest() throws Exception {
        String url = "http://localhost:" + port + "/extension/v1/extensions/";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseEntity.getBody());

        assertThat(responseJson.get("result").asBoolean()).isTrue();
        assertThat(responseJson.get("message").asText()).isEqualTo(ResponseMessage.SUCCESS_EXTENSION_LIST_MESSAGE);

        JsonNode extensions = responseJson.get("data");
        assertThat(extensions).isNotEmpty();
    }
}