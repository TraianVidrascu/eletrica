package model;

import java.util.Objects;

public class Issue {
    private Client client;
    private int year;
    private int month;
    private float toPay;
    private float paid;

    public Issue() {
        this.client = new Client();
        this.year = 0;
        this.month = 0;
        this.toPay = 0;
        this.paid = 0;
    }

    public Issue(Issue issue) {
        this.client = issue.client;
        this.year = issue.year;
        this.month = issue.month;
        this.toPay = issue.toPay;
        this.paid = issue.paid;
    }

    public Issue(Client client, int year, int month, float toPay, float paid) {
        this.client = client;
        this.year = year;
        this.month = month;
        this.toPay = toPay;
        this.paid = paid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getToPay() {
        return toPay;
    }

    public void setToPay(float toPay) {
        this.toPay = toPay;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        String r = String.format("%s" + System.getProperty("line.separator") + "%d,%d,%2.0f,%2.0f",
                this.client.toString(), this.year, this.month, this.toPay, this.paid);
        return r;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Issue)) return false;
        Issue i = (Issue) object;
        if (i.client == null) return false;
        if ((i.month == this.month) && (i.year == this.year) && (i.client.equals(this.client))) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.client);
        hash = 71 * hash + this.year;
        hash = 71 * hash + this.month;
        return hash;
    }
}
