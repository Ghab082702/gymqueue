import { useState, useEffect } from "react";

function EquipmentList() {
    const [equipment, setEquipment] = useState([]);
    const [loading, setLoading] = useState(true); 
    const [error, setError] = useState(null); 

    useEffect(() => {
    const fetchEquipment = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/equipment');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setEquipment(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    
       fetchEquipment();
  }, []);

  if (loading) return <p>Loading equipment...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h1>Available Equipment</h1>
      <ul>
        {equipment.map((item) => (
          <li key={item.id}>
            <h3>{item.name}</h3>
            <p>{item.description}</p>
            <p>Max Capacity: {item.maxCapacity}</p>
            <p>Status: {item.status}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default EquipmentList;