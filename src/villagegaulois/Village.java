package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].isEtalOccupe()){
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int cpt = 0;
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].contientProduit(produit)){
					cpt++;
				}
			}
			Etal[] tab = new Etal[cpt]; int indice = 0;
		
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].contientProduit(produit)) {
					tab[indice] = etals[i];
					indice++;
				}
			}
			return tab;	 
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0 ; i < etals.length ; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche(){
			int nbEtalVide = 0;StringBuilder chaine = new StringBuilder();
			for (int i = 0;i <etals.length ; i++) {
				if (etals[i].isEtalOccupe()){
					chaine.append((etals[i].getVendeur().getNom() + " vend " + etals[i].quantite )   )
				}
			}
			
			return "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n”;
		}
		/*
		public String afficherEtal() {
			if (etalOccupe) {
				return "L'Ã©tal de " + vendeur.getNom() + " est garni de " + quantite
						+ " " + produit + "\n";
			}
			return "L'Ã©tal est libre";
		}
		*/
	}
}











