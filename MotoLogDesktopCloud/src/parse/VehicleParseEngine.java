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
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import parse.objectmodels.Customer;
import parse.objectmodels.ObjectType;
import parse.objectmodels.ParseDataFieldNames;
import parse.objectmodels.Vehicle;

/**
 *
 * @author mark.milford
 */
public class VehicleParseEngine {
    private final ParseEngine parseEngine;
    
    public VehicleParseEngine(ParseEngine incomingParseEngine){
        this.parseEngine = incomingParseEngine;
    }
    
        /**
     * Method to create a vehicle in Parse
     * @param parseUser
     * @param incomingVehicle
     * @return true if successful
     */
    public boolean createVehicle(Vehicle incomingVehicle){
        if(incomingVehicle.isWellFormed()){
            try {
                incomingVehicle.save();
                return true;
            } catch (ParseException ex) {
                String message = "VehicleParseEngine - We failed to create the Vehicle!";
                Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
                return false;
            } 
        }else{
            String message = "VehicleParseEngine - Vehicle object was not well formed, not creating vehicle!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.WARNING, message);
            return false;
        }
    }
    
        /**
     * Method to use to get list of vehicles NEED TO TWEAK !!!! CHECK BY CUSTOMER!!!!!
     * @param garageObjectId
     * @param incomingCustomer
     * @return List<Vehicle>
     */
    public List<Vehicle> getVehicles(String garageObjectId, Customer incomingCustomer){
        List<Vehicle> vehicleList = new ArrayList<>();
        List<ParseObject> list = this.parseEngine.getChildObjects
        (ParseDataFieldNames.customerId,incomingCustomer.getObjectId(), garageObjectId, ObjectType.Vehicle);
        if(list==null){
            return vehicleList;
        }
        if(!list.isEmpty() && list.size()>0){
            for(ParseObject parseObject : list){
                Vehicle foundVehicle = new Vehicle();
                foundVehicle.setObjectId(parseObject.getObjectId());
                foundVehicle.setMake(parseObject.getString(ParseDataFieldNames.make.toString()));
                foundVehicle.setModel(parseObject.getString(ParseDataFieldNames.model.toString()));
                foundVehicle.setYear(parseObject.getInt(ParseDataFieldNames.year.toString()));

                // add to list
                vehicleList.add(foundVehicle); 
            }
        }
        return vehicleList;
    }
}
