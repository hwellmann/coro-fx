/*
 * Copyright 2015 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.ops4j.coro.musicxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.ops4j.coro.musicxml.gen.ScorePartwise;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * @author hwellmann
 *
 */
public class MusicXmlReader {

    public ScorePartwise readScore(File file) throws JAXBException {
        try (InputStream is = new FileInputStream(file)) {
            // create a reader with a custom entity resolver to skip DTD references
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();            
            xmlReader.setEntityResolver((p, s) -> new InputSource(new StringReader("")));

            JAXBContext context = JAXBContext.newInstance(ScorePartwise.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
            Source saxSource = new SAXSource(xmlReader, new InputSource(is));
            return unmarshaller.unmarshal(saxSource, ScorePartwise.class).getValue();
        }
        catch (ParserConfigurationException | SAXException | IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}
