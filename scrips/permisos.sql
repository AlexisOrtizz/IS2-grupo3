"""Permisos otorgados al super user de id=1"""

INSERT INTO usuarios_usuario_user_permissions VALUES (1,1,1);
INSERT INTO usuarios_usuario_user_permissions VALUES (2,1,2);
INSERT INTO usuarios_usuario_user_permissions VALUES (3,1,3);
INSERT INTO usuarios_usuario_user_permissions VALUES (4,1,4);
INSERT INTO usuarios_usuario_user_permissions VALUES (5,1,5);
INSERT INTO usuarios_usuario_user_permissions VALUES (6,1,6);
INSERT INTO usuarios_usuario_user_permissions VALUES (7,1,7);
INSERT INTO usuarios_usuario_user_permissions VALUES (8,1,8);
INSERT INTO usuarios_usuario_user_permissions VALUES (9,1,9);
INSERT INTO usuarios_usuario_user_permissions VALUES (10,1,10);
INSERT INTO usuarios_usuario_user_permissions VALUES (11,1,11);
INSERT INTO usuarios_usuario_user_permissions VALUES (12,1,12);
INSERT INTO usuarios_usuario_user_permissions VALUES (13,1,13);
INSERT INTO usuarios_usuario_user_permissions VALUES (14,1,14);
INSERT INTO usuarios_usuario_user_permissions VALUES (15,1,15);
INSERT INTO usuarios_usuario_user_permissions VALUES (16,1,16);
INSERT INTO usuarios_usuario_user_permissions VALUES (17,1,17);
INSERT INTO usuarios_usuario_user_permissions VALUES (18,1,18);
INSERT INTO usuarios_usuario_user_permissions VALUES (19,1,19);
INSERT INTO usuarios_usuario_user_permissions VALUES (20,1,10);
INSERT INTO usuarios_usuario_user_permissions VALUES (21,1,21);
INSERT INTO usuarios_usuario_user_permissions VALUES (22,1,22);
INSERT INTO usuarios_usuario_user_permissions VALUES (23,1,23);
INSERT INTO usuarios_usuario_user_permissions VALUES (24,1,24);
INSERT INTO usuarios_usuario_user_permissions VALUES (25,1,25);


----------TABLA ROL_PERMISOS------------
INSERT INTO rol_permiso VALUES (1,"Crear Usuarios",1);
INSERT INTO rol_permiso VALUES (2,"Modificar Usuarios",1);
INSERT INTO rol_permiso VALUES (3,"Ver Usuarios",1);
INSERT INTO rol_permiso VALUES (4,"Ver Roles",1);
INSERT INTO rol_permiso VALUES (5,"Crear Roles",1);
INSERT INTO rol_permiso VALUES (6,"Modificar Roles",1);
INSERT INTO rol_permiso VALUES (7,"Crear Proyectos",2);
INSERT INTO rol_permiso VALUES (8,"Modificar Proyectos",2);
INSERT INTO rol_permiso VALUES (9,"Ver Proyectos",2);
INSERT INTO rol_permiso VALUES (10,"Crear Flujos",2);
INSERT INTO rol_permiso VALUES (11,"Modificar Flujos",2);
INSERT INTO rol_permiso VALUES (12,"Ver Flujos",2);
INSERT INTO rol_permiso VALUES (13,"Crear Tipos de User Story",2);
INSERT INTO rol_permiso VALUES (14,"Modificar Tipos de User Story",2);
INSERT INTO rol_permiso VALUES (15,"Ver Tipos de User Story",2);
INSERT INTO rol_permiso VALUES (16,"Crear User Stories",2);
INSERT INTO rol_permiso VALUES (17,"Modificar User Stories",2);
INSERT INTO rol_permiso VALUES (18,"Ver User Stories",2);
INSERT INTO rol_permiso VALUES (19,"Crear Sprints",2);
INSERT INTO rol_permiso VALUES (20,"Modificar Sprints",2);
INSERT INTO rol_permiso VALUES (21,"Ver Sprints",2);
INSERT INTO rol_permiso VALUES (22,"Crear Flujos",2);
INSERT INTO rol_permiso VALUES (23,"Modificar Flujos",2);
INSERT INTO rol_permiso VALUES (24,"Ver Flujos",2);
INSERT INTO rol_permiso VALUES (25,"Ver Definiciones de Proyectos",2);
INSERT INTO rol_permiso VALUES (26,"Ver Product Backlog",2);
INSERT INTO rol_permiso VALUES (27,"Asignar User Stories",2);
INSERT INTO rol_permiso VALUES (28,"Reasignar Team Member",2);
INSERT INTO rol_permiso VALUES (29,"Ver Control de Calidad",2);
INSERT INTO rol_permiso VALUES (30,"Ver Tablero",2);
INSERT INTO rol_permiso VALUES (31,"Ver Definiciones de Proyectos",2);
INSERT INTO rol_permiso VALUES (32,"Iniciar Sprints",2);
INSERT INTO rol_permiso VALUES (33,"Reiniciar Proyecto",2);
INSERT INTO rol_permiso VALUES (34,"Ver Ejecucion de Proyecto",2);
INSERT INTO rol_permiso VALUES (35,"Ver Ejecuciones de Proyectos",2);
INSERT INTO rol_permiso VALUES (36,"Iniciar Proyecto",2);
INSERT INTO rol_permiso VALUES (37,"Cancelar Proyecto",2);
INSERT INTO rol_permiso VALUES (38,"Terminar Proyecto",2);
INSERT INTO rol_permiso VALUES (39,"Suspender Proyecto",2);
INSERT INTO rol_permiso VALUES (40,"Ver Product",2);



