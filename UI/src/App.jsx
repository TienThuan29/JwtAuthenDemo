import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import { DEFAULT_PAGE, HOME_PAGE, LOGIN_PAGE, REGISTER_PAGE } from './config/ConstantConfig'
import Login from './components/Login'
import Register from './components/Register'
import Home from './components/Home'


function App() {

  return (
    <div className='container'>
      <BrowserRouter>
      
        <Routes>

          <Route path={DEFAULT_PAGE} element={ <Login/> }></Route>

          <Route path={LOGIN_PAGE} element={ <Login/> }></Route>

          <Route path={REGISTER_PAGE} element={ <Register/> }></Route>

          <Route path={HOME_PAGE} element={ <Home/> }></Route>

        </Routes>
      
      </BrowserRouter>
     
    </div>
  )
}

export default App
