package model;

import java.util.Objects;

public class Client {
	private String name;
    private String address;
    private String idClient;
    
    public Client(){
        this.name = "";
        this.address = "";
        this.idClient= "";
    }
    
    public Client(Client client){
        this.name = client.name;
        this.address = client.address;
        this.idClient=client.idClient;       		
    }
    
    public Client(String name, String address, String idC){
        this.name = name;
        this.address = address;
        this.idClient=idC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString(){
        String r = String.format("%s,%s,%s", this.name, this.address, this.idClient);
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(address, client.address) &&
                Objects.equals(idClient, client.idClient);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.address);
        hash = 59 * hash + Objects.hashCode(this.idClient);
        return hash;
    }
}
