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
import parse.objectmodels.ObjectType;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import parse.objectmodels.Customer;
import parse.objectmodels.ParseDataFieldNames;

/**
 *
 * @author mark.milford
 */
public class CustomerParseEngine {
    private final ParseEngine parseEngine;
    
    public CustomerParseEngine(ParseEngine incomingParseEngine){
        this.parseEngine = incomingParseEngine;
    }
    
    /**
     * Method to create a customer in Parse
     * @param parseUser
     * @param incomingCustomer
     * @return true if successful
     */
    public boolean createCustomer(Customer incomingVehicle){
        if(incomingVehicle.isWellFormed()){
            try {
                incomingVehicle.save();
                return true;
            } catch (ParseException ex) {
                String message = "CustomerParseEngine - We failed to create the Customer!";
                Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
                return false;
            } 
        }else{
            String message = "CustomerParseEngine - Customer object was not well formed, not creating customer!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.WARNING, message);
            return false;
        }
    }
    
    /**
     * Method to use to get list of customers
     * @param parseUser
     * @return 
     */
    public List<Customer> getCustomers(String garageObjectId){
        List<Customer> customerList = new ArrayList<>();
        List<ParseObject> list = this.parseEngine.getObjects(garageObjectId, ObjectType.Customer);
        if(list==null){
            return customerList;
        }
        if(!list.isEmpty() && list.size()>0){
            for(ParseObject parseObject : list){
                Customer newCustomer = new Customer();
                newCustomer.setObjectId(parseObject.getObjectId());
                newCustomer.setFirstName(parseObject.getString(ParseDataFieldNames.firstName.toString()));
                newCustomer.setMiddleName(parseObject.getString(ParseDataFieldNames.middleName.toString()));
                newCustomer.setLastName(parseObject.getString(ParseDataFieldNames.lastName.toString()));
                newCustomer.setDescription(parseObject.getString(ParseDataFieldNames.description.toString()));
                // add to list
                customerList.add(newCustomer); 
            }
        }
        return customerList;
    }
}
