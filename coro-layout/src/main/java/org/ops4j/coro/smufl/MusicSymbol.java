package org.ops4j.coro.smufl;

public enum MusicSymbol {

    NOTEHEAD_BLACK("noteheadBlack", '\uE0A4'), 
    NOTEHEAD_HALF("noteheadHalf", '\uE0A3'), 
    NOTEHEAD_WHOLE("noteheadWhole", '\uE0A2'),

    ACCIDENTAL_FLAT("accidentalFlat", '\uE260'), 
    ACCIDENTAL_NATURAL("accidentalNatural", '\uE261'), 
    ACCIDENTAL_SHARP("accidentalSharp", '\uE262'),

    C_CLEF("gClef", '\uE05C'), 
    F_CLEF("gClef", '\uE062'), 
    G_CLEF("gClef", '\uE050'),

    TIME_SIG_0("timeSig0", '\uE080'), 
    TIME_SIG_1("timeSig1", '\uE081'), 
    TIME_SIG_2("timeSig2", '\uE082'), 
    TIME_SIG_3("timeSig3", '\uE083'), 
    TIME_SIG_4("timeSig4", '\uE084'), 
    TIME_SIG_5("timeSig5", '\uE085'), 
    TIME_SIG_6("timeSig6", '\uE086'), 
    TIME_SIG_7("timeSig7", '\uE087'), 
    TIME_SIG_8("timeSig8", '\uE088'), 
    TIME_SIG_9("timeSig9", '\uE089'),
    
    FLAG_8TH_UP("flag8thUp", '\ue240'),
    FLAG_8TH_DOWN("flag8thDown", '\ue241'),
    FLAG_16TH_UP("flag16thUp", '\ue242'),
    FLAG_16TH_DOWN("flag16thDown", '\ue243'),
    FLAG_32ND_UP("flag32ndUp", '\ue244'),
    FLAG_32ND_DOWN("flag32ndDown", '\ue245'),
    FLAG_64TH_UP("flag64thUp", '\ue246'),
    FLAG_64TH_DOWN("flag64thDown", '\ue246'),
    
    AUGMENTATION_DOT("augmentationDot", '\ue1e7')
    ;

    private String name;
    private char codePoint;

    private MusicSymbol(String name, char codePoint) {
        this.name = name;
        this.codePoint = codePoint;
    }

    public String getName() {
        return name;
    }

    public char getCodePoint() {
        return codePoint;
    }

    public String asString() {
        return Character.toString(codePoint);
    }

    public static MusicSymbol getTimeSignature(int number) {
        return values()[MusicSymbol.TIME_SIG_0.ordinal() + number];
    }
}
