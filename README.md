
 Written by Betzalel Silver. 
I put emphasis on clean code, strong structure and scalable architecture. 

 
# Flow
welcome screen -> check if light conditions are good to move to capture screen -> capture light sensor data -> 
view summary screen when session ends (30 sec or click to end session) -> export data button click creates a CSV file and allows to share it. 


# Features and tools
- MVVM architecture and Repo pattern.
- Kotlin code with Compose UI.
- Compose Navigation Component.
- Dependency Injection with Dagger Hilt.
- sealed class Resource.kt for state management.
- Interfaces for abstraction ability to change repo impl easily.

