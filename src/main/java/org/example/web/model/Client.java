package org.example.web.model;

import javax.validation.constraints.NotEmpty;

public class Client {

    private Integer id;
    @NotEmpty
    private String fio;

    private String numberPhone;

    private String carModel;

    private Integer yearCar;

    private String numberCar;

    private Integer mileage;

    private String vinCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Integer getYearCar() {
        return yearCar;
    }

    public void setYearCar(Integer yearCar) {
        this.yearCar = yearCar;
    }

    public String getNumberCar() {
        return numberCar;
    }

    public void setNumberCar(String numberCar) {
        this.numberCar = numberCar;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", carModel='" + carModel + '\'' +
                ", yearCar=" + yearCar +
                ", numberCar='" + numberCar + '\'' +
                ", mileage=" + mileage +
                ", vinCode='" + vinCode + '\'' +
                '}';
    }
}
