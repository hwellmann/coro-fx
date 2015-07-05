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
package org.ops4j.coro.ui.appl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.ops4j.coro.model.Score;
import org.ops4j.coro.musicxml.MusicXmlConverter;
import org.ops4j.coro.musicxml.MusicXmlReader;
import org.ops4j.coro.musicxml.gen.ScorePartwise;
import org.ops4j.coro.smufl.Metadata;
import org.ops4j.coro.smufl.MetadataReader;
import org.ops4j.coro.ui.node.ScoreNode;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.Printer.MarginType;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CoroViewer extends Application {

    private static final double EM = 40;
    private static final double SP = EM / 4;
    private Metadata metadata;

    private Font font;
    private ScrollPane drawingPane;
    private LayoutContext layoutContext;
    private Score score;

    private void loadMusicFont() {
        font = Font.loadFont(getClass().getResource("/fonts/Bravura.otf").toExternalForm(), EM);
    }

    private void openScore(String fileName) {
        MusicXmlReader reader = new MusicXmlReader();
        try {
            ScorePartwise scorePartwise = reader.readScore(new File(fileName));
            MusicXmlConverter converter = new MusicXmlConverter();
            score = converter.convertScore(scorePartwise);
        }
        catch (JAXBException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }
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
        layoutContext = new LayoutContext(font, metadata, SP);
    }

    @Override
    public void start(Stage primaryStage) {
        loadMusicFont();
        readMetadata();

        String fileName = getParameters().getUnnamed().get(0);
        openScore(fileName);

        ScoreNode scoreNode = new ScoreNode(score, layoutContext);
        scoreNode.render();
        

        VBox root = new VBox();
        drawingPane = new DrawingPane(scoreNode);
        root.getChildren().addAll(createMenuBar(), drawingPane);

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.GREY);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Coro Viewer");
        primaryStage.show();

        root.requestFocus();
    }
    
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem printItem = new MenuItem("Print");
        printItem.setOnAction(e -> printScore(e));
        menuFile.getItems().add(printItem);
        Menu menuEdit = new Menu("Edit");
        menuBar.getMenus().addAll(menuFile, menuEdit);
        return menuBar;
    }
    
    /**
     * @param e
     * @return
     */
    private void printScore(ActionEvent e) {
        System.out.println("printing....");
        Optional<Printer> optPrinter = Printer.getAllPrinters().stream()
            .filter(p -> p.getName().equals("PDF")).findFirst();
        if (!optPrinter.isPresent()) {
            return;
        }

        PageLayout pageLayout = optPrinter.get().createPageLayout(Paper.A4,
            PageOrientation.PORTRAIT, MarginType.DEFAULT);
        PrinterJob printerJob = PrinterJob.createPrinterJob(optPrinter.get());
        printerJob.getJobSettings().setJobName("CoroScore");
        boolean success = printerJob.printPage(pageLayout, drawingPane);
        System.out.println("success = " + success);
        printerJob.endJob();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
