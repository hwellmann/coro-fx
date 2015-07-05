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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harald Wellmann
 *
 */
public class Page {

    private List<StaffGroup> staffGroups = new ArrayList<>();
    private String pageNumber;
    private boolean pageNumberVisible;
    private Margins margins;

    private double width;
    private double height;

    private double staffSpaceInMm;

    /**
     * Gets the pageNumber.
     * 
     * @return the pageNumber
     */
    public String getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the pageNumber.
     * 
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets the pageNumberVisible.
     * 
     * @return the pageNumberVisible
     */
    public boolean isPageNumberVisible() {
        return pageNumberVisible;
    }

    /**
     * Sets the pageNumberVisible.
     * 
     * @param pageNumberVisible
     *            the pageNumberVisible to set
     */
    public void setPageNumberVisible(boolean pageNumberVisible) {
        this.pageNumberVisible = pageNumberVisible;
    }

    /**
     * Gets the staffGroups.
     * 
     * @return the staffGroups
     */
    public List<StaffGroup> getStaffGroups() {
        return staffGroups;
    }

    /**
     * Gets the margins.
     * 
     * @return the margins
     */
    public Margins getMargins() {
        return margins;
    }

    /**
     * Sets the margins.
     * 
     * @param margins
     *            the margins to set
     */
    public void setMargins(Margins margins) {
        this.margins = margins;
    }

    /**
     * Gets the page width in staff spaces.
     * 
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width.
     * 
     * @param width
     *            the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the page height in staff spaces.
     * 
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height.
     * 
     * @param height
     *            the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the size of a staff space in millimetres.
     * 
     * @return the staffSpaceInMm
     */
    public double getStaffSpaceInMm() {
        return staffSpaceInMm;
    }

    /**
     * Sets the size of a staff space in millimetres.
     * 
     * @param staffSpaceInMm
     *            the staffSpaceInMm to set
     */
    public void setStaffSpaceInMm(double staffSpaceInMm) {
        this.staffSpaceInMm = staffSpaceInMm;
    }
}
