/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person.internal.properties;

public class Resources {
    static String[] m_Names = new String[] { "attr_en.dat", "attr_ru.dat", "attr_ua.dat", "personproperty.png", "ShortNames.txt", "chinasurnames.txt", "identity.png", "mail.png", "man.png", "boss.png", "king.png", "kin.png", "person.png", "general.png", "women.png", "militaryrank.png", "nationality.png" }; 
    public static String[] getNames() { return m_Names; } 
    public static com.pullenti.unisharp.Stream getStream(String name) throws java.io.IOException {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) throw new Exception("Can't find resource file " + m_Names[i]);
                        byte[] dat = com.pullenti.unisharp.Utils.readAllBytes(istr);
                        istr.close();
                        return new com.pullenti.unisharp.MemoryStream(dat);
                    }
                }
            }
        } catch (Exception ee) {
               System.out.println(ee);
        }
        return null;
    }
    public static Object getResourceInfo(String name) {
        try {
            for (int k = 0; k < 2; k++) {
                for (int i = 0; i < m_Names.length; i++) {
                    if ((k == 0 && name.equalsIgnoreCase(m_Names[i]))
                            || (k == 1 && name.endsWith(m_Names[i]))) {
                        java.io.InputStream istr = Resources.class.getResourceAsStream(m_Names[i]);
                        if (istr == null) return null;
                        istr.close();
                        return m_Names[i];
                    }
                }
            }
        } catch (Exception ee) {
        }
        return null;
    }
}
