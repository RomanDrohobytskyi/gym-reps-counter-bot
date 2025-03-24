INSERT INTO muscle_groups (id, name)
VALUES (1, 'Chest'),
       (2, 'Back'),
       (3, 'Shoulders'),
       (4, 'Biceps'),
       (5, 'Triceps'),
       (6, 'Forearms'),
       (7, 'Abs'),
--     (8, 'Obliques'),
       (9, 'Lower Back'),
       (10, 'Quadriceps'),
       (11, 'Hamstrings'),
       (12, 'Glutes'),
       (13, 'Calves'),
--     (14, 'Neck'),
       (15, 'Full Body') -- Special category for full-body workouts
    ON CONFLICT (id) DO NOTHING;


INSERT INTO exercises (id, name, description, muscle_group_id)
VALUES
    -- Chest
    (1, 'Bench Press', 'A compound movement that targets the chest using a barbell.', 1),
    (2, 'Push-Ups', 'A bodyweight exercise targeting the chest and triceps.', 1),
    (3, 'Dumbbell Fly', 'An isolation exercise that stretches and contracts the chest.', 1),

    -- Back
    (4, 'Pull-Ups', 'A bodyweight exercise focusing on the lats and upper back.', 2),
    (5, 'Deadlift', 'A full-body movement that heavily engages the back.', 2),
    (6, 'Barbell Row', 'A compound lift that builds thickness in the back.', 2),

    -- Shoulders
    (7, 'Overhead Press', 'A barbell or dumbbell press targeting the shoulders.', 3),
    (8, 'Lateral Raises', 'An isolation movement for the deltoids.', 3),
    (9, 'Face Pulls', 'A cable exercise that improves shoulder health.', 3),

    -- Biceps
    (10, 'Barbell Curl', 'A classic bicep exercise using a barbell.', 4),
    (11, 'Hammer Curl', 'A dumbbell curl variation focusing on the brachialis.', 4),
    (12, 'Concentration Curl', 'An isolation curl for the biceps peak.', 4),

    -- Triceps
    (13, 'Triceps Dips', 'A bodyweight exercise for the triceps.', 5),
    (14, 'Skull Crushers', 'An isolation movement for the triceps.', 5),
    (15, 'Close-Grip Bench Press', 'A pressing movement that emphasizes the triceps.', 5),

    -- Forearms
    (16, 'Wrist Curls', 'A wrist flexion movement to build forearm strength.', 6),
    (17, 'Reverse Curls', 'A curl variation focusing on the brachioradialis.', 6),
    (18, 'Farmer’s Walk', 'A grip-strengthening carry exercise.', 6),

    -- Abs
    (19, 'Crunches', 'A classic core exercise targeting the upper abs.', 7),
    (20, 'Plank', 'A core endurance exercise engaging multiple muscles.', 7),
    (21, 'Leg Raises', 'An abdominal exercise focusing on the lower abs.', 7),

    -- Obliques
--     (22, 'Russian Twists', 'A rotational core exercise for the obliques.', 8),
--     (23, 'Side Plank', 'A static hold targeting the obliques.', 8),
--     (24, 'Hanging Oblique Raises', 'A hanging movement to work the obliques.', 8),

    -- Lower Back
    (25, 'Hyperextensions', 'A lower back strengthening exercise.', 9),
    (26, 'Good Mornings', 'A barbell exercise targeting the posterior chain.', 9),
    (27, 'Superman Hold', 'A bodyweight exercise for lower back endurance.', 9),

    -- Quadriceps
    (28, 'Squats', 'A fundamental leg movement focusing on the quads.', 10),
    (29, 'Leg Press', 'A machine-based leg exercise.', 10),
    (30, 'Lunges', 'A unilateral exercise that builds leg strength.', 10),

    -- Hamstrings
    (31, 'Romanian Deadlift', 'A hip hinge exercise for the hamstrings.', 11),
    (32, 'Hamstring Curls', 'A machine exercise isolating the hamstrings.', 11),
    (33, 'Nordic Curls', 'A bodyweight hamstring strengthener.', 11),

    -- Glutes
    (34, 'Hip Thrusts', 'A barbell exercise for glute activation.', 12),
    (35, 'Glute Bridges', 'A bodyweight variation of hip thrusts.', 12),
    (36, 'Bulgarian Split Squats', 'A single-leg exercise targeting the glutes.', 12),

    -- Calves
    (37, 'Standing Calf Raises', 'A vertical movement for calf development.', 13),
    (38, 'Seated Calf Raises', 'A seated version focusing on the soleus.', 13),
    (39, 'Jump Rope', 'A dynamic exercise that builds calf endurance.', 13),

    -- Neck
--     (40, 'Neck Flexion', 'A resistance-based movement to strengthen the neck.', 14),
--     (41, 'Neck Extension', 'A movement targeting the back of the neck.', 14),
--     (42, 'Neck Side Raises', 'A lateral movement strengthening the neck.', 14),

    -- Full Body
    (43, 'Burpees', 'A full-body conditioning exercise.', 15),
    (44, 'Kettlebell Swings', 'A powerful posterior chain exercise.', 15),
    (45, 'Snatch', 'An Olympic lift that works the entire body.', 15) ON CONFLICT (id) DO NOTHING;
