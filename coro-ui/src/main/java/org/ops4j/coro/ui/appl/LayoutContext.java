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

import org.ops4j.coro.smufl.Metadata;
import org.ops4j.coro.smufl.MusicSymbol;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;

/**
 * @author hwellmann
 *
 */
public class LayoutContext {

    
    private Font font;
    private Metadata metadata;
    private double staffSpace;

    /**
     * 
     */
    public LayoutContext(Font font, Metadata metadata, double staffSpace) {
        this.font = font;
        this.metadata = metadata;
        this.staffSpace = staffSpace;
    }
    
    public double getStaffSpace() {
        return staffSpace;
    }
    
    public double getLedgerLineThickness() {
        return staffSpace * metadata.getEngravingDefault("legerLineThickness");
        
    }
    
    public double getLedgerLineExtension() {
        return staffSpace * metadata.getEngravingDefault("legerLineExtension");        
    }
    
    public double getBarLineThickness() {
        return staffSpace * metadata.getEngravingDefault("thinBarlineThickness");
    }
    
    public double getStemThickness() {
        return staffSpace * metadata.getEngravingDefault("stemThickness");
    }
    
    public Font getMusicFont() {
        return font;
    }

//    /**
//     * @param symbol
//     * @return
//     */
//    public double getNominalWidth(MusicSymbol symbol) {
//        return staffSpace * metadata.getBoundingBox(symbol);
//    }
    
    public BoundingBox getBoundingBox(MusicSymbol symbol) {
        return metadata.getBoundingBox(symbol);
    }
    
    public Point2D getAnchorStemDown(MusicSymbol symbol) {
        return metadata.getAnchor(symbol, "stemDownNW");
    }

    public Point2D getAnchorStemUp(MusicSymbol symbol) {
        return metadata.getAnchor(symbol, "stemUpSE");
    }
}
