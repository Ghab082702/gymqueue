import {useState} from "react";

function BookingForm() {
    const [userName, setUserName] = useState("");
    const [equipmentId, setEquipmentId] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState(null);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage(null);
        setError(null);

        try {
            const response = await fetch("http://localhost:8080/api/bookings", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    userName,
                    equipmentId: parseInt(equipmentId),
                    status: "QUEUED",
                }),
            });

            if (!response.ok) {
                throw new Error(`Failed to create booking: ${response.status}`);
            }

            const booking = await response.json();
            setMessage(`Booking created successfully! ID: ${booking.queuePosition}`
            );
            setUserName("");
            setEquipmentId("");
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };
    return (
        <div>
            <h2>Book Equipment</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name: </label>
                    <input type="text" value={userName} onChange={(e) => setUserName(e.target.value)} required/>
                </div>
                <div>
                    <label>Equipment ID: </label>
                    <input type="number" value={equipmentId} onChange={(e) => setEquipmentId(e.target.value)} required/>
                </div>
                <button type="submit" disabled={loading}>
                    {loading ? "Booking..." : "Book Now"}
                </button>
            </form>
            {message && <p style={{color: "green"}}>{message}</p>}
            {error && <p style={{color: "red"}}>{error}</p>}
        </div>
    );
}

export default BookingForm;