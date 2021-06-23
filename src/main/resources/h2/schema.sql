CREATE TABLE Project(
    project_id BIGINT IDENTITY(1, 1) NOT NULL,
    description TEXT,
    CONSTRAINT PK_Project PRIMARY KEY(project_id)
);

CREATE TABLE User(
    user_id BIGINT IDENTITY(1, 1) NOT NULL,
    name VARCHAR(100),
    project_id BIGINT,
    CONSTRAINT PK_User PRIMARY KEY(user_id),
    CONSTRAINT FK_project_id FOREIGN KEY(project_id) REFERENCES Project(project_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE Task(
    task_id BIGINT IDENTITY(1, 1) NOT NULL,
    description TEXT,
    user_id BIGINT,
    parent_task_id BIGINT,
    CONSTRAINT PK_Task PRIMARY KEY(task_id),
    CONSTRAINT FK_user_id FOREIGN KEY(user_id) REFERENCES User(user_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT FK_parent_task_id FOREIGN KEY(parent_task_id) REFERENCES Task(task_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);