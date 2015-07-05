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
public class StaffGroup {

    private List<Staff> staves = new ArrayList<>();

    private Offset offset;

    /**
     * Gets the staves.
     * 
     * @return the staves
     */
    public List<Staff> getStaves() {
        return staves;
    }

    /**
     * Gets the offset.
     * 
     * @return the offset
     */
    public Offset getOffset() {
        return offset;
    }

    /**
     * Sets the offset.
     * 
     * @param offset
     *            the offset to set
     */
    public void setOffset(Offset offset) {
        this.offset = offset;
    }

}