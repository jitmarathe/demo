package org.springframework.samples.travel;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("booking")
public class HotelsController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private BookingService bookingService;
	
	static int status = 0;
	
	/*@Autowired
    @Qualifier("bookingValidator")
    private Validator validator;
 
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }*/

	@Inject
	public HotelsController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@RequestMapping(value = "/hotels/search", method = RequestMethod.GET)
	public void search(SearchCriteria searchCriteria, Principal currentUser, Model model) {
		logger.info("Start  search method");
		if(status++ == 0){
			bookingService.createBasicData();
		}
		if (currentUser != null) {
			List<Booking> booking = bookingService.findBookings(currentUser.getName());
			model.addAttribute(booking);
		}
		logger.info("End of search method");
	}

	@RequestMapping(value = "/hotels", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		logger.info("Start  Hotel list method");
		List<Hotel> hotels = bookingService.findHotels(criteria);		
		model.addAttribute(hotels);
		logger.info("End of Hotel list method");
		return "hotels/list";
	}


	@RequestMapping(value = "/hotels/{id}", method = RequestMethod.GET)
	public String show(@PathVariable Long id, Model model) {
		logger.info("Start  Hotel detail method");
		model.addAttribute(bookingService.findHotelById(id));
		logger.info("End of Hotel detail method");
		return "hotels/show";
	}
	
	@RequestMapping(value = "/booking/{id}", method = RequestMethod.GET)
	public String addBooking(@PathVariable Long id, Principal currentUser, Model model) {
		logger.info("Start addBooking method");
		if (currentUser != null) {
			Booking bookingObj = bookingService.createBooking(id, currentUser.getName());
			model.addAttribute("booking", bookingObj);
			return "enterBookingDetails";
		}
		logger.info("End addBooking method");
		
		return "redirect:../users/login";
	}
	
	@RequestMapping(value = "/booking/{id}", method = RequestMethod.POST)
	public String displayBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult result, Model model, HttpServletRequest servletRequest, @PathVariable Long id,
			Principal currentUser) {
		logger.info("Start  display Booking method");
		if (result.hasErrors()) {
			return "enterBookingDetails";
		}
		
		if (currentUser != null) {
			Booking bookingObj = bookingService.confirmBooking(booking, id, currentUser.getName());
			model.addAttribute("booking", bookingObj);
		}
		logger.info("End  display Booking method");
		return "reviewBooking";
	}

	
	@RequestMapping(value = "/bookingSaved/{id}", method = RequestMethod.POST)
	public String saveBooking(@ModelAttribute("booking") Booking booking, BindingResult result, @PathVariable Long id, Principal currentUser,Model model) {
		logger.info("Start  persist Booking method");
		if (currentUser != null) {
			model.addAttribute("booking", bookingService.persistBooking(booking, id, currentUser.getName()));
		}
		logger.info("End  persist Booking method");
		return "confirmBooking";
	}
	
	@RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
	public String deleteBooking(@PathVariable Long id) {
		bookingService.cancelBooking(id);
		return "redirect:../hotels/search";
	}

}