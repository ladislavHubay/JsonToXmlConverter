# JSON to XML Converter with Digital Signature

## Popis projektu

Aplikácia načíta JSON súbory zo zadaného vstupného adresára, vyfiltruje a spracuje ich obsah
podľa definovaných validačných pravidiel a vygeneruje z nich XML súbory do výstupného adresára.

Ako bonus je výsledný XML súbor digitálne podpísaný pomocou self-signed X.509 certifikátu.
Digitálny podpis je uložený do samostatného súboru s príponou `.signed`.

---

## Funkcionalita

- načítanie JSON súborov zo vstupného adresára
- validácia a filtrovanie dát podľa dátumového rozsahu
- generovanie XML súboru
- vytvorenie digitálneho podpisu nad celým obsahom XML súboru
- uloženie podpisu do samostatného súboru (detached signature)

---

## Digitálny podpis

### Vytvorenie certifikátu

Certifikát je generovaný **priamo v aplikácii pri jej spustení** pomocou knižnice
**Bouncy Castle**.

- typ certifikátu: self-signed X.509
- algoritmus kľúča: RSA (2048 bit)
- hodnota `CN`: `MyTestCertificate`
- certifikát je podpísaný vlastným privátnym kľúčom

Certifikát sa používa výhradne na vytvorenie digitálneho podpisu a nie je trvalo ukladaný.
Riešenie je však navrhnuté tak, aby bolo možné v budúcnosti certifikát alebo verejný kľúč
uložiť a použiť na overovanie digitálneho podpisu.

---

### Vytvorenie podpisu

- podpis sa vytvára nad **celým obsahom XML súboru**
- použitý algoritmus podpisu: `SHA256withRSA`
- kryptografické operácie zabezpečuje **Bouncy Castle provider**
- podpis je **detached** – XML súbor nie je súčasťou podpisu
- výsledný podpis je uložený ako **Base64 text** do súboru s príponou `.signed`

#### Príklad:
input1.json → input1.xml → input1.signed

---

## Použité technológie

- Java
- Bouncy Castle (security provider)
- SHA256withRSA
- Base64 encoding