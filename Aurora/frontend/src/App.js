import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AppointmentsPage from './pages/AppointmentsPage';

const App = () => (
    <Router>
        <Routes>
            <Route path="/" element={<AppointmentsPage />} />
        </Routes>
    </Router>
);

export default App;
