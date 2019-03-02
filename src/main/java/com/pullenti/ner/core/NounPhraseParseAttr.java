/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Параметры выделения
 */
public class NounPhraseParseAttr implements Comparable<NounPhraseParseAttr> {

    public static final NounPhraseParseAttr NO; // 0

    public static final NounPhraseParseAttr PARSEPRONOUNS; // 1

    public static final NounPhraseParseAttr PARSEPREPOSITION; // 2

    public static final NounPhraseParseAttr IGNOREADJBEST; // 4

    public static final NounPhraseParseAttr IGNOREPARTICIPLES; // 8

    public static final NounPhraseParseAttr REFERENTCANBENOUN; // 0x10

    public static final NounPhraseParseAttr CANNOTHASCOMMAAND; // 0x20

    public static final NounPhraseParseAttr ADJECTIVECANBELAST; // 0x40

    public static final NounPhraseParseAttr PARSEADVERBS; // 0x80

    public static final NounPhraseParseAttr PARSEVERBS; // 0x100

    public static final NounPhraseParseAttr PARSENUMERICASADJECTIVE; // 0x200

    public static final NounPhraseParseAttr MULTILINES; // 0x400

    public static final NounPhraseParseAttr IGNOREBRACKETS; // 0x800


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private NounPhraseParseAttr(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(NounPhraseParseAttr v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, NounPhraseParseAttr> mapIntToEnum; 
    private static java.util.HashMap<String, NounPhraseParseAttr> mapStringToEnum; 
    private static NounPhraseParseAttr[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static NounPhraseParseAttr of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        NounPhraseParseAttr item = new NounPhraseParseAttr(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static NounPhraseParseAttr of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static NounPhraseParseAttr[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, NounPhraseParseAttr>();
        mapStringToEnum = new java.util.HashMap<String, NounPhraseParseAttr>();
        NO = new NounPhraseParseAttr(0, "NO");
        mapIntToEnum.put(NO.value(), NO);
        mapStringToEnum.put(NO.m_str.toUpperCase(), NO);
        PARSEPRONOUNS = new NounPhraseParseAttr(1, "PARSEPRONOUNS");
        mapIntToEnum.put(PARSEPRONOUNS.value(), PARSEPRONOUNS);
        mapStringToEnum.put(PARSEPRONOUNS.m_str.toUpperCase(), PARSEPRONOUNS);
        PARSEPREPOSITION = new NounPhraseParseAttr(2, "PARSEPREPOSITION");
        mapIntToEnum.put(PARSEPREPOSITION.value(), PARSEPREPOSITION);
        mapStringToEnum.put(PARSEPREPOSITION.m_str.toUpperCase(), PARSEPREPOSITION);
        IGNOREADJBEST = new NounPhraseParseAttr(4, "IGNOREADJBEST");
        mapIntToEnum.put(IGNOREADJBEST.value(), IGNOREADJBEST);
        mapStringToEnum.put(IGNOREADJBEST.m_str.toUpperCase(), IGNOREADJBEST);
        IGNOREPARTICIPLES = new NounPhraseParseAttr(8, "IGNOREPARTICIPLES");
        mapIntToEnum.put(IGNOREPARTICIPLES.value(), IGNOREPARTICIPLES);
        mapStringToEnum.put(IGNOREPARTICIPLES.m_str.toUpperCase(), IGNOREPARTICIPLES);
        REFERENTCANBENOUN = new NounPhraseParseAttr(0x10, "REFERENTCANBENOUN");
        mapIntToEnum.put(REFERENTCANBENOUN.value(), REFERENTCANBENOUN);
        mapStringToEnum.put(REFERENTCANBENOUN.m_str.toUpperCase(), REFERENTCANBENOUN);
        CANNOTHASCOMMAAND = new NounPhraseParseAttr(0x20, "CANNOTHASCOMMAAND");
        mapIntToEnum.put(CANNOTHASCOMMAAND.value(), CANNOTHASCOMMAAND);
        mapStringToEnum.put(CANNOTHASCOMMAAND.m_str.toUpperCase(), CANNOTHASCOMMAAND);
        ADJECTIVECANBELAST = new NounPhraseParseAttr(0x40, "ADJECTIVECANBELAST");
        mapIntToEnum.put(ADJECTIVECANBELAST.value(), ADJECTIVECANBELAST);
        mapStringToEnum.put(ADJECTIVECANBELAST.m_str.toUpperCase(), ADJECTIVECANBELAST);
        PARSEADVERBS = new NounPhraseParseAttr(0x80, "PARSEADVERBS");
        mapIntToEnum.put(PARSEADVERBS.value(), PARSEADVERBS);
        mapStringToEnum.put(PARSEADVERBS.m_str.toUpperCase(), PARSEADVERBS);
        PARSEVERBS = new NounPhraseParseAttr(0x100, "PARSEVERBS");
        mapIntToEnum.put(PARSEVERBS.value(), PARSEVERBS);
        mapStringToEnum.put(PARSEVERBS.m_str.toUpperCase(), PARSEVERBS);
        PARSENUMERICASADJECTIVE = new NounPhraseParseAttr(0x200, "PARSENUMERICASADJECTIVE");
        mapIntToEnum.put(PARSENUMERICASADJECTIVE.value(), PARSENUMERICASADJECTIVE);
        mapStringToEnum.put(PARSENUMERICASADJECTIVE.m_str.toUpperCase(), PARSENUMERICASADJECTIVE);
        MULTILINES = new NounPhraseParseAttr(0x400, "MULTILINES");
        mapIntToEnum.put(MULTILINES.value(), MULTILINES);
        mapStringToEnum.put(MULTILINES.m_str.toUpperCase(), MULTILINES);
        IGNOREBRACKETS = new NounPhraseParseAttr(0x800, "IGNOREBRACKETS");
        mapIntToEnum.put(IGNOREBRACKETS.value(), IGNOREBRACKETS);
        mapStringToEnum.put(IGNOREBRACKETS.m_str.toUpperCase(), IGNOREBRACKETS);
        java.util.Collection<NounPhraseParseAttr> col = mapIntToEnum.values();
        m_Values = new NounPhraseParseAttr[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
