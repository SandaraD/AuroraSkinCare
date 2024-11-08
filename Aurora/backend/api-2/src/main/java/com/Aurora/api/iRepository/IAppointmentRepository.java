
//
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.Aurora.api.Entity.Appointment;
//import com.Aurora.api.Enum.DermatologistName;
//import com.Aurora.api.Enum.TreatmentType;
//
//@Repository
//public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {
//
//    // Custom query method to find appointments by date, time, and dermatologist
//    List<Appointment> findByAppointmentDateAndAppointmentTimeAndDermatologist(
//            LocalDate appointmentDate, 
//            LocalTime appointmentTime, 
//            DermatologistName dermatologist
//    );
//    
//    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :date AND a.appointmentTime = :time AND a.dermatologist = :dermatologist AND a.treatment = :treatment AND a.id <> :id")
//    Optional<Appointment> findConflictingAppointment(LocalDate date, LocalTime time, DermatologistName dermatologist, TreatmentType treatment, int id);
//}

package com.Aurora.api.iRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Aurora.api.Entity.Appointment;
import com.Aurora.api.Enum.DermatologistName;
import com.Aurora.api.Enum.TreatmentType;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

    // Find appointments by date
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    // Find appointments by date, time, and dermatologist
    List<Appointment> findByAppointmentDateAndAppointmentTimeAndDermatologist(
            LocalDate appointmentDate, 
            LocalTime appointmentTime, 
            DermatologistName dermatologist
    );

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :date AND a.appointmentTime = :time AND a.dermatologist = :dermatologist AND a.treatment = :treatment AND a.id <> :id")
    Optional<Appointment> findConflictingAppointment(LocalDate date, LocalTime time, DermatologistName dermatologist, TreatmentType treatment, int id);

    // search for appointments by patient name or appointment ID
    @Query("SELECT a FROM Appointment a WHERE a.fName LIKE %:searchTerm% OR a.lName LIKE %:searchTerm% OR a.id = :id")
    List<Appointment> searchAppointments(@Param("searchTerm") String searchTerm, @Param("id") Integer id);
    
    Optional<Appointment> findById(int id);
    
    @Query("SELECT a FROM Appointment a WHERE a.fName LIKE %:name% OR a.lName LIKE %:name%")
    List<Appointment> findByPatientName(@Param("name") String name);
}


