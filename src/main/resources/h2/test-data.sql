INSERT INTO Project
    (project_id, description)
VALUES
    (1, 'Create a service that implements CRUD operations to the embedded database.'),
    (2, 'Implement a service that works with the API of the service providing weather forecast services based on data from the database.'),
    (3, 'Create views of weather forecast data obtained using the previously mentioned service.');

INSERT INTO User
    (user_id, name, project_id)
VALUES
    (1, 'Victor', 2),   --junior
    (2, 'Anton', 1),    --team lead
    (3, 'Juli', 3),     --junior
    (4, 'Max', 2),      --team lead
    (5, 'Victoria', 2), --junior
    (6, 'Denis', 3),    --team lead
    (7, 'Ann', 2),      --middle
    (8, 'Pavel', 1),    --junior
    (9, 'Maria', 3);    --junior

INSERT INTO Task
    (task_id, description, user_id, parent_task_id)
VALUES
    (1, 'Integrate database into the project and create all repositories and services.', 2, NULL),
    (2, 'Create business logic to work with the API.', 4, NULL),
    (3, 'Create reflection for data from API on basic Java objects.', 5, NULL),
    (4, 'Develop Web application using MVC pattern with the expectation of scaling.', 6, NULL),
    (5, 'Config Spring Boot and create controllers.', 8, 1),
    (6, 'Create entities and DTOs for it.', 7, 2),
    (7, 'Configure service.', 1, 3),
    (8, 'Write markup for application Views.', 3, 4),
    (9, 'Create all the required Models for the application.', 9, 4);


