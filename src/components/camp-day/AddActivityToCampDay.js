import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const AddActivityToCampDay = ({ campDayId, loadCampDays }) => {
  const [activities, setActivities] = useState([]);
  const [selectedActivityId, setSelectedActivityId] = useState("");

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
  }, []); // The empty dependency array ensures this runs only once on mount

  const handleAddActivity = async () => {
    try {
      // Make a request to add the selected activity to the camp day
      await axios.post(
        `http://localhost:8000/camp-days/${campDayId}/add-existing-activity/${selectedActivityId}`
      );

      // Reload the camp days after the addition
      loadCampDays();
    } catch (error) {
      console.error("Error adding activity to camp day:", error);
    }
  };

  return (
    <div>
      <h2>Add Activity to Camp Day</h2>
      <label htmlFor="activity">Select Activity:</label>
      <select
        id="activity"
        name="activity"
        value={selectedActivityId}
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
      <button onClick={handleAddActivity}>Add Activity</button>
    </div>
  );
};

export default AddActivityToCampDay;
