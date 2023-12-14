import React, { useState } from "react";
import { Form, Link, useNavigate, useNavigation } from "react-router-dom";
import axios from "axios";

const AddActivity = () => {
  let navigate = useNavigate();

  const [activity, setActivity] = useState({
    title: "",
    typeEnum: "GAME",
    description: "",
  });

  const { title, typeEnum, description } = activity;

  const handleInputChange = (e) => {
    setActivity({ ...activity, [e.target.name]: e.target.value });
  };

  const saveActivity = async (e) => {
    try {
      e.preventDefault();
      await axios.post("http://localhost:8000/activities/create", activity);
      navigate("/home-activities");
    } catch (error) {
      console.error("Error updating activity:", error.message);
      navigate("/home-activities");
    }
  };

  return (
    <div className="col-sm-8 py-2 px-5">
      <h2 className="mt-5">Add Activity</h2>
      <form onSubmit={(e) => saveActivity(e)}>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="title">
            title
          </label>
          <input
            className="form-control col-sm-6"
            type="text"
            name="title"
            id="title"
            required
            value={title}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="typeEnum">
            type
          </label>

          <select
            className="form-select"
            name="typeEnum"
            id="typeEnum"
            required
            value={typeEnum}
            onChange={(e) => handleInputChange(e)}
          >
            <option value="GAME">GAME</option>
            <option value=" TOPIC_OF_THE_DAY"> TOPIC_OF_THE_DAY</option>
            <option value="SPECIAL_ACTIVITY">SPECIAL_ACTIVITY</option>
          </select>
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="description">
            description
          </label>
          <input
            className="form-control col-sm-6"
            type="text"
            name="description"
            id="description"
            required
            value={description}
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
              to={"/home-activities"}
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

export default AddActivity;
