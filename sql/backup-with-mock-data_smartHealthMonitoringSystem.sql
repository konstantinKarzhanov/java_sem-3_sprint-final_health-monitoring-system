PGDMP                      {            backup    16rc1    16rc1 .    B           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            C           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            D           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            E           1262    20455    backup    DATABASE     h   CREATE DATABASE backup WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';
    DROP DATABASE backup;
                postgres    false            �            1259    20456    doctor    TABLE     �   CREATE TABLE public.doctor (
    doctor_id integer NOT NULL,
    medical_license_number character varying(50) NOT NULL,
    specialization character varying(50) NOT NULL,
    experience_years real NOT NULL
);
    DROP TABLE public.doctor;
       public         heap    postgres    false            �            1259    20459    doctor_patient    TABLE     h   CREATE TABLE public.doctor_patient (
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL
);
 "   DROP TABLE public.doctor_patient;
       public         heap    postgres    false            �            1259    20462    health_data    TABLE     �   CREATE TABLE public.health_data (
    id integer NOT NULL,
    user_id integer NOT NULL,
    weight numeric(5,2) NOT NULL,
    height numeric(5,2) NOT NULL,
    steps integer NOT NULL,
    heart_rate integer NOT NULL,
    date date NOT NULL
);
    DROP TABLE public.health_data;
       public         heap    postgres    false            �            1259    20465    health_data_id_seq    SEQUENCE     �   CREATE SEQUENCE public.health_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.health_data_id_seq;
       public          postgres    false    217            F           0    0    health_data_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.health_data_id_seq OWNED BY public.health_data.id;
          public          postgres    false    218            �            1259    20466    medicine_reminders    TABLE     (  CREATE TABLE public.medicine_reminders (
    id integer NOT NULL,
    user_id integer NOT NULL,
    medicine_name character varying(100) NOT NULL,
    dosage character varying(50) NOT NULL,
    schedule character varying(100) NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL
);
 &   DROP TABLE public.medicine_reminders;
       public         heap    postgres    false            �            1259    20469    medicine_reminders_id_seq    SEQUENCE     �   CREATE SEQUENCE public.medicine_reminders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.medicine_reminders_id_seq;
       public          postgres    false    219            G           0    0    medicine_reminders_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.medicine_reminders_id_seq OWNED BY public.medicine_reminders.id;
          public          postgres    false    220            �            1259    20470    recommendations    TABLE     �   CREATE TABLE public.recommendations (
    id integer NOT NULL,
    user_id integer NOT NULL,
    recommendation_text text NOT NULL,
    date date NOT NULL
);
 #   DROP TABLE public.recommendations;
       public         heap    postgres    false            �            1259    20475    recommendations_id_seq    SEQUENCE     �   CREATE SEQUENCE public.recommendations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.recommendations_id_seq;
       public          postgres    false    221            H           0    0    recommendations_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.recommendations_id_seq OWNED BY public.recommendations.id;
          public          postgres    false    222            �            1259    20476    users    TABLE     V  CREATE TABLE public.users (
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
       public         heap    postgres    false            �            1259    20479    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    223            I           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    224            �            1259    20480    view_user_doctor    VIEW     f  CREATE VIEW public.view_user_doctor AS
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
       public          postgres    false    223    223    223    223    223    223    223    215    215    215    215            �           2604    20484    health_data id    DEFAULT     p   ALTER TABLE ONLY public.health_data ALTER COLUMN id SET DEFAULT nextval('public.health_data_id_seq'::regclass);
 =   ALTER TABLE public.health_data ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217            �           2604    20485    medicine_reminders id    DEFAULT     ~   ALTER TABLE ONLY public.medicine_reminders ALTER COLUMN id SET DEFAULT nextval('public.medicine_reminders_id_seq'::regclass);
 D   ALTER TABLE public.medicine_reminders ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219            �           2604    20486    recommendations id    DEFAULT     x   ALTER TABLE ONLY public.recommendations ALTER COLUMN id SET DEFAULT nextval('public.recommendations_id_seq'::regclass);
 A   ALTER TABLE public.recommendations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221            �           2604    20487    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223            6          0    20456    doctor 
   TABLE DATA           e   COPY public.doctor (doctor_id, medical_license_number, specialization, experience_years) FROM stdin;
    public          postgres    false    215   f8       7          0    20459    doctor_patient 
   TABLE DATA           ?   COPY public.doctor_patient (doctor_id, patient_id) FROM stdin;
    public          postgres    false    216   �8       8          0    20462    health_data 
   TABLE DATA           [   COPY public.health_data (id, user_id, weight, height, steps, heart_rate, date) FROM stdin;
    public          postgres    false    217   9       :          0    20466    medicine_reminders 
   TABLE DATA           p   COPY public.medicine_reminders (id, user_id, medicine_name, dosage, schedule, start_date, end_date) FROM stdin;
    public          postgres    false    219   :       <          0    20470    recommendations 
   TABLE DATA           Q   COPY public.recommendations (id, user_id, recommendation_text, date) FROM stdin;
    public          postgres    false    221   C;       >          0    20476    users 
   TABLE DATA           j   COPY public.users (id, first_name, last_name, birth_date, gender, email, password, is_doctor) FROM stdin;
    public          postgres    false    223   �=       J           0    0    health_data_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.health_data_id_seq', 15, true);
          public          postgres    false    218            K           0    0    medicine_reminders_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.medicine_reminders_id_seq', 10, true);
          public          postgres    false    220            L           0    0    recommendations_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.recommendations_id_seq', 45, true);
          public          postgres    false    222            M           0    0    users_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.users_id_seq', 15, true);
          public          postgres    false    224            �           2606    20489 "   doctor_patient doctor_patient_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id);
 L   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_pkey;
       public            postgres    false    216    216            �           2606    20491    doctor doctor_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);
 <   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
       public            postgres    false    215            �           2606    20493    health_data health_data_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.health_data DROP CONSTRAINT health_data_pkey;
       public            postgres    false    217            �           2606    20495 *   medicine_reminders medicine_reminders_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.medicine_reminders DROP CONSTRAINT medicine_reminders_pkey;
       public            postgres    false    219            �           2606    20497 $   recommendations recommendations_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.recommendations DROP CONSTRAINT recommendations_pkey;
       public            postgres    false    221            �           2606    20499    users users_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public.users DROP CONSTRAINT users_email_key;
       public            postgres    false    223            �           2606    20501    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    223            �           2606    20502    doctor doctor_doctor_id_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);
 F   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_doctor_id_fkey;
       public          postgres    false    215    223    3487            �           2606    20507 ,   doctor_patient doctor_patient_doctor_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);
 V   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_doctor_id_fkey;
       public          postgres    false    216    3487    223            �           2606    20512 -   doctor_patient doctor_patient_patient_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doctor_patient
    ADD CONSTRAINT doctor_patient_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.users(id);
 W   ALTER TABLE ONLY public.doctor_patient DROP CONSTRAINT doctor_patient_patient_id_fkey;
       public          postgres    false    3487    216    223            �           2606    20517 $   health_data health_data_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.health_data
    ADD CONSTRAINT health_data_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 N   ALTER TABLE ONLY public.health_data DROP CONSTRAINT health_data_user_id_fkey;
       public          postgres    false    3487    217    223            �           2606    20522 2   medicine_reminders medicine_reminders_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.medicine_reminders
    ADD CONSTRAINT medicine_reminders_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 \   ALTER TABLE ONLY public.medicine_reminders DROP CONSTRAINT medicine_reminders_user_id_fkey;
       public          postgres    false    219    3487    223            �           2606    20527 ,   recommendations recommendations_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.recommendations
    ADD CONSTRAINT recommendations_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 V   ALTER TABLE ONLY public.recommendations DROP CONSTRAINT recommendations_user_id_fkey;
       public          postgres    false    221    223    3487            6   i   x�-�A@0E�3�S-횭�l�&�T7no���	��D%�.�>�a}�J��F��e�.E?ݠ�b��lL[��1'��E�	�%.�G
�<h���G�"��r ^      7   0   x��� D�s�`<���"�8�C�5�M�;t�ff�-�<�����      8   �   x�M�э !D��^�b/������e0�6��Ɯ���6����E�ېd\/�sِ�B���&Ǽ�� iFia�}�(1��Q[d�<[��X�A1#G�z]�eN1ь�F.�21�Rw�f6~ʉ?C\�>��Nv�PԠu���
���^�%��)�Ĝp~QJ'�OcL,��y�NaZ���"ku�)%^���9�XhS���5,�_���7e�;��-\:      :      x�}��n�0�������	�!�TQ#�ԋC6d%c#c"��5i�U{��|;��28��-�Բ���3V�n@D"#F٧�"��R;:�qT{�ɓB�š�����RpC{��P��.���u`�J�m�`�g��#�2ӯ�\��k6����Sآv��P��2�/��8��y^���Ro\�n��[�kKݴ�z����=TC�L�_��Y<�:�5��n�{V��*34��0ڡ���nZ��#��35O�/���d�Jg��OM�RS�ޟc�>��      <   �  x�嘽n�0Fg�)�֥1LJ�[;t�ڥ@Z�-")���}?ZJ� /@���!�.�w��3����S3z�l�NI�˨�*�	�бӖb��:�K�/�Mm�w��%ipAG}W�m��'w��Q��n�F�V���h��0���-�Rj�qx�@S�����b覀B�d��:N$mK��6��i�mTKF_U��Q�q��]y�/Yf�-;Q�p�"�F̩���	i�l����U��^��[��D��q���������X+��w(��5UH�Q�k��,�[�A��ލQ[�	���J�@��o]D-�~@���%�t��Y��0��6Iw�B�0��sm �����]D�����LM�l.���%�h���e��of�DY3Q�6���p#k4>u�˲��H$/F} �n�`[���� 09�I�s�"�5��|�^S�> ����PƼ\�3�h��ߤ#�
4�4��q:-m���z��[�UlA\ w2Ul_�L�;�]����s�P�9�9v����s��X�9b7�������9B�S��To�W��G��]�L{v.Q��;�m���sb�'�@u�K�ݩv	.y*�x�ϕxϽ��XU%��T3^�t�_rg��
�92^�!tuZrg)�9��/O�'n�'�5_r�(O-�
���ē�^rg)�>��/���f������      >   H  x�u�I��H��Y�-�<��RAdT���T��E�Q��/։U������^D�4X���n�"@�EP"����D��E��U��䱩�'��g����2�?s4��I�F�|�>omk�C�bW	��];��0`Q�75*����*�%���P��M��˜)��a'�>�Y�z�û��P���[A���JRW�ץ˛�Nt� �Y�65`ܕp�)�%��T�����}Wf�����Pv:U"����O8,J��_���l�-D]��Ԩ��GS�C�8�a�W7�$�!(	�.X���w���oH�S�{�����ĹIj�"[���խ�j?܈J�Qm�J�M<Xư�h��s�FX�8)�aqW�N������;�Mϲ�E*��nG-�IA:��y�i�F�3O���
$����i��G�G�������4�����E�򗾛�L$��պc����H�ԟw���y_����o|L�3�ى�tIp��q+ ��q���;I4A��ao����w�c�9�4\�m+Ϻ/�*��|�!/Y��%W�I��]����m��?�V	0���YǓR�>�ap�K���}\��U�R���P�Rݩ�ޥٱ��<*�������'}w�N�P��'��9j�f�M�%���8G���l���ޣ�,���ꭋ��Ջܪڝ}����J5����ӧ~���1NI
��!�2������X��S�ư�֦�>Eԑ�:�Y/yy�E�/��s��}j�0�h��u��e��$�����	��7���"uU�D*�fK����څi/9�߸�#
Q{I�Յ����������;-������ޅj�gaw��v�,�����P�aM9j��TU�L=gg�1sf�j(�$'<?�M�O��L���a�C,^��ZQq��|�%�͜�\�i���|�̓��e�G�ClKEN�������y��q�
)�
)�[�b���[�(��\�F����Mu�S���3��2�'���^��J�St�&+���33X���
_�i��$�|�5��=��3�n8w
C/,#-�S�]��߶� ��y���_�����'����0�     