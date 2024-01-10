/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import swing.table.EventAction;
import swing.table.ModelAction;
import swing.table.ModelProfile;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author oussa
 */
public class userEntity {
    private int id;
    private String firstName;
    private String familyName;
    private String email;
    private String password;
    private int type;
    private String dateOfBirth;
    private matiereEntity matieres;

    public userEntity(int id, String name, String email, String password) {
       this.id=id ;
       this.firstName=name;
       this.email=email;
       this.password=password;
    }
public userEntity colne(){
   userEntity colne= new userEntity();
    colne.setId(id);
    colne.setFirstName(firstName); 
        colne.setFamilyName(familyName); 
        colne.setEmail(email); 
      
        colne.type = type;
        return colne;
}
    public userEntity(String firstName, String familyName, String email, String password, int type, String dateOfBirth) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
    }

    public userEntity() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setId(int id) {
        this.id = id;
    }
public ModelStudent changeToModelStudent(){
    String s="";
    if (matieres!=null) {
        if (matieres.getJava()!=0) 
            s="Java";
            
        if (matieres.getBase()!=0) 
            s=s+"/data base";
           
        if (matieres.getProgrammationweb()!=0) 
            s=s+"/ prog web";
           
        if (matieres.getConception()!=0) 
            s=s+"conception";
           
        
    
    }
 
    return new ModelStudent(id,new ImageIcon(getClass().getResource("/icons/profile1.jpg")),firstName,familyName,s,0);
    
}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setMatieres(matiereEntity matieres) {
        this.matieres = matieres;
    }

}
