/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import motolog.MotoLogEngine;
import parse.objectmodels.Mechanic;
import parse.objectmodels.ObjectType;
import parse.objectmodels.Garage;
import parse.objectmodels.Vehicle;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.util.ParseRegistry;
import parse.objectmodels.Customer;
import parse.objectmodels.ParseDataFieldNames;

/**
 *
 * @author mark.milford
 */
public class ParseEngine {
    private MotoLogEngine motoLogEngine;
    private ParseUser currentParseUser = null;
    private String garageObjectId = "";
    // Helper Classes
    public MechanicParseEngine mechanicParseEngine;
    public GarageParseEngine garageParseEngine;
    public CustomerParseEngine customerParseEngine;
    public VehicleParseEngine vehicleParseEngine;
    // data for DEV environment
    private final String appId = "ijXu9OWAFfekiM8hFlLjrHa7A2BK3kWir8X1v5nM";
    private final String restApiAppId ="sB8eqMDf5M8gyEzgkwJ8EzQRhYGaTeAbTPhfhTNE";
    
    
    /**
     * Main constructor for ParseEngine
     * @param incomingMotoLogEngine - the MotoLogEngine that will be used for operations
     */
    public ParseEngine(MotoLogEngine incomingMotoLogEngine){
        this.motoLogEngine = incomingMotoLogEngine;
        // register subclasses
        ParseRegistry.registerSubclass(Customer.class); 
        ParseRegistry.registerSubclass(Garage.class);
        ParseRegistry.registerSubclass(Mechanic.class);
        ParseRegistry.registerSubclass(Vehicle.class);
        
        // init Parse
        Parse.initialize(appId, restApiAppId);
        // init helper classes
        this.mechanicParseEngine = new MechanicParseEngine(this);
        this.garageParseEngine = new GarageParseEngine(this);
        this.customerParseEngine = new CustomerParseEngine(this);
        this.vehicleParseEngine = new VehicleParseEngine(this);
    }
    
    public String getCurrentGarageObjectId(){return this.garageObjectId;}
    
    public ParseUser getCurrentParseUser(){return this.currentParseUser;}
       
