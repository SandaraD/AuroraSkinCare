package com.Aurora.api.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class InvoiceDTO {
    
    
    private int appointmentId;
    private String patientName;
    private LocalDate date;
    private LocalTime time;
    private String dermatologist;
    private String treatment;
    private double treatmentFee;
    private double tax;
    private double totalFee;
    
    
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getDermatologist() {
		return dermatologist;
	}
	public void setDermatologist(String dermatologist) {
		this.dermatologist = dermatologist;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
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
