package com.example.sos.beans;

import com.example.sos.dto.MarcaDTO;
import com.example.sos.utils.HttpMarcaClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class MarcaFormBean implements Serializable {
    private static final long serialVersionUID = -7521806921821775438L;
    private MarcaDTO marcaDTO;

    @ManagedProperty(value = "#{httpMarcaClient}")
    private HttpMarcaClient httpMarcaClient;

    public void setHttpMarcaClient(HttpMarcaClient httpMarcaClient) {
        this.httpMarcaClient = httpMarcaClient;
    }

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String id = request.getParameter("id");
        if (id != null) {
            editForm(id);
        } else {
            resetForm();
        }
    }

    public String salvar() {
        try {
            if (marcaDTO.getId() != null) {
                httpMarcaClient.updateMarca(marcaDTO);
            } else {
                httpMarcaClient.criarMarca(marcaDTO);
            }
            resetForm();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Registro criado com sucesso"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Error ao tentar salvar Marca"));
        }

        return "/pages/marca/list?faces-redirect=true";
    }

    public void editForm(String id) {
        MarcaDTO marcaDTO = httpMarcaClient.buscaUm(Long.valueOf(id));
        setMarcaDTO(marcaDTO);
    }

    public MarcaDTO getMarcaDTO() {
        return marcaDTO;
    }

    public void setMarcaDTO(MarcaDTO marcaDTO) {
        this.marcaDTO = marcaDTO;
    }

    private void resetForm() {
        marcaDTO = new MarcaDTO();
    }
}
