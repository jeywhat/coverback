
# CoverBack-API

CoverBack-API allows to expose the different games contained in the targeted folder and recursively.
We're using ChikenCoopAPI powered by MetaCritic API to find any information about games (ex:  cover, score, editor, ...)

You can use the UI with your API at this url : https://coverback.web.app/ 
**Or** your can pull the projet in VueJs : https://github.com/jeywhat/coverback-ui

It's an educational project. 
I'm not responsible for the misuse of this program. Use at you're own risks.
## Installation

### Prerequisites

- Java 11
- PostgreSQL Database
- Maven
- Docker (optionnal)

### Generate your .jar

```bash
mvn clean install -DskipTests
```

### Pull/Launch the Docker image

```bash
docker pull jeywhat/coverback-api
docker run -e "storage.location=/games" -e "BDD_PASSWORD=********" -e [...] -e JAVA_OPTS="-Xmx128M -Xms128M" -p 8090:8090 -t jeywhat/coverback-api:latest
```

More informations on https://hub.docker.com/r/jeywhat/coverback-api

### Examples Namefile

- GameName.extension (Treated)
- GameName_SuperXCI_NbDLC_Version.extension (Treated)
- PrefixIgnored_GameName.extension (Ignored)

### Params System

| Variable      |           Example |
| :------------ | -------------: |
|storage.location|/games|
|supported.extensions.files|.txt,.rar,.zip|
|ignored.prefix.files|UPD|
|allow.download|false|
|init.scan.games.enabled| false|
|API_PORT| default : 8090|
|BDD_URL| postgresql.com|
|BDD_PORT| 5432|
|BDD_TABLE| games|
|BDD_USER| root /!\|
|BDD_PASSWORD| ********|



## Usage

Exposing API on port : **8090**

### Swagger

| Variable      |     Protocole|
| :------------ | :-------------: | 
|/game/all|GET|
|/game/all/refresh|POST|
|/game/{name}|GET|
|/game/{name}/download|GET|
|/game/findNewGames|GET|



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)
