import {Link} from 'react-router-dom'
import React from 'react';
import './styles/Registration.css'


const Registration = () => {
    return (
        <div className='register'>
            <p className='register-header' style={{fontSize:'larger'}}>ASTHETICA</p>
            <h1>Create Account</h1>
            <p className='register-quote'>start your journey collecting stunning art</p>
            <div className='register-section'>
                <div className='register-input-fields'>
                    <label htmlFor="fullname">Full Name</label><br />
                    <input type="text" id='fullname' placeholder='Enter full name'/><br />
                    <label htmlFor="fullname">Email</label><br />
                    <input type="email" id='email' placeholder='Enter email'/><br />
                    <label htmlFor="pwd">Password</label><br />
                    <input type="password" id='pwd' placeholder='Enter password'/><br />
                    <label htmlFor="con-pwd">Confirm Password</label><br />
                    <input type="password" id='con-pwd' placeholder='confirm password'/><br />
                </div>
                <div className='register-create'>
                    <button>Create Account</button>
                </div>
            </div>
            <p className='register-sign-in'>already have an account? <Link to='/login'>Login here</Link></p>
        </div>
    );
}

export default Registration;
