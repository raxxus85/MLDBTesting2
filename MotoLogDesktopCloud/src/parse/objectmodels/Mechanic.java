/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse.objectmodels;

import org.parse4j.ParseClassName;
import org.parse4j.ParseUser;
import org.parse4j.ParseClassName;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;

/**
 *
 * @author mark.milford
 */
@ParseClassName("Mechanic")
public class Mechanic extends BaseParseObject{
    private String firstName;
    private String middleName;
    private String lastName;
    private String description;
    
    public Mechanic(){}
    
    public void setDescription(String incomingDescription){
        this.description = incomingDescription;
        //this.put("description", incomingDescription);
        //this.put(dataFieldNames.description.toString(), incomingDescription);
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public void setFirstName(String incomingFirstName){
        this.firstName = incomingFirstName;
        //this.put("firstName", incomingFirstName);
        this.put(ParseDataFieldNames.firstName.toString(), incomingFirstName);
    }
    
    public String getMiddleName(){
        return this.middleName;
    }
    
    public void setMiddleName(String incomingMiddleName){
        this.middleName = incomingMiddleName;
        //this.put("middleName", incomingMiddleName);
        this.put(ParseDataFieldNames.middleName.toString(), incomingMiddleName);
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public void setLastName(String incomingLastName){
        this.lastName = incomingLastName;
        //this.put("lastName", incomingLastName);
        this.put(ParseDataFieldNames.lastName.toString(), incomingLastName);
    }
    
    /**
     * Override toString to display relevant info etc
     * @return 
     */
    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName();
    }
}
