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
public class Margins {

    private double topMargin;
    private double bottomMargin;
    private double leftMargin;
    private double rightMargin;

    /**
     * Gets the topMargin.
     * 
     * @return the topMargin
     */
    public double getTopMargin() {
        return topMargin;
    }

    /**
     * Sets the topMargin.
     * 
     * @param topMargin
     *            the topMargin to set
     */
    public void setTopMargin(double topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * Gets the bottomMargin.
     * 
     * @return the bottomMargin
     */
    public double getBottomMargin() {
        return bottomMargin;
    }

    /**
     * Sets the bottomMargin.
     * 
     * @param bottomMargin
     *            the bottomMargin to set
     */
    public void setBottomMargin(double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    /**
     * Gets the leftMargin.
     * 
     * @return the leftMargin
     */
    public double getLeftMargin() {
        return leftMargin;
    }

    /**
     * Sets the leftMargin.
     * 
     * @param leftMargin
     *            the leftMargin to set
     */
    public void setLeftMargin(double leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * Gets the rightMargin.
     * 
     * @return the rightMargin
     */
    public double getRightMargin() {
        return rightMargin;
    }

    /**
     * Sets the rightMargin.
     * 
     * @param rightMargin
     *            the rightMargin to set
     */
    public void setRightMargin(double rightMargin) {
        this.rightMargin = rightMargin;
    }

}
