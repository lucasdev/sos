package com.example.sos.utils;

import com.example.sos.dto.PatrimonioDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "httpPatrimonioClient")
@ApplicationScoped
public class HttpPatrimonioClient {
    private CloseableHttpClient httpClient;

    @PostConstruct
    public void init() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public PatrimonioDTO criar(PatrimonioDTO patrimonioDTO) {
        try {
            String json = ObjSerializer.PatrimonioToJson(patrimonioDTO);

            StringEntity stringEntity = new StringEntity(json, HttpEndpointServer.CONTENT_TYPE);
            HttpPost post = new HttpPost(HttpEndpointServer.URL+"/patrimonios");
            post.setEntity(stringEntity);

            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(EntityUtils.toString(entity), Object.class);
            patrimonioDTO = mapper.convertValue(object, new TypeReference<PatrimonioDTO>(){});
        } catch (Exception ex) {

        }
        return patrimonioDTO;
    }

    public List<PatrimonioDTO> buscarTodos() {
        List<PatrimonioDTO> patrimonioDTOS = new ArrayList<>();
        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/patrimonios/");
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                List list = mapper.readValue(EntityUtils.toString(entity), List.class);
                patrimonioDTOS = mapper.convertValue(list, new TypeReference<List<PatrimonioDTO>>(){});
            }
        } catch (Exception ex) {

        }
        return patrimonioDTOS;
    }

    public PatrimonioDTO buscaUm(Long id) {
        PatrimonioDTO patrimonioDTO = null;
        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/patrimonios/"+id);
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                Object object = mapper.readValue(EntityUtils.toString(entity), Object.class);
                patrimonioDTO = mapper.convertValue(object, new TypeReference<PatrimonioDTO>() {
                });
            }
        } catch (Exception ex) {

        }
        return patrimonioDTO;
    }

    public void update(PatrimonioDTO patrimonioDTO) {
        String json = ObjSerializer.PatrimonioToJson(patrimonioDTO);
        StringEntity stringEntity = new StringEntity(json, HttpEndpointServer.CONTENT_TYPE);

        HttpPut put = new HttpPut(HttpEndpointServer.URL+"/patrimonios/"+patrimonioDTO.getId());
        put.setEntity(stringEntity);

        try (CloseableHttpResponse response = httpClient.execute(put)) {
            response.getEntity();
            final Header[] allHeaders = response.getAllHeaders();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public void delete(Long id) {
        HttpDelete delete = new HttpDelete(HttpEndpointServer.URL+"/patrimonios/"+id);

        try (CloseableHttpResponse response = httpClient.execute(delete)) {
            HttpEntity entity = response.getEntity();
        } catch (Exception ex) {

        }
    }
}
