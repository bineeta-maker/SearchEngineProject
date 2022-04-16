package com.searchengine.searcher;

import com.searchengine.utils.SEConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SEQueryParser {
    public String[] getQueryStatement(String[] topicQuery) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(new File(SEConstants.TOPICQUERYPATH));
        String[] _topicQuery = new String[3];
        _topicQuery[0] = topicQuery[0];
        _topicQuery[1] = topicQuery[1];

        NodeList nodeList = document.getElementsByTagName("description");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (topicQuery[0].equalsIgnoreCase(node.getParentNode().getAttributes().getNamedItem("number").getNodeValue())) {
                _topicQuery[2] = node.getTextContent();
                return _topicQuery;
            }
        }
        return null;
    }

}
