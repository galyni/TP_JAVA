
#### Pré-requis
- SQL Server + base appelée "Librairie' (voir les ressources)

#### Dossier resources/

- .properties (non synchronisé) : contient la chaine de connexion à la base de données.
Format : {"connectionString" : "jdbc:sqlserver://[instance];database=Librairie;integratedSecurity=true;authenticationScheme=nativeAuthentication;"}
- database.json : base sérialisée en json
- database.txt : base convertie en object puis sérialisée 
_ database*.sql : scripts permettant de créer la base Library puis d'y insérer quelques données.
- mssql-jdbc_auth-8.4.1.x64.dll : nécessaire à l'authentification windows SQL Server (voir le point spécifique)

#### JDBC - Activation de l'authentification windows sql server

- Etape 1 : Configuration sql -> TCI - IP -> Activer
- Etape 2 : Télécharger https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15
- Etape 3 : Ajouter le fichier à la configuration du projet dans l'IDE
- Etape 4 : Ajouter le fichier auth...dll dans le dossier bin du jdk
