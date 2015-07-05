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

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.ops4j.coro.model.Measure;
import org.ops4j.coro.model.Note;
import org.ops4j.coro.model.Part;
import org.ops4j.coro.model.Score;
import org.ops4j.coro.musicxml.gen.ScorePartwise;

/**
 * @author hwellmann
 *
 */
public class ConverterTest {
    
    private int numMeasures;
    private int numParts;
    
    @Test
    public void shouldConvertMenuet() throws JAXBException {
        MusicXmlReader reader = new MusicXmlReader();
        ScorePartwise score = reader.readScore(new File("src/test/resources/test-data/16 - Menuet 18.xml"));
        assertThat(score.getPart().size(), is(1));
        
        MusicXmlConverter converter = new MusicXmlConverter();
        Score coroScore = converter.convertScore(score);
        coroScore.getParts().forEach(p -> printPart(p));
    }

    private void printPart(Part part) {
        numParts++;
        System.out.println("part " + numParts);
        part.getMeasures().forEach(m -> printMeasure(m));
    }
    
    
    /**
     * @param m
     * @return
     */
    private void printMeasure(Measure measure) {
        numMeasures++;
        System.out.println("  measure " + numMeasures);
        measure.getNotes().forEach(n -> printNote(n));
    }

    /**
     * @param n
     * @return
     */
    private void printNote(Note note) {
        System.out.print(String.format("    %s%s %s", 
            note.getPitch().getStep(), 
            note.getPitch().getOctave(), 
            note.getNoteType().toString().toLowerCase()));
        if (note.getDots() > 0) {
            System.out.print(" ");
            for (int i = 0; i < note.getDots(); i++) {
                System.out.print(".");
            }
        }
        System.out.println();
    }

}
