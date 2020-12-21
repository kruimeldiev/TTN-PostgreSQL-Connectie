package org.example;

public class MQ135DataModel {

    String moduleNaam;
    Integer ppmWaarde;

    public MQ135DataModel(String moduleNaam, Integer ppmWaarde) {
        this.moduleNaam = moduleNaam;
        this.ppmWaarde = ppmWaarde;
    }

    public String getModuleNaam() {
        return moduleNaam;
    }

    public void setModuleNaam(String moduleNaam) {
        this.moduleNaam = moduleNaam;
    }

    public Integer getPpmWaarde() {
        return ppmWaarde;
    }

    public void setPpmWaarde(Integer ppmWaarde) {
        this.ppmWaarde = ppmWaarde;
    }
}
