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
package org.ops4j.coro.smufl;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import javax.json.JsonArray;
import javax.json.JsonObject;


public class MetadataImpl implements Metadata {
    
    
    private JsonObject metadata;

    public MetadataImpl(JsonObject metadata) {
        this.metadata = metadata;
    }

    @Override
    public double getEngravingDefault(String property) {
        JsonObject defaults = metadata.getJsonObject("engravingDefaults");
        return defaults.getJsonNumber(property).doubleValue();
    }

    @Override
    public BoundingBox getBoundingBox(MusicSymbol symbol) {
        JsonObject boxes = metadata.getJsonObject("glyphBBoxes");
        JsonObject box = boxes.getJsonObject(symbol.getName());
        JsonArray ne = box.getJsonArray("bBoxNE");
        double nex = ne.getJsonNumber(0).doubleValue();
        double ney = ne.getJsonNumber(1).doubleValue();
        JsonArray sw = box.getJsonArray("bBoxSW");
        double swx = sw.getJsonNumber(0).doubleValue();
        double swy = sw.getJsonNumber(1).doubleValue();
        return new BoundingBox(swx, swy, nex-swx, ney-swy);
    }

    @Override
    public Point2D getAnchor(MusicSymbol symbol, String property) {
        JsonObject anchorsMap = metadata.getJsonObject("glyphsWithAnchors");
        JsonObject anchors = anchorsMap.getJsonObject(symbol.getName());
        JsonArray anchor = anchors.getJsonArray(property);
        return new Point2D(anchor.getJsonNumber(0).doubleValue(), anchor.getJsonNumber(1).doubleValue());
    }

}
