# Tabella Description
Tabella is a demo application that shows my thoughts and workings on how to build a web application that can scale from a few users right through to thousands of users. “Tabella” is the latin name for ballot which is simple what the application provides. Users can create a single question (at the moment) ballot and it can have a number of defined answers. Other users will then be able to vote on the options and have the answers published to the owner by demographic regions, other aspects.

The system will have registered users that can vote on ballots. Users can create a ballot and publish it and then see the results. We can have anonymous users vote on ballots if the creator decides to allow it. The application will have an anonymous capability that allows non-registered users to vote on a ballot.

The system will start off as a web page system, but will also provide a mobile application to allow for the voting.

The view of the results will be statistical and also graphical by overlapping coloured regions onto maps etc. It may be that to provide the answers the voter has to provide some aspects of themselves (male,female), region in the world, age etc. The results will be analysed via these criteria. The solution will also look at “Big Data” as well as a relational database approach to holding the results and reviewing the answers.

The voting period may be time capped and the creator may want to see a dynamic playback of the how the results arrived. This may incorporate push technology from the server to the client via something websockets.

The system will have a compliment application that tests the system and provides automated results for ballots.

The users have roles of:
* anonymous:
They can vote on anonymous ballots
* registered voter:
They can vote on ballots, but they cannot create any ballots themselves
* registered account:
They can vote on ballots and also create and publish a ballot
* administrator:
They can administer the system

The system will demonstrate straightforward J2EE web based development. It will then review the aspect of growth and provide a section on how the application could be moved to a cloud hosted solution and provide HA across the application layers. Other virtualisation aspects such as docker containers will be investigated. The implementation will then be considered from a microservices aspect so that we can see how the underlying development would be changed if we want this system to go global.
