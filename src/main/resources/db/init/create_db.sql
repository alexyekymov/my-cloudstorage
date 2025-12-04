DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'mycloudstorage') THEN
            CREATE DATABASE mycloudstorage;
        END IF;
    END
$$;
