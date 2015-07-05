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
import java.math.BigInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.ops4j.coro.musicxml.gen.Attributes;
import org.ops4j.coro.musicxml.gen.BarStyle;
import org.ops4j.coro.musicxml.gen.BarStyleColor;
import org.ops4j.coro.musicxml.gen.Barline;
import org.ops4j.coro.musicxml.gen.Clef;
import org.ops4j.coro.musicxml.gen.ClefSign;
import org.ops4j.coro.musicxml.gen.Note;
import org.ops4j.coro.musicxml.gen.ObjectFactory;
import org.ops4j.coro.musicxml.gen.PartList;
import org.ops4j.coro.musicxml.gen.PartName;
import org.ops4j.coro.musicxml.gen.Pitch;
import org.ops4j.coro.musicxml.gen.RightLeftMiddle;
import org.ops4j.coro.musicxml.gen.ScorePart;
import org.ops4j.coro.musicxml.gen.ScorePartwise;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part.Measure;
import org.ops4j.coro.musicxml.gen.Step;
import org.ops4j.coro.musicxml.gen.Time;
import org.ops4j.coro.musicxml.gen.TimeSymbol;

/**
 * @author hwellmann
 *
 */
public class DomineTest {

    private ObjectFactory factory;

    @Test
    public void singleVoice() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ScorePartwise.class);
        Marshaller marshaller = context.createMarshaller();
        factory = new ObjectFactory();

        ScorePartwise score = new ScorePartwise();
        PartList partList = new PartList();
        score.setPartList(partList);
        ScorePart scorePart = new ScorePart();
        scorePart.setId("P1");
        PartName partName = new PartName();
        partName.setValue("Tenor");
        scorePart.setPartName(partName);
        partList.setScorePart(scorePart);

        Part part = new Part();
        part.setId(scorePart);
        score.getPart().add(part);
        Measure m1 = new Measure();
        m1.setNumber("1");
        part.getMeasure().add(m1);

        Attributes attr = new Attributes();
        attr.setDivisions(1.0f);
        Time time = new Time();
        time.setSymbol(TimeSymbol.CUT);
        time.getTimeSignature().add(factory.createTimeBeats("2"));
        time.getTimeSignature().add(factory.createTimeBeatType("2"));
        attr.getTime().add(time);
        attr.setStaves(BigInteger.ONE);
        Clef clef = new Clef();
        clef.setNumber(BigInteger.ONE);
        clef.setSign(ClefSign.G);
        clef.setLine(BigInteger.valueOf(2));
        attr.getClef().add(clef);

        m1.getNoteOrBackupOrForward().add(attr);

        addNote(m1, Step.E, 4, 3);
        addNote(m1, Step.C, 4, 1);

        Measure m2 = new Measure();
        m2.setNumber("2");
        part.getMeasure().add(m2);

        addNote(m2, Step.G, 4, 4);

        Barline barline = new Barline();
        barline.setLocation(RightLeftMiddle.RIGHT);
        BarStyleColor barStyleColor = new BarStyleColor();
        barStyleColor.setValue(BarStyle.LIGHT_HEAVY);
        barline.setBarStyle(barStyleColor);
        m2.getNoteOrBackupOrForward().add(barline);

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
                "http://www.musicxml.org/xsd/3.0/musicxml.xsd");
        marshaller.marshal(score, new File("target", "domine.xml"));
    }

    private void addNote(Measure measure, Step step, int octave, float duration) {
        Note note = new Note();
        Pitch pitch = new Pitch();
        pitch.setStep(step);
        pitch.setOctave(4);
        note.getContent().add(factory.createNotePitch(pitch));
        note.getContent().add(factory.createNoteDuration(duration));
        measure.getNoteOrBackupOrForward().add(note);
    }
}
