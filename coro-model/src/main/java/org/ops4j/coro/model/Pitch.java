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
public class Pitch {

    private Step step;
    private double alteration;
    private int octave;

    /**
     * Gets the step.
     * 
     * @return the step
     */
    public Step getStep() {
        return step;
    }

    /**
     * Sets the step.
     * 
     * @param step
     *            the step to set
     */
    public void setStep(Step step) {
        this.step = step;
    }

    /**
     * Gets the alteration.
     * 
     * @return the alteration
     */
    public double getAlteration() {
        return alteration;
    }

    /**
     * Sets the alteration.
     * 
     * @param alteration
     *            the alteration to set
     */
    public void setAlteration(double alteration) {
        this.alteration = alteration;
    }

    /**
     * Gets the octave.
     * 
     * @return the octave
     */
    public int getOctave() {
        return octave;
    }

    /**
     * Sets the octave.
     * 
     * @param octave
     *            the octave to set
     */
    public void setOctave(int octave) {
        this.octave = octave;
    }

}
