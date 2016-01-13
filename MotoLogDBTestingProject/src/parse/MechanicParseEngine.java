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
import parse.objectmodels.Mechanic;
import parse.objectmodels.ObjectType;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.ParseFile;
import parse.objectmodels.ParseDataFieldNames;

/**
 * Class used exclusively to interact with Mechanic Objects
 * <li> Mechanic Creation, Editing, Deletion <li>
 * <li> Mechanic querying<li>
 * @author mark.milford
 */
public class MechanicParseEngine {
    private final ParseEngine parseEngine;
    
    public MechanicParseEngine(ParseEngine incomingParseEngine){
        this.parseEngine = incomingParseEngine;
    }
    
    /**
     * Method used to create a mechanic
     * @param Mechanic mechanicToCreate
     * @return true if successful
     */
    public boolean createMechanic(Mechanic mechanic){
        if(mechanic.isWellFormed()){
            try {
                mechanic.save();
                return true;
            } catch (ParseException ex) {
                String message = "MechanicParseEngine - We failed to create the Mechanic!";
                Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
                return false;
            } 
        }else{
            String message = "MechanicParseEngine - Mechanic object was not well formed, not creating mechanic!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.WARNING, message);
            return false;
        }
    }
    
    /**
     * Method to return all the mechanics for a specific Garage
     * @param parseUser
     * @param incomingGarageObjectId
     * @return 
     */
    public List<Mechanic> getMechanics(ParseUser parseUser){
        List<Mechanic> mechanicList = new ArrayList<>();
        List<ParseObject> list = this.parseEngine.getObjects(parseUser, ObjectType.Mechanic);
        if(list==null){
            return mechanicList;
        }

        if(!list.isEmpty() && list.size()>0){
            for(ParseObject parseObject : list){
                Mechanic newMechanic = new Mechanic();
                newMechanic.setObjectId(parseObject.getObjectId());
                newMechanic.setFirstName(parseObject.getString(ParseDataFieldNames.firstName.toString()));
                newMechanic.setMiddleName(parseObject.getString(ParseDataFieldNames.middleName.toString()));
                newMechanic.setLastName(parseObject.getString(ParseDataFieldNames.lastName.toString()));
                newMechanic.setDescription(parseObject.getString(ParseDataFieldNames.description.toString()));
                // add to list
                mechanicList.add(newMechanic); 
            }
        }
        return mechanicList;
    }
}
