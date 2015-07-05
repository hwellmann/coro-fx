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

import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_HALF;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_WHOLE;

import org.ops4j.coro.model.Note;
import org.ops4j.coro.model.Pitch;
import org.ops4j.coro.model.Step;
import org.ops4j.coro.smufl.MusicSymbol;
import org.ops4j.coro.ui.appl.LayoutContext;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * @author hwellmann
 *
 */
public class NoteNode extends Group {
    
    
    private Note note;
    private LayoutContext context;
    private int numSteps;
    private MusicSymbol symbol;
    private double y;

    /**
     * 
     */
    public NoteNode(Note note, LayoutContext context) {
        this.note = note;
        this.context = context;
        setOnMouseClicked(e -> handleNoteSelected(e));
    }
    
    public void render() {
        double sp = context.getStaffSpace();
        numSteps = getNumSteps(note);
        y = sp / 2 * numSteps;
        symbol = getSymbol(note);
        String text = symbol.asString();
        Text noteUi = new Text(0, y, text);
        noteUi.setFont(context.getMusicFont());
        getChildren().add(noteUi);

        createLedgerLines();
        createStem();
        
    }
    
    private void createLedgerLines() {
        double sp = context.getStaffSpace();
        BoundingBox box = context.getBoundingBox(symbol);
        double width = box.getWidth();
        double ledgerLineThickness = context.getLedgerLineThickness();
        double ledgerExt = context.getLedgerLineExtension();
        if (numSteps < -1) {
            int numLedgerLines = -numSteps / 2;
            double dy = -sp;
            for (int i = 0; i < numLedgerLines; i++) {
                Line ledgerLine = new Line(-ledgerExt, dy, sp * width + ledgerExt, dy);
                ledgerLine.setStrokeWidth(ledgerLineThickness);
                dy -= sp;
                getChildren().add(ledgerLine);
            }
        }
        else if (numSteps > 9) {
            int numLedgerLines = (numSteps - 8) / 2;
            double dy = 5 * sp;
            for (int i = 0; i < numLedgerLines; i++) {
                Line ledgerLine = new Line(-ledgerExt, dy, sp + ledgerExt, dy);
                ledgerLine.setStrokeWidth(ledgerLineThickness);
                dy += sp;
                getChildren().add(ledgerLine);
            }
        }        
    }

    /**
     * 
     */
    private void createStem() {
        if (hasStemDown()) {
            createStemDown();
        }
        else {
            createStemUp();
        }
    }

    
    
    
    /**
     * 
     */
    private void createStemDown() {
        double sp = context.getStaffSpace();
        double stemThickness = context.getStemThickness();
        Point2D anchorStemDown = context.getAnchorStemDown(symbol);
        double x0 = stemThickness / 2;
        Line stemDown = new Line(x0, y -sp * anchorStemDown.getY(), x0, y + sp
            * (3.5 + anchorStemDown.getY()));
        stemDown.setStrokeWidth(stemThickness);
        getChildren().add(stemDown);
    }

    /**
     * @return
     */
    private boolean hasStemDown() {
        return numSteps < 5;
    }

    /**
     * 
     */
    private void createStemUp() {
        double sp = context.getStaffSpace();
        double stemThickness = context.getStemThickness();
        Point2D anchorStemUp = context.getAnchorStemUp(symbol);
        
        double x1 = sp * anchorStemUp.getX() - stemThickness / 2;
        Line stemUp = new Line(x1, y - sp * anchorStemUp.getY(), x1,
            y - sp * (3.5 - anchorStemUp.getY()));
        
        
        stemUp.setStrokeWidth(stemThickness);
        getChildren().add(stemUp);
    }

    private int getNumSteps(Note note) {
        Pitch pitch = note.getPitch();
        int distance = Step.F.ordinal() - pitch.getStep().ordinal();
        int octaveShift = 7 * (5 - pitch.getOctave());
        return distance + octaveShift;
    }

    /**
     * @param note
     * @return
     */
    private MusicSymbol getSymbol(Note note) {
        switch (note.getNoteType()) {
            case WHOLE:
                return NOTEHEAD_WHOLE;
            case HALF:
                return NOTEHEAD_HALF;
            default:
                return MusicSymbol.NOTEHEAD_BLACK;
        }
    }

    private void handleNoteSelected(MouseEvent e) {
        highlight();
    }

    private void highlight() {
        for (Node child : getChildren()) {
            if (child instanceof Shape) {
                Shape shape = (Shape) child;
                shape.setFill(Color.BLUE);
                shape.setStroke(Color.BLUE);
            }
        }
    }

    private void unhighlight(Group noteGroup) {
        for (Node child : noteGroup.getChildren()) {
            if (child instanceof Shape) {
                Shape shape = (Shape) child;
                shape.setFill(Color.BLACK);
                shape.setFill(Color.BLACK);
            }
        }
    }

}
