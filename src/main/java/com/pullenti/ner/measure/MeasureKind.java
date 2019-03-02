/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.measure;

/**
 * Что измеряется этой величиной
 */
public class MeasureKind implements Comparable<MeasureKind> {

    public static final MeasureKind UNDEFINED; // 0

    public static final MeasureKind TIME; // 1

    public static final MeasureKind LENGTH; // 2

    public static final MeasureKind AREA; // 3

    public static final MeasureKind VOLUME; // 4

    public static final MeasureKind WEIGHT; // 5

    public static final MeasureKind SPEED; // 6


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private MeasureKind(int val, String str) { m_val = val; m_str = str; }
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
    public int compareTo(MeasureKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, MeasureKind> mapIntToEnum; 
    private static java.util.HashMap<String, MeasureKind> mapStringToEnum; 
    private static MeasureKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static MeasureKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        MeasureKind item = new MeasureKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static MeasureKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static MeasureKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, MeasureKind>();
        mapStringToEnum = new java.util.HashMap<String, MeasureKind>();
        UNDEFINED = new MeasureKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        TIME = new MeasureKind(1, "TIME");
        mapIntToEnum.put(TIME.value(), TIME);
        mapStringToEnum.put(TIME.m_str.toUpperCase(), TIME);
        LENGTH = new MeasureKind(2, "LENGTH");
        mapIntToEnum.put(LENGTH.value(), LENGTH);
        mapStringToEnum.put(LENGTH.m_str.toUpperCase(), LENGTH);
        AREA = new MeasureKind(3, "AREA");
        mapIntToEnum.put(AREA.value(), AREA);
        mapStringToEnum.put(AREA.m_str.toUpperCase(), AREA);
        VOLUME = new MeasureKind(4, "VOLUME");
        mapIntToEnum.put(VOLUME.value(), VOLUME);
        mapStringToEnum.put(VOLUME.m_str.toUpperCase(), VOLUME);
        WEIGHT = new MeasureKind(5, "WEIGHT");
        mapIntToEnum.put(WEIGHT.value(), WEIGHT);
        mapStringToEnum.put(WEIGHT.m_str.toUpperCase(), WEIGHT);
        SPEED = new MeasureKind(6, "SPEED");
        mapIntToEnum.put(SPEED.value(), SPEED);
        mapStringToEnum.put(SPEED.m_str.toUpperCase(), SPEED);
        java.util.Collection<MeasureKind> col = mapIntToEnum.values();
        m_Values = new MeasureKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
