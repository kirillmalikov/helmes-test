CREATE TABLE "sector"
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    name      VARCHAR               NOT NULL,
    parent_id BIGINT REFERENCES "sector" (id)
);

CREATE UNIQUE INDEX ON "sector" ("name", "parent_id");

CREATE TABLE "user"
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR NOT NULL,
    agreed_to_terms TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE user_sector
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    user_id   BIGINT REFERENCES "user" (id) NOT NULL ,
    sector_id BIGINT REFERENCES "sector" (id) NOT NULL
);

CREATE INDEX ON "user_sector" (user_id);
CREATE INDEX ON "user_sector" (sector_id);

-- Insert the top-level sectors (with no parent)
INSERT INTO "sector" (name)
VALUES ('Manufacturing'),
       ('Other'),
       ('Service');

-- Insert the subsectors under the corresponding top-level sectors
WITH p_id AS (SELECT id FROM sector WHERE name = 'Manufacturing')
    INSERT INTO sector (name, parent_id)
    VALUES ('Construction materials', (SELECT * FROM p_id)),
           ('Electronics and Optics', (SELECT * FROM p_id)),
           ('Food and Beverage', (SELECT * FROM p_id)),
           ('Furniture', (SELECT * FROM p_id)),
           ('Machinery', (SELECT * FROM p_id)),
           ('Metalworking', (SELECT * FROM p_id)),
           ('Plastic and Rubber', (SELECT * FROM p_id)),
           ('Printing', (SELECT * FROM p_id)),
           ('Textile and Clothing', (SELECT * FROM p_id)),
           ('Wood', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Food and Beverage')
    INSERT INTO sector (name, parent_id)
    VALUES ('Bakery & confectionery products', (SELECT * FROM p_id)),
           ('Beverages', (SELECT * FROM p_id)),
           ('Fish & fish products', (SELECT * FROM p_id)),
           ('Meat & meat products', (SELECT * FROM p_id)),
           ('Milk & dairy products', (SELECT * FROM p_id)),
           ('Other', (SELECT * FROM p_id)),
           ('Sweets & snack food', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Furniture')
    INSERT INTO sector (name, parent_id)
    VALUES ('Bathroom/sauna', (SELECT * FROM p_id)),
           ('Bedroom', (SELECT * FROM p_id)),
           ('Children’s room', (SELECT * FROM p_id)),
           ('Kitchen', (SELECT * FROM p_id)),
           ('Living room', (SELECT * FROM p_id)),
           ('Office', (SELECT * FROM p_id)),
           ('Other (Furniture)', (SELECT * FROM p_id)),
           ('Outdoor', (SELECT * FROM p_id)),
           ('Project furniture', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Machinery')
    INSERT INTO sector (name, parent_id)
    VALUES ('Machinery components', (SELECT * FROM p_id)),
           ('Machinery equipment/tools', (SELECT * FROM p_id)),
           ('Manufacture of machinery', (SELECT * FROM p_id)),
           ('Maritime', (SELECT * FROM p_id)),
           ('Metal structures', (SELECT * FROM p_id)),
           ('Other', (SELECT * FROM p_id)),
           ('Repair and maintenance service', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Maritime')
    INSERT INTO sector (name, parent_id)
    VALUES ('Aluminium and steel workboats', (SELECT * FROM p_id)),
           ('Boat/Yacht building', (SELECT * FROM p_id)),
           ('Ship repair and conversion', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Metalworking')
    INSERT INTO sector (name, parent_id)
    VALUES ('Construction of metal structure', (SELECT * FROM p_id)),
           ('Houses and buildings', (SELECT * FROM p_id)),
           ('Metal products', (SELECT * FROM p_id)),
           ('Metal works', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Metal works')
    INSERT INTO sector (name, parent_id)
    VALUES ('CNC-machining', (SELECT * FROM p_id)),
           ('Forgings, Fasteners', (SELECT * FROM p_id)),
           ('Gas, Plasma, Laser cutting', (SELECT * FROM p_id)),
           ('MIG, TIG, Aluminum welding', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Plastic and Rubber')
    INSERT INTO sector (name, parent_id)
    VALUES ('Packaging', (SELECT * FROM p_id)),
           ('Plastic goods', (SELECT * FROM p_id)),
           ('Plastic processing technology', (SELECT * FROM p_id)),
           ('Plastic profiles', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Plastic processing technology')
    INSERT INTO sector (name, parent_id)
    VALUES ('Blowing', (SELECT * FROM p_id)),
           ('Moulding', (SELECT * FROM p_id)),
           ('Plastics welding and processing', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Printing')
    INSERT INTO sector (name, parent_id)
    VALUES ('Advertising', (SELECT * FROM p_id)),
           ('Book/Periodicals printing', (SELECT * FROM p_id)),
           ('Labelling and packaging printing', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Textile and Clothing')
    INSERT INTO sector (name, parent_id)
    VALUES ('Clothing', (SELECT * FROM p_id)),
           ('Textile', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Wood')
    INSERT INTO sector (name, parent_id)
    VALUES ('Other (Wood)', (SELECT * FROM p_id)),
           ('Wooden building materials', (SELECT * FROM p_id)),
           ('Wooden houses', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Other' and parent_id IS NULL)
    INSERT INTO sector (name, parent_id)
    VALUES ('Creative industries', (SELECT * FROM p_id)),
           ('Energy technology', (SELECT * FROM p_id)),
           ('Environment', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Service')
    INSERT INTO sector (name, parent_id)
    VALUES ('Business services', (SELECT * FROM p_id)),
           ('Engineering', (SELECT * FROM p_id)),
           ('Information Technology and Telecommunications', (SELECT * FROM p_id)),
           ('Tourism', (SELECT * FROM p_id)),
           ('Translation services', (SELECT * FROM p_id)),
           ('Transport and Logistics', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Information Technology and Telecommunications')
    INSERT INTO sector (name, parent_id)
    VALUES ('Data processing, Web portals, E-marketing', (SELECT * FROM p_id)),
           ('Programming, Consultancy', (SELECT * FROM p_id)),
           ('Software, Hardware', (SELECT * FROM p_id)),
           ('Telecommunications', (SELECT * FROM p_id));

WITH p_id AS (SELECT id FROM sector WHERE name = 'Transport and Logistics')
    INSERT INTO sector (name, parent_id)
    VALUES ('Air', (SELECT * FROM p_id)),
           ('Rail', (SELECT * FROM p_id)),
           ('Road', (SELECT * FROM p_id)),
           ('Water', (SELECT * FROM p_id));
