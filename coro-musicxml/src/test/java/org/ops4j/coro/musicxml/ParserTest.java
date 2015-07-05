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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.ops4j.coro.musicxml.gen.Note;
import org.ops4j.coro.musicxml.gen.NoteType;
import org.ops4j.coro.musicxml.gen.Pitch;
import org.ops4j.coro.musicxml.gen.ScorePartwise;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part.Measure;

/**
 * @author hwellmann
 *
 */
public class ParserTest {
    
    @Test
    public void shouldParseSarabande() throws JAXBException {
        File file = new File("src/test/resources/test-data/16 - Menuet 18.xml");
        MusicXmlReader reader = new MusicXmlReader();
        ScorePartwise score = reader.readScore(file);
        assertThat(score.getPart().size(), is(1));
        
        Part part = score.getPart().get(0);
        part.getMeasure().forEach(m -> parseMeasure(m));
    }

    /**
     * @param m
     * @return
     */
    private void parseMeasure(Measure m) {
        System.out.println("measure " +m.getNumber());
        for (Object child : m.getNoteOrBackupOrForward()) {
            if (child instanceof Note) {
                Note note = (Note) child;
                parseNote(note);
                System.out.println();
            }            
        }
    }

    /**
     * @param note
     */
    private void parseNote(Note note) {
        for (JAXBElement<?> child : note.getContent()) {
            if (child.getValue() instanceof Pitch) {
                Pitch pitch = (Pitch) child.getValue();
                System.out.print("    " + pitch.getStep() + pitch.getOctave());
            }
            if (child.getValue() instanceof NoteType) {
                NoteType type = (NoteType) child.getValue();
                System.out.print(" " + type.getValue());
            }
            else if (child.getName().getLocalPart().equals("dot")) {
                System.out.print(" dot");                
            }
        }
    }

}
