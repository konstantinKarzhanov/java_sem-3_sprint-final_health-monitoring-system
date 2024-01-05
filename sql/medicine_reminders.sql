-- Table: public.medicine_reminders

-- DROP TABLE IF EXISTS public.medicine_reminders;

CREATE TABLE IF NOT EXISTS public.medicine_reminders
(
    id integer NOT NULL DEFAULT nextval('medicine_reminders_id_seq'::regclass),
    user_id integer NOT NULL,
    medicine_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    dosage character varying(50) COLLATE pg_catalog."default" NOT NULL,
    schedule character varying(100) COLLATE pg_catalog."default" NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    CONSTRAINT medicine_reminders_pkey PRIMARY KEY (id),
    CONSTRAINT medicine_reminders_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.medicine_reminders
    OWNER to postgres;