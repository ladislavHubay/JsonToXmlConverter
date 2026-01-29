package sk.drake_solutions.converter.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

/**
 * Trieda podpisuje XML subor.
 */
public class XmlSigner {

    /**
     * Metoda vytvara dokument s digitalnym podpisom XML suboru.
     * @param xmlFile XML subor urceny pre podpis.
     * @param outputSignedFile Umiestnenie vystupneho dokumentu.
     * @param privateKey Privatny kluc na vytvorenie podpisu.
     * @throws Exception Ak sa nepodari nacitat XML subor, vytvorit podpis alebo ulozit vysledny dokument.
     */
    public static void signXml(Path xmlFile, Path outputSignedFile, PrivateKey privateKey) throws Exception {
        byte[] xmlData = Files.readAllBytes(xmlFile);           // Nacita XML subor urceny pre podpis.

        Signature signature = Signature.getInstance("SHA256withRSA", "BC");     // vytvori sa objekt s SHA-256 hash,
                                                                                                // zasifrovany privatnym RSA klucom a
                                                                                                // Bouncy Castle poskytovatelom.
        signature.initSign(privateKey);                                                         // Subor sa podpisuje s privatnym klucom.
        signature.update(xmlData);                                                              // Data z XML sa vlozia do objektu s podpisom.

        byte[] digitalSignature = signature.sign();                                             // Vytvori digitalny podpis XML suboru.
        String base64Signature = Base64.getEncoder().encodeToString(digitalSignature);          // Digitalny podpis prevedie do String.

        Files.writeString(outputSignedFile, base64Signature);                                   // Vytvori subor s podpisom pre XML.
    }
}
