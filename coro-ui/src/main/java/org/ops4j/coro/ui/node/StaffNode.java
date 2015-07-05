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
package org.ops4j.coro.ui.node;

import java.util.ArrayList;
import java.util.List;

import org.ops4j.coro.model.Staff;
import org.ops4j.coro.ui.appl.LayoutContext;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;

/**
 * @author hwellmann
 *
 */
public class StaffNode extends Group {
    
    
    private Staff staff;
    private LayoutContext context;

    /**
     * 
     */
    public StaffNode(Staff staff, LayoutContext context) {
        this.staff = staff;
        this.context = context;
    }
    
    public void render() {
        double SP = context.getStaffSpace();
        double staffLineThickness = context.getStaffLineThickness();

        int numLines = 5;
        List<Node> lines = new ArrayList<>(5);
        double y = 0;
        for (int i = 0; i < numLines; i++) {
            Line line = new Line(0, y, 75 * SP, y);
            line.setStrokeWidth(staffLineThickness);
            lines.add(line);
            y += SP;
        }
        getChildren().addAll(lines);
    }

}
