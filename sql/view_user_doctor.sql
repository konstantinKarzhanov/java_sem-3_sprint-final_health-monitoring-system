-- View: public.view_user_doctor

-- DROP VIEW public.view_user_doctor;

CREATE OR REPLACE VIEW public.view_user_doctor
 AS
 SELECT 
    doctor.doctor_id,
    doctor.medical_license_number,
    users.first_name,
    users.last_name,
    doctor.specialization,
    doctor.experience_years,
    users.birth_date,
    users.gender,
    users.email,
    users.password
   FROM users
     JOIN doctor ON users.id = doctor.doctor_id;

ALTER TABLE public.view_user_doctor
    OWNER TO postgres;

