package sk.drake_solutions.converter.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sk.drake_solutions.converter.model.OutputRecord;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Zapisuje data do suboru XML.
 */
public class XmlWriter {

    /**
     * Metoda vytvory vystupny subor vo formate XML.
     * @param records Zoznam predpripravenych udajov urcene na export do XML.
     * @param outputFile Cesta kde sa ma umiestnit vystupny subor.
     */
    public void write(List<OutputRecord> records, File outputFile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element messages = doc.createElement("Messages");
            doc.appendChild(messages);

            for (OutputRecord outputRecord : records){
                Element message = doc.createElement("Message");
                messages.appendChild(message);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(outputRecord.getId()));
                message.appendChild(id);

                Element type = doc.createElement("type");
                type.appendChild(doc.createTextNode(outputRecord.getType()));
                message.appendChild(type);

                Element created = doc.createElement("created");
                created.appendChild(doc.createTextNode(outputRecord.getCreated().toString()));
                message.appendChild(created);

                Element amount = doc.createElement("amount");
                amount.appendChild(doc.createTextNode(String.valueOf(outputRecord.getAmount())));
                message.appendChild(amount);

                Element vat = doc.createElement("vat");
                vat.appendChild(doc.createTextNode(String.valueOf(outputRecord.getVat())));
                message.appendChild(vat);

                Element amountWithVat = doc.createElement("amountWithVat");
                amountWithVat.appendChild(doc.createTextNode(String.valueOf(outputRecord.getAmountWithVat())));
                message.appendChild(amountWithVat);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFile);

            transformer.transform(source, result);

            System.out.println("XML bolo ulozene: " + outputFile.getAbsolutePath());

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
