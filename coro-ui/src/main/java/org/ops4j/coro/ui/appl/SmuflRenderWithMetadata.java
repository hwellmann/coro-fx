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
package org.ops4j.coro.ui.appl;

import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_BLACK;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_HALF;
import static org.ops4j.coro.smufl.MusicSymbol.NOTEHEAD_WHOLE;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ops4j.coro.model.Measure;
import org.ops4j.coro.model.Note;
import org.ops4j.coro.model.Pitch;
import org.ops4j.coro.model.Step;
import org.ops4j.coro.smufl.Metadata;
import org.ops4j.coro.smufl.MetadataReader;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class SmuflRenderWithMetadata extends Application {

    private static final double EM = 40;
    private static final double SP = EM / 4;
    private Metadata metadata;
    
    private Measure part;
    private Font font;
    private int currentNoteIndex;
    private Map<Note, Group> presentations;
    private Pane drawingPane;
    
    private void loadMusicFont() {
        font = Font.loadFont(getClass().getResource("/fonts/Bravura.otf").toExternalForm(), EM);                
    }

    private void createPart() {
        part = new Measure();
        part.getNotes().add(createNote(Step.D, 24));
        part.getNotes().add(createNote(Step.E, 24));
        part.getNotes().add(createNote(Step.F, 24));
        part.getNotes().add(createNote(Step.G, 24));
        part.getNotes().add(createNote(Step.A, 24));
        part.getNotes().add(createNote(Step.B, 24));
        currentNoteIndex = part.getNotes().size() - 1;
        presentations = new HashMap<>();
    }

    /**
     * @param b
     * @param i
     * @return
     */
    private Note createNote(Step b, int i) {
        Note note = new Note();
        Pitch pitch = new Pitch();
        pitch.setStep(b);
        pitch.setOctave(4);
        note.setPitch(pitch);
        note.setDuration(i);
        return note;
    }

    private void readMetadata() {
        MetadataReader reader = new MetadataReader();
        try (InputStream is = getClass().getResourceAsStream("/fonts/bravura/metadata.json")) {
            metadata = reader.readMetadata(is);
        }
        catch (IOException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
    }

    private Group renderStaff() {
        double staffLineThickness = SP * metadata.getEngravingDefault("staffLineThickness");

        int numLines = 5;
        List<Node> lines = new ArrayList<>(5);
        double y = 2*SP;
        for (int i = 0; i < numLines; i++) {
            Line line = new Line(50, y, 300, y);
            line.setStrokeWidth(staffLineThickness);
            lines.add(line);
            y += SP;
        }
        return new Group(lines);
    }

    Group renderNotes() {

        double stemThickness = SP * metadata.getEngravingDefault("stemThickness");
        Text note3 = new Text(2 * SP, 0, NOTEHEAD_WHOLE.asString());
        note3.setFont(font);

        Text note4 = new Text(4 * SP, 0, NOTEHEAD_HALF.asString());
        note4.setFont(font);

        Point2D anchorStemUp = metadata.getAnchor(NOTEHEAD_HALF, "stemUpSE");
        Point2D anchorStemDown = metadata.getAnchor(NOTEHEAD_HALF, "stemDownNW");
        System.out.println("anchorStemUp = " + anchorStemUp);
        System.out.println("anchorStemDown = " + anchorStemDown);

        double x0 = 4 * SP + stemThickness / 2;
        Line stemDown = new Line(x0, -SP * anchorStemDown.getY(), x0, SP
            * (3.5 + anchorStemDown.getY()));
        stemDown.setStrokeWidth(stemThickness);

        double x1 = 4 * SP + SP * anchorStemUp.getX() - stemThickness / 2; // 51.4
        Line stemUp = new Line(x1, -SP * anchorStemUp.getY(), x1, -SP * (3.5 - anchorStemUp.getY()));
        stemUp.setStrokeWidth(stemThickness);

        Group note4Group = new Group(note4, stemUp, stemDown);
        note4Group.setTranslateY(note4Group.getTranslateY() + SP * 1.5);

        Text note5 = new Text(6 * SP, 0, NOTEHEAD_BLACK.asString());
        note5.setFont(font);

        return new Group(note3, note4Group, note5);        
    }

    private Group renderPart() {
        Group notes = new Group();
        double x = 2*SP;
        
        int noteIndex = 0;
        for (Note note : part.getNotes()) {
            Group noteGroup = renderNote(note);
            if (noteIndex == currentNoteIndex) {
                for (Node child : noteGroup.getChildren()) {
                    if (child instanceof Shape) {
                        ((Shape) child).setFill(Color.BLUE);                        
                    }
                }
            }
            //noteGroup.setTranslateX(noteGroup.getTranslateX() + x);
            noteGroup.getTransforms().add(new Translate(x, 0));
            notes.getChildren().add(noteGroup);
            presentations.put(note, noteGroup);
            noteIndex++;
            x += 3*SP;
        }
        
        
        return notes;
    }

    private Group renderNote(Note note) {
        double x = 30 + 2*SP;
        double y =  30 + SP/2 * (Step.B.ordinal() - note.getPitch().getStep().ordinal());
        String symbol = NOTEHEAD_HALF.asString();
        Text noteUi = new Text(x, y, symbol);
        noteUi.setFont(font);
        noteUi.setOnMouseClicked(e -> handleNoteSelected(e));
        return new Group(noteUi);
    }
    
    
    
    @Override
    public void start(Stage primaryStage) {
        loadMusicFont();
        readMetadata();
        createPart();
        
        primaryStage.setTitle("Music Demo");


        Group staff = renderStaff();
        //Group notes = renderNotes();
        Group notes = renderPart();
        
        Rectangle sheet = new Rectangle(5, 5, 300, 200);
        sheet.setFill(Color.WHITE);
        sheet.setStroke(Color.BLACK);

        Group group = new Group(sheet, staff, notes);
        
        drawingPane = new Pane(group);

        
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().addAll(menuFile, menuEdit);

        VBox root = new VBox();
        root.getChildren().addAll(menuBar, drawingPane);
        
        Scene scene = new Scene(root, 600, 400);
        scene.setFill(Color.GREY);
        scene.setOnScroll(e -> handleScroll(scene, e));

        primaryStage.setScene(scene);
        primaryStage.show();

        root.setOnKeyPressed(e -> handleKeyEvent(e));       
        root.requestFocus();
    }
    
    private void handleScroll(Scene scene, ScrollEvent e) {
        double deltaY = e.getDeltaY();
        double scale = (deltaY < 0) ? 1.1 : 1.0 / 1.1;
        drawingPane.getTransforms().add(new Scale(scale, scale));
    }
    
    private Group getCurrentNoteGroup() {
        Note note = part.getNotes().get(currentNoteIndex);
        Group noteGroup = presentations.get(note);
        return noteGroup;
    }

    private void handleNoteSelected(MouseEvent e) {
        unhighlight(getCurrentNoteGroup());
        presentations.forEach( (k, v) -> {
            if (v.equals(e.getSource())) {
                currentNoteIndex = part.getNotes().indexOf(k);
                highlight(getCurrentNoteGroup());
            }
        });
    }
    
    private void highlight(Group noteGroup) {        
        for (Node child : noteGroup.getChildren()) {
            if (child instanceof Shape) {
                ((Shape) child).setFill(Color.BLUE);                        
            }
        }
    }

    private void unhighlight(Group noteGroup) {
        for (Node child : noteGroup.getChildren()) {
            if (child instanceof Shape) {
                ((Shape) child).setFill(Color.BLACK);                        
            }
        }        
    }

    private void handleKeyEvent(KeyEvent e) {
        Note note = part.getNotes().get(currentNoteIndex);
        Group noteUi = presentations.get(note);
        switch (e.getCode()) {
            case UP:
                increasePitch(note, noteUi);
                break;
            case DOWN:
                decreasePitch(note, noteUi);
                break;
            case LEFT:
                moveBackward(note, noteUi);
                break;
            case RIGHT:
                moveForward(note, noteUi);
                break;
            default:
        }
    }

    private void moveBackward(Note note, Group noteUi) {
        if (currentNoteIndex == 0) {
            return;
        }
        
        unhighlight(noteUi);
        currentNoteIndex--;
        highlight(getCurrentNoteGroup());
    }

    private void moveForward(Note note, Group noteUi) {
        if (currentNoteIndex == part.getNotes().size() - 1) {
            return;
        }

        unhighlight(noteUi);
        currentNoteIndex++;
        highlight(getCurrentNoteGroup());
    }

    private void decreasePitch(Note note, Group noteUi) {
        System.out.println("DOWN");
        if (note.getPitch().getStep() == Step.C) {
            return;
        }
        Pitch pitch = new Pitch();
        pitch.setOctave(4);
        pitch.setStep(Step.values()[note.getPitch().getStep().ordinal() - 1]);
        note.setPitch(pitch);
        double y = noteUi.getTranslateY();
        System.out.println("y = " + y);
        noteUi.setTranslateY(y + 5);
        System.out.println(noteUi.getLayoutY());
    }

    private void increasePitch(Note note, Group noteUi) {
        System.out.println("UP");
        if (note.getPitch().getStep() == Step.B) {
            return;
        }
        Pitch pitch = new Pitch();
        pitch.setOctave(4);
        pitch.setStep(Step.values()[note.getPitch().getStep().ordinal() + 1]);
        note.setPitch(pitch);
        noteUi.setTranslateY(noteUi.getTranslateY() - 5);
    }

    
    

    public static void main(String[] args) {
        launch(args);
    }

}
