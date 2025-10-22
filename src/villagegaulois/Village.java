package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	private int nbEtal;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		this.nbEtal = nbEtal;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
                if (chef == null) {
                    // Lancer l'exception si le chef est null
                    throw new VillageSansChefException();
                }
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i] != null && !etals[i].isEtalOccupe()){
                                    return i;
                                }
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
                    int cpt = 0;

                    //  Compter le nombre d'étals
                    for (int i = 0 ; i < etals.length ; i++) {
                        //Vérifier si le tableau n'est pas null
                        if (etals[i] != null && etals[i].contientProduit(produit)){
                            cpt++;
                        }
                    }

                    Etal[] tab = new Etal[cpt]; 
                    int indice = 0;

                    // Remplir le tableau
                    for (int i = 0 ; i < etals.length ; i++) {
                        // Vérifier si le tableau n'est pas null
                        if (etals[i] != null && etals[i].contientProduit(produit)) {
                            tab[indice] = etals[i];
                            indice++;
                        }
                    }

                    return tab;	
                }
		
		public Etal trouverVendeur(Gaulois gaulois) {
                    for (int i = 0 ; i < etals.length ; i++) {
                        // Vérifie si l'emplacement du tableau n'est pas null
                        if (etals[i] != null && etals[i].isEtalOccupe()) {
                            if (etals[i].getVendeur() == gaulois) {
                                return etals[i];
                            }
                        }
                    }
                    return null;
                }
		
		public String afficherMarche(){
			int nbEtalVide = 0;StringBuilder chaine = new StringBuilder();
			for (int i = 0;i <etals.length ; i++) {
				if (etals[i].isEtalOccupe()){
					chaine.append((etals[i].afficherEtal()));
				}
				else nbEtalVide++;
			}
			
			return "Il reste " + nbEtalVide + " etals non utilises dans le marche.\n";
		}
		
	}
        
        //Installe un vendeur sur le premier étal libre.
        
        public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
            StringBuilder chaine = new StringBuilder();
            int indiceEtalLibre = marche.trouverEtalLibre();

            if (indiceEtalLibre != -1) {
                marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
                chaine.append(vendeur.getNom() + " vous a installé sur l'étal n°" + indiceEtalLibre + ".");
            } else {
                chaine.append("Désolé, " + vendeur.getNom() + ", il n'y a plus d'étal libre au marché.");
            }
            return chaine.toString();
        }

        
        //Retourne les étals qui contiennent un produit donné.
        
        public Gaulois[] rechercherVendeursProduit(String produit) {
            Etal[] etalsTrouves = marche.trouverEtals(produit);
            Gaulois[] vendeurs = new Gaulois[etalsTrouves.length];

            for (int i = 0; i < etalsTrouves.length; i++) {
                vendeurs[i] = etalsTrouves[i].getVendeur();
            }

            // Retourne le tableau des Gaulois. L'affichage de la console fera un toString() du tableau.
            return vendeurs;
        }

        
        //Retourne l'étal occupé par un Gaulois spécifique.
        
        public Etal rechercherEtal(Gaulois gaulois) {
            return marche.trouverVendeur(gaulois);
        }

        
        //Libère l'étal d'un vendeur donné.
        
        public String partirVendeur(Gaulois gaulois) {
            Etal etal = marche.trouverVendeur(gaulois);
            if (etal != null) {
                // La méthode libererEtal() dans la classe Etal retourne déjà la chaîne de bilan
                String bilanVente = etal.libererEtal();
                // Une fois libéré, l'étal peut être réoccupé.
                return bilanVente;
            }
            return "Le vendeur " + gaulois.getNom() + " n'occupait aucun étal.";
        }

        
        //Affiche l'état complet du marché.
        
        public String afficherMarche() {
            return marche.afficherMarche();
        }
}











