DO
$$
    BEGIN
        IF
            NOT EXISTS (SELECT columns.column_name
                        FROM information_schema.columns
                        WHERE table_name = 'employee'
                          AND column_name = 'employee_password') THEN
            ALTER TABLE employee
                ADD COLUMN employee_password VARCHAR(255);
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF
            NOT EXISTS (SELECT columns.column_name
                        FROM information_schema.columns
                        WHERE table_name = 'employee'
                          AND column_name = 'employee_admin') THEN
            ALTER TABLE employee
                ADD COLUMN employee_admin BOOLEAN;
        END IF;
    END
$$;
