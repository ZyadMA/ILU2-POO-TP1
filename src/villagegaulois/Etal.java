package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
                if (!etalOccupe || vendeur == null) {
                    // Gérer l'exception ici en la lançant explicitement
                    throw new IllegalStateException("Tentative de libérer un étal qui n'est pas occupé par un vendeur.");
                }
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder(
				"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
                // Gérer l'état : "l'étal doit être occupé" (2c)
                if (!etalOccupe) {
                    throw new IllegalStateException("Impossible d'acheter : l'étal n'est pas occupé.");
                }

                // Gérer l'argument : "la quantité doit être positive" (2b)
                if (quantiteAcheter < 1) {
                    throw new IllegalArgumentException("La quantité à acheter doit être strictement positive.");
                }

                // Gérer l'exécution : "l'acheteur ne doit pas être null" (2a)
                try {
                    StringBuilder chaine = new StringBuilder();
                    chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
                            + " " + produit + " à " + vendeur.getNom());

                    if (quantite == 0) {
                        chaine.append(", malheureusement il n'y en a plus !");
                        quantiteAcheter = 0;
                    } else if (quantiteAcheter > quantite) {
                        chaine.append(", comme il n'y en a plus que " + quantite + ", "
                                + acheteur.getNom() + " vide l'étal de "
                                + vendeur.getNom() + ".\n");
                        quantiteAcheter = quantite;
                        quantite = 0;
                    } else {
                        quantite -= quantiteAcheter;
                        chaine.append(". " + acheteur.getNom()
                                + ", est ravi de tout trouver sur l'étal de "
                                + vendeur.getNom() + "\n");
                    }

                    return chaine.toString();

                } catch (NullPointerException e) {
                    // Gestion de l'exception NullPointerException (2a.iv)
                    System.err.println("L'acheteur ou le vendeur est null. Détails de l'erreur ci-dessous :");
                    e.printStackTrace(System.err);
                    return ""; 
                }
        }

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
