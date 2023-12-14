import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import "./App.css";
import NavBar from "./components/common/NavBar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomeCampDay from "./components/camp-day/HomeCampDay";
import HomeActivities from "./components/activities/HomeActivities";
import HomeVolunteer from "./components/volunteers/HomeVolunteer";
import Home from "./components/other/Home";
import AddVolunteer from "./components/volunteers/AddVolunteer";
import UpdateVolunteer from "./components/volunteers/UpdateVolunteer";
import AddActivity from "./components/activities/AddActivity";
import UpdateActivity from "./components/activities/UpdateActivity";
import AddCampDay from "./components/camp-day/AddCampDay";
import UpdateCampDay from "./components/camp-day/UpdateCampDay";
import AddActivityToCampDay from "./components/camp-day/AddActivityToCampDay";

function App() {
  return (
    <main className="container mt-5">
      <link
        href="https://fonts.googleapis.com/icon?family=Material+Icons"
        rel="stylesheet"
      ></link>

      <Router>
        <NavBar />
        <Routes>
          <Route exact path="/" element={<Home />}></Route>
          <Route exact path="/home-camp-day" element={<HomeCampDay />}></Route>

          <Route
            exact
            path="/home-volunteers"
            element={<HomeVolunteer />}
          ></Route>

          <Route
            exact
            path="/home-activities"
            element={<HomeActivities />}
          ></Route>

          <Route
            exact
            path="/volunteers-add"
            element={<AddVolunteer />}
          ></Route>

          <Route
            exact
            path="/volunteers-update/:id"
            element={<UpdateVolunteer />}
          ></Route>

          <Route exact path="/activities-add" element={<AddActivity />}></Route>

          <Route
            exact
            path="/activies-update/:id"
            element={<UpdateActivity />}
          ></Route>

          <Route exact path="/campDays-add" element={<AddCampDay />}></Route>

          <Route
            exact
            path="/campDays-update/:id"
            element={<UpdateCampDay />}
          ></Route>

          <Route
            exact
            path="/campDays-addActivities"
            element={<AddActivityToCampDay />}
          ></Route>
        </Routes>
      </Router>
    </main>
  );
}

export default App;
