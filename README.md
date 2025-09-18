🩸 Blood Donor Finder
A web-based application to connect blood donors and recipients quickly and efficiently. 
This project aims to simplify the process of finding compatible blood donors in urgent 
situations by providing a centralized platform for registration, search, and management.


🚀 Features
i. 🔐 User Authentication – Secure login & registration using Session based authentication.

ii. 👤 Donor Registration – Donors can register with their details (name, blood group, location, contact info).

iii. 🔍 Search Functionality – Recipients can search donors by blood group & location.

iv. 📱 Responsive UI – Clean and user-friendly interface (HTML, Tailwind CSS).

v. 🛡 Backend Security – Protected APIs with Spring Boot.

vi. 💾 Database Integration – Donor and user data stored securely in MySQL.


🛠 Tech Stack
Frontend: HTML, Tailwind CSS
Backend: Java, Spring Boot
Database: MySQL
Authentication: Session based

⚙️ Installation & Setup
1. Clone the Repository
   git clone https://github.com/your-username/blood-donor-finder.git
   cd blood-donor-finder

2. Backend Setup
    Install Java (JDK 17+) and Maven.

    Navigate to blood_donor_finder:
    mvn spring-boot:run

3. Database Setup

    Install MySQL.

    Create a database:

    CREATE DATABASE blood_donor_finder;

Update application.properties with your DB username & password.

Run the project to auto-create tables.

4. Frontend Setup

 Open frontend/index.html in your browser.
 
##Screenshots

**LoginPage**
![Login Page](images/LoginForm.png)

**IndexPage**
![Index Page](images/Indexpage.png)

**Registration**
![Registration](images/Registration.png)

**HomePage**
![Home Page](images/HomePage.png)

**DonationForm**
![Donation Form](images/DonationForm.png)

🔮 Future Improvements

i. Add email/SMS notifications for donor requests.

ii. Implement Google Maps API for donor location tracking.

iii. Mobile app integration (React Native / Flutter).