package com.Aurora.api.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.Aurora.api.Enum.DermatologistName;
import com.Aurora.api.Enum.TreatmentType;

public class AppointmentDTO {
	
    private int id;
    private String nic;
    private String fName;
    private String lName;
    private String email;
    private int telephone;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private DermatologistName dermatologist;
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

	// Constructor
    public AppointmentDTO() {
    	
    };


	public AppointmentDTO(int id, String nic, String fName, String lName, String email, int telephone,
			LocalDate appointmentDate, LocalTime appointmentTime, DermatologistName dermatologist, TreatmentType treatment,
			boolean registrationFeePaid, double treatmentFee, double tax, double totalFee) {
		
		super();
		this.id = id;
		this.nic = nic;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.telephone = telephone;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.dermatologist = dermatologist;
		this.treatment = treatment;
		this.registrationFeePaid = registrationFeePaid;
		this.treatmentFee = treatmentFee;
		this.tax = tax;
		this.totalFee = totalFee;
	} 
}
