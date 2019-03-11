# LeBonSandwich
Liste des membres du groupe :
- Benjamin THOUVENIN
- Johan SALTUTTI
- Anthony ZINK
- Alexandre RALLI

Pour réaliser ce projet, nous avons repris les notions vues en td pour essayer de les intégrer au projets.

Vous trouverez à la racine du projet, une archive ZIP LBS-stories.ods contenant un fichier excel décrivant les user stories de l'application Le Bon Sandwich. Les lignes dont la case POint est remplie en vert sont les routes fonctionnelles du projet, donc que vous pourrez tester avec POstman.

## Mise en place
Cloner le dépôt avec la commande ```git clone https://github.com/JesterHeads/LeBonSandwich.git```

## Lancer le service de commande
A la racine du dépôt , lancer les commandes ```cd serviceCommande``` et ```make run``` afin de lancer la dockerisartion du service de commande avec le service de base de donnée postgres.
Pour une raison que nous n'avons pas réussi a identifié, le service de commande s'arrête la première fois que les conteneurs sont montés, il faudra donc lancer la commande ```make stop``` puis relancer al commande ```make run``` pour pouvoir intéragir avec l'API depuis Postman.

credentials pour tester la route d'authentification : username=john.doe, password=jwtpass
username=amdin.admin, password=jwtpass

## Lancer le service de catalogue

A la racine du dépôt , lancer les commandes ```cd catalogue``` et ```make run``` afin de lancer la dockerisartion du service de catalogue
