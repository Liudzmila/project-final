--changeset sobalevaluda4:change_ACTIVITY
INSERT INTO TASK (TITLE, TYPE_CODE, STATUS_CODE, PROJECT_ID, SPRINT_ID, STARTPOINT)
values ('task-8', 'task', 'todo', 2, 5, '2023-06-14 09:28:10'),
       ('task-9', 'task', 'done', 2, 5, '2023-06-14 09:28:10'),
       ('task-10', 'task', 'canceled', 2, 5, '2023-06-14 09:28:10'),
       ('task-11', 'task', 'canceled', 2, 5, '2023-06-14 09:28:10');

insert into ACTIVITY(AUTHOR_ID, TASK_ID, UPDATED, COMMENT, TITLE, DESCRIPTION, ESTIMATE, TYPE_CODE, STATUS_CODE,
                     PRIORITY_CODE)
VALUES (1, 11, '2024-03-13T09:00:00', null, 'Data', null, 3, null, 'in_progress', 'normal');

insert into ACTIVITY(AUTHOR_ID, TASK_ID, UPDATED, COMMENT, TITLE, DESCRIPTION, ESTIMATE, TYPE_CODE, STATUS_CODE,
                     PRIORITY_CODE)
VALUES (1, 11, '2024-03-15T12:00:00', null, 'Data', null, 3, null, 'ready_for_review', 'normal');

insert into ACTIVITY(AUTHOR_ID, TASK_ID, UPDATED, COMMENT, TITLE, DESCRIPTION, ESTIMATE, TYPE_CODE, STATUS_CODE,
                     PRIORITY_CODE)
VALUES (1, 11, '2024-03-18T16:30:10', null, 'Data', null, 3, null, 'done', 'normal');