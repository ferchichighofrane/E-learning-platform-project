/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author oussa
 */
public class matiereEntity {
    private int id;
    private int java;
    private int base;
    private int programmationweb;
    private int conception;
    public int getId() {
        return id;
    }

    public matiereEntity(int id, int java, int base, int programmationweb, int conception) {
        this.id = id;
        this.java = java;
        this.base = base;
        this.programmationweb = programmationweb;
        this.conception = conception;
    }

    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getProgrammationweb() {
        return programmationweb;
    }

    public void setProgrammationweb(int programmationweb) {
        this.programmationweb = programmationweb;
    }

    public int getConception() {
        return conception;
    }

    public void setConception(int conception) {
        this.conception = conception;
    }
    

}
