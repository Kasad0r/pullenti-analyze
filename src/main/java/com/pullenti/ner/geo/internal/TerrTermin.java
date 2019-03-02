/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo.internal;

public class TerrTermin extends com.pullenti.ner.core.Termin {

    public TerrTermin(String source, com.pullenti.morph.MorphLang _lang) {
        super(null, _lang, false);
        this.initByNormalText(source, _lang);
    }

    public boolean isState;

    public boolean isRegion;

    public boolean isAdjective;

    public boolean isAlwaysPrefix;

    public boolean isDoubt;

    public boolean isMoscowRegion;

    public boolean isStrong;

    public boolean isSpecificPrefix;

    public boolean isSovet;

    public static TerrTermin _new1163(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        return res;
    }

    public static TerrTermin _new1164(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        return res;
    }

    public static TerrTermin _new1165(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isDoubt = _arg3;
        return res;
    }

    public static TerrTermin _new1166(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isDoubt = _arg4;
        return res;
    }

    public static TerrTermin _new1169(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isState = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1170(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isState = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1171(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        return res;
    }

    public static TerrTermin _new1174(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        return res;
    }

    public static TerrTermin _new1175(String _arg1, boolean _arg2, String _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.acronym = _arg3;
        return res;
    }

    public static TerrTermin _new1176(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, String _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static TerrTermin _new1181(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAlwaysPrefix = _arg3;
        return res;
    }

    public static TerrTermin _new1183(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1192(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isStrong = _arg3;
        return res;
    }

    public static TerrTermin _new1195(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isStrong = _arg4;
        return res;
    }

    public static TerrTermin _new1198(String _arg1, String _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.setCanonicText(_arg2);
        res.isSovet = _arg3;
        return res;
    }

    public static TerrTermin _new1201(String _arg1, boolean _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isAdjective = _arg3;
        return res;
    }

    public static TerrTermin _new1202(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isAdjective = _arg4;
        return res;
    }

    public static TerrTermin _new1203(String _arg1, boolean _arg2, boolean _arg3, boolean _arg4) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isRegion = _arg2;
        res.isSpecificPrefix = _arg3;
        res.isAlwaysPrefix = _arg4;
        return res;
    }

    public static TerrTermin _new1204(String _arg1, com.pullenti.morph.MorphLang _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        TerrTermin res = new TerrTermin(_arg1, _arg2);
        res.isRegion = _arg3;
        res.isSpecificPrefix = _arg4;
        res.isAlwaysPrefix = _arg5;
        return res;
    }

    public static TerrTermin _new1205(String _arg1, String _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        return res;
    }

    public static TerrTermin _new1206(String _arg1, String _arg2, boolean _arg3) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.acronym = _arg2;
        res.isRegion = _arg3;
        return res;
    }

    public static TerrTermin _new1208(String _arg1, boolean _arg2) {
        TerrTermin res = new TerrTermin(_arg1, null);
        res.isMoscowRegion = _arg2;
        return res;
    }
    public TerrTermin() {
        super();
    }
}
