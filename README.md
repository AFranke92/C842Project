# Inventory Management System (JavaFX)
Overview

This project is a desktop inventory management application built using Java and JavaFX. The application allows users to manage parts and products for a small manufacturing organization, supporting full CRUD functionality with real-time UI updates.

The system replaces a spreadsheet-based workflow with a structured, object-oriented solution designed around maintainability, usability, and clear separation of concerns.

Features:

Add, modify, delete, and search Parts and Products

Associate parts with products

Real-time table updates using ObservableList

Input validation and error handling

Clean JavaFX UI built from FXML layouts

MVC-inspired architecture separating UI, business logic, and data models

Technologies Used:

Java

JavaFX

FXML

Object-Oriented Programming (OOP)

Model–View–Controller (MVC) principles

Application Architecture:

The application is structured using a clear separation of responsibilities:

Model:

Part, Product, and Inventory classes manage application data and business logic

Uses ObservableList to keep the UI synchronized with data changes

View:

JavaFX FXML files define the user interface layouts

Tables and forms dynamically reflect data updates

Controller:

Handles user interactions, validation, and navigation between screens

Connects the UI to the underlying inventory model

Key Implementation Details:

Inventory data is stored in memory using observable collections

Search functionality supports both ID-based and name-based queries

Products maintain associated parts, enforcing business rules before deletion

Designed for clarity and extensibility rather than persistence or networking

Screens

(UI screenshots will be added here)

Purpose:

This project was developed to demonstrate proficiency in:

Java and JavaFX application development

Object-oriented design

UI-driven data modeling

Implementing business requirements into functional software

Future Improvements:

Add persistent storage (database or file-based)

Improve update logic using direct indexing or UUIDs

Implement advanced filtering and sorting

Add unit testing for inventory operations
