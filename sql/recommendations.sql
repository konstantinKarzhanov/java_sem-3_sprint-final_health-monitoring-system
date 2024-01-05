-- Table: public.recommendations

-- DROP TABLE IF EXISTS public.recommendations;

CREATE TABLE IF NOT EXISTS public.recommendations
(
    id integer NOT NULL DEFAULT nextval('recommendations_id_seq'::regclass),
    user_id integer NOT NULL,
    recommendation_text text COLLATE pg_catalog."default" NOT NULL,
    date date NOT NULL,
    CONSTRAINT recommendations_pkey PRIMARY KEY (id),
    CONSTRAINT recommendations_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.recommendations
    OWNER to postgres;