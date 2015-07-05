/*
 * Copyright 2015 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 *
 * See the License for the specific language governing permissions and limitations under the
 * License.
 */
package org.ops4j.coro.model;

/**
 * @author Harald Wellmann
 *
 */
public class Offset {

    private double x;
    private double y;

    
    /**
     * 
     */
    public Offset() {
        // TODO Auto-generated constructor stub
    }
    
    public Offset(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the x.
     * 
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x.
     * 
     * @param x
     *            the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the y.
     * 
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y.
     * 
     * @param y
     *            the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    public Offset add(Offset other) {
        return new Offset(this.x + other.x, this.y + other.y);
    }

    public Offset subtract(Offset other) {
        return new Offset(this.x - other.x, this.y - other.y);
    }
}
