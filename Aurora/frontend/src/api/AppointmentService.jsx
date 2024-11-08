import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/appointments';

// Function to get appointments by date
const getAppointmentsByDate = (date) => {
    return axios.get(`${BASE_URL}/date/${date}`);
};

// Function to get appointments by time
const getAppointmentsByTime = (time) => {
    return axios.get(`${BASE_URL}/time/${time}`);
};

// Function to search appointments by name or ID
const searchAppointments = (searchTerm) => {
    return axios.get(`${BASE_URL}/search?term=${searchTerm}`);
};

// Function to check appointment availability
export const checkAppointmentAvailability = async (dermatologist, date, time) => {
    try {
        const response = await axios.get(`${BASE_URL}/check-availability`, {
            params: { dermatologist, date, time }
        });
        return response.data;
    } catch (error) {
        console.error("Error checking availability", error);
        throw error;
    }
};

// Function to create a new appointment
export const createAppointment = async (appointmentData) => {
    try {
        const response = await axios.post(BASE_URL, appointmentData);
        return response.data;
    } catch (error) {
        console.error("Error creating appointment", error);
        throw error;
    }
};


export { getAppointmentsByDate, getAppointmentsByTime, searchAppointments };