-----------TABLA ROL_ROL----------
INSERT INTO rol_rol VALUES (1, false, "Administrador", "Administra los usuarios del sistema");
INSERT INTO rol_rol VALUES (2, false, "Administrador del Proyecto", "Administra todo lo referente al proyecto");


----------TABLA ROL_ROL_PERMISOS-----------
INSERT INTO rol_rol_permisos VALUES (1,1,1);
INSERT INTO rol_rol_permisos VALUES (2,1,2);
INSERT INTO rol_rol_permisos VALUES (3,1,3);
INSERT INTO rol_rol_permisos VALUES (4,1,4);
INSERT INTO rol_rol_permisos VALUES (5,1,5);
INSERT INTO rol_rol_permisos VALUES (6,1,6);
INSERT INTO rol_rol_permisos VALUES (7,2,7);
INSERT INTO rol_rol_permisos VALUES (8,2,8);
INSERT INTO rol_rol_permisos VALUES (9,2,9);


----------TABLA USUARIOS_PERMISOS---------
INSERT INTO usuarios_usuario_permisos VALUES (1,1,1);
INSERT INTO usuarios_usuario_permisos VALUES (2,1,2);
INSERT INTO usuarios_usuario_permisos VALUES (3,1,3);
INSERT INTO usuarios_usuario_permisos VALUES (4,1,4);
INSERT INTO usuarios_usuario_permisos VALUES (5,1,5);
INSERT INTO usuarios_usuario_permisos VALUES (6,1,6);
INSERT INTO usuarios_usuario_permisos VALUES (25,1,7);
INSERT INTO usuarios_usuario_permisos VALUES (26,1,8);
INSERT INTO usuarios_usuario_permisos VALUES (27,1,9);
INSERT INTO usuarios_usuario_permisos VALUES (28,1,10);
INSERT INTO usuarios_usuario_permisos VALUES (29,1,11);
INSERT INTO usuarios_usuario_permisos VALUES (30,1,12);
INSERT INTO usuarios_usuario_permisos VALUES (31,1,13);
INSERT INTO usuarios_usuario_permisos VALUES (32,1,14);
INSERT INTO usuarios_usuario_permisos VALUES (33,1,15);
INSERT INTO usuarios_usuario_permisos VALUES (34,1,16);
INSERT INTO usuarios_usuario_permisos VALUES (35,1,17);
INSERT INTO usuarios_usuario_permisos VALUES (36,1,18);
INSERT INTO usuarios_usuario_permisos VALUES (37,1,19);
INSERT INTO usuarios_usuario_permisos VALUES (38,1,20);
INSERT INTO usuarios_usuario_permisos VALUES (39,1,21);
INSERT INTO usuarios_usuario_permisos VALUES (40,1,22);
INSERT INTO usuarios_usuario_permisos VALUES (41,1,23);
INSERT INTO usuarios_usuario_permisos VALUES (42,1,24);
INSERT INTO usuarios_usuario_permisos VALUES (43,1,25);
INSERT INTO usuarios_usuario_permisos VALUES (44,1,26);
INSERT INTO usuarios_usuario_permisos VALUES (45,1,27);
INSERT INTO usuarios_usuario_permisos VALUES (46,1,28);
INSERT INTO usuarios_usuario_permisos VALUES (47,1,29);


