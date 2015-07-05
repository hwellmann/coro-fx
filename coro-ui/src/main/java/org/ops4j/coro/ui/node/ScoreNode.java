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
package org.ops4j.coro.ui.node;

import org.ops4j.coro.model.Measure;
import org.ops4j.coro.model.Part;
import org.ops4j.coro.model.Score;
import org.ops4j.coro.model.Staff;
import org.ops4j.coro.ui.appl.LayoutContext;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

/**
 * @author hwellmann
 *
 */
public class ScoreNode extends Group {

    private Score score;
    private LayoutContext context;
    private double staffWidth;

    /**
     * 
     */
    public ScoreNode(Score score, LayoutContext context) {
        this.score = score;
        this.context = context;
        this.staffWidth = 75 * context.getStaffSpace();
    }

    public void render() {
        double SP = context.getStaffSpace();

        Rectangle sheet = new Rectangle(0, 0, 90 * SP, 120 * SP);
        sheet.setFill(Color.WHITE);
        sheet.setStroke(Color.BLACK);

        Text heading = new Text(5 * SP, 8 * SP, "Menuet");
        heading.setFont(Font.font("Droid Serif", FontWeight.BOLD, 3 * SP));

        getChildren().addAll(sheet, heading);

        renderPart();

    }

    private void renderPart() {
        double SP = context.getStaffSpace();
        double measureOffsetX = 0;
        Part part = this.score.getParts().get(0);

        double staffOffsetX = 5 * SP;
        double staffOffsetY = 12 * SP;
        Staff staff = new Staff();
        StaffNode staffNode = new StaffNode(staff, context);
        staffNode.render();
        staffNode.getTransforms().add(new Translate(staffOffsetX, staffOffsetY));
        getChildren().add(staffNode);

        Measure firstMeasure = part.getMeasures().get(0);

        for (Measure measure : part.getMeasures()) {
            MeasureNode measureNode = new MeasureNode(measure, context);
            measureNode.render();

            double measureWidth = measureNode.getBoundsInLocal().getWidth();
            if (measureOffsetX + measureWidth <= staffWidth) {

                measureNode.getTransforms().add(new Translate(measureOffsetX, 0));
                staffNode.getChildren().add(measureNode);
                staff.getMeasures().add(measure);
                measureOffsetX += measureNode.getBoundsInLocal().getWidth() + SP;
            }
            else {
                justifyStaff(staffNode, measureOffsetX);
                staff = new Staff();
                staffNode = new StaffNode(staff, context);
                staffNode.render();
                staffOffsetY += 10 * SP;
                staffNode.getTransforms().add(new Translate(staffOffsetX, staffOffsetY));
                getChildren().add(staffNode);
                measureOffsetX = 0;
                measure.setClef(firstMeasure.getClef());
                measure.setKey(firstMeasure.getKey());

                measureNode = new MeasureNode(measure, context);
                measureNode.render();
                staffNode.getChildren().add(measureNode);
                staff.getMeasures().add(measure);
                measureOffsetX += measureNode.getBoundsInLocal().getWidth() + SP;
            }
        }
    }

    /**
     * 
     */
    private void justifyStaff(StaffNode staffNode, double actualWidth) {
        double extraSpace = staffWidth - actualWidth;
        Staff staff = staffNode.getStaff();
        double extraPerMeasure = extraSpace / (staff.getMeasures().size() - 1);
        double offset = 0;
        for (Node child : staffNode.getChildren()) {
            if (child instanceof MeasureNode) {
                if (offset > 0) {
                    child.getTransforms().add(new Translate(offset, 0));
                }
                offset += extraPerMeasure;
            }
        }
    }
}
