# WoW Addon Project:

## Definition of project:

- Collect data from Battlegrounds via Ingame Lua Addon.
- Sending the data to a REST API.
- Analysing and compare data against players.
- Presenting data on single-page REACT application.

## Features: 
- Hvor meget sloges du omkring objectives.
- Honor/Death Rating.
- Utility Usage.
- Objective rating
- why i don dedded?


## Lærings mål and where to find them:

### Wishfull programming og abstraction:

Vi skriver vores backend i java, og har tænkt os at implementere 
wishfull programming og interfaces for at gøre backenden mere abstract.



### Threading og multi processing:

Når vi får data ind kunne vi godt tænke os at bruge en masse threading 
til at håndtere alle de tasks der skal gennemføres. Vi skal derfor sørge 
for at indele vores analyse i nogen spor, som skal bruge data fra 
hinanden for at gennemføre den færdige analyse. Og når analysen er 
færdig, skal den gå igang med en sammenlignings analyse af en anden 
spiller, hvilket kræver at hans analyse er færdig.

vi tænker at en tråd kommer til at se således ud:

legend: 
- [] er data pakker.
- {} er threads.

[Data]-> {data handler} -> {Analyst thread} -> [Analysed package] -> 
{Sumary thread}.

### Streams?
Vi kan godt finde brug af streams ind over div threads i projektet.

### Funktionel Programming:

Vi kunne godt tænke os at implementere et andet programerings sprog i 
projektet. Vi tænker umiddelbart Hascell, men hvilket som helst ville 
kunne gå an.

evt. funktionalitet.

- Tjek data integritet.
- chat analyse.
- Spam filter (har vi allerede analyseret dataen?).
- udføre en kill kommand på opgaven hvis den er irrelevant eller dårlig.
- Være vores data handler.
- Være vores sumary handler.


