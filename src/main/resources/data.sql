INSERT INTO users VALUES (1, 'admin@admin.ad','admin', 'admin');

INSERT INTO topic VALUES (1, 'Biology');

INSERT INTO fun_fact VALUES (1,' Arctic predators such as polar bears have a greater capacity to store ' ||
<<<<<<< HEAD
                               'vitamin A in their liver than most other animals. It is thought to be ' ||
                               'because of the effect of naturally occurring vitamin A in marine algae ' ||
                               'being passed up the food chain to the polar bear. So great is the polar ' ||
                               'bears ability to store this vitamin that if you were to consume the ' ||
                               'liver, you would more than likely succumb to the effects of ' ||
                               'Hypervitaminosis A.  Put simply it would be too much vitamin A for your ' ||
                               'body to handle and you would suffer from vitamin A poisoning. ' ||
                               'Symptoms of this include liver and bone damage, hair loss, double vision,' ||
                               ' vomiting and headaches.', 5, 'Polar bears', 1);
=======
                                               'vitamin A in their liver than most other animals. It is thought to be ' ||
                                               'because of the effect of naturally occurring vitamin A in marine algae ' ||
                                               'being passed up the food chain to the polar bear. So great is the polar ' ||
                                               'bears ability to store this vitamin that if you were to consume the ' ||
                                               'liver, you would more than likely succumb to the effects of ' ||
                                               'Hypervitaminosis A.  Put simply it would be too much vitamin A for your ' ||
                                               'body to handle and you would suffer from vitamin A poisoning. ' ||
                                               'Symptoms of this include liver and bone damage, hair loss, double vision,' ||
                                               ' vomiting and headaches.', 5, 'Polar bears', 1);
>>>>>>> deploy

INSERT INTO fun_fact_topic_set VALUES (1, 1);
INSERT INTO topic_fun_fact_set VALUES (1, 1);

INSERT INTO topic VALUES (2, 'Physics');

INSERT INTO fun_fact VALUES (2, 'Atoms are 99.9999% empty space. If you forced all the atoms together, removing ' ||
                                'the space between them, a single teaspoon or sugar cube of the resulting mass would ' ||
                                'weigh five billion tons; about ten times the weight of all the humans who are ' ||
                                'currently alive. ', 3, 'Humans in a sugarcube', 1);


INSERT INTO fun_fact_topic_set VALUES (2, 2);
INSERT INTO topic_fun_fact_set VALUES (2, 2);

INSERT INTO fun_fact VALUES (3, 'Based on fossil and biological evidence, most scientists accept that birds are ' ||
                                'a specialised subgroup of theropod dinosaurs, and more specifically, they are members ' ||
                                'of Maniraptora. The consensus view in contemporary palaeontology is that the flying ' ||
                                'theropods, or avialans, are the closest relatives of the deinonychosaurs, which ' ||
                                'include dromaeosaurids and troodontids. According to a paper published in GMC Genomics,' ||
                                ' the closest living relatives of dinosaurs are chickens and turkeys.', 3, 'Living dinosaurs', 1);

INSERT INTO fun_fact_topic_set VALUES (3, 1);
INSERT INTO topic_fun_fact_set VALUES (1, 3);