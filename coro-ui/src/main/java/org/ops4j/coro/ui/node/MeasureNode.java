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

import org.ops4j.coro.model.Clef;
import org.ops4j.coro.model.Key;
import org.ops4j.coro.model.Measure;
import org.ops4j.coro.model.Note;
import org.ops4j.coro.model.Time;
import org.ops4j.coro.smufl.MusicSymbol;
import org.ops4j.coro.ui.appl.LayoutContext;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

/**
 * @author hwellmann
 *
 */
public class MeasureNode extends Group {

    private Measure measure;
    private LayoutContext layoutContext;
    private Line barLine;

    public MeasureNode(Measure measure, LayoutContext context) {
        this.measure = measure;
        this.layoutContext = context;
    }
    
    public void render() {
        double sp = layoutContext.getStaffSpace();
        double x = 0.5 * sp;
        
        Line line = new Line(0.5, 0, 0.5, 4 * sp);
        line.setStroke(Color.TRANSPARENT);
        line.setStrokeWidth(1);
        getChildren().add(line);
        
        if (measure.getClef() != null) {
            Group clef = renderClef(measure.getClef());
            clef.getTransforms().add(new Translate(x, 0));
            getChildren().add(clef);
            x = getBoundsInLocal().getWidth() + 0.5 * sp;
        }

        if (measure.getKey() != null) {
            Group key = renderKey(measure.getKey());
            key.getTransforms().add(new Translate(x, 0));
            getChildren().add(key);
            x = getBoundsInLocal().getWidth() + 0.5 * sp;
        }

        if (measure.getTime() != null) {
            Group key = renderTime(measure.getTime());
            key.getTransforms().add(new Translate(x, 0));
            getChildren().add(key);
            x = getBoundsInLocal().getWidth() + 1 * sp;
        }

        x = getBoundsInLocal().getWidth() + 1.5 * sp;

        for (Note note : measure.getNotes()) {
            NoteNode noteGroup = new NoteNode(note, layoutContext);
            noteGroup.render();
            
            noteGroup.getTransforms().add(new Translate(x, 0));
            getChildren().add(noteGroup);
            x = getBoundsInLocal().getWidth() + sp;
        }

        double barLineThickness = layoutContext.getBarLineThickness();
        barLine = new Line(0, 0, 0, 4 * sp);
        barLine.getTransforms().add(new Translate(x, 0));
        barLine.setStrokeWidth(barLineThickness);
        getChildren().add(barLine);
    }
    
    public void addPadding(double paddingPerNote) {
        double offsetX = 0;
        for (Node child : getChildren()) {
            if (child instanceof NoteNode) {
                NoteNode noteNode = (NoteNode) child;
                if (offsetX != 0) {
                    noteNode.getTransforms().add(new Translate(offsetX, 0));
                }
                offsetX += paddingPerNote;
            }
        }
        barLine.getTransforms().add(new Translate(offsetX, 0));
    }
    
    /**
     * @param time
     * @return
     */
    private Group renderTime(Time time) {
        double sp = layoutContext.getStaffSpace();
        Group timeGroup = new Group();
        MusicSymbol numerator = MusicSymbol.getTimeSignature(time.getNumerator());
        MusicSymbol denominator = MusicSymbol.getTimeSignature(time.getDenominator());
        Text text = new Text(0, sp, numerator.asString());
        text.setFont(layoutContext.getMusicFont());
        timeGroup.getChildren().add(text);
        text = new Text(0, 3 * sp, denominator.asString());
        text.setFont(layoutContext.getMusicFont());
        timeGroup.getChildren().add(text);
        return timeGroup;
    }

    private Group renderClef(Clef clef) {
        double sp = layoutContext.getStaffSpace();
        MusicSymbol symbol = MusicSymbol.G_CLEF;
        Text text = new Text(0, sp * clef.getLine(), symbol.asString());
        text.setFont(layoutContext.getMusicFont());

        Group clefGroup = new Group(text);
        clef.getClefSign();
        return clefGroup;
    }

    private Group renderKey(Key key) {
        double sp = layoutContext.getStaffSpace();
        Group keyGroup = new Group();
        if (key.getFifths() > 0) {
            String sharp = MusicSymbol.ACCIDENTAL_SHARP.asString();
            Text text = new Text(0, 0, sharp);
            text.setFont(layoutContext.getMusicFont());
            keyGroup.getChildren().add(text);

            if (key.getFifths() > 1) {
                text = new Text(sp, 1.5 * sp, sharp);
                text.setFont(layoutContext.getMusicFont());
                keyGroup.getChildren().add(text);
            }
            if (key.getFifths() > 2) {
                text = new Text(2 * sp, -0.5 * sp, sharp);
                text.setFont(layoutContext.getMusicFont());
                keyGroup.getChildren().add(text);
            }
        }
        else if (key.getFifths() < 0) {
            String flat = MusicSymbol.ACCIDENTAL_FLAT.asString();
            Text text = new Text(0, 2 * sp, flat);
            text.setFont(layoutContext.getMusicFont());
            keyGroup.getChildren().add(text);
            if (key.getFifths() < -1) {
                text = new Text(sp, 0.5 * sp, flat);
                text.setFont(layoutContext.getMusicFont());
                keyGroup.getChildren().add(text);
            }
            if (key.getFifths() < -2) {
                text = new Text(2 * sp, 2.5 * sp, flat);
                text.setFont(layoutContext.getMusicFont());
                keyGroup.getChildren().add(text);
            }
        }
        return keyGroup;
    }

    

    /**
     * Gets the measure.
     * 
     * @return the measure
     */
    public Measure getMeasure() {
        return measure;
    }

    /**
     * Sets the measure.
     * 
     * @param measure
     *            the measure to set
     */
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }
}
