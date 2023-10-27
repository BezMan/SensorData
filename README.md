
 Written by Betzalel Silver. 
I put emphasis on clean code, strong structure and scalable architecture. 

 
# Flow
welcome screen -> grant permissions -> check if light conditions are good to move to capture screen -> capture light sensor data -> 
view summary screen when session ends (30 sec or click to end session) -> EXPORT DATA creates a CSV file and allows to share/send it. 

* I added 300 ms delay per row, in order to be able to view the LOADING state.
* I open the camera for user experience. not required in order to capture the light sensor data.

# Features and tools
- MVVM architecture and Repo pattern.
- Kotlin code with Compose UI.
- Compose Navigation Component.
- Dependency Injection with Dagger Hilt.
- sealed class Resource.kt for state management.
- Interfaces for abstraction ability to change repo impl easily.
- PermissionManager to handle permission logic.

# Libs used:
- opencsv
- CameraX
  

