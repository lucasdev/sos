package com.example.sos.beans;

import com.example.sos.dto.MarcaDTO;
import com.example.sos.dto.PatrimonioDTO;
import com.example.sos.utils.HttpMarcaClient;
import com.example.sos.utils.HttpPatrimonioClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@RequestScoped
public class PatrimonioFormBean implements Serializable {
    private static final long serialVersionUID = 622364698990132747L;
    private PatrimonioDTO patrimonioDTO;
    private Long selectValue;
    private List<MarcaDTO> marcaDTOS;

    private List<SelectItem> marcas = new ArrayList<>();

    public Long getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(Long selectValue) {
        this.selectValue = selectValue;
    }

    public List<SelectItem> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<SelectItem> marcas) {
        this.marcas = marcas;
    }

    @ManagedProperty(value = "#{httpPatrimonioClient}")
    private HttpPatrimonioClient httpPatrimonioClient;

    @ManagedProperty(value = "#{httpMarcaClient}")
    private HttpMarcaClient httpMarcaClient;

    public void setHttpPatrimonioClient(HttpPatrimonioClient httpPatrimonioClient) {
        this.httpPatrimonioClient = httpPatrimonioClient;
    }

    public void setHttpMarcaClient(HttpMarcaClient httpMarcaClient) {
        this.httpMarcaClient = httpMarcaClient;
    }

    @PostConstruct
    public void init() {
        marcaDTOS = httpMarcaClient.buscaTodas();
        for (MarcaDTO m : marcaDTOS) {
            getMarcas().add(new SelectItem(m.getId(), m.getNome()));
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String id = request.getParameter("id");
        if (id != null) {
            editForm(id);
        } else {
            resetForm();
        }
    }

    private void editForm(String id) {
        PatrimonioDTO patrimonioDTO = httpPatrimonioClient.buscaUm(Long.valueOf(id));
        setSelectValue(patrimonioDTO.getMarca().getId());
        setPatrimonioDTO(patrimonioDTO);
    }

    public String salvar() {
        try {
            if (patrimonioDTO.getId() == null) {
                httpPatrimonioClient.criar(patrimonioDTO);
            } else {
                httpPatrimonioClient.update(patrimonioDTO);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Error ao tentar salvar Marca"));
        }
        return "/pages/patrimonio/list?faces-redirect=true";
    }

    public PatrimonioDTO getPatrimonioDTO() {
        return patrimonioDTO;
    }

    public void setPatrimonioDTO(PatrimonioDTO patrimonioDTO) {
        this.patrimonioDTO = patrimonioDTO;
    }

    private void resetForm() {
        patrimonioDTO = new PatrimonioDTO();
    }

    public void selectedMarca(ValueChangeEvent event) {
        Long selectedId = (Long) event.getNewValue();
        for (MarcaDTO m : marcaDTOS) {
            if (m.getId().equals(selectedId))  {
                patrimonioDTO.setMarca(new MarcaDTO(m.getId(), m.getNome()));
            }
        }
    }
}
