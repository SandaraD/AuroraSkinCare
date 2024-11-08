import React, { useState, useEffect } from 'react';
import { Form, Input, Button, DatePicker, Select, message, Checkbox } from 'antd';
import axios from 'axios';
import moment from 'moment';

const { Option } = Select;

const EditAppointmentForm = ({ appointment, onClose, onEdit }) => {
    const [form] = Form.useForm();
    const [treatmentPrice, setTreatmentPrice] = useState(appointment.treatmentFee || 0);
    const [taxAmount, setTaxAmount] = useState(appointment.tax || 0);
    const [availableTimes, setAvailableTimes] = useState([]);

    // Define available time ranges for each day
    const timeRanges = {
        Monday: ["10:00", "13:00"],
        Wednesday: ["14:00", "17:00"],
        Friday: ["16:00", "20:00"],
        Saturday: ["09:00", "13:00"]
    };

    // Disable dates other than Monday, Wednesday, Friday, and Saturday
    const disabledDate = (current) => {
        if (!current) return false;
        const day = current.day();
        return day !== 1 && day !== 3 && day !== 5 && day !== 6; // 1: Monday, 3: Wednesday, 5: Friday, 6: Saturday
    };

    useEffect(() => {
        form.setFieldsValue({
            ...appointment,
            appointmentDate: moment(appointment.appointmentDate),
            appointmentTime: appointment.appointmentTime,
        });
        setAvailableTimes(generateAvailableTimes(moment(appointment.appointmentDate)));
    }, [appointment, form]);

    const generateAvailableTimes = (date) => {
        const day = date.format("dddd");
        const [start, end] = timeRanges[day] || [];
        const startTime = moment(start, "HH:mm");
        const endTime = moment(end, "HH:mm");
        const times = [];

        while (startTime.isBefore(endTime)) {
            times.push(startTime.format("HH:mm"));
            startTime.add(15, "minutes");
        }
        return times;
    };

    const handleDateChange = (date) => {
        if (!date) return;
        const day = date.format("dddd");
        const [start, end] = timeRanges[day] || [];
        const startTime = moment(start, "HH:mm");
        const endTime = moment(end, "HH:mm");
        const times = [];

        while (startTime.isBefore(endTime)) {
            times.push(startTime.format("HH:mm"));
            startTime.add(15, "minutes");
        }
        setAvailableTimes(times);
        form.setFieldsValue({ appointmentTime: null });
    };

    const handleTreatmentChange = async (treatment) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/treatments/${treatment}`);
            
           
            const newTreatmentPrice = response.data.price;
            const newTaxAmount = newTreatmentPrice * 0.025; 
            const newTotalFee = newTreatmentPrice + newTaxAmount;
    
           
            setTreatmentPrice(newTreatmentPrice);
            setTaxAmount(newTaxAmount);
    
            form.setFieldsValue({
                treatmentFee: newTreatmentPrice,
                tax: newTaxAmount,
                totalFee: newTotalFee
            });
        } catch (error) {
            console.error('Error fetching treatment price:', error);
        }
    };
    
    const handleFinish = async (values) => {
        try {
            await axios.put(`http://localhost:8080/api/appointments/${appointment.id}`, {
                ...values,
                appointmentDate: values.appointmentDate.format('YYYY-MM-DD'),
                appointmentTime: values.appointmentTime,
                treatmentFee: treatmentPrice,
                tax: taxAmount,
                totalFee: treatmentPrice + taxAmount,
            });
            message.success('Appointment updated successfully!');
            onClose();
            onEdit();
        } catch (error) {
            message.error('Selected time slot is unavailable');
        }
    };
    
    

    return (
        <Form form={form} onFinish={handleFinish} layout="vertical">
            <Form.Item name="fName" label="First Name" rules={[{ required: true }]}>
                <Input />
            </Form.Item>
            <Form.Item name="lName" label="Last Name" rules={[{ required: true }]}>
                <Input />
            </Form.Item>
            <Form.Item name="nic" label="NIC" rules={[{ required: true }]}>
                <Input />
            </Form.Item>
            <Form.Item name="email" label="Email" rules={[{ required: true, type: 'email' }]}>
                <Input />
            </Form.Item>
            <Form.Item name="telephone" label="Telephone" rules={[{ required: true }]}>
                <Input type="number" />
            </Form.Item>
            <Form.Item name="appointmentDate" label="Date" rules={[{ required: true }]}>
                <DatePicker 
                    disabledDate={disabledDate} 
                    onChange={handleDateChange} 
                    format="YYYY-MM-DD"
                />
            </Form.Item>
            <Form.Item name="appointmentTime" label="Time" rules={[{ required: true }]}>
                <Select placeholder="Select time">
                    {availableTimes.map((time) => (
                        <Option key={time} value={time}>{time}</Option>
                    ))}
                </Select>
            </Form.Item>
            <Form.Item name="dermatologist" label="Dermatologist" rules={[{ required: true }]}>
                <Select>
                    <Option value="DR_SAYURI_HEWAWASAM">Dr. Sayuri Hewawasam</Option>
                    <Option value="DR_BINARA_WICKRAMA">Dr. Binara Wickrama</Option>
                </Select>
            </Form.Item>
            <Form.Item name="treatment" label="Treatment" rules={[{ required: true }]}>
                <Select onChange={handleTreatmentChange}>
                    <Option value="ACNE_TREATMENT">Acne Treatment</Option>
                    <Option value="SKIN_WHITENING">Skin Whitening</Option>
                    <Option value="MOLE_REMOVAL">Mole Removal</Option>
                    <Option value="LASER_TREATMENT">Laser Treatment</Option>
                </Select>
            </Form.Item>
            <Form.Item name="registrationFeePaid" label="Registration Fee " valuePropName="checked">
                <Checkbox />
            </Form.Item>
            <Button type="primary" htmlType="submit">Update</Button>
        </Form>
    );
};

export default EditAppointmentForm;

