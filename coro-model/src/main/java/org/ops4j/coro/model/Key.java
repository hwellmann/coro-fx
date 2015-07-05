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
public class Key {

    private int fifths;
    private String mode;

    /**
     * Gets the fifths.
     * 
     * @return the fifths
     */
    public int getFifths() {
        return fifths;
    }

    /**
     * Sets the fifths.
     * 
     * @param fifths
     *            the fifths to set
     */
    public void setFifths(int fifths) {
        this.fifths = fifths;
    }

    /**
     * Gets the mode.
     * 
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the mode.
     * 
     * @param mode
     *            the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
