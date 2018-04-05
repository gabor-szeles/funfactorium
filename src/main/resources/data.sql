INSERT INTO users VALUES (1, 'admin@admin.ad','admin', 'admin');

INSERT INTO topic VALUES (1, 'Biology');

INSERT INTO fun_fact VALUES (1,' Arctic predators such as polar bears have a greater capacity to store ' ||
                                               'vitamin A in their liver than most other animals. It is thought to be ' ||
                                               'because of the effect of naturally occurring vitamin A in marine algae ' ||
                                               'being passed up the food chain to the polar bear. So great is the polar ' ||
                                               'bears ability to store this vitamin that if you were to consume the ' ||
                                               'liver, you would more than likely succumb to the effects of ' ||
                                               'Hypervitaminosis A.  Put simply it would be too much vitamin A for your ' ||
                                               'body to handle & and you would suffer from vitamin A poisoning. ' ||
                                               'Symptoms of this include liver & bone damage, hair loss, double vision,' ||
                                               ' vomiting and headaches.', 5, 'Polar bears', 1);

INSERT INTO fun_fact_topic_set VALUES (1, 1);
INSERT INTO topic_fun_fact_set VALUES (1, 1);

INSERT INTO topic VALUES (2, 'test');

INSERT INTO fun_fact VALUES (2, 'test', 2, 'test', 1);

INSERT INTO fun_fact_topic_set VALUES (2, 2);
INSERT INTO topic_fun_fact_set VALUES (2, 2);
