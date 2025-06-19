# Cat-clinic

An Android mobile app that helps users practice Cognitive Behavioral Therapy (CBT) techniques through a playful, cat-themed interface. Users “put their thoughts on trial,” weigh evidence for/against them, and arrive at a verdict—all with end-to-end encrypted storage.

---

## Table of Contents

1. [Features](#features)  
2. [Prerequisites](#prerequisites)  
3. [Getting Started](#getting-started)  
4. [Project Structure](#project-structure)  
5. [Security & Encryption](#security--encryption)  
6. [Contributing](#contributing)  
7. [License](#license)

---

## Features

- **User Authentication** – Sign up and log in securely.  
- **Thought Trial Worksheet** – Record a thought, list evidence for and against, and capture a final verdict.  
- **History View** – Browse past entries, expand for details, and edit or delete as needed.  
- **Real-time Sync** – Changes are saved immediately to Firebase Firestore.  
- **End-to-End Encryption** – All user data is encrypted locally before upload.

## Prerequisites

- Android Studio (Arctic Fox or later)  
- JDK 11 or newer  
- A Firebase project with Firestore and Authentication enabled  
- Android SDK Platform 35 (or matching compileSdkVersion)

## Getting Started

1. **Clone the repo**  
   ```bash
   git clone https://github.com/Rastogi-Anmol/Cat-clinic.git
