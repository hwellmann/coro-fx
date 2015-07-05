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
import org.ops4j.coro.ui.appl.LayoutContext;

import javafx.scene.Group;
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

    /**
     * 
     */
    public ScoreNode(Score score, LayoutContext context) {
        this.score = score;
        this.context = context;
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
        double x = 0;
        int numMeasures = -1;
        Part part = this.score.getParts().get(0);

        double staffOffsetX = 5 * SP;
        double staffOffsetY = 12 * SP;
        StaffNode staff = new StaffNode(null, context);
        staff.render();
        staff.getTransforms().add(new Translate(staffOffsetX, staffOffsetY));
        getChildren().add(staff);
        int numStaves = 0;

        Measure firstMeasure = part.getMeasures().get(0);
        

        for (Measure measure : part.getMeasures()) {
            numMeasures++;
            if (numMeasures % 4 == 0) {
                if (numStaves > 0) {
                    staff = new StaffNode(null, context);
                    staff.render();
                    staffOffsetY += 10 * SP;
                    staff.getTransforms().add(new Translate(staffOffsetX, staffOffsetY));
                    getChildren().add(staff);
                    x = 0;
                    measure.setClef(firstMeasure.getClef());
                    measure.setKey(firstMeasure.getKey());
                }
                numStaves++;
            }
            MeasureNode measureNode = new MeasureNode(measure, context);
            measureNode.render();
            measureNode.getTransforms().add(new Translate(x, 0));
            staff.getChildren().add(measureNode);
            x += measureNode.getBoundsInLocal().getWidth() + SP;
        }
    }

}
