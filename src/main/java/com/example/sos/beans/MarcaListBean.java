package com.example.sos.beans;

import com.example.sos.dto.MarcaDTO;
import com.example.sos.utils.HttpMarcaClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class MarcaListBean implements Serializable {
    private static final long serialVersionUID = -1543595805764615112L;

    private List<MarcaDTO> marcasDTO = new ArrayList<>();

    @ManagedProperty(value = "#{httpMarcaClient}")
    private HttpMarcaClient httpMarcaClient;

    public void setHttpMarcaClient(HttpMarcaClient httpMarcaClient) {
        this.httpMarcaClient = httpMarcaClient;
    }

    @PostConstruct
    public void init() {
        List<MarcaDTO> listar = httpMarcaClient.buscaTodas();
        setMarcasDTO(listar);
    }

    public List<MarcaDTO> getMarcasDTO() {
        return marcasDTO;
    }

    public void setMarcasDTO(List<MarcaDTO> marcasDTO) {
        this.marcasDTO = marcasDTO;
    }

    public String delete() {
        String id = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("id");

        if (id != null) {
            httpMarcaClient.deleteMarca(Long.valueOf(id));
        }

        return "/pages/marca/list?faces-redirect?true";
    }
}
