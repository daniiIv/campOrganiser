import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const HomeVolunteer = () => {
  const [volunteers, setVolunteers] = useState([]);

  useEffect(() => {
    loadVolunteers();
  }, []);

  const loadVolunteers = async () => {
    try {
      const result = await axios.get("http://localhost:8000/volunteers/all", {
        validateStatus: () => true,
      });
      setVolunteers(result.data);
    } catch (error) {
      console.error("Error loading volunteers:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8000/volunteers/${id}`);
      loadVolunteers();
    } catch (error) {
      console.error("Error deleting volunteer:", error);
    }
  };

  return (
    <section>
      <div className="mb-4">
        <h1>Volunteers</h1>
      </div>

      <table className="table table-bordered table-hover">
        <thead>
          <tr className="text-center">
            <th>
              <Link className="nav-link " to={"/volunteers-add"}>
                <span class="material-icons">add</span>
              </Link>
            </th>
            <th>id</th>
            <th>name</th>
            <th>age</th>
            <th>gender</th>
            <th>email</th>
            <th>phone number</th>
            <th>activities</th>
            <th>update</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {volunteers.map((volunteer, index) => (
            <tr key={volunteer.id}>
              <td>
                <button
                  className="btn btn-transperant btn-sm fw-bold fs-5"
                  onClick={() => handleDelete(volunteer.id)}
                >
                  <span className="material-icons">remove</span>
                </button>
              </td>
              <th scope="row">{index + 1}</th>
              <td>{volunteer.name}</td>
              <td>{volunteer.age}</td>
              <td>{volunteer.gender}</td>
              <td>{volunteer.email}</td>
              <td>{volunteer.phoneNumber}</td>
              <td className="col-12">
                {volunteer.activities && volunteer.activities.length > 0 ? (
                  volunteer.activities.map((activity, activityIndex) => (
                    <ul key={activityIndex}>
                      {activity.type} : {activity.title}
                    </ul>
                  ))
                ) : (
                  <div>No Activities</div>
                )}
              </td>

              <td>
                {" "}
                <Link
                  to={`/volunteers-update/${volunteer.id}`}
                  className="nav-link "
                >
                  <span className="btn btn-transperant btn-sm material-icons">
                    autorenew
                  </span>
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default HomeVolunteer;
