import React, { useState } from 'react'
import Button from '@atlaskit/button/new';
import TextField from '@atlaskit/textfield';
import Form, {
    Field,
} from '@atlaskit/form';
import { useNavigate } from 'react-router-dom';
import AuthenticationAPI from '../service/AuthenticationAPI';
import Banner from '@atlaskit/banner';
import Link from '@atlaskit/link';
import { AUTH_TOKEN, REGISTER_PAGE } from '../config/ConstantConfig';

export default function Login() {

    const navigator = useNavigate();
    const [token, setToken] = useState('');
    const [errorResponse, setErrorResponse] = useState({
        httpStatus: '',
        message: ''
    });

    const handleSubmit = (data) => {
        console.log("input data:", data);

        const authenRequest = {
            username: data.username.trim(),
            password: data.password.trim()
        }

        if (authenRequest.username && authenRequest.password) {
            AuthenticationAPI.authenticate(authenRequest).then(
                (response) => {
                    setToken(response.data);
                    console.log(response.data);
                    // save in local storage
                    localStorage.setItem(AUTH_TOKEN, response.data);
                }
            )
                .catch(
                    (error) => {
                        setErrorResponse(error.response.data);
                        console.log(errorResponse);
                    }
                )
        }

    };
    return (
        <div>
            <h2 className='text-center mt-2'>Sign In</h2>
            <div className='d-flex justify-content-center'>
                <Form onSubmit={handleSubmit}>
                    {({ formProps }) => (
                        <form {...formProps} className='w-50'>

                            <Field name="username" label="Username" isRequired>
                                {
                                    ({ fieldProps }) => (
                                        <TextField
                                            type='text'
                                            autoComplete="username"
                                            placeholder="Enter your username"
                                            {...fieldProps}
                                        />)
                                }
                            </Field>

                            <Field name="password" label="Password" isRequired>
                                {({ fieldProps }) => (
                                    <TextField
                                        type="password"
                                        autoComplete="current-password"
                                        placeholder="Enter your password"
                                        {...fieldProps}
                                    />
                                )}
                            </Field>

                            {
                                errorResponse.message && 
                                <div className='mt-3'>
                                    <Banner appearance="error">
                                        { errorResponse.message }
                                    </Banner>
                                </div>
                            }

                            <div className='mt-3 d-flex justify-content-between'>
                                <Button type="submit" appearance="primary">
                                    Log In
                                </Button>
                                <div>
                                    Don't have account?<Link href={REGISTER_PAGE}> Register here</Link>
                                </div>
                            </div>

                        </form>
                    )}
                </Form>
            </div>
        </div>

    )
}
