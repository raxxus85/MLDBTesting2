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
 *
 * @author mark.milford
 */
abstract class BaseParseObject extends ParseObject{
    private String garageObjectId;
    private ParseUser parseUser;
        
    public void setGarageObjectId(String incomingGarageObjectId){
        this.garageObjectId = incomingGarageObjectId;
        //this.put("garageObjectId", incomingGarageObjectId);
        this.put(ParseDataFieldNames.garageObjectId.toString(), incomingGarageObjectId);
    }
    
    public String getGarageObjectId(){
        return this.garageObjectId;
    }
    
    public ParseUser getParseUser(){ return this.parseUser; }
    
    public void setParseUser(ParseUser incomingParseUser){ 
        this.parseUser = incomingParseUser; 
        this.put(ParseDataFieldNames.user.toString(), incomingParseUser);
    }
    
    /**
     * Method to ensure garage object id and parse user aren't null
     * <li> NOTE : any NULL ParseObject values breaks !! <li>
     * @return 
     */
    public boolean isWellFormed(){
        if(this.getGarageObjectId()== null || this.getParseUser() == null){
            return false;
        }else{
            return true;
        }
    }
}
