INSERT INTO custom_field (code,min,max) VALUES
                                             ('applicant',5, 255),
                                             ('owner',5,255)
ON CONFLICT (code) DO UPDATE
    SET
        min = EXCLUDED.min,
        max = EXCLUDED.max;