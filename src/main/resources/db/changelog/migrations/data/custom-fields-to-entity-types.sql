
INSERT INTO custom_fields_entity_type (custom_field_id, entity_type_id, required)
SELECT
    (SELECT id FROM custom_field WHERE code = 'applicant') AS custom_field_id,
    et.id AS entity_type_id,
    true AS required
FROM entity_type et on conflict do nothing;

INSERT INTO custom_fields_entity_type (custom_field_id, entity_type_id, required)
SELECT
    (SELECT id FROM custom_field WHERE code = 'owner') AS custom_field_id,
    et.id AS entity_type_id,
    true AS required
FROM entity_type et on conflict do nothing;