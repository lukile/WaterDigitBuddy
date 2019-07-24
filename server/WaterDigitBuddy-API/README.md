# WaterDigitBuddy

----

> Projet Annuel 2019 - MOC

----
## Utilisation de MongoDB


**Installation des node_modules**

    npm install

**Créer d'un dossier pour la bdd à la racine**

    sudo mkdir -p /MongoDB/projectName

**Terminal 1**

    cd Workspace/mongodb/bin
    sudo ./mongod --dbpath /MongoDB/projectName --port 27017

**Terminal 2**

    cd Workspace/projectName
    npm run start:dev

Si bdd déjà configurée, lancer

    npm run start:db
