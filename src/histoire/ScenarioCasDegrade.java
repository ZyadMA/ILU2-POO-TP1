package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
   
        Gaulois vendeur = new Gaulois("Bonemine", 7);
        Gaulois abraracourcix = new Gaulois("Abraracourcix", 7);
        Etal etal = new Etal();
        etal.occuperEtal(vendeur, "fleurs", 20);
        
        // test a) : acheteur = null
        etal.acheterProduit(10, null); 
        Etal etalNonOccupe = new Etal();

        try {
            // TEST c) : Etal non occupé
            etalNonOccupe.acheterProduit(10, abraracourcix); 
        } catch (IllegalStateException e) {
            System.out.println("\n*** Gestion de l'exception 'étal non occupé' ***");
            System.err.println("Erreur gérée : " + e.getMessage());
        }
        System.out.println("Fin du test");
	}
}
