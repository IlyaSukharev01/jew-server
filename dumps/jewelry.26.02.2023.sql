PGDMP     1    -                {            jewelry    15.1    15.1 $    "           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            #           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            $           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            %           1262    24576    jewelry    DATABASE     {   CREATE DATABASE jewelry WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE jewelry;
                postgres    false            ?            1259    58114    jewelry_items    TABLE     ?  CREATE TABLE public.jewelry_items (
    id bigint NOT NULL,
    jewelry_name character varying(255) NOT NULL,
    jewelry_type integer NOT NULL,
    base_material character varying(255) NOT NULL,
    added_date date DEFAULT '2023-02-24'::date NOT NULL,
    price integer NOT NULL,
    weight double precision NOT NULL,
    views bigint NOT NULL,
    gem_material character varying(255),
    gems_count integer,
    probe integer
);
 !   DROP TABLE public.jewelry_items;
       public         heap    postgres    false            ?            1259    58113    jewelry_items_id_seq    SEQUENCE     }   CREATE SEQUENCE public.jewelry_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.jewelry_items_id_seq;
       public          postgres    false    220            &           0    0    jewelry_items_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.jewelry_items_id_seq OWNED BY public.jewelry_items.id;
          public          postgres    false    219            ?            1259    57996    jewelry_media    TABLE     ?   CREATE TABLE public.jewelry_media (
    id bigint NOT NULL,
    jew_id bigint NOT NULL,
    crop_img_url character varying(255),
    full_img_url character varying(255),
    video_url character varying(255)
);
 !   DROP TABLE public.jewelry_media;
       public         heap    postgres    false            ?            1259    57995    jewelry_media_id_seq    SEQUENCE     }   CREATE SEQUENCE public.jewelry_media_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.jewelry_media_id_seq;
       public          postgres    false    216            '           0    0    jewelry_media_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.jewelry_media_id_seq OWNED BY public.jewelry_media.id;
          public          postgres    false    215            ?            1259    58129    jewelry_saved    TABLE     v   CREATE TABLE public.jewelry_saved (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    jewelry_id bigint NOT NULL
);
 !   DROP TABLE public.jewelry_saved;
       public         heap    postgres    false            ?            1259    58128    jewelry_saved_id_seq    SEQUENCE     }   CREATE SEQUENCE public.jewelry_saved_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.jewelry_saved_id_seq;
       public          postgres    false    222            (           0    0    jewelry_saved_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.jewelry_saved_id_seq OWNED BY public.jewelry_saved.id;
          public          postgres    false    221            ?            1259    58107    jewelry_types    TABLE     q   CREATE TABLE public.jewelry_types (
    id integer NOT NULL,
    jewelry_type character varying(255) NOT NULL
);
 !   DROP TABLE public.jewelry_types;
       public         heap    postgres    false            ?            1259    58106    jewelry_types_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.jewelry_types_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.jewelry_types_id_seq;
       public          postgres    false    218            )           0    0    jewelry_types_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.jewelry_types_id_seq OWNED BY public.jewelry_types.id;
          public          postgres    false    217            ?            1259    57987 	   uuid_auth    TABLE     ?   CREATE TABLE public.uuid_auth (
    id uuid NOT NULL,
    device_type character varying(255) NOT NULL,
    ip_address character varying(255) NOT NULL,
    added_date date DEFAULT '2023-02-24'::date NOT NULL
);
    DROP TABLE public.uuid_auth;
       public         heap    postgres    false            {           2604    58117    jewelry_items id    DEFAULT     t   ALTER TABLE ONLY public.jewelry_items ALTER COLUMN id SET DEFAULT nextval('public.jewelry_items_id_seq'::regclass);
 ?   ALTER TABLE public.jewelry_items ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            y           2604    57999    jewelry_media id    DEFAULT     t   ALTER TABLE ONLY public.jewelry_media ALTER COLUMN id SET DEFAULT nextval('public.jewelry_media_id_seq'::regclass);
 ?   ALTER TABLE public.jewelry_media ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            }           2604    58132    jewelry_saved id    DEFAULT     t   ALTER TABLE ONLY public.jewelry_saved ALTER COLUMN id SET DEFAULT nextval('public.jewelry_saved_id_seq'::regclass);
 ?   ALTER TABLE public.jewelry_saved ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            z           2604    58110    jewelry_types id    DEFAULT     t   ALTER TABLE ONLY public.jewelry_types ALTER COLUMN id SET DEFAULT nextval('public.jewelry_types_id_seq'::regclass);
 ?   ALTER TABLE public.jewelry_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218                      0    58114    jewelry_items 
   TABLE DATA           ?   COPY public.jewelry_items (id, jewelry_name, jewelry_type, base_material, added_date, price, weight, views, gem_material, gems_count, probe) FROM stdin;
    public          postgres    false    220   )                 0    57996    jewelry_media 
   TABLE DATA           Z   COPY public.jewelry_media (id, jew_id, crop_img_url, full_img_url, video_url) FROM stdin;
    public          postgres    false    216   c*                 0    58129    jewelry_saved 
   TABLE DATA           =   COPY public.jewelry_saved (id, uuid, jewelry_id) FROM stdin;
    public          postgres    false    222   ?*                 0    58107    jewelry_types 
   TABLE DATA           9   COPY public.jewelry_types (id, jewelry_type) FROM stdin;
    public          postgres    false    218   ?*                 0    57987 	   uuid_auth 
   TABLE DATA           L   COPY public.uuid_auth (id, device_type, ip_address, added_date) FROM stdin;
    public          postgres    false    214   ?*       *           0    0    jewelry_items_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.jewelry_items_id_seq', 1, false);
          public          postgres    false    219            +           0    0    jewelry_media_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.jewelry_media_id_seq', 1, false);
          public          postgres    false    215            ,           0    0    jewelry_saved_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.jewelry_saved_id_seq', 1, true);
          public          postgres    false    221            -           0    0    jewelry_types_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.jewelry_types_id_seq', 1, false);
          public          postgres    false    217            ?           2606    58122     jewelry_items jewelry_items_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.jewelry_items
    ADD CONSTRAINT jewelry_items_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.jewelry_items DROP CONSTRAINT jewelry_items_pkey;
       public            postgres    false    220            ?           2606    58003     jewelry_media jewelry_media_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.jewelry_media
    ADD CONSTRAINT jewelry_media_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.jewelry_media DROP CONSTRAINT jewelry_media_pkey;
       public            postgres    false    216            ?           2606    58134     jewelry_saved jewelry_saved_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.jewelry_saved
    ADD CONSTRAINT jewelry_saved_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.jewelry_saved DROP CONSTRAINT jewelry_saved_pkey;
       public            postgres    false    222            ?           2606    58112     jewelry_types jewelry_types_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.jewelry_types
    ADD CONSTRAINT jewelry_types_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.jewelry_types DROP CONSTRAINT jewelry_types_pkey;
       public            postgres    false    218                       2606    57994    uuid_auth uuid_auth_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.uuid_auth
    ADD CONSTRAINT uuid_auth_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.uuid_auth DROP CONSTRAINT uuid_auth_pkey;
       public            postgres    false    214            ?           2606    58123 /   jewelry_items fk_jewelry_items_jewelry_type__id    FK CONSTRAINT     ?   ALTER TABLE ONLY public.jewelry_items
    ADD CONSTRAINT fk_jewelry_items_jewelry_type__id FOREIGN KEY (jewelry_type) REFERENCES public.jewelry_types(id) ON UPDATE RESTRICT;
 Y   ALTER TABLE ONLY public.jewelry_items DROP CONSTRAINT fk_jewelry_items_jewelry_type__id;
       public          postgres    false    218    3203    220               L  x???MN?0?דS??????p??B???-? )(JD?p??1vRQ?D???l??o?<G?Ѹ???]?m?=v??u?V)?n?;?:?8?s?ё$<?U?V8??9U?????RQYU?d&	??y5RdK??w;??]???YUr??~?????n???G;?c??? ?U@(r/??A?Ç??.T޸S??9???7???}\0m????>>3~?Q7?E.|`l>??????;??X)?.??e0????r?|?~s??lGkt?????B?%?ˌA?~??I)?t?ox2?"t?S/?O??1[P??K2????dI?|?y??            x?????? ? ?         5   x???  ????	.???`?%8#ˏZ??籖?!Y#?L?H/??D??
\         '   x?3?LM,*??K/?2??\ƜIE?ɩ9?%\1z\\\ ??	?         \   x?u???0?7?e){???\??%? 5?ϑǽ?????F?]?????u????6v
:AmƱ$??V???O	??H?x????????$K     