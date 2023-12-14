import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const AddCampDay = () => {
  let navigate = useNavigate();
  const { id } = useParams();

  const [campDay, setCampDay] = useState({
    date: "",
  });

  useEffect(() => {
    loadCampDay();
  }, []);

  const { date } = campDay;

  const handleInputChange = (e) => {
    setCampDay({ ...campDay, [e.target.name]: e.target.value });
  };

  const loadCampDay = async () => {
    try {
      const result = await axios.get(`http://localhost:8000/camp-days/${id}`, {
        validateStatus: () => true,
      });
      setCampDay(result.data);
    } catch (error) {
      console.error("Error loading camp day:", error);
    }
  };

  const updateCampDay = async (e) => {
    try {
      e.preventDefault();
      await axios.put(`http://localhost:8000/camp-days/${id}`, campDay);
      navigate("/home-camp-day");
    } catch (error) {
      console.error("Error updating camp day:", error.message);
    }
  };

  return (
    <div className="col-sm-8 py-2 px-5">
      <h2 className="mt-5">Add Camp Day</h2>
      <form onSubmit={(e) => updateCampDay(e)}>
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
