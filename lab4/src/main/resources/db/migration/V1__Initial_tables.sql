DO
$$
    BEGIN
        IF NOT EXISTS (SELECT
                       FROM pg_tables
                       WHERE schemaname = 'public'
                         AND tablename = 'employee') THEN
            CREATE TABLE employee
            (
                employee_id       BIGSERIAL PRIMARY KEY,
                employee_name     VARCHAR(255),
                employee_birthdate DATE
            );
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_tables WHERE schemaname = 'public' AND tablename = 'task') THEN
            CREATE TABLE task
            (
                task_id          BIGSERIAL PRIMARY KEY,
                task_name       VARCHAR(255),
                task_deadline    DATE,
                task_description VARCHAR(255),
                task_type    VARCHAR(50) CHECK (task_type IN ('новый функционал', 'ошибка', 'улучшение', 'аналитика')),
                task_employee  BIGINT,
                FOREIGN KEY (task_employee) REFERENCES employee (employee_id)
            );
        END IF;
    END
$$;
DO
$$
    BEGIN
        IF
            NOT EXISTS (SELECT
                        FROM pg_tables
                        WHERE schemaname = 'public'
                          AND tablename = 'comment') THEN
            CREATE TABLE comment
            (
                comment_id      BIGINT PRIMARY KEY,
                comment_content VARCHAR,
                comment_author  VARCHAR NOT null,
                comment_date    DATE    NOT null,
                comment_task_id BIGINT  NOT NULL,
                FOREIGN KEY (comment_task_id) REFERENCES task (task_id)
            );
        END IF;
    END
$$;