    /**
     * Method used to count objects from Parse
     * Requires the garageObjectId and the ObjectType
     * @param garageObjectId
     * @param objectType the objectType that needs counted
     * @return integer, the amount of that object (0-n)
     */
    public int getObjectCount(String garageObjectId, ObjectType objectType){
        String objectBeingCounted = objectType.toString();
        
        ParseQuery<ParseObject> basicQuery = ParseQuery.getQuery(objectBeingCounted);
            //basicQuery.whereEqualTo(ParseDataFieldNames.user.toString(), parseUser);
            basicQuery.whereEqualTo(ParseDataFieldNames.garageObjectId.toString(), this.garageObjectId);
            basicQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    if(objectList == null || objectList.isEmpty()){
                        String message = "ParseEngine- 0 " + objectBeingCounted + " found.";
                        Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.INFO, message, e);
                    }
                    else{
                        String message = "ParseEngine - Counted " + objectList.size() + " " + objectBeingCounted;
                        Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.INFO, message, e);
                    }
                } else {
                    Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
        });
        // Time to extract the object count now
        int objectCount = 0;
        try {
            objectCount = basicQuery.count();
        } catch (ParseException ex) {
            String message = "ParseEngine- Error attempting to get count from BasicQuery...";
            Logger.getLogger(MechanicParseEngine.class.getName()).log(Level.SEVERE, message + ex.getMessage(), ex);
        }
        return objectCount;
    }
    
    /**
     * Method used to retrieve objects from Parse that are children of other objects
     * Used for Vehicles, Maintenance Actions, etc
     * @param parentFieldName
     * @param parentStringId
     * @param garageObjectId
     * @param objectType
     * @return List of ParseObjects
     */
    public List<ParseObject> getChildObjects(ParseDataFieldNames parentFieldName, String parentStringId, String garageObjectId, ObjectType objectType){
        String objectsToGet = objectType.toString();
        ParseQuery<ParseObject> basicQuery = ParseQuery.getQuery(objectsToGet);
            basicQuery.whereEqualTo(ParseDataFieldNames.garageObjectId.toString(), this.garageObjectId);
            basicQuery.whereEqualTo(parentFieldName.toString(), parentStringId);
            basicQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    // No need for anything to be done if no exception.... 
                } else {
                    String message = "ParseEngine - Something failing getting objects!";
                    Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, e);
                }
            }
        });
        // Time to extract actual list
        List<ParseObject> parseObjectList = null;
        try {
            parseObjectList = basicQuery.find();
        } catch (ParseException ex) {
            String message = "ParseEngine - Something failed compling list of ParseObjects!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
        }
        return parseObjectList;  
    }
    
    
    /**
     * Method used to retrieve objects from Parse
     * Used for Mechanics, Customers
     * @param garageObjectId
     * @param objectType
     * @return List<ParseObject>
     */
    public List<ParseObject> getObjects(String garageObjectId, ObjectType objectType){   
        String objectsToGet = objectType.toString();
        ParseQuery<ParseObject> basicQuery = ParseQuery.getQuery(objectsToGet);
            basicQuery.whereEqualTo(ParseDataFieldNames.garageObjectId.toString(), this.garageObjectId);
            basicQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    // No need for anything to be done if no exception.... 
                } else {
                    String message = "ParseEngine - Something failing getting objects!";
                    Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, e);
                }
            }
        });
        // Time to extract actual list
        List<ParseObject> parseObjectList = null;
        try {
            parseObjectList = basicQuery.find();
        } catch (ParseException ex) {
            String message = "ParseEngine - Something failed compling list of ParseObjects!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
        }
        return parseObjectList;  
    }
    
    /**
     * Method used to retrieve objects from Parse without GarageObjectId (only a Garage for now...)
     * @param parseUser
     * @return List<ParseObject> 
     */
    public List<ParseObject> getGarages(ParseUser parseUser){
        String objectsToGet = ObjectType.Garage.toString();
        ParseQuery<ParseObject> basicQuery = ParseQuery.getQuery(objectsToGet);
            basicQuery.whereEqualTo(ParseDataFieldNames.user.toString(), parseUser);
            basicQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    // No need for anything to be done if no exception.... 
                    //System.out.println("TEST");
                } else {
                    String message = "ParseEngine - Something failed getting objects!";
                    Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, e);
                }
            }
        });
        // Time to extract actual list
        List<ParseObject> parseObjectList = null;
        try {
            // common break point?
            //System.out.println("TESTING HERE IS THE COUNT" + basicQuery.count());
            parseObjectList = basicQuery.find();
        } catch (ParseException ex) {
            String message = "ParseEngine - Something failed getting Objects from Query!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message, ex);
        }
        return parseObjectList;  
    }
    
    /**
     * Method to return the mechanic parse engine, which is used for mechanic related stuff
     * @return 
     */
    public MechanicParseEngine getMechanicParseEngine(){
        return this.mechanicParseEngine;
    }
    
    /**
     * Method used to get the Garage Parse Engine, which is used for Garage related stuff
     * @return 
     */
    public GarageParseEngine getGarageParseEngine(){
        return this.garageParseEngine;
    }
    
    /**
     * Method used to get the Customer Parse Engine
     * @return 
     */
    public CustomerParseEngine getCustomerParseEngine(){ return this.customerParseEngine; }
    
    public VehicleParseEngine getVehicleParseEngine(){ return this.vehicleParseEngine; }
    
    /**
     * Method used to login, will return ParseUser object
     * <li> Should query to ensure there is ONE Garage associated with that User, if not, create one <li>
     * <li> If we find multiple garages, wooo boy something is wrong! break! <li>
     * @param login
     * @param password
     * @return ParseUser object
     */
    public ParseUser signInUser(String login, String password){
        // Step 1 : Login
        ParseUser user = new ParseUser();
        user.setUsername(login);
        user.setPassword(password);  
        
        try {
            user = user.login(login,password);
            user.save();
            this.currentParseUser = user;
        }catch (ParseException ex) {
            int parseExceptionCode = ex.getCode();
            switch (parseExceptionCode) {
                case 101:
                    System.out.println(parseExceptionCode + " Invalid login parameters! "
                            + "Check your e-mail address and password and try again.");
                    //this.motoLogEngine.getDialogFactory().createDialogMessage(incomingComponent, DialogType.WARNING_MESSAGE, "Invalid login parameters! Check your login and password and try again.");
                    break;
                case 100:
                    System.out.println(parseExceptionCode + " An error occured attempting to reach MotoLog Cloud. "
                            + "Please check your network connection and try again.");
                    //this.motoLogEngine.getDialogFactory().createDialogMessage(incomingComponent, DialogType.WARNING_MESSAGE, "An error occured attempting to reach MotoLog Cloud Servers. Please check your internet connection and try again. If problem persists, please contact MotoLog Support.");
                    break;
                default:
                    System.out.println(parseExceptionCode + " Unexpected error occured, please report.");
                    //this.motoLogEngine.getDialogFactory().createDialogMessage(incomingComponent, DialogType.WARNING_MESSAGE, "An error occured attempting to login, please report." + ex.toString());
            }
  
        }    
        
        // Step 2: Ensure user has one Garage and get garageobjectId (will take 2 calls...?)
        boolean userHasOneGarage = this.garageParseEngine.ensureUserHasGarage(this.currentParseUser);
        if(!userHasOneGarage){
            String message = "ParseEngine - We failed to create the Garage!!! Must quit!!";
            Logger.getLogger(MotoLogEngine.class.getName()).log(Level.SEVERE, message);
            System.exit(0);
        }else{
            this.garageObjectId = this.garageParseEngine.getUserGarageObjectId(user);
        }
        
        // Step 3: Return User
        return user;
    }
    
    
    /**
     * Method used to create a new MotoLog User
     * @param email
     * @param password
     * @return ParseUser object
     */
    public ParseUser signUpUser(String email, String password){
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);       

        try {
            user.signUp();
            this.currentParseUser = user;                   
        } catch (ParseException ex) {
            int parseExceptionCode = ex.getCode();
            switch (parseExceptionCode) {
                case 202:
                    //this.motoLogEngine.getDialogFactory().createDialogMessage(incomingComponent, DialogType.WARNING_MESSAGE, "Email address already taken!");
                    
                    System.out.println("ParseEngine89 -  Email address already taken!");
                    user = null;
                    break;
            }

        }        
        return user;
        
    }
}
