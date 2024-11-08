


//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//
//import com.Aurora.api.DTO.AppointmentDTO;
//
//public interface IAppointmentService {
//	
//	AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
//	AppointmentDTO updateAppointment(int id, AppointmentDTO appointmentDTO);
//	AppointmentDTO getAppointmentById(int id);
//	List<AppointmentDTO> getAllAppointments();
//	List<AppointmentDTO> getAppointmentsByDate(LocalDate date);
//	List<AppointmentDTO> getAppointmentsByTime(LocalTime time);
//	List<AppointmentDTO> searchAppointments(String searchTerm);
//
//}

package com.Aurora.api.iService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.Aurora.api.DTO.AppointmentDTO;
import com.Aurora.api.Enum.DermatologistName;

public interface IAppointmentService {
    
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    AppointmentDTO updateAppointment(int id, AppointmentDTO appointmentDTO);
    AppointmentDTO getAppointmentById(int id);
    List<AppointmentDTO> getAllAppointments();
    List<AppointmentDTO> getAppointmentsByDate(LocalDate date);
    List<AppointmentDTO> getAppointmentsByTime(LocalTime time);
    List<AppointmentDTO> searchAppointments(String searchTerm);
    
    // Check if a slot is available for a specific Dermatologist
    boolean isSlotAvailable(DermatologistName dermatologist, LocalDate date, LocalTime time);
}




