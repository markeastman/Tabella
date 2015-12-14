insert into BALLOT ( id, question, answerIndex) values ( 1, 'What is your favourite colour', -1);
insert into BALLOT_ANSWERS (Ballot_id, answer_sequence, answers) values (1, 0, 'red');
insert into BALLOT_ANSWERS (Ballot_id, answer_sequence, answers) values (1, 1, 'green');
insert into BALLOT_ANSWERS (Ballot_id, answer_sequence, answers) values (1, 2, 'blue');

alter sequence hibernate_sequence restart with 10 increment by 1;