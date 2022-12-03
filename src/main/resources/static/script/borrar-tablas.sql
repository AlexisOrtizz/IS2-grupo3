DO $$ DECLARE
  r RECORD;
BEGIN
  FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema()) LOOP
    EXECUTE 'DROP TABLE ' || quote_ident(r.tablename) || ' CASCADE';
  END LOOP;
END $$;

select * from rol_permiso

SELECT * FROM usuario;
SELECT * FROM rol;
SELECT * FROM permiso;
SELECT * FROM vista;
SELECT * FROM proyecto;
SELECT * FROM sprint;
SELECT * FROM backlog;
SELECT * FROM user_story;