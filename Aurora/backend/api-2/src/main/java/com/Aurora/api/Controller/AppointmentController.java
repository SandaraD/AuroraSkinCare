package com.Aurora.api.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Aurora.api.DTO.AppointmentDTO;
import com.Aurora.api.DTO.InvoiceDTO;
import com.Aurora.api.Entity.Appointment;
import com.Aurora.api.Enum.DermatologistName;
import com.Aurora.api.Service.AppointmentService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Create a new appointment
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.status(201).body(createdAppointment);
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable int id, @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Get an appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable int id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // Get all appointments
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Get appointments by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDate(@PathVariable String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDate(localDate);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // You can customize the error response
        }
    }

    // Get appointments by time
    @GetMapping("/time/{time}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByTime(@PathVariable String time) {
        try {
            LocalTime localTime = LocalTime.parse(time);
            List<AppointmentDTO> appointments = appointmentService.getAppointmentsByTime(localTime);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // You can customize the error response
        }
    }

 // Search for appointments by patient name or appointment ID
    @GetMapping("/search")
    public ResponseEntity<List<AppointmentDTO>> searchAppointments(@RequestParam String term) {
        List<AppointmentDTO> appointments = appointmentService.searchAppointments(term);
        return ResponseEntity.ok(appointments);
    }

    
    @GetMapping("/appointments/invoice/{id}")
    public ResponseEntity<InvoiceDTO> generateInvoice(@PathVariable int id) {
    	AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        InvoiceDTO invoice = new InvoiceDTO();
        invoice.setAppointmentId(appointment.getId());
        invoice.setPatientName(appointment.getfName() + " " + appointment.getlName());
        invoice.setDate(appointment.getAppointmentDate());
        invoice.setTime(appointment.getAppointmentTime());
        invoice.setDermatologist(appointment.getDermatologist().toString());
        invoice.setTreatment(appointment.getTreatment().toString());
        invoice.setTreatmentFee(appointment.getTreatmentFee());
        invoice.setTax(appointment.getTax());
        invoice.setTotalFee(appointment.getTotalFee());

        return ResponseEntity.ok(invoice);
    }

    

    @GetMapping("/check-availability")
    public boolean checkAvailability(
        @RequestParam("dermatologist") DermatologistName dermatologist,
        @RequestParam("date") LocalDate date,
        @RequestParam("time") LocalTime time
    ) {
        return appointmentService.isSlotAvailable(dermatologist, date, time);
    }
}
