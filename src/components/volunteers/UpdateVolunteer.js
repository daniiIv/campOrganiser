import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const UpdateVolunteer = () => {
  let navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    loadVolunteer();
  }, []);

  const [volunteer, setVolunteer] = useState({
    name: "",
    age: 0,
    phoneNumber: 0,
    email: "",
    gender: "",
  });

  const { name, age, phoneNumber, email, gender } = volunteer;

  const loadVolunteer = async () => {
    try {
      const result = await axios.get(`http://localhost:8000/volunteers/${id}`);
      setVolunteer(result.data);
    } catch (error) {
      console.error("Error loading volunteers:", error.message);
    }
  };

  const handleInputChange = (e) => {
    setVolunteer({ ...volunteer, [e.target.name]: e.target.value });
  };

  const updateVolunteer = async (e) => {
    try {
      e.preventDefault();
      await axios.put(`http://localhost:8000/volunteers/${id}`, volunteer);
      navigate("/home-volunteers");
    } catch (error) {
      console.error("Error updating volunteer:", error.message);
    }
  };

  return (
    <div className="col-sm-8 py-2 px-5">
      <h2 className="mt-5">Update Volunteer</h2>
      <form onSubmit={(e) => updateVolunteer(e)}>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="name">
            name
          </label>
          <input
            className="form-control col-sm-6"
            type="text"
            name="name"
            id="name"
            required
            value={name}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="age">
            age
          </label>
          <input
            className="form-control col-sm-6"
            type="number"
            name="age"
            id="age"
            required
            value={age}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="phoneNumber">
            phone
          </label>
          <input
            className="form-control col-sm-6"
            type="tel"
            name="phoneNumber"
            id="phoneNumber"
            required
            value={phoneNumber}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="email">
            email
          </label>
          <input
            className="form-control col-sm-6"
            type="email"
            name="email"
            id="email"
            required
            value={email}
            onChange={(e) => handleInputChange(e)}
          />
        </div>
        <div className="input-group mb-5">
          <label className="input-group-text" htmlFor="gender">
            gender
          </label>

          <select
            className="form-select"
            name="gender"
            id="gender"
            required
            value={gender}
            onChange={(e) => handleInputChange(e)}
          >
            <option value="MALE">MALE</option>
            <option value="FEMALE">FEMALE</option>
          </select>
        </div>

        <div className="row mb-5">
          <div className="col-sm-2">
            <button type="submit" className="btn btn-outline-dark btn-lg">
              Save
            </button>
          </div>
          <div className="col-sm-2">
            <Link
              to={"/home-volunteers"}
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

export default UpdateVolunteer;
