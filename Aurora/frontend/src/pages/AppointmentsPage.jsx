import React, { useEffect, useState } from 'react';
import { Table, Button, Modal, message, Tag, Card, Input, DatePicker, TimePicker } from 'antd';
import AddAppointmentForm from '../components/AppointmentForm';
import EditAppointmentForm from '../components/EditAppointmentForm';
import { getAppointmentsByDate, getAppointmentsByTime, searchAppointments } from '../api/AppointmentService'; 
import moment from 'moment'; 
import axios from 'axios';
import { jsPDF } from 'jspdf';
import autoTable from 'jspdf-autotable';
import { EditOutlined, FilePdfFilled, FilePdfOutlined, PrinterOutlined } from '@ant-design/icons';


const AppointmentsPage = () => {
    const [appointments, setAppointments] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [isEditModalVisible, setIsEditModalVisible] = useState(false);
    const [selectedAppointment, setSelectedAppointment] = useState(null);
    const [dateFilter, setDateFilter] = useState(null);
    const [timeFilter, setTimeFilter] = useState(null);
    const [searchTerm, setSearchTerm] = useState(''); 

    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/appointments');
            setAppointments(response.data);
        } catch (error) {
            message.error('Error fetching appointments');
        }
    };

    const fetchAppointmentsByDate = async (date) => {
        try {
            const response = await getAppointmentsByDate(date);
            setAppointments(response.data);
        } catch (error) {
            message.error('Error fetching appointments by date');
        }
    };

    const fetchAppointmentsByTime = async (time) => {
        try {
            const response = await getAppointmentsByTime(time);
            setAppointments(response.data);
        } catch (error) {
            message.error('Error fetching appointments by time');
        }
    };

    const handleSearchSubmit = async () => {
        if (searchTerm) {
            try {
                const response = await searchAppointments(searchTerm);
                setAppointments(response.data);
            } catch (error) {
                message.error('Error searching appointments');
            }
        } else {
            fetchAppointments(); 
        }
    };

    const handleEditClick = (appointment) => {
        setSelectedAppointment(appointment);
        setIsEditModalVisible(true);
    };

    const handleDateFilterChange = (date) => {
        setDateFilter(date);
        if (date) {
            fetchAppointmentsByDate(date.format('YYYY-MM-DD'));
        } else {
            fetchAppointments();
        }
    };

    const handleTimeFilterChange = (time) => {
        setTimeFilter(time);
        if (time) {
            fetchAppointmentsByTime(time.format('HH:mm'));
        } else {
            fetchAppointments(); 
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
    };

    const handleGenerateInvoice = () => {
        if (!selectedAppointment) return;
    
        const doc = new jsPDF();
    
        
        doc.setFont("helvetica", "normal");
    
       
        doc.setFontSize(22);
        doc.text('Invoice for Appointment', 105, 22, { align: 'center' });
    
      
        doc.setFontSize(12);
        doc.text(`Appointment ID: ${selectedAppointment.id}`, 14, 30);
        doc.text(`Patient Name: ${selectedAppointment.fName} ${selectedAppointment.lName}`, 14, 38);
        doc.text(`Date: ${selectedAppointment.appointmentDate}`, 14, 46);
        doc.text(`Time: ${selectedAppointment.appointmentTime}`, 14, 54);
        doc.text(`Dermatologist: ${selectedAppointment.dermatologist}`, 14, 62);
        doc.text(`Treatment: ${selectedAppointment.treatment}`, 14, 70);
    
        const registrationStatus = selectedAppointment.registrationFeePaid ? 'Paid' : 'Not Paid';
        doc.text(`Registration Fee Status: ${registrationStatus}`, 14, 78);
    
       
        doc.setFontSize(14);
        doc.setFont("helvetica", "bold");
        doc.text('Fee Breakdown:', 14, 90);
        
        autoTable(doc, {
            head: [['Description', 'Amount']],
            body: [
                ['Treatment Fee', selectedAppointment.treatmentFee.toFixed(2)],  // Treatment Fee from backend
                ['Registration Fee', '500.00'], 
                ['Sub Total', (selectedAppointment.treatmentFee + 500).toFixed(2)],  // Treatment + Registration Fee
                ['Tax (2.5%)', selectedAppointment.tax.toFixed(2)],  // Tax from backend
                [{ content: 'Total', styles: { fontStyle: 'bold' } }, selectedAppointment.totalFee.toFixed(2)],  // Total Fee from backend
            ],
            startY: 100,
            theme: 'grid',
            styles: {
                cellPadding: 5,
                fontSize: 12,
                font: 'helvetica',
                halign: 'center', 
                valign: 'middle', 
            },
            headStyles: {
                fontStyle: 'bold', 
                fillColor: [255, 223, 186], 
                textColor: [0, 0, 0], 
            },
        });
    
        doc.save('invoice.pdf');
        message.success('Invoice PDF generated successfully!');
    };
    

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'Patient Name',
            dataIndex: 'fName',
            key: 'fName',
            render: (text, record) => `${record.fName} ${record.lName}`,
        },
        {
            title: 'Date',
            dataIndex: 'appointmentDate',
            key: 'appointmentDate',
        },
        {
            title: 'Time',
            dataIndex: 'appointmentTime',
            key: 'appointmentTime',
        },
        {
            title: 'Dermatologist',
            dataIndex: 'dermatologist',
            key: 'dermatologist',
        },
        {
            title: 'Treatment',
            dataIndex: 'treatment',
            key: 'treatment',
        },
        {
            title: 'Treatment Fee',
            dataIndex: 'treatmentFee',
            key: 'treatmentFee',
            render: (text) => `${text.toFixed(2)}`,
        },
        {
            title: "Registration Fee",
            dataIndex: "registrationFeePaid",
            key: "registrationFeePaid",
            render: (text) => (
                text ? <Tag color="green"> 500.00</Tag> : <Tag color="red"> 500.00</Tag>
            ),
        },
        {
            title: 'Tax',
            dataIndex: 'tax',
            key: 'tax',
        },
        {
            title: 'Total Fee',
            dataIndex: 'totalFee',
            key: 'totalFee',
            render: (text) => `${text.toFixed(2)}`,
        },
        {
            title: 'Action',
            key: 'action',
            render: (_, record) => (
                <div>
                    <Button color="primary" variant="filled" onClick={() => handleEditClick(record)} style={{ marginBottom: 5 }}>Edit <EditOutlined /></Button>
                    <Button color="default" variant="filled" onClick={() => {
                        setSelectedAppointment(record);
                        handleGenerateInvoice();
                    }}>Invoice <FilePdfOutlined /></Button>
                </div>
            ),
        },
    ];

    return (
        <div>
            <h1><center>Aurora Skin Care </center></h1>
            <Card style={{ margin: 20 }}>
                <h1>Appointments</h1>
                <Button type="primary" onClick={() => setIsModalVisible(true)} style={{ marginBottom: 20 }}>
                    Add Appointment
                </Button>

                <div style={{ marginBottom: 20 }}>
                    <DatePicker onChange={handleDateFilterChange} style={{ marginRight: 10 }} />
                    <TimePicker onChange={handleTimeFilterChange} style={{ marginRight: 10 }} />
                    <Input
                        placeholder="Search by Name or ID"
                        value={searchTerm}
                        onChange={handleSearchChange}
                        onPressEnter={handleSearchSubmit}
                        style={{ width: 200, marginRight: 10 }}
                    />
                    <Button onClick={handleSearchSubmit}>Search</Button>
                </div>

                <Table dataSource={appointments} columns={columns} rowKey="id" />
            </Card>
            <Modal
                title="Add Appointment"
                visible={isModalVisible}
                onCancel={() => setIsModalVisible(false)}
                footer={null}
            >
                <AddAppointmentForm onClose={() => setIsModalVisible(false)} onAdd={fetchAppointments} />
            </Modal>
            <Modal
                title="Edit Appointment"
                visible={isEditModalVisible}
                onCancel={() => setIsEditModalVisible(false)}
                footer={null}
            >
                {selectedAppointment && (
                    <EditAppointmentForm
                        appointment={selectedAppointment}
                        onClose={() => setIsEditModalVisible(false)}
                        onEdit={fetchAppointments}
                    />
                )}
            </Modal>
        </div>
    );
};

export default AppointmentsPage;
