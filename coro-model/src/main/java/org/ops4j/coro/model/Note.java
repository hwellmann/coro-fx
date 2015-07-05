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

public class Note {

    private Pitch pitch;
    private int duration;
    private NoteType noteType;
    private int dots;
    private Accidental accidental;

    /**
     * Gets the pitch.
     * 
     * @return the pitch
     */
    public Pitch getPitch() {
        return pitch;
    }

    /**
     * Sets the pitch.
     * 
     * @param pitch
     *            the pitch to set
     */
    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
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

    /**
     * Gets the noteType.
     * 
     * @return the noteType
     */
    public NoteType getNoteType() {
        return noteType;
    }

    /**
     * Sets the noteType.
     * 
     * @param noteType
     *            the noteType to set
     */
    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    /**
     * Gets the dots.
     * 
     * @return the dots
     */
    public int getDots() {
        return dots;
    }

    /**
     * Sets the dots.
     * 
     * @param dots
     *            the dots to set
     */
    public void setDots(int dots) {
        this.dots = dots;
    }

    /**
     * Gets the accidental.
     * 
     * @return the accidental
     */
    public Accidental getAccidental() {
        return accidental;
    }

    /**
     * Sets the accidental.
     * 
     * @param accidental
     *            the accidental to set
     */
    public void setAccidental(Accidental accidental) {
        this.accidental = accidental;
    }

}
