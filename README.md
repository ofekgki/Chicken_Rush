# Chicken Rush!

Project for a Application Development course, in Afeka - the academic college of Engineering in Tel Aviv.

# Overview

Chicken Rush! is a farm theme game application using Kotlin, in the game you should escape from obstacles and collect eggs for maximum score!
The game features multiple input methods and location-based scoring.

# Features
-   **Mode Control**: Players can choose between buttons or physical device tilting using the phone sensors.
-   **Changing Speed**: Includes a Fast Speed Mode toggle to increase game difficulty.

-   **Sound Effects**: Custom audio for hit and collectables.

-   **Location Based Leaderboard**: High scores are saved with the player's name and GPS coordinates, clicking an entry will zoom on the score location.

## Getting Started

To run this project in your local machine, you'll need to use a Google Maps API key.

1. **Create local.properties**: In your project root folder, find or create a file named `local.properties`.
2. **Add the Key**: Add the following line to the file, replacing `YOUR_API_KEY` with your actual key:

   MAPS_API_KEY=YOUR_API_KEY
 
## App Screens
#### Start Screen: 
 - Choose between Buttons or Sensors Mode
- Choose Speed - Fast/Slow
- Turn On/Off the background music
- Enter Top 10 screen

<img src="screenshots/1_start_screen.png" width="300" alt="Start Screen">

#### Button/Sensors Mode Screens:

1. Pan - escape the pans to evoid damage
2. Egg - collect the eggs to earn more points
3. Seed - collect seeds to earn more HP aftett gettting hit

 Sensors - 
-  Tilt your phone Left/Right to switch lanes
 -  Tilt your phone Forward/Backward to change the speed 
 
<img src="screenshots/2_Buttons_Mode.png" width="300" alt="Buttons Mode">
 <img src="screenshots/3_Sensor_Mode.png" width="300" alt=" Sensor Mode">

#### End Game Screen:

Enter your name to enter top ten list
Choose between  Top 10 /  Main Menu

<img src="screenshots/4_End_Game.png" width="300" alt="End Game">


#### Top Ten Screen: 

Scrool the list to see all top ten scores
Tap any name to see exact location on the map

Choose between starting a New Game / back to Main Menu

<img src="screenshots/5_Top_Ten.png" width="300" alt="Top Ten">
