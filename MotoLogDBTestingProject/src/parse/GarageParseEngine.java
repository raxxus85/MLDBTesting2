/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import motolog.MotoLogEngine;
import parse.objectmodels.Garage;
import parse.objectmodels.ObjectType;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import parse.objectmodels.ParseDataFieldNames;

/**
 *
 * @author mark.milford
 */
public class GarageParseEngine {
    private ParseEngine parseEngine;
    
    public GarageParseEngine(ParseEngine incomingParseEngine){
        this.parseEngine = incomingParseEngine;
    }
    
    
    /**
     * Method used to ensure a user has ONE garage
     * <li> if user has one, do nothing
     * <li> if user as zero, create garage
     * <li> if user has more than one, break(throw severe) like a little baby
     * @param user 
     */
    public boolean ensureUserHasGarage(ParseUser parseUser){
        int numberOfGarages = this.getUserGarageCount(parseUser);
        if(numberOfGarages == 0){
            // if 0 garages, create a new garage for user
            Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.INFO, "GarageParseEngine - User has no Garage - creating it...");
            
            // create new Garage
            Garage newGarage = new Garage();
            newGarage.setName(parseUser.getUsername() + "'s Garage");
            newGarage.setParseUser(parseUser);
            this.createGarage(newGarage);
            // ensure creation was successflu?
            numberOfGarages = this.getUserGarageCount(parseUser);
        }else if(numberOfGarages > 1){
            Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.SEVERE, "GarageParseEngine - User has more than one garage!");
        }else if(numberOfGarages == 1){
            Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.INFO, "GarageParseEngine - User had exactly one garage.");
        }
        
        return(numberOfGarages == 1);
        
    }
    
    /**
     * Method used to create the user's garage
     * 
     * @param parseUser
     * @param garageName
     * @return true if created
     */
    public boolean createGarage(Garage garageToCreate){
        try {
            garageToCreate.save();
            Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.INFO, "GarageParseEngine - User Garage creation successful");
            return true;
        } catch (ParseException ex) {
            String message = "GarageParseEngine - Something failing creating user garage!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
            return false;
        }
    }
    
    /**
     * Method to get the users Garage Count
     * <li> Should be either 0(for new user) or 1<li>
     * @param parseUser
     * @return integer - number of Garages
     */
    public int getUserGarageCount(ParseUser parseUser){
        return this.parseEngine.getObjectCount(parseUser, ObjectType.Garage);
        
    }
    
    
  
    
    /**
     * Method used to return the GarageObjectId for a specific user
     * <li> User should only have ONE<li>
     * @param parseUser
     * @return String garageObjectId for user
     */
    public String getUserGarageObjectId(ParseUser parseUser){
        List<Garage> garageList = new ArrayList<Garage>();
        List<ParseObject> list = this.parseEngine.getGarages(parseUser);
        if(list==null){
            return null;
        }else if(list.size() >1){
            String message = "User has " + list.size() + " Garages! Should only have 0 or 1!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message);
        }else if(!list.isEmpty() && list.size() == 1){
            for(ParseObject parseObject : list){
                Garage garage = new Garage();
                garage.setObjectId(parseObject.getObjectId());
                garage.setName(parseObject.getString(ParseDataFieldNames.name.toString()));
                // add to list
                garageList.add(garage); 
            }
        }
        return garageList.get(0).getObjectId();
    }
    
    
}
