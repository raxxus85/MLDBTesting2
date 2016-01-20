/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse.objectmodels;

import org.parse4j.ParseClassName;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;

/**
 * Garage Parse Object
 * <li>Doesn't extend BaseParseObject as it doesn't have a garageObjectId<li>
 * @author mark.milford
 */
@ParseClassName("Garage")
public class Garage extends ParseObject{
    private String name;
    private ParseUser parseUser;
  
    public Garage(){}
    
    public void setName(String incomingName){
        this.name = incomingName;
        this.put(ParseDataFieldNames.name.toString(), incomingName);
    }
    
    public String getName(){
        return this.name;
    }
    
    public ParseUser getParseUser(){ return this.parseUser; }
    
    public void setParseUser(ParseUser incomingParseUser){ 
        this.parseUser = incomingParseUser; 
        this.put(ParseDataFieldNames.user.toString(), incomingParseUser);
    }
}
