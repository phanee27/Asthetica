import React from "react";
import { Route, Routes, Link } from "react-router-dom";
import NotFound from "../main/NotFound";
import "./AdminNavbar.css";
import DashBoard from "./DashBoard";
import ManageUsers from "./ManageUsers";
import ManageArtWorks from "./ManageArtWorks";
import Transactions from "./Transactions";
import ReportsAnalytics from "./ReportsAnalytics";
import Login from "../authentication/Login";
import { useAuth } from "../contextapi/AuthContext";
import SellerRequest from "./SellerRequest";

const AdminNavbar = () => {
  const { setIsAdminLoggedIn, setUsername } = useAuth();

  const handleLogout = () => {
    setIsAdminLoggedIn(false);
    setUsername(null)
  };
  return (
    <div className="navbar">
      <div className="nav-links">
        <div className="admin-nav-start-section">
          <Link to="/admin-dashboard">
            <strong>Asthetica</strong>
          </Link>
        </div>
        <div className="nav-mid-section">
          <Link to="/admin-dashboard">DashBoard</Link>
          <Link to="/manage-users">ManageUsers</Link>
          <Link to="/sellerrequest">SellerApproval</Link>
          <Link to="/manage-artworks">ManageArtWorks</Link>
          <Link to="/reports-analytics">ReportsAnalytics</Link>
          <Link to="/transactions">Transactions</Link>
        </div>
        <div className="nav-end-section">
          <Link to="/login" onClick={handleLogout} >
            Logout
          </Link>
          </div>
      </div>

      <Routes>
        <Route path="/admin-dashboard" element={<DashBoard />} />
        <Route path="/manage-users" element={<ManageUsers />} />
        <Route path="/manage-artworks" element={<ManageArtWorks />} />
        <Route path="/reports-analytics" element={<ReportsAnalytics />} />
        <Route path="/transactions" element={<Transactions />} />
        <Route path="/login" element={<Login />} />
        <Route path="*" element={<NotFound />} />
        <Route path="/sellerrequest" element={<SellerRequest/>} />
      </Routes>
    </div>
  );
};

export default AdminNavbar;
