CREATE TABLE "task"
(
    id bigserial,
    title varchar(40),
    description varchar(255),
    completed boolean,
    CONSTRAINT pr_id PRIMARY KEY (id)
)