package com.skyspaceoneoneone.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.skyspaceoneoneone.dao.GenericDAO;
import com.skyspaceoneoneone.service.GenericService;
import com.skyspaceoneoneone.service.impl.GenericServiceImpl;
import com.skyspaceoneoneone.dao.BookingDAO;
import com.skyspaceoneoneone.domain.Booking;
import com.skyspaceoneoneone.dto.BookingDTO;
import com.skyspaceoneoneone.dto.BookingSearchDTO;
import com.skyspaceoneoneone.dto.BookingPageDTO;
import com.skyspaceoneoneone.dto.BookingConvertCriteriaDTO;
import com.skyspaceoneoneone.dto.common.RequestDTO;
import com.skyspaceoneoneone.dto.common.ResultDTO;
import com.skyspaceoneoneone.service.BookingService;
import com.skyspaceoneoneone.util.ControllerUtils;





@Service
public class BookingServiceImpl extends GenericServiceImpl<Booking, Integer> implements BookingService {

    private final static Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Autowired
	BookingDAO bookingDao;

	


	@Override
	public GenericDAO<Booking, Integer> getDAO() {
		return (GenericDAO<Booking, Integer>) bookingDao;
	}
	
	public List<Booking> findAll () {
		List<Booking> bookings = bookingDao.findAll();
		
		return bookings;	
		
	}

	public ResultDTO addBooking(BookingDTO bookingDTO, RequestDTO requestDTO) {

		Booking booking = new Booking();

		booking.setBookingId(bookingDTO.getBookingId());


		booking.setBookingDate(bookingDTO.getBookingDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		booking = bookingDao.save(booking);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Booking> getAllBookings(Pageable pageable) {
		return bookingDao.findAll(pageable);
	}

	public Page<Booking> getAllBookings(Specification<Booking> spec, Pageable pageable) {
		return bookingDao.findAll(spec, pageable);
	}

	public ResponseEntity<BookingPageDTO> getBookings(BookingSearchDTO bookingSearchDTO) {
	
			Integer bookingId = bookingSearchDTO.getBookingId(); 
   			String sortBy = bookingSearchDTO.getSortBy();
			String sortOrder = bookingSearchDTO.getSortOrder();
			String searchQuery = bookingSearchDTO.getSearchQuery();
			Integer page = bookingSearchDTO.getPage();
			Integer size = bookingSearchDTO.getSize();

	        Specification<Booking> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, bookingId, "bookingId"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Booking> bookings = this.getAllBookings(spec, pageable);
		
		//System.out.println(String.valueOf(bookings.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(bookings.getTotalPages()));
		
		List<Booking> bookingsList = bookings.getContent();
		
		BookingConvertCriteriaDTO convertCriteria = new BookingConvertCriteriaDTO();
		List<BookingDTO> bookingDTOs = this.convertBookingsToBookingDTOs(bookingsList,convertCriteria);
		
		BookingPageDTO bookingPageDTO = new BookingPageDTO();
		bookingPageDTO.setBookings(bookingDTOs);
		bookingPageDTO.setTotalElements(bookings.getTotalElements());
		return ResponseEntity.ok(bookingPageDTO);
	}

	public List<BookingDTO> convertBookingsToBookingDTOs(List<Booking> bookings, BookingConvertCriteriaDTO convertCriteria) {
		
		List<BookingDTO> bookingDTOs = new ArrayList<BookingDTO>();
		
		for (Booking booking : bookings) {
			bookingDTOs.add(convertBookingToBookingDTO(booking,convertCriteria));
		}
		
		return bookingDTOs;

	}
	
	public BookingDTO convertBookingToBookingDTO(Booking booking, BookingConvertCriteriaDTO convertCriteria) {
		
		BookingDTO bookingDTO = new BookingDTO();
		
		bookingDTO.setBookingId(booking.getBookingId());

	
		bookingDTO.setBookingDate(booking.getBookingDate());

	

		
		return bookingDTO;
	}

	public ResultDTO updateBooking(BookingDTO bookingDTO, RequestDTO requestDTO) {
		
		Booking booking = bookingDao.getById(bookingDTO.getBookingId());

		booking.setBookingId(ControllerUtils.setValue(booking.getBookingId(), bookingDTO.getBookingId()));

		booking.setBookingDate(ControllerUtils.setValue(booking.getBookingDate(), bookingDTO.getBookingDate()));



        booking = bookingDao.save(booking);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public BookingDTO getBookingDTOById(Integer bookingId) {
	
		Booking booking = bookingDao.getById(bookingId);
			
		
		BookingConvertCriteriaDTO convertCriteria = new BookingConvertCriteriaDTO();
		return(this.convertBookingToBookingDTO(booking,convertCriteria));
	}







}
