package com.driver.model;

import javax.persistence.*;

@Entity
public class TripBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tripBookingId;

    String fromLocation;
    String toLocation;

    int driverId;
    int customerId;
    int distenceKm;
    @Enumerated(EnumType.STRING)
    TripStatus tripStatus;
    int bill;

    public TripBooking() {
    }

    public TripBooking(int tripBookingId, String fromLocation, String toLocation, int driverId,
                       int customerId, int distenceKm, TripStatus tripStatus, int bill) {
        this.tripBookingId = tripBookingId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.driverId = driverId;
        this.customerId = customerId;
        this.distenceKm = distenceKm;
        this.tripStatus = tripStatus;
        this.bill = bill;
    }

    public int getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(int tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public int getDistenceKm() {
        return distenceKm;
    }

    public void setDistenceKm(int distenceKm) {
        this.distenceKm = distenceKm;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getBill() {
        return bill;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}