package org.acme.dto;

import java.util.List;

public class TypeResponseDTO {
    private String tipo;
    private List<String> forteContra;
    private List<String> fracoContra;
    private List<String> meioForteContra;
    private List<String> meioFracoContra;
    private List<String> naoDanoContra;
    private List<String> naoDanoRecebe;

    public TypeResponseDTO(String tipo,
                           List<String> forteContra,
                           List<String> fracoContra,
                           List<String> meioForteContra,
                           List<String> meioFracoContra,
                           List<String> naoDanoContra,
                           List<String> naoDanoRecebe) {
        this.tipo = tipo;
        this.forteContra = forteContra;
        this.fracoContra = fracoContra;
        this.meioForteContra = meioForteContra;
        this.meioFracoContra = meioFracoContra;
        this.naoDanoContra = naoDanoContra;
        this.naoDanoRecebe = naoDanoRecebe;
    }

        public String getTipo() {
        return tipo;
    }

    public List<String> getForteContra() {
        return forteContra;
    }

    public List<String> getFracoContra() {
        return fracoContra;
    }

    public List<String> getMeioForteContra() {
        return meioForteContra;
    }

    public List<String> getMeioFracoContra() {
        return meioFracoContra;
    }

    public List<String> getNaoDanoContra() {
        return naoDanoContra;
    }

    public List<String> getNaoDanoRecebe() {
        return naoDanoRecebe;
    }

}
