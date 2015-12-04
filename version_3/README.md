# Tabella Version 3
Extends the servlet system to provide some example screens for the application. It uses CDI to inject
the controllers into the servlet and also a BallotService (which acts as a mock repository facade for the
Ballot entities).

The home page also displays the questions and answers for the dummy ballots that are built in the BallotService.
Later we will extend the service object to work with a repository object and extract them from database.
