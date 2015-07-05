package org.ops4j.coro.smufl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import org.junit.BeforeClass;
import org.junit.Test;


public class MetadataReaderTest {
    
    private static Metadata metadata;

    @BeforeClass
    public static void beforeClass() throws IOException {
        MetadataReader reader = new MetadataReader();
        try (InputStream is = MetadataReaderTest.class.getResourceAsStream("/fonts/bravura/metadata.json")) {
            metadata = reader.readMetadata(is);
        }        
    }

    @Test
    public void shouldGetBeamThickness()  {
        assertThat(metadata.getEngravingDefault("beamThickness"), is(0.5));
    }

    @Test
    public void shouldGetNoteheadHalfBoundingBox()  {
        BoundingBox bbox = metadata.getBoundingBox(MusicSymbol.NOTEHEAD_HALF);
        assertThat(bbox, is(notNullValue()));
        assertThat(bbox.getMinX(), is(0.0));
        assertThat(bbox.getMinY(), is(-0.5));
        assertThat(bbox.getMaxX(), is(1.18));
        assertThat(bbox.getMaxY(), is(0.5));
    }
    
    @Test
    public void shouldGetNoteheadHalfAnchor() {
        Point2D anchor = metadata.getAnchor(MusicSymbol.NOTEHEAD_HALF, "stemDownNW");
        assertThat(anchor.getX(), is(0.0));
        assertThat(anchor.getY(), is(-0.168));
    }
}
