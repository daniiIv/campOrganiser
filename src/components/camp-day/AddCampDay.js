import React, { useState } from "react";
import { Form, Link, useNavigate, useNavigation } from "react-router-dom";
import axios from "axios";

const AddCampDay = () => {
  let navigate = useNavigate();

  const [campDay, setCampDay] = useState({
    date: "",
  });

  const { date } = campDay;

  const handleInputChange = (e) => {
    setCampDay({ ...campDay, [e.target.name]: e.target.value });
  };

  const saveCampDay = async (e) => {
    try {
      e.preventDefault();
      await axios.post("http://localhost:8000/camp-days/create", campDay);
      navigate("/home-camp-day");
    } catch (error) {
      console.error("Error updating camp day:", error.message);
      navigate("/home-camp-day");
    }
  };

  return (
    <div className="col-sm-8 py-2 px-5">
      <h2 className="mt-5">Add Camp Day</h2>
      <form onSubmit={(e) => saveCampDay(e)}>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="date">
            Date
          </label>
          <input
            className="form-control col-sm-6"
            type="date"
            name="date"
            id="date"
            required
            value={date}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="row mb-5">
          <div className="col-sm-2">
            <button type="submit" className="btn btn-outline-dark btn-lg">
              Save
            </button>
          </div>
          <div className="col-sm-2">
            <Link
              to={"/home-camp-day"}
              className="btn btn-outline-secondary btn-lg"
            >
              Cancel
            </Link>
          </div>
        </div>
      </form>
    </div>
  );
};
export default AddCampDay;
