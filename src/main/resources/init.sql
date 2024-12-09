CREATE TABLE role (
                      role_id SERIAL PRIMARY KEY,
                      role_name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE person (
                      person_id SERIAL PRIMARY KEY,
                      person_name VARCHAR(100) NOT NULL UNIQUE,
                      email VARCHAR(150) NOT NULL UNIQUE,
                      password_hash VARCHAR(255) NOT NULL,
                      role_id INT NOT NULL,
                      rating DECIMAL(3, 2) DEFAULT 0.0,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (role_id) REFERENCES role(role_id)
);
CREATE TABLE projectEntity (
                         project_id SERIAL PRIMARY KEY,
                         person_id INT NOT NULL,
                         title VARCHAR(255) NOT NULL,
                         description TEXT NOT NULL,
                         budget_min DECIMAL(10, 2) NOT NULL,
                         budget_max DECIMAL(10, 2) NOT NULL,
                         deadline DATE NOT NULL,
                         status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'in_progress', 'completed')),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (person_id) REFERENCES person(person_id)
);
CREATE TABLE bid (
                     bid_id SERIAL PRIMARY KEY,
                     project_id INT NOT NULL,
                     person_id INT NOT NULL,
                     bid_amount DECIMAL(10, 2) NOT NULL,
                     proposal TEXT NOT NULL,
                     status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'accepted', 'rejected')),
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     FOREIGN KEY (project_id) REFERENCES projectEntity(project_id),
                     FOREIGN KEY (person_id) REFERENCES person(person_id)
);
CREATE TABLE project_update (
                                update_id SERIAL PRIMARY KEY,
                                project_id INT NOT NULL,
                                update_text TEXT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (project_id) REFERENCES projectEntity(project_id)
);
CREATE TABLE message (
                         message_id SERIAL PRIMARY KEY,
                         sender_id INT NOT NULL,
                         receiver_id INT NOT NULL,
                         project_id INT,
                         content TEXT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (sender_id) REFERENCES person(person_id),
                         FOREIGN KEY (receiver_id) REFERENCES person(person_id),
                         FOREIGN KEY (project_id) REFERENCES projectEntity(project_id)
);
CREATE TABLE review (
                        review_id SERIAL PRIMARY KEY,
                        project_id INT NOT NULL,
                        reviewer_id INT NOT NULL,
                        reviewed_person_id INT NOT NULL,
                        rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                        comment TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (project_id) REFERENCES projectEntity(project_id),
                        FOREIGN KEY (reviewer_id) REFERENCES person(person_id),
                        FOREIGN KEY (reviewed_person_id) REFERENCES person(person_id)
);