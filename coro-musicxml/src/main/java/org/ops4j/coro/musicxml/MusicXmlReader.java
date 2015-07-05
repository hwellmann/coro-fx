/*
 * Copyright 2015 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.coro.musicxml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.ops4j.coro.musicxml.gen.ScorePartwise;

/**
 * @author hwellmann
 *
 */
public class MusicXmlReader {
    
    public ScorePartwise readScore(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ScorePartwise.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StreamSource source = new StreamSource(file);
        return unmarshaller.unmarshal(source, ScorePartwise.class).getValue();
    }

}
