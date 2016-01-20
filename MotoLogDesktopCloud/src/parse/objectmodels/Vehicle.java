/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parse.objectmodels;

import org.parse4j.ParseClassName;

/**
 * Vehicle ParseObject
 * @author mark.milford
 */
@ParseClassName("Vehicle")
public class Vehicle extends BaseParseObject{
    // Vital field, links Vehicle to Customer
    private String customerId;
    // Vehicle Specific Fields
    private String make;
    private String model;
    private Integer year;
    
    public Vehicle(){}
    
    public String getCustomerId(){
        return this.customerId;
    }
    
    public void setCustomerId(String incomingCustomerId){
        this.customerId = incomingCustomerId;
        this.put(ParseDataFieldNames.customerId.toString(), incomingCustomerId);
    }
    
    public String getMake(){
        return this.make;
    }
    
    public void setMake(String incomingMake){
        this.make = incomingMake;
        this.put(ParseDataFieldNames.make.toString(), incomingMake);
    }
    
    public String getModel(){
        return this.model;
    }
    
    public void setModel(String incomingModel){
        this.model = incomingModel;
        this.put(ParseDataFieldNames.model.toString(), incomingModel);
    }
    
    public Integer getYear(){ return this.year; }
    
    public void setYear(Integer incomingYear){
        this.year = incomingYear;
        this.put(ParseDataFieldNames.year.toString(), incomingYear);
    }
}
