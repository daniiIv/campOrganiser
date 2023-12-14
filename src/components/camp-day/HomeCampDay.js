import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const HomeCampDay = () => {
  const [campDays, setCampDays] = useState([]);
  const [activities, setActivities] = useState([]);
  const [selectedActivityId, setSelectedActivityId] = useState("");

  useEffect(() => {
    loadCampDays();
  }, []);

  const loadCampDays = async () => {
    try {
      const result = await axios.get("http://localhost:8000/camp-days/all", {
        validateStatus: () => true,
      });
      setCampDays(result.data);
    } catch (error) {
      console.error("Error loading activiies:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8000/camp-days/${id}`);
      loadCampDays();
    } catch (error) {
      console.error("Error deleting activiies:", error);
    }
  };

  const handleActiviyDelete = async (id, activityId) => {
    try {
      await axios.delete(
        `http://localhost:8000/camp-days/${id}/remove-existing-activity/${activityId}`
      );
      loadCampDays();
    } catch (error) {
      console.error("Error deleting activiies:", error);
    }
  };
  useEffect(() => {
    // Fetch the list of all activities when the component mounts
    const fetchActivities = async () => {
      try {
        const result = await axios.get("http://localhost:8000/activities/all");
        setActivities(result.data);
      } catch (error) {
        console.error("Error loading activities:", error);
      }
    };

    fetchActivities();
  }, []);

  const handleAddActivity = async (id, activityId) => {
    try {
      // Make a request to add the selected activity to the camp day
      await axios.post(
        `http://localhost:8000/camp-days/${id}/add-existing-activity/${activityId}`
      );

      // Reload the camp days after the addition
      loadCampDays();
    } catch (error) {
      console.error("Error adding activity to camp day:", error);
    }
  };

  return (
    <section>
      <h1 className="mb-4">Camp Day</h1>

      <table className="table table-bordered table-hover">
        <thead>
          <tr className="text-center">
            <th>
              <Link className="nav-link " to={"/campDays-add"}>
                <span class="material-icons">add</span>
              </Link>
            </th>
            <th>id</th>
            <th>date</th>
            <th colSpan="2">activies</th>
            <th>update</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {campDays.map((campDay, index) => (
            <tr key={campDay.id}>
              <td>
                <button
                  className="btn btn-transperant btn-sm fw-bold fs-5"
                  onClick={() => handleDelete(campDay.id)}
                >
                  <span className="material-icons">remove</span>
                </button>
              </td>
              <th scope="row">{index + 1}</th>
              <td>{campDay.date}</td>
              <td>
                <table>
                  <td>
                    {campDay.activities.map((activity) => (
                      <tr>
                        <td className="text-left" key={activity.id}>
                          {activity.type} - {activity.title}
                        </td>
                        <td>
                          <button
                            className="btn btn-transperant btn-sm fw-bold fs-5"
                            onClick={() =>
                              handleActiviyDelete(campDay.id, activity.id)
                            }
                          >
                            <span className="material-icons">remove</span>
                          </button>
                        </td>
                      </tr>
                    ))}
                  </td>
                </table>
              </td>
              <td>
                <div>
                  <label htmlFor={`activity-${campDay.id}`}>
                    Select Activity:
                  </label>
                  <select
                    id={`activity-${campDay.id}`}
                    name={`activity-${campDay.id}`}
                    value={
                      campDay.id === selectedActivityId
                        ? ""
                        : selectedActivityId
                    }
                    onChange={(e) => setSelectedActivityId(e.target.value)}
                  >
                    <option value="" disabled>
                      Select an activity
                    </option>
                    {activities.map((activity) => (
                      <option key={activity.id} value={activity.id}>
                        {activity.title}
                      </option>
                    ))}
                  </select>
                  <button
                    className="btn btn-transperant btn-sm fw-bold fs-5"
                    onClick={() =>
                      handleAddActivity(campDay.id, selectedActivityId)
                    }
                  >
                    <span className="material-icons">add</span>
                  </button>
                </div>
              </td>
              <td>
                {" "}
                <Link
                  to={`/campDays-update/${campDay.id}`}
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

export default HomeCampDay;
