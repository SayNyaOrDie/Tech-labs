DO
$$
    BEGIN
        IF
            NOT EXISTS (SELECT columns.column_name
                        FROM information_schema.columns
                        WHERE table_name = 'task'
                          AND column_name = 'task_author') THEN
            ALTER TABLE task
                ADD COLUMN task_author VARCHAR(255);
        END IF;
    END
$$;
