package org.example.web.model;

public class CompleteWork {

   private String code;

   private String name;

   private String price;

   private String master;

    public CompleteWork() {
    }

    public CompleteWork(String code, String name, String price,String master) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.master = master;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "CompleteWork{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
