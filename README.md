# Application Setup Guide

This guide will help you set up and run the application successfully.

## Prerequisites

- Android Studio version: Girrafe 2022.3.1
- Java Development Kit (JDK): Java 17
- Gradle version: 8.1.1

## Getting an API Key

1. Go to [https://newsapi.org/](https://newsapi.org/).
2. Click on "Get Api Key" and follow the instructions to obtain an API key.

## Configuration

1. Open the project in Android Studio.
2. Navigate to the directory: `secrets/development.properties`.
3. Replace Base Url and API Key in default.properties:
   In the `default.properties` file, find the line:
   ```kotlin
   BASE_URL = "https://newsapi.org/v2/"
   API_KEY = ""
   ```
   Replace "API_KEY" with the API key you obtained earlier.
5. Gradle Version Configuration:
   - Open Android Studio.
   - Go to Preferences/Settings > "File" > "Project Structure" > "Project".
   - Under "Gradle Version," select 8.1.1.
6. JDK Version Configuration:
   - Open Android Studio.
   - Go to Preferences/Settings > "File" > "Project Structure" > "SDK Location" > "Gradle Settings".
   - Under "Gradle JDK," select Java 17.

## Running the Application

1. Open the project in Android Studio.
2. Press the "Run" button in Android Studio to run the application.

## Kotlin-DSL Build Scripts
The project uses Kotlin-DSL build scripts to manage dependencies and plugins.
