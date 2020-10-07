package com.example.sos.beans;

import com.example.sos.dto.ArquivoDTO;
import com.example.sos.utils.HttpEndpointServer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class ArquivoBean implements Serializable {
    private static final long serialVersionUID = -2730615998956785543L;
    private UploadedFile file;
    List<ArquivoDTO> arquivoDTOS;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<ArquivoDTO> getArquivoDTOS() {
        return arquivoDTOS;
    }

    public void setArquivoDTOS(List<ArquivoDTO> arquivoDTOS) {
        this.arquivoDTOS = arquivoDTOS;
    }

    @PostConstruct
    public void init() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/arquivos/");
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                ObjectMapper mapper = new ObjectMapper();
                List list = mapper.readValue(EntityUtils.toString(entity), List.class);
                arquivoDTOS = mapper.convertValue(list, new TypeReference<List<ArquivoDTO>>(){});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String upload() throws IOException {
        final CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        try {
            HttpPost httpUpload = new HttpPost(HttpEndpointServer.URL+"/arquivos/upload/");

            //final InputStreamBody inputStreamBody = new InputStreamBody(file.getInputstream(), ContentType.MULTIPART_FORM_DATA);
            HttpEntity reqEntity = MultipartEntityBuilder.create().setStrictMode()
                    //.addPart("upfile", inputStreamBody)
                    .addBinaryBody("upfile", file.getInputstream(), ContentType.create(file.getContentType()), file.getFileName())
                    .build();

            httpUpload.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httpUpload);
            HttpEntity resEntity = response.getEntity();
            response.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            httpclient.close();
        }

        return "/pages/arquivo/list?faces-redirect=true";
    }

    public void download() {
        String arquivo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("arquivo");

        CloseableHttpClient clientHttp = HttpClientBuilder.create().build();
        try {
            HttpGet get = new HttpGet(HttpEndpointServer.URL+"/arquivos/download/"+arquivo);
            HttpResponse response = clientHttp.execute(get);
            HttpEntity entity = response.getEntity();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse _response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            _response.reset();
            //_response.setHeader("Content-Type", "application/pdf");
            _response.setHeader("Content-Disposition", "attachment; filename= download.pdf");
            OutputStream responseOutputStream = _response.getOutputStream();

            InputStream pdfInputStream = entity.getContent();

            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
                responseOutputStream.write(bytesBuffer, 0, bytesRead);
            }
            responseOutputStream.flush();

            pdfInputStream.close();
            responseOutputStream.close();
            facesContext.responseComplete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
