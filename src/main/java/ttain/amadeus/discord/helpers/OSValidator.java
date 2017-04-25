package ttain.amadeus.discord.helpers;

/**
 * -- Amadeus Discord System --
 * Creator: Tai
 * Date: 3/22/2017
 * Package: ttain.amadeus.discord.helpers
 * Filename: OSValidator.java
 */

public class OSValidator
{
    private String OS = System.getProperty("os.name").toLowerCase();

    public boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    public boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }

    public String getOS(){
        if (isWindows()) {
            return "win";
        } else if (isMac()) {
            return "osx";
        } else if (isUnix()) {
            return "uni";
        } else if (isSolaris()) {
            return "sol";
        } else {
            return "err";
        }
    }
}
