-- Insert initial data into `user` table
INSERT INTO user (`name`, `email`, `access_token`) VALUES
                                                       ('Captain Jack', 'captain@example.com', '123e4567-e89b-12d3-a456-426614174000'),
                                                       ('TBob Brown', 'slim.shady@example.com', '987e6543-e21b-54d3-b765-126614174321');

-- Insert initial data into `monitored_endpoint` table
INSERT INTO monitored_endpoint (`name`, `url`, `date_of_creation`, `date_of_last_check`, `monitored_interval`, `user_id`) VALUES
                                                                                                                              ('Website of Random Cat Memes', 'https://www.catmemes.com', '2024-11-01T10:00:00.000000', '2024-11-02T10:30:00.000000', 60*5, 1),
                                                                                                                              ('Conspiracy Theories Central', 'https://www.conspiracies.com', '2024-11-01T11:00:00.000000', '2024-11-02T11:45:00.000000', 60*5, 2),
                                                                                                                              ('Daily Dad Jokes', 'https://www.dadjokes.com', '2024-11-01T12:00:00.000000', null, 60*5, 1);
