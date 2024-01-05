PGDMP  +    8                {            test    16rc1    16rc1 .    B           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            C           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            D           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            E           1262    20181    test    DATABASE     f   CREATE DATABASE test WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';
    DROP DATABASE test;
                postgres    false            �            1259    20244    doctor    TABLE     �   CREATE TABLE public.doctor (
    doctor_id integer NOT NULL,
    medical_license_number character varying(50) NOT NULL,
    specialization character varying(50) NOT NULL,
    experience_years real NOT NULL
);
    DROP TABLE public.doctor;
       public         heap    postgres    false            �            1259    20182    doctor_patient    TABLE     h   CREATE TABLE public.doctor_patient (
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL
);
 "   DROP TABLE public.doctor_patient;
       public         heap    postgres    false            �            1259    20185    health_data    TABLE     �   CREATE TABLE public.health_data (
    id integer NOT NULL,
    user_id integer NOT NULL,
    weight numeric(5,2) NOT NULL,
    height numeric(5,2) NOT NULL,
    steps integer NOT NULL,
    heart_rate integer NOT NULL,
    date date NOT NULL
);
    DROP TABLE public.health_data;
       public         heap    postgres    false            �            1259    20188    health_data_id_seq    SEQUENCE     �   CREATE SEQUENCE public.health_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.health_data_id_seq;
       public          postgres    false    216            F           0    0    health_data_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.health_data_id_seq OWNED BY public.health_data.id;
          public          postgres    false    217            �            1259    20189    medicine_reminders    TABLE     (  CREATE TABLE public.medicine_reminders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    medicine_name character varying(100) NOT NULL,
    dosage character varying(50) NOT NULL,
    schedule character varying(100) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);
 &   DROP TABLE public.medicine_reminders;
       public         heap    postgres    false            �            1259    20192    medicine_reminders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.medicine_reminders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.medicine_reminders_id_seq;
       public          postgres    false    218            G           0    0    medicine_reminders_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.medicine_reminders_id_seq OWNED BY public.medicine_reminders.id;
          public          postgres    false    219            �            1259    20193    recommendations    TABLE     �   CREATE TABLE public.recommendations (
    id integer NOT NULL,
    user_id integer NOT NULL,
    recommendation_text text NOT NULL,
    date date NOT NULL
);
 #   DROP TABLE public.recommendations;
       public         heap    postgres    false            �            1259    20198    recommendations_id_seq    SEQUENCE     �   CREATE SEQUENCE public.recommendations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.recommendations_id_seq;
       public          postgres    false    220            H           0    0    recommendations_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.recommendations_id_seq OWNED BY public.recommendations.id;
          public          postgres    false    221            �            1259    20199    users    TABLE     V  CREATE TABLE public.users (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    birth_date date NOT NULL,
    gender character varying(30) NOT NULL,
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    is_doctor boolean NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    20202    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    222            I           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    223            �            1259    20270    view_user_doctor    VIEW     f  CREATE VIEW public.view_user_doctor AS
 SELECT doctor.doctor_id,
    doctor.medical_license_number,
    users.first_name,
    users.last_name,
    doctor.specialization,
    doctor.experience_years,
    users.birth_date,
    users.gender,
    users.email,
    users.password
   FROM (public.users
     JOIN public.doctor ON ((users.id = doctor.doctor_id)));
 #   DROP VIEW public.view_user_doctor;
       public          postgres    false    222    222    222    224    222    222    224    224    224    222    222            �           2604    20203    health_data id    DEFAULT     p   ALTER TABLE ONLY public.health_data ALTER COLUMN id SET DEFAULT nextval('public.health_data_id_seq'::regclass);
 =   ALTER TABLE public.health_data ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216            �           2604    20204    medicine_reminders id    DEFAULT     ~   ALTER TABLE ONLY public.medicine_reminders ALTER COLUMN id SET DEFAULT nextval('public.medicine_reminders_id_seq'::regclass);
 D   ALTER TABLE public.medicine_reminders ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218            �           2604    20205    recommendations id    DEFAULT     x   ALTER TABLE ONLY public.recommendations ALTER COLUMN id SET DEFAULT nextval('public.recommendations_id_seq'::regclass);
 A   ALTER TABLE public.recommendations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    220            �           2604    20206    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    223    222            ?          0    20244    doctor 
   TABLE DATA           e   COPY public.doctor (doctor_id, medical_license_number, specialization, experience_years) FROM stdin;
    public          postgres    false    224   ^8       6          0    20182    doctor_patient 
   TABLE DATA           ?   COPY public.doctor_patient (doctor_id, patient_id) FROM stdin;
    public          postgres    false    215   {8       7          0    20185    health_data 
   TABLE DATA           [   COPY public.health_data (id, user_id, weight, height, steps, heart_rate, date) FROM stdin;
    public          postgres    false    216   �8       9          0    20189    medicine_reminders 
   TABLE DATA           p   COPY public.medicine_reminders (id, user_id, medicine_name, dosage, schedule, start_date, end_date) FROM stdin;
    public          postgres    false    218   �8       ;          0    20193    recommendations 
   TABLE DATA           Q   COPY public.recommendations (id, user_id, recommendation_text, date) FROM stdin;
    public          postgres    false    220   �8       =          0    20199    users 
   TABLE DATA           j   COPY public.users (id, first_name, last_name, birth_date, gender, email, password, is_doctor) FROM stdin;
    public          postgres    false    222   �8       J           0    0    health_data_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.health_data_id_seq', 1, false);
          public          postgres    false    217            K           0    0    medicine_reminders_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.medicine_reminders_id_seq', 1, false);
          public          postgres    false    219            L           0    0    recommendations_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.recommendations_id_seq', 1, false);
          public          postgres    false    221            M           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 1, false);
          public          postgres    false    223            �           2606    20208 "   doctor_patient doctor_patient_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id);
 L   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_pkey;
       public            postgres    false    215    215            �           2606    20248    doctor doctor_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);
 <   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
       public            postgres    false    224            �           2606    20210    health_data health_data_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.health_data DROP CONSTRAINT health_data_pkey;
       public            postgres    false    216            �           2606    20212 *   medicine_reminders medicine_reminders_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.medicine_reminders DROP CONSTRAINT medicine_reminders_pkey;
       public            postgres    false    218            �           2606    20214 $   recommendations recommendations_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.recommendations DROP CONSTRAINT recommendations_pkey;
       public            postgres    false    220            �           2606    20216    users users_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_key;
       public            postgres    false    222            �           2606    20218    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    222            �           2606    20249    doctor doctor_doctor_id_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);
 F   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_doctor_id_fkey;
       public          postgres    false    224    222    3485            �           2606    20219 ,   doctor_patient doctor_patient_doctor_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);
 V   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_doctor_id_fkey;
       public          postgres    false    3485    222    215            �           2606    20224 -   doctor_patient doctor_patient_patient_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.users(id);
 W   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_patient_id_fkey;
       public          postgres    false    215    222    3485            �           2606    20229 $   health_data health_data_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.health_data DROP CONSTRAINT health_data_user_id_fkey;
       public          postgres    false    3485    216    222            �           2606    20234 2   medicine_reminders medicine_reminders_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 \   ALTER TABLE ONLY public.medicine_reminders DROP CONSTRAINT medicine_reminders_user_id_fkey;
       public          postgres    false    3485    222    218            �           2606    20239 ,   recommendations recommendations_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 V   ALTER TABLE ONLY public.recommendations DROP CONSTRAINT recommendations_user_id_fkey;
       public          postgres    false    220    222    3485            ?      x������ � �      6      x������ � �      7      x������ � �      9      x������ � �      ;      x������ � �      =      x������ � �     