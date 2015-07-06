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

import static org.ops4j.coro.smufl.MusicSymbol.AUGMENTATION_DOT;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_16TH_DOWN;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_16TH_UP;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_32ND_DOWN;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_32ND_UP;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_64TH_DOWN;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_64TH_UP;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_8TH_DOWN;
import static org.ops4j.coro.smufl.MusicSymbol.FLAG_8TH_UP;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_BLACK;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_HALF;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_WHOLE;

import org.ops4j.coro.model.Note;
import org.ops4j.coro.model.NoteType;
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
    private double stemOffsetX;

    /**
     * 
     */
    public NoteNode(Note note, LayoutContext context) {
        this.note = note;
        this.context = context;
        setOnMouseClicked(e -> handleNoteSelected(e));
    }

    public void render() {
        if (note.isRest()) {
            renderRest();
        }
        else {
            renderNote();
        }
        createDots();
    }

    public void renderNote() {
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
        createFlag();
        createDots();
    }
    
    public void renderRest() {
        double sp = context.getStaffSpace();
        numSteps = 4;
        y = sp / 2 * numSteps;
        symbol = getRestSymbol(note);
        String text = symbol.asString();
        Text noteUi = new Text(0, y, text);
        noteUi.setFont(context.getMusicFont());
        getChildren().add(noteUi);
    }

    /**
     * 
     */
    private void createDots() {
        double sp = context.getStaffSpace();
        double x = 1.5 * sp;
        for (int i = 0; i < note.getDots(); i++) {
            Text dot = new Text(x, y, AUGMENTATION_DOT.asString());
            dot.setFont(context.getMusicFont());
            getChildren().add(dot);
            x += 0.5 * sp;
        }
    }

    /**
     * 
     */
    private void createFlag() {
        if (!hasFlag()) {
            return;
        }

        if (hasStemDown()) {
            createFlagDown();
        }
        else {
            createFlagUp();
        }
    }

    /**
     * 
     */
    private void createFlagDown() {
        double sp = context.getStaffSpace();
        MusicSymbol symbol = getFlagDown(note);
        String text = symbol.asString();
        Text flag = new Text(0, y + 3.5 * sp, text);
        flag.setFont(context.getMusicFont());
        getChildren().add(flag);
    }

    /**
     * 
     */
    private void createFlagUp() {
        double sp = context.getStaffSpace();
        MusicSymbol symbol = getFlagUp(note);
        String text = symbol.asString();
        Text flag = new Text(stemOffsetX, y - 3.5 * sp, text);
        flag.setFont(context.getMusicFont());
        getChildren().add(flag);
    }

    /**
     * @return
     */
    private boolean hasFlag() {
        return note.getNoteType().ordinal() >= NoteType.EIGHTH.ordinal();
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
        Point2D anchorStemDown = context.getAnchorStemDownNW(symbol);
        
        double dy = 0;
        if (hasFlag()) {
            MusicSymbol flag = getFlagDown(note);
            dy = context.getAnchorStemDownSW(flag).getY();
        }
        double x0 = stemThickness / 2;
        Line stemDown = new Line(x0, y - sp * anchorStemDown.getY(), 
            x0, y + sp * (3.5 + anchorStemDown.getY() + dy));
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
        Point2D anchorStemUp = context.getAnchorStemUpSE(symbol);

        stemOffsetX = sp * anchorStemUp.getX() - stemThickness / 2;
        Line stemUp = new Line(stemOffsetX, y - sp * anchorStemUp.getY(), stemOffsetX,
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
                return NOTEHEAD_BLACK;
        }
    }

    private MusicSymbol getRestSymbol(Note note) {
        NoteType noteType = note.getNoteType();
        if (noteType == null) {
            noteType = NoteType.WHOLE;
        }
        switch (noteType) {
            case MAXIMA:
                return MusicSymbol.REST_MAXIMA;
            case LONG:
                return MusicSymbol.REST_LONGA;
            case BREVE:
                return MusicSymbol.REST_BREVE;
            case WHOLE:
                return MusicSymbol.REST_WHOLE;
            case HALF:
                return MusicSymbol.REST_HALF;
            case QUARTER:
                return MusicSymbol.REST_QUARTER;
            case EIGHTH:
                return MusicSymbol.REST_EIGHTH;
            case N16TH:
                return MusicSymbol.REST_16TH;
            case N32ND:
                return MusicSymbol.REST_32ND;
            case N64TH:
                return MusicSymbol.REST_64TH;
            default:
                return null;
        }
    }

    private MusicSymbol getFlagDown(Note note) {
        switch (note.getNoteType()) {
            case EIGHTH:
                return FLAG_8TH_DOWN;
            case N16TH:
                return FLAG_16TH_DOWN;
            case N32ND:
                return FLAG_32ND_DOWN;
            case N64TH:
                return FLAG_64TH_DOWN;
            default:
                return null;
        }
    }

    private MusicSymbol getFlagUp(Note note) {
        switch (note.getNoteType()) {
            case EIGHTH:
                return FLAG_8TH_UP;
            case N16TH:
                return FLAG_16TH_UP;
            case N32ND:
                return FLAG_32ND_UP;
            case N64TH:
                return FLAG_64TH_UP;
            default:
                return null;
        }
    }

    private void handleNoteSelected(MouseEvent e) {
        highlight();
    }

    private void highlight() {
        for (Node child : getChildren()) {
            if (child instanceof Shape) {
                Shape shape = (Shape) child;
                if (child instanceof Text) {
                    shape.setFill(Color.BLUE);
                }
                else {
                    shape.setStroke(Color.BLUE);                    
                }
            }
        }
    }
}
