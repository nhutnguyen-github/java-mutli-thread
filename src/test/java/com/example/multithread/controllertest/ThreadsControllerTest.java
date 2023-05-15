package com.example.multithread.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.catalina.connector.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import static org.mockito.BDDMockito.*;
import com.example.multithread.services.ServiceShort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ThreadsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCallUri() throws InterruptedException {

        // given(this.serviceShort.getNextTwoInt(1)).willReturn(6);


        // ResponseEntity<String> rs = restTemplate.getForEntity("/getshort/1", String.class);
        // Assertions.assertThat(rs).isNotNull(); 
        // Assertions.assertThat(rs.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Assertions.assertThat(rs.getBody()).contains("6");        
    }

    @Test
    public void testMultiThreadsShort() throws Exception {
        int sum = 0;
        List<Integer> lst = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());

        sum = lst.parallelStream().map(i -> {
            ResponseEntity<Integer> rs = restTemplate.getForEntity("/getshort/" + i, Integer.class);
            System.out.println(String.format("test %s", rs));

            return rs.getBody();
        }).collect(Collectors.summingInt(Integer::intValue));

        Assertions.assertThat(sum).isEqualTo(30);
    }

    @Test
    public void testMultiThreadsLong() {
        int sum = IntStream.rangeClosed(1, 5)
        .parallel().map(i -> {
            return restTemplate.getForObject("/getlong/" + i, Integer.class);
        }).sum();

        assertEquals(sum, 45);
    }

    @Test
    public void testCallAPIs() {
        // int rs1 = restTemplate.getForObject("/getlong/1", Integer.class);
        // assertEquals(rs1, 3);

        int rs2 = restTemplate.getForObject("/getshort/" + 2, Integer.class);
        assertEquals(rs2, 4);
    }

}
