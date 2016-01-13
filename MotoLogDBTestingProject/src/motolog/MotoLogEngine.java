/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motolog;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parse.objectmodels.Garage;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import parse.ParseEngine;
import parse.objectmodels.Mechanic;
import parse.objectmodels.ObjectType;
import parse.objectmodels.Customer;
import parse.objectmodels.Vehicle;

/**
 *
 * @author mark.milford
 */
public class MotoLogEngine {
    private ParseEngine parseEngine;

    public MotoLogEngine(){
        this.parseEngine = new ParseEngine(this);
    }
    
    public ParseEngine getParseEngine(){
        return this.parseEngine;
    }
          
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MotoLogEngine motoLogEngine = new MotoLogEngine();

        
        
        // TESTING STUFF BELOW //
        motoLogEngine.getParseEngine().signInUser("mark.p.milford@gmail.com", "bkelly69");
        ParseUser parseUser = motoLogEngine.getParseEngine().getCurrentParseUser();
        
        String garageObjectId = motoLogEngine.getParseEngine().getCurrentGarageObjectId();
                 //Test Mechanic Creation
        Mechanic newMechanic = new Mechanic();
        newMechanic.setFirstName("Mark");
        newMechanic.setMiddleName("Patrick");
        newMechanic.setLastName("Milford");
        newMechanic.setParseUser(parseUser);
        newMechanic.setGarageObjectId(garageObjectId);
        
        
        motoLogEngine.parseEngine.getMechanicParseEngine().createMechanic(newMechanic);
        
        // get the useres garageObjectId
        //String garageObjectId = motoLogEngine.parseEngine.getGarageParseEngine().getUserGarageObjectId(parseUser);

        List<Mechanic> mechanicList = motoLogEngine.parseEngine.getMechanicParseEngine().getMechanics(parseUser);
        System.out.println("Testoutput - Time to get mechanics.. we have " + mechanicList.size());
        for(Mechanic mechanic : mechanicList){
            System.out.println("Testoutput - " + mechanic.getObjectId());
            System.out.println("Testoutput - " + mechanic.toString());
           
        }
        
        // Test Customer Creation
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Hannah");
        newCustomer.setMiddleName("Paige");
        newCustomer.setLastName("Milford");
        newCustomer.setParseUser(parseUser);
        newCustomer.setGarageObjectId(garageObjectId);
        
        
        motoLogEngine.parseEngine.getCustomerParseEngine().createCustomer(newCustomer);
        
        Customer testCustomer = null;
        List<Customer> customerList = motoLogEngine.parseEngine.getCustomerParseEngine().getCustomers(parseUser);
        System.out.println("Testoutput - Time to get customers, we have " + customerList.size());
        for(Customer customer : customerList){
            System.out.println("Testoutput - " + customer.getObjectId());
            System.out.println("Testoutput - " + customer.toString());
            testCustomer = customer;
        }
        
        // Test Vehicle Creation
        Vehicle newVehicle = new Vehicle();
        newVehicle.setCustomerId(testCustomer.getObjectId());
        newVehicle.setMake("Pontiac");
        newVehicle.setModel("Trans Am");
        newVehicle.setYear(1999);
        newVehicle.setParseUser(parseUser);
        newVehicle.setGarageObjectId(garageObjectId);
        
        motoLogEngine.parseEngine.getVehicleParseEngine().createVehicle(newVehicle);

        System.exit(0);

    }

}
