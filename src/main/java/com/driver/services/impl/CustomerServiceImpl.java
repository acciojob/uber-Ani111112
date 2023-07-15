package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		if (customerRepository2.existsById(customerId)) {
			customerRepository2.deleteById(customerId);
		}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		if (!customerRepository2.existsById(customerId))return null;

		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		List<Driver> driverList = driverRepository2.findAll();
		Driver driver = null;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < driverList.size(); i++) {
			Driver driver1 = driverList.get(i);
			Cab cab = driver1.getCab();
			if (driver1.getDriverId() < min && cab.isAvailable()) {
				driver = driver;
			}
		}

		if (driver != null) {
			TripBooking tripBooking = new TripBooking();
			tripBooking.setDistenceKm(distanceInKm);
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setCustomerId(customerId);
			tripBooking.setDriverId(driver.getDriverId());

			TripBooking saveTrip = tripBookingRepository2.save(tripBooking);

			Customer customer = optionalCustomer.get();
			customer.getTripBookingList().add(saveTrip);
			driver.getTripBookingList().add(saveTrip);
			Cab cab = driver.getCab();
			cab.setAvailable(false);
			customerRepository2.save(customer);
			driverRepository2.save(driver);
			return saveTrip;
		}else throw new Exception("No cab available!");
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		completeTrip(tripId);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		if (tripBookingRepository2.existsById(tripId)) {
			Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);
			TripBooking tripBooking = optionalTripBooking.get();
			Driver driver = driverRepository2.getOne(tripBooking.getDriverId());
			Customer customer = customerRepository2.getOne(tripBooking.getCustomerId());
			Cab cab = driver.getCab();
			//remove from driver
			List<TripBooking> driverTripList = driver.getTripBookingList();
			for (TripBooking trip : driverTripList) {
				if (trip.getTripBookingId() == tripBooking.getTripBookingId()) {
					driverTripList.remove(trip);
				}
			}
			driver.setTripBookingList(driverTripList);

			//remove from customer
			List<TripBooking> customerTripList = customer.getTripBookingList();
			for (TripBooking trip : customerTripList) {
				if (trip.getTripBookingId() == tripBooking.getTripBookingId()) {
					customerTripList.remove(trip);
				}
			}
			customer.setTripBookingList(customerTripList);

			//make cab  free
			cab.setAvailable(true);
			//save every entity
			driverRepository2.save(driver);
			customerRepository2.save(customer);
		}
	}
}
