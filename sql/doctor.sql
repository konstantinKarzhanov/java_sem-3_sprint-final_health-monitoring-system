-- Table: public.doctor

-- DROP TABLE IF EXISTS public.doctor;

CREATE TABLE IF NOT EXISTS public.doctor
(
    doctor_id integer NOT NULL,
    medical_license_number character varying(50) COLLATE pg_catalog."default" NOT NULL,
    specialization character varying(50) COLLATE pg_catalog."default" NOT NULL,
    experience_years real NOT NULL,
    CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id),
    CONSTRAINT doctor_doctor_id_fkey FOREIGN KEY (doctor_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.doctor
    OWNER to postgres;