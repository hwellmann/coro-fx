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
public class Time {

    private int numerator;
    private int denominator;

    /**
     * Gets the numerator.
     * 
     * @return the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Sets the numerator.
     * 
     * @param numerator
     *            the numerator to set
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    /**
     * Gets the denominator.
     * 
     * @return the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Sets the denominator.
     * 
     * @param denominator
     *            the denominator to set
     */
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

}
