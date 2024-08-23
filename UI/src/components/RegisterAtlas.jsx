import React, { useState } from 'react'
import TextField from '@atlaskit/textfield';
import Button from '@atlaskit/button/new';
import Select from '@atlaskit/select';
import Link from '@atlaskit/link';
import Form, { Field } from '@atlaskit/form';
import { DEFAULT_ENABLE, LOGIN_PAGE, ROLE_ADMIN, ROLE_USER } from '../config/ConstantConfig';
import AuthenticationAPI from '../service/AuthenticationAPI';
import {
    ERROR_FULLNAME_INVALID,
    ERROR_PASS_INVALID, ERROR_RE_PASS_INVALID,
    ERROR_SELECT_ROLE_INVALID,
    ERROR_USERNAME_INVALID,
    MESS_REGISTER_SUCCESS
}
    from '../config/MessageConfig';
import SuccessIcon from '@atlaskit/icon/glyph/check-circle';
import { token } from '@atlaskit/tokens';
import Flag from '@atlaskit/flag';



export default function Register() {

    const [formData, setFormData] = useState({
        username: '',
        password: '',
        repeatPassword: '',
        fullname: '',
        roleCode: null
    });

    const inputErr = {
        usernameErr: '',
        passErr: '',
        repassErr: '',
        fullnameErr: '',
        roleErr: ''
    };

    const successMessage = '';

    let validAll = false;

    const roles = [
        { label: 'Admin', value: ROLE_ADMIN },
        { label: 'User', value: ROLE_USER }
    ];

    const handleChange = (field, value) => {
        setFormData({ ...formData, [field]: value });
    };

    const handleSubmit = () => {
        console.log('Form Data:', formData);
        validAll = true;
        const registerRequest = {
            username: formData.username.trim(),
            password: formData.password.trim(),
            fullname: formData.fullname.trim(),
            enable: DEFAULT_ENABLE,
            roleCode: formData.roleCode
        }

        // find user exist ????

        if (!registerRequest.username) {
            inputErr.usernameErr = ERROR_USERNAME_INVALID; validAll = false;
        }
        if (!registerRequest.password) {
            inputErr.passErr = ERROR_PASS_INVALID; validAll = false;
        }
        if (registerRequest.password != formData.repeatPassword || !formData.repeatPassword.trim()) {
            inputErr.repassErr = ERROR_RE_PASS_INVALID; validAll = false;
        }
        if (!registerRequest.fullname) {
            inputErr.fullnameErr = ERROR_FULLNAME_INVALID; validAll = false;
        }
        if (!registerRequest.roleCode) {
            inputErr.roleErr = ERROR_SELECT_ROLE_INVALID; validAll = false;
        }

        if (validAll) {
            successMessage = MESS_REGISTER_SUCCESS;
            // AuthenticationAPI.register(registerRequest).then(
            //     (response) => {
            //         setSuccessMessage(MESS_REGISTER_SUCCESS);
            //     }
            // )
            //     .catch(
            //         (error) => {
            //             console.log(error);
            //         }
            //     )

            console.log(registerRequest);
            console.log("Valid all"); 
        }
        console.log(inputErr);
        
    };

    return (
        <div>
            <h2 className='text-center'>Register</h2>
            <div className='d-flex justify-content-center'>
                <Form onSubmit={handleSubmit}>
                    {({ formProps }) => (
                        <form {...formProps} className='w-50'>
                            <Field name="username" label="Username" isRequired>
                                {({ fieldProps }) => (
                                    <TextField
                                        {...fieldProps}
                                        onChange={(e) => handleChange('username', e.target.value)}
                                        value={formData.username}
                                        placeholder='Enter your username'
                                    />
                                )}
                            </Field>
                            <div className='text-danger'>{inputErr.usernameErr}</div>


                            <Field name="password" label="Password" isRequired>
                                {({ fieldProps }) => (
                                    <TextField
                                        type="password"
                                        {...fieldProps}
                                        onChange={(e) => handleChange('password', e.target.value)}
                                        value={formData.password}
                                        placeholder='Enter your password'
                                    />
                                )}
                            </Field>
                            <div className='text-danger'>{inputErr.passErr}</div>

                            <Field name="repeatPassword" label="Repeat password" isRequired>
                                {({ fieldProps }) => (
                                    <TextField
                                        type="password"
                                        {...fieldProps}
                                        onChange={(e) => handleChange('repeatPassword', e.target.value)}
                                        value={formData.repeatPassword}
                                        placeholder='Repeat your password'
                                    />
                                )}
                            </Field>
                            <div className='text-danger'>{inputErr.repassErr}</div>

                            <Field name="fullname" label="Full Name" isRequired>
                                {({ fieldProps }) => (
                                    <TextField
                                        {...fieldProps}
                                        onChange={(e) => handleChange('fullname', e.target.value)}
                                        value={formData.fullname}
                                        placeholder='Enter your full name'
                                    />
                                )}
                            </Field>
                            <div className='text-danger'>{inputErr.fullnameErr}</div>

                            <Field name="roleCode" label="Role" isRequired>
                                {({ fieldProps }) => (
                                    <Select
                                        {...fieldProps}
                                        options={roles}
                                        onChange={(selectedOption) =>
                                            handleChange('roleCode', selectedOption.value)
                                        }
                                        value={roles.find((role) => role.value === formData.roleCode)}
                                        placeholder='Select role'
                                    />
                                )}
                            </Field>
                            <div className='text-danger'>{inputErr.roleErr}</div>

                            {
                                validAll &&
                                <div className='mt-3'>
                                    <Flag
                                        icon={<SuccessIcon primaryColor={token('color.icon.success')} label="Success" />}
                                        id="1"
                                        key="1"
                                        title={''}
                                    />
                                </div>
                            }

                            <div className='mt-3 d-flex justify-content-between'>
                                <Button type="submit" appearance="primary">
                                    Register
                                </Button>
                                <div>
                                    Have account? <Link href={LOGIN_PAGE}>Signin</Link>
                                </div>
                            </div>

                        </form>
                    )}
                </Form>
            </div>

        </div>

    );
}
