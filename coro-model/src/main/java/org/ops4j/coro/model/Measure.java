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
package org.ops4j.coro.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hwellmann
 *
 */
public class Measure {

    private Clef clef;
    private Key key;
    private Time time;
    private List<Note> notes = new ArrayList<>();
    private int divisions;
    private int duration;

    /**
     * Gets the clef.
     * 
     * @return the clef
     */
    public Clef getClef() {
        return clef;
    }

    /**
     * Sets the clef.
     * 
     * @param clef
     *            the clef to set
     */
    public void setClef(Clef clef) {
        this.clef = clef;
    }

    /**
     * Gets the key.
     * 
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            the key to set
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Gets the time.
     * 
     * @return the time
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets the time.
     * 
     * @param time
     *            the time to set
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Gets the notes.
     * 
     * @return the notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     * 
     * @param notes
     *            the notes to set
     */
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    /**
     * Gets the divisions.
     * 
     * @return the divisions
     */
    public int getDivisions() {
        return divisions;
    }

    /**
     * Sets the divisions.
     * 
     * @param divisions
     *            the divisions to set
     */
    public void setDivisions(int divisions) {
        this.divisions = divisions;
    }

    /**
     * Gets the duration.
     * 
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration.
     * 
     * @param duration
     *            the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

}
