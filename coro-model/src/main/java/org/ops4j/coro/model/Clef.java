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

/**
 * @author hwellmann
 *
 */
public class Clef {

    private ClefSign clefSign;
    private int line;
    private int octaveChange;

    /**
     * Gets the clefSign.
     * 
     * @return the clefSign
     */
    public ClefSign getClefSign() {
        return clefSign;
    }

    /**
     * Sets the clefSign.
     * 
     * @param clefSign
     *            the clefSign to set
     */
    public void setClefSign(ClefSign clefSign) {
        this.clefSign = clefSign;
    }

    /**
     * Gets the line.
     * 
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * Sets the line.
     * 
     * @param line
     *            the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * Gets the octaveChange.
     * 
     * @return the octaveChange
     */
    public int getOctaveChange() {
        return octaveChange;
    }

    /**
     * Sets the octaveChange.
     * 
     * @param octaveChange
     *            the octaveChange to set
     */
    public void setOctaveChange(int octaveChange) {
        this.octaveChange = octaveChange;
    }

}
