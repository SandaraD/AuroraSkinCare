package com.Aurora.api.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.Aurora.api.Enum.DermatologistName;
import com.Aurora.api.Enum.TreatmentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appointment {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    @Column(nullable = false)
	private String nic;
    
    @Column(nullable = false)
	private String fName;
    
    @Column(nullable = false)
	private String lName;
    
    @Column(nullable = false)
	private String email;
    
    @Column(nullable = false)
	private int telephone;
    
    @Column(nullable = false)
    private LocalDate appointmentDate;
    
    @Column(nullable = false)
    private LocalTime appointmentTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DermatologistName dermatologist;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TreatmentType treatment;
    
	
    private boolean registrationFeePaid;
    private double treatmentFee;
    private double tax;
    private double totalFee;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNic() {
		return nic;
	}
	
	public void setNic(String nic) {
		this.nic = nic;
	}
	
	public String getfName() {
		return fName;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public String getlName() {
		return lName;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getTelephone() {
		return telephone;
	}
	
	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
	
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}
	
	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
	public DermatologistName getDermatologist() {
		return dermatologist;
	}
	
	public void setDermatologist(DermatologistName dermatologist) {
		this.dermatologist = dermatologist;
	}
	
	public TreatmentType getTreatment() {
		return treatment;
	}
	
	public void setTreatment(TreatmentType treatment) {
		this.treatment = treatment;
	}
	
	public boolean isRegistrationFeePaid() {
		return registrationFeePaid;
	}
	
	public void setRegistrationFeePaid(boolean registrationFeePaid) {
		this.registrationFeePaid = registrationFeePaid;
	}
	
	public double getTreatmentFee() {
		return treatmentFee;
	}
	
	public void setTreatmentFee(double treatmentFee) {
		this.treatmentFee = treatmentFee;
	}
	
	public double getTax() {
		return tax;
	}
	
	public void setTax(double tax) {
		this.tax = tax;
	}
	
	public double getTotalFee() {
		return totalFee;
	}
	
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

}
