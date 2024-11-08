package com.Aurora.api.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Aurora.api.DTO.AppointmentDTO;
import com.Aurora.api.Entity.Appointment;
import com.Aurora.api.iRepository.IAppointmentRepository;
import com.Aurora.api.iService.IAppointmentService;

import com.Aurora.api.Enum.DermatologistName;

@Service
public class AppointmentService implements IAppointmentService {

    @Autowired
    private IAppointmentRepository appointmentRepository;
    
    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Method to create a new appointment
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // Extract necessary details from the DTO
        LocalDate date = appointmentDTO.getAppointmentDate();
        LocalTime time = appointmentDTO.getAppointmentTime();
        DermatologistName dermatologist = appointmentDTO.getDermatologist();

        // Check slot availability
        if (!isSlotAvailable(dermatologist, date, time)) {
            throw new IllegalArgumentException("The selected time slot is not available. Please choose a different slot.");
        }

        // Create a new Appointment entity if slot is available
        Appointment appointment = new Appointment();
        
        // Set patient details
        appointment.setNic(appointmentDTO.getNic());
        appointment.setfName(appointmentDTO.getfName());
        appointment.setlName(appointmentDTO.getlName());
        appointment.setEmail(appointmentDTO.getEmail());
        appointment.setTelephone(appointmentDTO.getTelephone());

        // Set appointment details
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setDermatologist(dermatologist);
        appointment.setTreatment(appointmentDTO.getTreatment());

        // Set registration fee paid status
        appointment.setRegistrationFeePaid(appointmentDTO.isRegistrationFeePaid());

        // Calculate treatment fee and total fee
        double treatmentFee = appointment.getTreatment().getPrice(); // Get price from the TreatmentType enum
        double registrationFee = 500.0;
        double tax = (treatmentFee + registrationFee) * 0.025;
        double totalFee = treatmentFee + registrationFee + tax;

        // Set fees in the appointment entity
        appointment.setTreatmentFee(treatmentFee);
        appointment.setTax(tax);
        appointment.setTotalFee(Math.round(totalFee * 100.0) / 100.0);

        // Save the appointment to the database
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Map saved appointment back to DTO
        AppointmentDTO savedAppointmentDTO = new AppointmentDTO();
        savedAppointmentDTO.setId(savedAppointment.getId());
        savedAppointmentDTO.setNic(savedAppointment.getNic());
        savedAppointmentDTO.setfName(savedAppointment.getfName());
        savedAppointmentDTO.setlName(savedAppointment.getlName());
        savedAppointmentDTO.setEmail(savedAppointment.getEmail());
        savedAppointmentDTO.setTelephone(savedAppointment.getTelephone());
        savedAppointmentDTO.setAppointmentDate(savedAppointment.getAppointmentDate());
        savedAppointmentDTO.setAppointmentTime(savedAppointment.getAppointmentTime());
        savedAppointmentDTO.setDermatologist(savedAppointment.getDermatologist());
        savedAppointmentDTO.setTreatment(savedAppointment.getTreatment());
        savedAppointmentDTO.setRegistrationFeePaid(savedAppointment.isRegistrationFeePaid());
        savedAppointmentDTO.setTreatmentFee(savedAppointment.getTreatmentFee());
        savedAppointmentDTO.setTax(savedAppointment.getTax());
        savedAppointmentDTO.setTotalFee(savedAppointment.getTotalFee());

