package com.example.sos.validators;

import com.example.sos.utils.HttpEndpointServer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("nomeMarcaValidator")
public class NomeMarcaValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext,
                         UIComponent uIComponent, Object value) throws ValidatorException {

        String nome = (String) value;
        String nomeFound = findNome(nome);

        if (nome.equalsIgnoreCase(nomeFound)) {
            FacesMessage msg = new FacesMessage("Já existe o nome "+nome+" registrado no banco de dados.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(msg);
        }
    }

    private String findNome(String nome) {
        HttpGet get = new HttpGet(HttpEndpointServer.URL+"/marcas/"+nome+"/find");
        String nameFound = "";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                nameFound = EntityUtils.toString(entity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return nameFound;
    }
}
