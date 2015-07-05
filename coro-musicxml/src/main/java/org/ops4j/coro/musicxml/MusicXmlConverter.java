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

import static org.ops4j.coro.model.NoteType.BREVE;
import static org.ops4j.coro.model.NoteType.EIGHTH;
import static org.ops4j.coro.model.NoteType.HALF;
import static org.ops4j.coro.model.NoteType.LONG;
import static org.ops4j.coro.model.NoteType.MAXIMA;
import static org.ops4j.coro.model.NoteType.N16TH;
import static org.ops4j.coro.model.NoteType.N32ND;
import static org.ops4j.coro.model.NoteType.N64TH;
import static org.ops4j.coro.model.NoteType.QUARTER;
import static org.ops4j.coro.model.NoteType.WHOLE;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.ops4j.coro.model.Clef;
import org.ops4j.coro.model.ClefSign;
import org.ops4j.coro.model.Score;
import org.ops4j.coro.musicxml.gen.Attributes;
import org.ops4j.coro.musicxml.gen.Key;
import org.ops4j.coro.musicxml.gen.Note;
import org.ops4j.coro.musicxml.gen.NoteType;
import org.ops4j.coro.musicxml.gen.Pitch;
import org.ops4j.coro.musicxml.gen.ScorePartwise;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part;
import org.ops4j.coro.musicxml.gen.ScorePartwise.Part.Measure;
import org.ops4j.coro.musicxml.gen.Time;

/**
 * @author hwellmann
 *
 */
public class MusicXmlConverter {
    
    private static Map<String, org.ops4j.coro.model.NoteType> noteTypes = new HashMap<>();
    
    static  {
        noteTypes.put("maxima", MAXIMA);
        noteTypes.put("long", LONG);
        noteTypes.put("breve", BREVE);
        noteTypes.put("whole", WHOLE);
        noteTypes.put("half", HALF);
        noteTypes.put("quarter", QUARTER);
        noteTypes.put("eighth", EIGHTH);
        noteTypes.put("16th", N16TH);
        noteTypes.put("32nd", N32ND);
        noteTypes.put("64th", N64TH);        
    }
    
    
    public Score convertScore(ScorePartwise score) {
        Score coroScore = new Score();
        score.getPart().forEach(p -> coroScore.getParts().add(convertPart(p)));
        return coroScore;
    }
    
    public org.ops4j.coro.model.Part convertPart(Part part) {
        org.ops4j.coro.model.Part coroPart = new org.ops4j.coro.model.Part();
        part.getMeasure().forEach(m -> coroPart.getMeasures().add(convertMeasure(m)));
        return coroPart;
    }

    /**
     * @param m
     * @return
     */
    private org.ops4j.coro.model.Measure convertMeasure(Measure measure) {
        org.ops4j.coro.model.Measure coroMeasure = new org.ops4j.coro.model.Measure();
        for (Object child : measure.getNoteOrBackupOrForward()) {
            if (child instanceof Note) {
                Note note = (Note) child;
                coroMeasure.getNotes().add(convertMeasure(note));
            }
            else if (child instanceof Attributes) {
                Attributes attr = (Attributes) child;
                if (!attr.getClef().isEmpty()) {
                    org.ops4j.coro.musicxml.gen.Clef clef = attr.getClef().get(0);
                    Clef coroClef = new Clef();
                    coroClef.setClefSign(ClefSign.G);
                    coroClef.setLine(5 - clef.getLine().intValue());
                    coroMeasure.setClef(coroClef);
                }
                if (!attr.getKey().isEmpty()) {
                    Key key = attr.getKey().get(0);
                    org.ops4j.coro.model.Key coroKey = new org.ops4j.coro.model.Key();
                    coroKey.setFifths(key.getFifths().intValue());
                    coroKey.setMode(key.getMode());
                    coroMeasure.setKey(coroKey);
                }
                if (!attr.getTime().isEmpty()) {
                    Time time = attr.getTime().get(0);
                    org.ops4j.coro.model.Time coroTime = new org.ops4j.coro.model.Time();
                    for (JAXBElement<String> t : time.getTimeSignature()) {
                        String name = t.getName().getLocalPart();
                        if (name.equals("beats")) {
                            coroTime.setNumerator(Integer.parseInt(t.getValue()));
                        }
                        else if (name.equals("beat-type")) {
                            coroTime.setDenominator(Integer.parseInt(t.getValue()));
                        }
                    }
                    coroMeasure.setTime(coroTime);
                }
            }            
        }

        return coroMeasure;
    }

    /**
     * @param note
     * @return
     */
    private org.ops4j.coro.model.Note convertMeasure(Note note) {
        org.ops4j.coro.model.Note coroNote = new org.ops4j.coro.model.Note();
        for (JAXBElement<?> child : note.getContent()) {
            if (child.getValue() instanceof Pitch) {
                Pitch pitch = (Pitch) child.getValue();
                org.ops4j.coro.model.Pitch coroPitch = new org.ops4j.coro.model.Pitch();
                coroPitch.setOctave(pitch.getOctave());
                coroPitch.setStep(org.ops4j.coro.model.Step.valueOf(pitch.getStep().name()));
                coroNote.setPitch(coroPitch);
            }
            else if (child.getValue() instanceof NoteType) {
                NoteType type = (NoteType) child.getValue();
                coroNote.setNoteType(convertNoteType(type));
            }
            else if (child.getName().getLocalPart().equals("dot")) {
                coroNote.setDots(coroNote.getDots() + 1);                
            }
        }
        return coroNote;
    }

    /**
     * @param type
     * @return
     */
    private org.ops4j.coro.model.NoteType convertNoteType(NoteType type) {
        return noteTypes.get(type.getValue());
    }
}