        return savedAppointmentDTO;
    }


    @Override
    public AppointmentDTO updateAppointment(int id, AppointmentDTO appointmentDTO) {
        // Check if the appointment exists
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        
        // Check for conflicting appointments
        Optional<Appointment> conflictingAppointment = appointmentRepository.findConflictingAppointment(
            appointmentDTO.getAppointmentDate(),
            appointmentDTO.getAppointmentTime(),
            appointmentDTO.getDermatologist(),
            appointmentDTO.getTreatment(),
            id
        );

        if (conflictingAppointment.isPresent()) {
            throw new RuntimeException("The selected time slot is unavailable.");
        }

      
        double treatmentFee = appointmentDTO.getTreatment().getPrice(); 
        double registrationFee = 500.0;
        double tax = (treatmentFee + registrationFee) * 0.025;
        double totalFee = treatmentFee + registrationFee + tax;

        // Debugging output
        System.out.println("Updating appointment ID: " + id);
        System.out.println("Selected Treatment: " + appointmentDTO.getTreatment());
        System.out.println("Treatment Price: " + treatmentFee);
        System.out.println("Registration Fee: " + registrationFee);
        System.out.println("Tax Amount: " + tax);
        System.out.println("Total Fee: " + totalFee);

        // Update fields in the existing appointment
        existingAppointment.setNic(appointmentDTO.getNic());
        existingAppointment.setfName(appointmentDTO.getfName());
        existingAppointment.setlName(appointmentDTO.getlName());
        existingAppointment.setEmail(appointmentDTO.getEmail());
        existingAppointment.setTelephone(appointmentDTO.getTelephone());
        existingAppointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        existingAppointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        existingAppointment.setDermatologist(appointmentDTO.getDermatologist());
        existingAppointment.setTreatment(appointmentDTO.getTreatment());
        existingAppointment.setRegistrationFeePaid(appointmentDTO.isRegistrationFeePaid());
        existingAppointment.setTreatmentFee(treatmentFee);
        existingAppointment.setTax(tax);
        existingAppointment.setTotalFee(totalFee);
        
        // Save and return the updated appointment
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return mapEntityToDto(updatedAppointment);
    }



    // Method to get an appointment by ID
    @Override
    public AppointmentDTO getAppointmentById(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        return mapEntityToDto(appointment);
    }

    // Method to get all appointments
    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // Method to get appointments by date
    @Override
    public List<AppointmentDTO> getAppointmentsByDate(LocalDate date) {
       
        return appointmentRepository.findByAppointmentDate(date)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    // Method to get appointments by time
    @Override
    public List<AppointmentDTO> getAppointmentsByTime(LocalTime time) {
        return appointmentRepository.findAll() 
                .stream()
                .filter(appointment -> appointment.getAppointmentTime().equals(time))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> searchAppointments(String searchTerm) {
        List<Appointment> appointments;

        if (isNumeric(searchTerm)) {
            int id = Integer.parseInt(searchTerm);
            appointments = appointmentRepository.findById(id).map(Collections::singletonList).orElse(Collections.emptyList());
        } else {
            appointments = appointmentRepository.findByPatientName(searchTerm); 
        }

        return appointments.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    
    

    // Method to map AppointmentDTO to Appointment entity
    private Appointment mapDtoToEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setNic(dto.getNic());
        appointment.setfName(dto.getfName());
        appointment.setlName(dto.getlName());
        appointment.setEmail(dto.getEmail());
        appointment.setTelephone(dto.getTelephone());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setDermatologist(dto.getDermatologist());
        appointment.setTreatment(dto.getTreatment());
        appointment.setRegistrationFeePaid(dto.isRegistrationFeePaid());
        appointment.setTreatmentFee(dto.getTreatmentFee());
        appointment.setTax(dto.getTax());
        appointment.setTotalFee(dto.getTotalFee());
        return appointment;
    }

    // Method to map Appointment entity to AppointmentDTO
    private AppointmentDTO mapEntityToDto(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setNic(appointment.getNic());
        dto.setfName(appointment.getfName());
        dto.setlName(appointment.getlName());
        dto.setEmail(appointment.getEmail());
        dto.setTelephone(appointment.getTelephone());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setDermatologist(appointment.getDermatologist());
        dto.setTreatment(appointment.getTreatment());
        dto.setRegistrationFeePaid(appointment.isRegistrationFeePaid());
        dto.setTreatmentFee(appointment.getTreatmentFee());
        dto.setTax(appointment.getTax());
        dto.setTotalFee(appointment.getTotalFee());
        return dto;
    }



    @Override
    public boolean isSlotAvailable(DermatologistName dermatologist, LocalDate date, LocalTime time) {
     
        List<Appointment> existingAppointments = appointmentRepository.
        		findByAppointmentDateAndAppointmentTimeAndDermatologist(date, time, dermatologist);

        for (Appointment appt : existingAppointments) {
            // Calculate time overlap
            LocalTime apptStart = appt.getAppointmentTime();
            LocalTime apptEnd = apptStart.plusMinutes(15);

            if (!time.isBefore(apptStart) && !time.isAfter(apptEnd)) {

                return false;
            }
        }
        return true;
    }

}
