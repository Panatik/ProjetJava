# Projet Java

## Setup de l'environnement sur IntelliJ

### Création de la base de données
Il sera nécessaire de créer une base de données **bd_java** dans MySQL et de configurer les identifiants appropriés dans le fichier `DataBaseManager.java`.

Au lancement du programme, le setup de la base de données se fera automatiquement.

### Installation des librairies
Le projet utilise deux librairies situées dans le dossier `.lib` :
- `jbcrypt-0.4.jar`
- `mysql-connector-j-9.1.0.jar`

Pour les intégrer dans votre projet :
1. Ouvrez **Project Structure**
2. Allez dans l'onglet **Libraries**
3. Ajoutez les fichiers présents dans le dossier `.lib`
(Il est fortement conseiller d'ajouter les librairies une par une dans IntelliJ)

## Lancement de l'application
L'application propose deux interfaces :
- **Login** : Se connecter avec un compte existant
- **Register** : Créer un compte

En fonction du rôle de l'utilisateur qui se connecte (**Admin** ou **User**), l'application adapte les fonctionnalités accessibles.

## Fonctionnalités de l'AdminDashboard
L'interface AdminDashboard permet d'effectuer plusieurs actions :
- **Ajouter un employé à la whitelist**
- **Créer un magasin**
- **Supprimer un magasin**
- **Assigner des employés à un magasin**
- **Supprimer un employé**
- **Mettre à jour un utilisateur**
- **Mettre à jour un item de l'inventaire**
- **Supprimer un item de l'inventaire**
- **Créer un item dans l'inventaire**

## Fonctionnalités de l'EmployeeDashboard
L'interface EmployeeDashboard permet d'effectuer plusieurs actions :
- **Mettre à jour mon compte**
- **Supprimer mon compte et me déconnecter**
- **Accéder à mon magasin**
- **Modifier la quantité d'un item**

## Visualisation des tables et droits d'accès
Les utilisateurs peuvent visualiser certaines tables en fonction de leur rôle :

### Admin
- Accès complet à toutes les tables (utilisateurs, magasins, inventaire, employés, etc.)
- Droit de modification, suppression et ajout sur toutes les données

### Employé
- Accès en lecture seule aux informations du magasin auquel il est assigné
- Possibilité de modifier la quantité des items dans l'inventaire de son magasin
- Accès restreint aux informations des autres utilisateurs

