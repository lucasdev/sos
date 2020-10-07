package com.example.sos.utils;

import com.example.sos.dto.MarcaDTO;
import com.example.sos.dto.PatrimonioDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@ManagedBean(name = "httpMarcaClient")
@ApplicationScoped
public class HttpMarcaClient {
    private CloseableHttpClient httpClient;

    @PostConstruct
    public void init() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    public MarcaDTO criarMarca(MarcaDTO marcaDTO) {
        try {
            String json = ObjSerializer.MarcaToJson(marcaDTO);
            ContentType contentType = ContentType.create("application/json", "utf-8");
            StringEntity stringEntity = new StringEntity(json, contentType);

            HttpPost post = new HttpPost(HttpEndpointServer.URL+"/marcas");
            post.setEntity(stringEntity);

            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(EntityUtils.toString(entity), Object.class);
            marcaDTO = mapper.convertValue(object, new TypeReference<MarcaDTO>(){});

        } catch (Exception ex) {

        }
        return marcaDTO;
    }

    public List<MarcaDTO> buscaTodas() {
        List<MarcaDTO> marcaDTOs = new ArrayList<>();
        HttpGet request = new HttpGet(HttpEndpointServer.URL+"/marcas/");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                List list = mapper.readValue(EntityUtils.toString(entity), List.class);
                marcaDTOs = mapper.convertValue(list, new TypeReference<List<MarcaDTO>>(){});
            }
        } catch (Exception ex) {

        }
        return marcaDTOs;
    }

    public MarcaDTO buscaUm(Long id) {
        MarcaDTO marcaDTO = null;
        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/marcas/"+id);
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                Object object = mapper.readValue(EntityUtils.toString(entity), Object.class);
                marcaDTO = mapper.convertValue(object, new TypeReference<MarcaDTO>() {
                });
            }
        } catch (Exception ex) {

        }
        return marcaDTO;
    }

    public void updateMarca(MarcaDTO marcaDTO) {
        String json = ObjSerializer.MarcaToJson(marcaDTO);
        ContentType contentType = ContentType.create("application/json", "utf-8");
        StringEntity stringEntity = new StringEntity(json, contentType);

        HttpPut put = new HttpPut(HttpEndpointServer.URL+"/marcas/"+marcaDTO.getId());
        put.setEntity(stringEntity);

        try (CloseableHttpResponse response = httpClient.execute(put)) {
            response.getEntity();
        } catch (Exception ex) {

        }
    }

    public void deleteMarca(Long id) {
        HttpDelete delete = new HttpDelete(HttpEndpointServer.URL+"/marcas/"+id);

        try (CloseableHttpResponse response = httpClient.execute(delete)) {
            HttpEntity entity = response.getEntity();
        } catch (Exception ex) {

        }
    }

    public List<PatrimonioDTO> buscarPatrimoniosMarca(Long id) {
        List<PatrimonioDTO> patrimonioDTOS = new ArrayList<>();
        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/marcas/"+id+"/patrimonios/");
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
}
