package org.streusa.entities;

public class Supplier {

    private String _id;
    private String _rev;
    private String nome;
    private float gp;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getGp() {
        return gp;
    }

    public void setGp(float gp) {
        this.gp = gp;
    }
}
