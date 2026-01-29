package sk.drake_solutions.converter.service;

import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * Trieda generuje certifikat.
 */
public class CertificateGenerator {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * Metoda vygeneruje par klucov (sukromny / PrivateKey a verejny / PublicKey) a vrati objekt obsahujuci tieto kluce.
     * @return Vrati objekt obsahujuci oba kluce.
     * @throws NoSuchAlgorithmException Ak sa nepodari ziskat algoritmus RSA.
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");      // Generuje kluce (sukromny a verejny)
                                                                                    // RSA - pouzity algoritmus na podpis
        keyGen.initialize(2048);                                             // Velkost kluca (2048 bitov)
        return keyGen.generateKeyPair();                                            // Vracia objekt ktory obsahuje oba kluce (sukromny a verejny)
    }

    /**
     * Metoda vytvara certifikat a podpisovy vzor.
     * @param keyPair Kluce privatny a verejny.
     * @return Vrati obejt obsahujuci self-signed X.509 certifikat podpisany privatnym klucom.
     * @throws Exception Vynimkla sa vyhodi ak pocas generovania alebo podpisu self-signed X.509 certifikat nastane chyba.
     */
    public static X509Certificate generateSelfSignedCertificate(KeyPair keyPair) throws Exception {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + 365L * 24 * 60 * 60 * 1000);  // Prepocet 1rok na milisekundy pre dobu platnosti certifikatu.

        String dn = "CN=MyTestCertificate";                         // Distinguished Name - identita ktoru certifikat predstavuje.
                                                                    // CN - Common Name (Dalsie napr.: O – Organization,
                                                                    //                                 OU – Organizational Unit,
                                                                    //                                 C – Country)
                                                                    // CN - kluc, MyTestCertificate - hodnota.

        BigInteger serialNumber = BigInteger.valueOf(now);          // Unikatne seriove cislo pre certifikat.

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(  // Stavba certifikatu
                new org.bouncycastle.asn1.x500.X500Name(dn),                        // vydavatel certifikatu
                serialNumber,                                                       // serialne cislo certifikatu
                startDate,                                                          // zaciatok platnosti certifikatu
                endDate,                                                            // koniec platnosti certifikatu
                new org.bouncycastle.asn1.x500.X500Name(dn),                        // subjekt, komu certifikat patri
                keyPair.getPublic()                                                 // verejny kluc, privatny kluc sa nikdy nepridava do certifikatu
        );                                                                          // vydavatel a subjekt su rovnaky - 'self-signed'

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")      // Pripravy podpisovy vzor.
                .build(keyPair.getPrivate());                                       // Pouziva algoritmus SHA256withRSA a privatny kluc.

        return new JcaX509CertificateConverter()
                .setProvider("BC")                                                  // Vrati hotovy self-signed X.509 certifikat
                .getCertificate(certBuilder.build(signer));                         // podpisany privatnym klucom ako objekt.
    }
}
