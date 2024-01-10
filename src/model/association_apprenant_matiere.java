/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author oussa
 */
public class association_apprenant_matiere {
    private int id_apprenant;
    private int id_matiere;
    private int isConserned;
    private int progres;

    public association_apprenant_matiere(int id_apprenant, int id_matiere, int isConserned, int progres) {
        this.id_apprenant = id_apprenant;
        this.id_matiere = id_matiere;
        this.isConserned = isConserned;
        this.progres = progres;
    }

    public association_apprenant_matiere() {
    }

    public int getId_apprenant() {
        return id_apprenant;
    }

   
    public int getId_matiere() {
        return id_matiere;
    }

   

    public int getIsConserned() {
        return isConserned;
    }

    public void setIsConserned(int isConserned) {
        this.isConserned = isConserned;
    }

    public int getProgres() {
        return progres;
    }

    public void setProgres(int progres) {
        this.progres = progres;
    }
    
}
