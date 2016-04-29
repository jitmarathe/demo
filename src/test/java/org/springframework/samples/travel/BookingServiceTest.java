package org.springframework.samples.travel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.Assert;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/root-context.xml")
@Transactional
public class BookingServiceTest {

	@Autowired
    private BookingService bookingService;
    
       
	@Test
	public void testUser(){    	

		bookingService.createBasicData();

		User findUser = bookingService.findUser("testuser");

		if(findUser != null){		
			Assert.assertEquals("testuser", findUser.getUsername());
		}
	} 
    
	@Test
	public void testHotel(){    	

		bookingService.createBasicData();

		Hotel findHotel = bookingService.findHotelById(1L);

		if(findHotel != null){		
			Assert.assertEquals("Westin Diplomat", findHotel.getName());
		}
	}   

    
	@Test (expected = NullPointerException.class)
    public void testHandleException(){    	
    	List<Booking> bookings;
    	
    	bookingService.createBasicData();
    	
    	Booking booking = bookingService.createBooking(1L, "testuser");
    	
    		booking = bookingService.confirmBooking(booking, 1L, "testuser");
    		Date date1 = null;
    		Date date2 = null;
    		SimpleDateFormat fomatter = new SimpleDateFormat("MM-dd-yyyy");
    		String checkInDate = "10-28-2016";
    		String checkOutDate = "11-28-2016";
    		try{
    			date1 = fomatter.parse(checkInDate);
        		date2 = fomatter.parse(checkOutDate);
    		}catch(ParseException e) {
    			e.getMessage();
    		}
    		booking.setCheckinDate(date1);
    		booking.setCheckoutDate(date2);
    		booking.setCreditCardName("Ramesh Test");
    		booking.setCreditCard("1234567890123456");
    		booking = null;
    		booking = bookingService.persistBooking(booking, 1L, "testuser");
    		
    		bookings = bookingService.findBookings("testuser");
    		
    		Iterator<Booking> bookingIter = bookings.iterator();
    		
    		if(bookingIter.hasNext()) {
    			booking = bookingIter.next();
    			if(booking != null){		
    				Assert.assertEquals("Westin Diplomat, Oct 28, 2016 to Nov 28, 2016", booking.getDescription());
    			}
    		}
    	
    }
	
	
	@Test
	public void testBooking(){    	
		List<Booking> bookings;

		bookingService.createBasicData();

		Booking booking = bookingService.createBooking(1L, "testuser");

		booking = bookingService.confirmBooking(booking, 1L, "testuser");
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat fomatter = new SimpleDateFormat("MM-dd-yyyy");
		String checkInDate = "10-28-2016";
		String checkOutDate = "11-28-2016";
		try{
			date1 = fomatter.parse(checkInDate);
			date2 = fomatter.parse(checkOutDate);
		}catch(ParseException e) {
			e.getMessage();
		}
		booking.setCheckinDate(date1);
		booking.setCheckoutDate(date2);
		booking.setCreditCardName("Ramesh Test");
		booking.setCreditCard("1234567890123456");
		booking = bookingService.persistBooking(booking, 1L, "testuser");

		bookings = bookingService.findBookings("testuser");

		Iterator<Booking> bookingIter = bookings.iterator();

		if(bookingIter.hasNext()) {
			booking = bookingIter.next();
			if(booking != null){		
				Assert.assertEquals("Westin Diplomat, Oct 28, 2016 to Nov 28, 2016", booking.getDescription());
			}
		}
	}
	
	@Test
	public void testCancelBooking(){    	
		List<Booking> bookings;

		bookingService.createBasicData();

		Booking booking = bookingService.createBooking(1L, "testuser");

		booking = bookingService.confirmBooking(booking, 1L, "testuser");
		Date date1 = null;
		Date date2 = null;
		SimpleDateFormat fomatter = new SimpleDateFormat("MM-dd-yyyy");
		String checkInDate = "10-28-2016";
		String checkOutDate = "11-28-2016";
		try{
			date1 = fomatter.parse(checkInDate);
			date2 = fomatter.parse(checkOutDate);
		}catch(ParseException e) {
			e.getMessage();
		}
		booking.setCheckinDate(date1);
		booking.setCheckoutDate(date2);
		booking.setCreditCardName("Ramesh Test");
		booking.setCreditCard("1234567890123456");
		booking = bookingService.persistBooking(booking, 1L, "testuser");

		bookings = bookingService.findBookings("testuser");

		Iterator<Booking> bookingIter = bookings.iterator();

		if(bookingIter.hasNext()) {
			booking = bookingIter.next();
			if(booking != null){
				bookingService.cancelBooking(booking.getId());
				Assert.assertEquals("Westin Diplomat, Oct 28, 2016 to Nov 28, 2016", booking.getDescription());
			}
		}
		
		bookings = bookingService.findBookings("testuser");
		Assert.assertTrue(bookings.isEmpty());
		
	}
	
}
