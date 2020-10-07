package com.example.sos.beans;

import com.example.sos.dto.PatrimonioDTO;
import com.example.sos.utils.HttpMarcaClient;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class MarcaPatrimoniosListBean implements Serializable {
    private static final long serialVersionUID = -8742734716865458557L;

    private List<PatrimonioDTO> patrimonioDTOS = new ArrayList<>();

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
            List<PatrimonioDTO> patrimonioDTOS = httpMarcaClient.buscarPatrimoniosMarca(Long.valueOf(id));
            setPatrimonioDTOS(patrimonioDTOS);
        }
    }

    public List<PatrimonioDTO> getPatrimonioDTOS() {
        return patrimonioDTOS;
    }

    public void setPatrimonioDTOS(List<PatrimonioDTO> patrimonioDTOS) {
        this.patrimonioDTOS = patrimonioDTOS;
    }
}
