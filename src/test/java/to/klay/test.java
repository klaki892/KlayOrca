package to.klay;

/**
 * @author Klayton Killough
 * Date: 6/29/2019
 */
public class test {

    public static void main(String... args) {
        String val = "The Domino's Giveaway is LIVE! Click fast to score. Link is unique/no sharing. Claim now https://claim.quikly.com/6175/2u979fws5b HELP=Help STOP=End msgs";

        System.out.println(decipher(val));


    }

    public static String decipher(String s) {
        int start = s.indexOf("http");
        int end = s.indexOf(" ", start); //start search from start
        return s.substring(start, end).trim();
    }
}
