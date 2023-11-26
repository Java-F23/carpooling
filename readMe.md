Seifeldin Elshabshiri
900202310
section 1
https://chat.openai.com/share/a55743f7-599a-4f80-8beb-7294ff562536


Refactoring Rationale:

- OOP a base entity class that has an id parameter which is common for all entities and that is why all the entities inherit from it.
- BaseUser is an entity that also has common factors between rider and captain and that is why they both enherit from it
- I used the repository pattern in which I created a Genric repo that can be used by all the entities
- Additionally there is a rideRepository that adds an additional function to the generic repo
- The services are then a layer above the repo which includes some logic.
- the Big controller class calls makes the services and calls the FrameController which is the start of the GUI
- overided functions are used throughout the code, overloading can be shown in the signUp function inside the userService in which i can either call it for a captain or a rider.
- additionally streams are used when filtering rides
- lambdas are used in the gui
- regular expression is used when signing up in the email field and number field


