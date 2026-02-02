# Inventory Management System (JavaFX)
Overview

This project is a JavaFX-based inventory management application designed to manage parts and products for a small manufacturing or service organization.

The application replaces spreadsheet-based inventory tracking with a structured desktop solution that supports searching, adding, modifying, and deleting inventory items through a graphical user interface.

Features:

Add, modify, and delete parts and products

Associate parts with products

Search inventory by ID or name

Centralized inventory state management

Input validation to prevent invalid data entry

JavaFX-based GUI built with FXML

Application Design:

The application follows a clear separation of concerns:

Model Layer

Represents parts, products, and inventory data

Manages relationships between parts and products

Controller Layer

Handles user interactions and screen navigation

Enforces business logic and validation

View Layer

JavaFX UI built using FXML layouts

The Inventory class acts as a centralized manager for all parts and products, providing lookup, update, and deletion functionality.

Technologies Used:

Java

JavaFX

FXML

Object-Oriented Programming (OOP)

How to Run:

Build and run the application

Use the main screen to manage parts and products

Add, modify, or remove inventory items as needed

Screens

(UI screenshots will be added here)

Purpose:

This project demonstrates:

Desktop application development with JavaFX

Object-oriented design principles

Inventory modeling and state management

Practical business application logic

Future Improvements:

Add persistent storage (database or file-based)

Improve update logic using direct indexing or UUIDs

Implement advanced filtering and sorting

Add unit testing for inventory operations
