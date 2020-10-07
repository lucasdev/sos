package com.example.sos.beans;

import com.example.sos.dto.PatrimonioDTO;
import com.example.sos.utils.HttpPatrimonioClient;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@RequestScoped
public class PatrimonioListBean implements Serializable {
    private static final long serialVersionUID = -8675390887154760103L;
    private List<PatrimonioDTO> patrimonioDTO;

    @ManagedProperty(value = "#{httpPatrimonioClient}")
    private HttpPatrimonioClient httpPatrimonioClient;

    public void setHttpPatrimonioClient(HttpPatrimonioClient httpPatrimonioClient) {
        this.httpPatrimonioClient = httpPatrimonioClient;
    }

    @PostConstruct
    public void init() {
        List<PatrimonioDTO> patrimonioDTOS = httpPatrimonioClient.buscarTodos();
        setPatrimonioDTO(patrimonioDTOS);
    }

    public List<PatrimonioDTO> getPatrimonioDTO() {
        return patrimonioDTO;
    }

    public void setPatrimonioDTO(List<PatrimonioDTO> patrimonioDTO) {
        this.patrimonioDTO = patrimonioDTO;
    }

    public String delete() {
        String id = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("id");

        httpPatrimonioClient.delete(Long.valueOf(id));

        return "/pages/patrimonio/list?faces-redirect=true";
    }
}
