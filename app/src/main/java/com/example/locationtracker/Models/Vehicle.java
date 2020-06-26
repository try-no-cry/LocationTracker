package com.example.locationtracker.Models;


public class Vehicle {

    private class Location{
        public  String _id,latitude,longitude;
    }

    private String _id,owner,contact,vehicleRegNo,vehicleType;

    private Location currentLocation;

    public Vehicle(String _id, String owner, String contact, String vehicleRegNo, String vehicleType, Location currentLocation) {
        this._id = _id;
        this.owner = owner;
        this.contact = contact;
        this.vehicleRegNo = vehicleRegNo;
        this.vehicleType = vehicleType;
        this.currentLocation = currentLocation;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
