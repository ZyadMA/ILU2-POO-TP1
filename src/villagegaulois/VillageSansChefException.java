
package villagegaulois;

public class VillageSansChefException extends RuntimeException {
    
    public VillageSansChefException() {
        super("Le village n'a pas de chef .");
    }
    
}
