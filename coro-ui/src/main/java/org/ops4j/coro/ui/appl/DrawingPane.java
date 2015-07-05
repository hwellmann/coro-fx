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

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;

/**
 * @author Harald Wellmann
 *
 */
public class DrawingPane extends ScrollPane {
    
    private static final double[] ZOOM_LEVELS = { 0.1, 0.25, 0.5, 0.75, 1, 1.25, 1.5, 2, 4, 8 };
    private static final int NUM_ZOOM_LEVELS = ZOOM_LEVELS.length;
    private static final int DEFAULT_ZOOM_LEVEL = 4;
    
    private int zoomLevel = DEFAULT_ZOOM_LEVEL;
    
    private Group zoomedContent;

    
    public DrawingPane(Group content) {
        this.zoomedContent = content;
        Group zoomGroup = new Group(content);
        setContent(zoomGroup);
        setPannable(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.NEVER);
        setOnKeyTyped(e -> handleKeyTyped(e));
    }

    private void handleKeyTyped(KeyEvent e) {
        if (e.isControlDown()) {
            switch (e.getCharacter()) {
                case "+":
                    handleZoomIn();
                    break;
                case "-": 
                    handleZoomOut();
                    break;
                case "0":
                    handleZoomReset();
                    break;
            }
        }
    }

    private void handleZoomIn() {
        if (zoomLevel < NUM_ZOOM_LEVELS - 1) {
            zoomLevel++;
            scale();
        }
    }

    private void handleZoomOut() {
        if (zoomLevel > 0) {
            zoomLevel--;
            scale();
        }
    }

    private void handleZoomReset() {
        if (zoomLevel != DEFAULT_ZOOM_LEVEL) {
            zoomLevel = DEFAULT_ZOOM_LEVEL;
            scale();
        }
    }
    
    private void scale() {
        double scale = ZOOM_LEVELS[zoomLevel];
        zoomedContent.setScaleX(scale);
        zoomedContent.setScaleY(scale);        
    }
}
