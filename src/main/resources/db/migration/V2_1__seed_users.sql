SET @ADMIN_ROLE_ID = (SELECT id FROM tbl_roles WHERE name = 'ADMIN');
SET @USER_ROLE_ID = (SELECT id FROM tbl_roles WHERE name = 'USER');

INSERT INTO tbl_users (id, role_id, email, password)
VALUES
    (UUID(), @ADMIN_ROLE_ID, 'mrlotzo@gmail.com', ''),
    (UUID(), @USER_ROLE_ID, 'prince.oncada@gmail.com', '');
