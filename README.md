# Flypass Quantum: Airline Management System (JavaFX Demo)

Educational JavaFX application demonstrating a full-stack airline management system with database integration, role-based access, and USB authentication. Models users, flights, duties using OOP principles.

<div align="center">
    <img src="./UML.png" alt="UML" width="70%">
</div>

## Project Overview

- **Purpose:** Comprehensive demo of JavaFX UI with MySQL backend for airline operations, including secure login, flight booking, and admin tools.
- **Language:** Java (JDK 17+ with JavaFX), MySQL for persistence.

## Key Classes

- `Main` — Application entry point, launches JavaFX stage.
- `DBConnection` — Handles MySQL connection and schema initialization.
- `UserDAO`, `FlightDAO`, `DutyDAO` — Data access objects for CRUD operations.
- `User`, `Flight`, `Duty` — Model classes representing entities.
- Controllers (e.g., `LoginController`, `ProfileController`) — Handle UI logic for FXML views.
- Utilities (e.g., `EncryptionUtil`, `UsbKeyFetcher`) — For security and USB detection.

## Project Structure

- src/
  - Main.java
  - DBConnection.java
  - dao/ (UserDAO.java, FlightDAO.java, DutyDAO.java)
  - models/ (User.java, Flight.java, Duty.java)
  - controllers/ (LoginController.java, ProfileController.java, etc.)
  - utils/ (EncryptionUtil.java, UsbKeyFetcher.java)
  - fxml/ (*.fxml) — UI layouts
- css/ (*.css) — Stylesheets for themes
- resources/ (titleicon.png, etc.)

## Build & Run

From the repository root run:

```bash
javac -cp .:/path/to/mysql-connector.jar --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d out src/*.java src/controllers/*.java src/dao/*.java src/models/*.java src/utils/*.java
java -cp .:/path/to/mysql-connector.jar --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -Djava.library.path=/path/to/javafx-sdk/lib out.Main