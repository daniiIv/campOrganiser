import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const HomeActivities = () => {
  const [activities, setActivities] = useState([]);
  const [campDate, setCampDate] = useState({});
  const [volunteerLists, setVolunteerLists] = useState({});
  const [selectedVolunteerId, setSelectedVolunteerId] = useState("");
  const [volunteers, setVolunteers] = useState([]);

  useEffect(() => {
    loadActivities();
  }, []);

  const loadActivities = async () => {
    try {
      const result = await axios.get("http://localhost:8000/activities/all", {
        validateStatus: () => true,
      });
      setActivities(result.data);
      fetchCampDate(result.data);
      fetchVolunteerLists(result.data);
      fetchVolunteers();
    } catch (error) {
      console.error("Error loading activiies:", error);
    }
  };

  const fetchCampDate = async (activitiesData) => {
    try {
      const campDatesData = {};
      for (const activity of activitiesData) {
        const response = await axios.get(
          `http://localhost:8000/activities/${activity.id}/camp-day`
        );
        const campDate = response.data.date; // Adjust this based on your response structure
        campDatesData[activity.id] = campDate;
      }
      setCampDate(campDatesData);
    } catch (error) {
      console.error("Error fetching camp dates:", error);
    }
  };

  const fetchVolunteers = async () => {
    try {
      const result = await axios.get("http://localhost:8000/volunteers/all"); // Adjust the URL based on your server API
      setVolunteers(result.data);
    } catch (error) {
      console.error("Error fetching volunteers:", error);
    }
  };

  const fetchVolunteerLists = async (activitiesData) => {
    try {
      const volunteerListsData = {};
      for (const activity of activitiesData) {
        const response = await axios.get(
          `http://localhost:8000/activities/${activity.id}/volunteers`
        );
        const volunteers = response.data;
        volunteerListsData[activity.id] = volunteers;
      }
      setVolunteerLists(volunteerListsData);
    } catch (error) {
      console.error("Error fetching volunteer lists:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8000/activities/${id}`);
      loadActivities();
    } catch (error) {
      console.error("Error deleting activiies:", error);
    }
  };
  const handleAddVolunteer = async (id, volunteerId) => {
    try {
      // Make a request to add the selected activity to the camp day
      await axios.post(
        `http://localhost:8000/activities/${id}/add-volunteer/${volunteerId}`
      );

      // Reload the camp days after the addition
      loadActivities();
    } catch (error) {
      console.error("Error adding activity to camp day:", error);
    }
  };

  const handleVolunteerDelete = async (id, volunteerId) => {
    try {
      await axios.delete(
        `http://localhost:8000/activities/remove-volunteer/${id}/${volunteerId}`
      );
      loadActivities();
    } catch (error) {
      console.error("Error deleting activiies:", error.message);
    }
  };

  return (
    <section>
      <h1 className="mb-4">Activities</h1>

      <table className="table table-bordered table-hover">
        <thead>
          <tr className="text-center">
            <th>
              <Link className="nav-link " to={"/activities-add"}>
                <span class="material-icons">add</span>
              </Link>
            </th>
            <th>id</th>
            <th>title</th>
            <th>type</th>
            <th>description</th>
            <th>camp day</th>
            <th colSpan="2">volunteers</th>
            <th>update</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {activities.map((activity, index) => (
            <tr key={activity.id}>
              <td>
                <button
                  className="btn btn-transperant btn-sm fw-bold fs-5"
                  onClick={() => handleDelete(activity.id)}
                >
                  <span className="material-icons">remove</span>
                </button>
              </td>
              <th scope="row">{index + 1}</th>
              <td>{activity.title}</td>
              <td>{activity.type}</td>
              <td>{activity.description}</td>

              <td>
                {campDate[activity.id] ? (
                  <div>
                    {new Date(campDate[activity.id]).toLocaleDateString()}
                  </div>
                ) : (
                  <div>No Camp Day</div>
                )}
              </td>

              <td>
                {volunteerLists[activity.id] &&
                volunteerLists[activity.id].length > 0 ? (
                  <div>
                    {volunteerLists[activity.id].map(
                      (volunteer, volunteerIndex) => (
                        <div key={volunteerIndex}>
                          {volunteer.name}
                          <button
                            className="btn btn-transperant btn-sm fw-bold fs-5"
                            onClick={() =>
                              handleVolunteerDelete(activity.id, volunteer.id)
                            }
                          >
                            <span className="material-icons">remove</span>
                          </button>
                        </div>
                      )
                    )}
                  </div>
                ) : (
                  <div>No Volunteers</div>
                )}
              </td>
              <td>
                <div>
                  <label htmlFor={`volunteer-${activity.id}`}>
                    Select Volunteer:
                  </label>
                  <select
                    id={`volunteer-${activity.id}`}
                    name={`volunteer-${activity.id}`}
                    value={
                      activity.id === selectedVolunteerId
                        ? ""
                        : selectedVolunteerId
                    }
                    onChange={(e) => setSelectedVolunteerId(e.target.value)}
                  >
                    <option value="" disabled>
                      Select a volunteer
                    </option>
                    {volunteers.map((volunteer) => (
                      <option key={volunteer.id} value={volunteer.id}>
                        {volunteer.name}
                      </option>
                    ))}
                  </select>
                  <button
                    className="btn btn-transperant btn-sm fw-bold fs-5"
                    onClick={() =>
                      handleAddVolunteer(activity.id, selectedVolunteerId)
                    }
                  >
                    <span className="material-icons">add</span>
                  </button>
                </div>
              </td>
              <td>
                {" "}
                <Link
                  to={`/activies-update/${activity.id}`}
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

export default HomeActivities;
