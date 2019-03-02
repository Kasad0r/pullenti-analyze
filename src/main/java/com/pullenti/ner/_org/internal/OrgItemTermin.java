/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner._org.internal;

public class OrgItemTermin extends com.pullenti.ner.core.Termin {

    public OrgItemTermin(String s, com.pullenti.morph.MorphLang _lang, com.pullenti.ner._org.OrgProfile p1, com.pullenti.ner._org.OrgProfile p2) {
        super(s, _lang, false);
        if(_globalInstance == null) return;
        if (p1 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p1);
        if (p2 != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
            profiles.add(p2);
    }

    public Types getTyp() {
        if (isPurePrefix) 
            return Types.PREFIX;
        return m_Typ;
    }

    public Types setTyp(Types value) {
        if (value == Types.PREFIX) {
            isPurePrefix = true;
            m_Typ = Types.ORG;
        }
        else {
            m_Typ = value;
            if (m_Typ == Types.DEP || m_Typ == Types.DEPADD) {
                if (!profiles.contains(com.pullenti.ner._org.OrgProfile.UNIT)) 
                    profiles.add(com.pullenti.ner._org.OrgProfile.UNIT);
            }
        }
        return value;
    }


    private Types m_Typ = Types.UNDEFINED;

    public boolean mustBePartofName = false;

    public boolean isPurePrefix;

    public boolean canBeNormalDep;

    public boolean canHasNumber;

    public boolean canHasSingleName;

    public boolean canHasLatinName;

    public boolean mustHasCapitalName;

    public boolean isTop;

    public boolean canBeSingleGeo;

    public boolean isDoubtWord;

    public float coeff;

    public java.util.ArrayList<com.pullenti.ner._org.OrgProfile> profiles = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();

    public com.pullenti.ner._org.OrgProfile getProfile() {
        return com.pullenti.ner._org.OrgProfile.UNDEFINED;
    }

    public com.pullenti.ner._org.OrgProfile setProfile(com.pullenti.ner._org.OrgProfile value) {
        profiles.add(value);
        return value;
    }


    private void copyFrom(OrgItemTermin it) {
        com.pullenti.unisharp.Utils.addToArrayList(profiles, it.profiles);
        isPurePrefix = it.isPurePrefix;
        canBeNormalDep = it.canBeNormalDep;
        canHasNumber = it.canHasNumber;
        canHasSingleName = it.canHasSingleName;
        canHasLatinName = it.canHasLatinName;
        mustBePartofName = it.mustBePartofName;
        mustHasCapitalName = it.mustHasCapitalName;
        isTop = it.isTop;
        canBeNormalDep = it.canBeNormalDep;
        canBeSingleGeo = it.canBeSingleGeo;
        isDoubtWord = it.isDoubtWord;
        coeff = it.coeff;
    }

    public static java.util.ArrayList<OrgItemTermin> deserializeSrc(org.w3c.dom.Node xml, OrgItemTermin set) throws Exception, NumberFormatException {
        java.util.ArrayList<OrgItemTermin> res = new java.util.ArrayList<OrgItemTermin>();
        boolean isSet = com.pullenti.unisharp.Utils.stringsEq(com.pullenti.unisharp.Utils.getXmlLocalName(xml), "set");
        if (isSet) 
            res.add((set = new OrgItemTermin(null, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
        if (xml.getAttributes() == null) 
            return res;
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (!nam.startsWith("name")) 
                continue;
            com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameUa")) 
                _lang = com.pullenti.morph.MorphLang.UA;
            else if (com.pullenti.unisharp.Utils.stringsEq(nam, "nameEn")) 
                _lang = com.pullenti.morph.MorphLang.EN;
            OrgItemTermin it = null;
            for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(s)) {
                    if (it == null) {
                        res.add((it = new OrgItemTermin(s, _lang, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED)));
                        if (set != null) 
                            it.copyFrom(set);
                    }
                    else 
                        it.addVariant(s, false);
                }
            }
        }
        for (org.w3c.dom.Node a : (new com.pullenti.unisharp.XmlAttrListWrapper(xml.getAttributes())).arr) {
            String nam = com.pullenti.unisharp.Utils.getXmlLocalName(a);
            if (nam.startsWith("name")) 
                continue;
            if (nam.startsWith("abbr")) {
                com.pullenti.morph.MorphLang _lang = com.pullenti.morph.MorphLang.RU;
                if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrUa")) 
                    _lang = com.pullenti.morph.MorphLang.UA;
                else if (com.pullenti.unisharp.Utils.stringsEq(nam, "abbrEn")) 
                    _lang = com.pullenti.morph.MorphLang.EN;
                for (OrgItemTermin r : res) {
                    if (com.pullenti.morph.MorphLang.ooEq(r.lang, _lang)) 
                        r.acronym = a.getNodeValue();
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "profile")) {
                java.util.ArrayList<com.pullenti.ner._org.OrgProfile> li = new java.util.ArrayList<com.pullenti.ner._org.OrgProfile>();
                for (String s : com.pullenti.unisharp.Utils.split(a.getNodeValue(), String.valueOf(';'), false)) {
                    try {
                        com.pullenti.ner._org.OrgProfile p = (com.pullenti.ner._org.OrgProfile)com.pullenti.ner._org.OrgProfile.of(s);
                        if (p != com.pullenti.ner._org.OrgProfile.UNDEFINED) 
                            li.add(p);
                    } catch (Exception ex) {
                    }
                }
                for (OrgItemTermin r : res) {
                    r.profiles = li;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "coef")) {
                float v = com.pullenti.unisharp.Utils.parseFloat(a.getNodeValue(), null);
                for (OrgItemTermin r : res) {
                    r.coeff = v;
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "partofname")) {
                for (OrgItemTermin r : res) {
                    r.mustBePartofName = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "top")) {
                for (OrgItemTermin r : res) {
                    r.isTop = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "geo")) {
                for (OrgItemTermin r : res) {
                    r.canBeSingleGeo = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "purepref")) {
                for (OrgItemTermin r : res) {
                    r.isPurePrefix = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            if (com.pullenti.unisharp.Utils.stringsEq(nam, "number")) {
                for (OrgItemTermin r : res) {
                    r.canHasNumber = com.pullenti.unisharp.Utils.stringsEq(a.getNodeValue(), "true");
                }
                continue;
            }
            throw new Exception("Unknown Org Type Tag: " + a.getNodeName());
        }
        return res;
    }

    public static class Types implements Comparable<Types> {
    
        public static final Types UNDEFINED; // 0
    
        public static final Types ORG; // 1
    
        public static final Types PREFIX; // 2
    
        public static final Types DEP; // 3
    
        public static final Types DEPADD; // 4
    
    
        public int value() { return m_val; }
        private int m_val;
        private String m_str;
        private Types(int val, String str) { m_val = val; m_str = str; }
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
        public int compareTo(Types v) {
            if(m_val < v.m_val) return -1;
            if(m_val > v.m_val) return 1;
            return 0;
        }
        private static java.util.HashMap<Integer, Types> mapIntToEnum; 
        private static java.util.HashMap<String, Types> mapStringToEnum; 
        private static Types[] m_Values; 
        private static java.util.Collection<Integer> m_Keys; 
        public static Types of(int val) {
            if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
            Types item = new Types(val, ((Integer)val).toString());
            mapIntToEnum.put(val, item);
            mapStringToEnum.put(item.m_str.toUpperCase(), item);
            return item; 
        }
        public static Types of(String str) {
            str = str.toUpperCase(); 
            if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
            return null; 
        }
        public static boolean isDefined(Object val) {
            if(val instanceof Integer) return m_Keys.contains((Integer)val); 
            return false; 
        }
        public static Types[] getValues() {
            return m_Values; 
        }
        static {
            mapIntToEnum = new java.util.HashMap<Integer, Types>();
            mapStringToEnum = new java.util.HashMap<String, Types>();
            UNDEFINED = new Types(0, "UNDEFINED");
            mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
            mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
            ORG = new Types(1, "ORG");
            mapIntToEnum.put(ORG.value(), ORG);
            mapStringToEnum.put(ORG.m_str.toUpperCase(), ORG);
            PREFIX = new Types(2, "PREFIX");
            mapIntToEnum.put(PREFIX.value(), PREFIX);
            mapStringToEnum.put(PREFIX.m_str.toUpperCase(), PREFIX);
            DEP = new Types(3, "DEP");
            mapIntToEnum.put(DEP.value(), DEP);
            mapStringToEnum.put(DEP.m_str.toUpperCase(), DEP);
            DEPADD = new Types(4, "DEPADD");
            mapIntToEnum.put(DEPADD.value(), DEPADD);
            mapStringToEnum.put(DEPADD.m_str.toUpperCase(), DEPADD);
            java.util.Collection<Types> col = mapIntToEnum.values();
            m_Values = new Types[col.size()];
            col.toArray(m_Values);
            m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
        }
    }


    public static OrgItemTermin _new1711(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new1716(String _arg1, boolean _arg2, float _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.canHasLatinName = _arg2;
        res.coeff = _arg3;
        return res;
    }

    public static OrgItemTermin _new1720(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new1730(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, Types _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg4;
        res.setTyp(_arg5);
        res.isTop = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new1733(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        return res;
    }

    public static OrgItemTermin _new1734(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new1735(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1738(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new1739(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTermin _new1740(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1741(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        return res;
    }

    public static OrgItemTermin _new1742(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1744(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1746(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canBeSingleGeo = _arg6;
        return res;
    }

    public static OrgItemTermin _new1747(String _arg1, float _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTermin _new1749(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new1752(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1754(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canBeNormalDep = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1756(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1757(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeSingleGeo = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1758(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1760(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1767(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1768(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1769(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1770(String _arg1, float _arg2, com.pullenti.morph.MorphLang _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.lang = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1778(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new1779(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new1780(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTermin _new1783(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }

    public static OrgItemTermin _new1788(String _arg1, Types _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        res.canBeSingleGeo = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1791(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1792(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, com.pullenti.ner._org.OrgProfile _arg4, Types _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setProfile(_arg4);
        res.setTyp(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1795(String _arg1, float _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1801(String _arg1, float _arg2, String _arg3, Types _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.setProfile(_arg5);
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1802(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new1808(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1816(String _arg1, float _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.setProfile(_arg5);
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1820(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.isDoubtWord = _arg3;
        return res;
    }

    public static OrgItemTermin _new1831(String _arg1, float _arg2, Types _arg3, boolean _arg4, String _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1832(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, String _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1833(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTermin _new1843(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1844(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new1847(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.isTop = _arg4;
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new1848(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isTop = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasLatinName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTermin _new1850(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1851(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1852(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new1853(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new1854(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new1855(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.mustBePartofName = _arg4;
        return res;
    }

    public static OrgItemTermin _new1856(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.setCanonicText(_arg4);
        return res;
    }

    public static OrgItemTermin _new1858(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.mustBePartofName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1859(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.setCanonicText(_arg5);
        return res;
    }

    public static OrgItemTermin _new1865(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1866(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasNumber = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTermin _new1869(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new1871(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canBeSingleGeo = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1872(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canBeSingleGeo = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1873(String _arg1, float _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new1874(String _arg1, float _arg2, Types _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1877(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTermin _new1881(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        return res;
    }

    public static OrgItemTermin _new1886(String _arg1, Types _arg2) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        return res;
    }

    public static OrgItemTermin _new1887(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        return res;
    }

    public static OrgItemTermin _new1889(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new1894(String _arg1, Types _arg2, float _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasNumber = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new1897(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasNumber = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1898(String _arg1, String _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTermin _new1899(String _arg1, com.pullenti.morph.MorphLang _arg2, String _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTermin _new1904(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        return res;
    }

    public static OrgItemTermin _new1905(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTermin _new1909(String _arg1, Types _arg2, boolean _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canBeNormalDep = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new1910(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1914(String _arg1, Types _arg2, boolean _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        return res;
    }

    public static OrgItemTermin _new1915(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        res.isDoubtWord = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new1916(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        res.isDoubtWord = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        return res;
    }

    public static OrgItemTermin _new1923(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasNumber = _arg3;
        return res;
    }

    public static OrgItemTermin _new1924(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasNumber = _arg4;
        return res;
    }

    public static OrgItemTermin _new1925(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new1926(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, com.pullenti.ner._org.OrgProfile _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.setProfile(_arg4);
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new1931(String _arg1, Types _arg2, String _arg3, com.pullenti.ner._org.OrgProfile _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.setProfile(_arg4);
        return res;
    }

    public static OrgItemTermin _new1932(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new1936(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        return res;
    }

    public static OrgItemTermin _new1953(String _arg1, Types _arg2, String _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        return res;
    }

    public static OrgItemTermin _new1954(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new2043(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymCanBeLower = _arg4;
        res.canBeSingleGeo = _arg5;
        return res;
    }

    public static OrgItemTermin _new2044(String _arg1, Types _arg2, String _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2045(String _arg1, Types _arg2, boolean _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        return res;
    }

    public static OrgItemTermin _new2046(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new2049(String _arg1, Types _arg2, boolean _arg3) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        return res;
    }

    public static OrgItemTermin _new2051(String _arg1, Types _arg2, boolean _arg3, String _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.acronym = _arg4;
        res.acronymSmart = _arg5;
        return res;
    }

    public static OrgItemTermin _new2062(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, String _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.acronym = _arg5;
        res.acronymSmart = _arg6;
        return res;
    }

    public static OrgItemTermin _new2079(String _arg1, Types _arg2, String _arg3, String _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.acronymSmart = _arg4;
        return res;
    }

    public static OrgItemTermin _new2086(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2092(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2095(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, String _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasNumber = _arg4;
        res.acronym = _arg5;
        return res;
    }

    public static OrgItemTermin _new2096(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, String _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        res.acronym = _arg6;
        return res;
    }

    public static OrgItemTermin _new2101(String _arg1, Types _arg2, String _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.acronym = _arg3;
        res.canHasLatinName = _arg4;
        res.canHasNumber = _arg5;
        return res;
    }

    public static OrgItemTermin _new2112(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new2113(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2114(String _arg1, Types _arg2, com.pullenti.ner._org.OrgProfile _arg3, boolean _arg4, float _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.setProfile(_arg3);
        res.canHasLatinName = _arg4;
        res.coeff = _arg5;
        return res;
    }

    public static OrgItemTermin _new2115(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2116(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2117(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2118(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasSingleName = _arg5;
        res.canHasLatinName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2121(String _arg1, float _arg2, Types _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canBeNormalDep = _arg4;
        return res;
    }

    public static OrgItemTermin _new2122(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canBeNormalDep = _arg5;
        return res;
    }

    public static OrgItemTermin _new2123(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        return res;
    }

    public static OrgItemTermin _new2125(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new2126(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, com.pullenti.ner._org.OrgProfile _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.isDoubtWord = _arg5;
        res.setProfile(_arg6);
        return res;
    }

    public static OrgItemTermin _new2127(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, com.pullenti.ner._org.OrgProfile _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasSingleName = _arg3;
        res.canHasLatinName = _arg4;
        res.setProfile(_arg5);
        return res;
    }

    public static OrgItemTermin _new2128(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasSingleName = _arg4;
        res.canHasLatinName = _arg5;
        res.isDoubtWord = _arg6;
        return res;
    }

    public static OrgItemTermin _new2129(String _arg1, Types _arg2, float _arg3, boolean _arg4) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.coeff = _arg3;
        res.canHasSingleName = _arg4;
        return res;
    }

    public static OrgItemTermin _new2130(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, float _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.coeff = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2140(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        return res;
    }

    public static OrgItemTermin _new2141(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2142(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canBeSingleGeo = _arg7;
        return res;
    }

    public static OrgItemTermin _new2143(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, String _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.acronym = _arg5;
        res.canHasLatinName = _arg6;
        res.canHasSingleName = _arg7;
        res.canBeSingleGeo = _arg8;
        return res;
    }

    public static OrgItemTermin _new2150(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2151(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2152(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        return res;
    }

    public static OrgItemTermin _new2157(String _arg1, com.pullenti.morph.MorphLang _arg2, com.pullenti.ner._org.OrgProfile _arg3, Types _arg4, float _arg5, boolean _arg6, String _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, _arg3, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg4);
        res.coeff = _arg5;
        res.canHasLatinName = _arg6;
        res.acronym = _arg7;
        return res;
    }

    public static OrgItemTermin _new2158(String _arg1, Types _arg2, boolean _arg3, boolean _arg4, boolean _arg5, boolean _arg6) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.setTyp(_arg2);
        res.canHasLatinName = _arg3;
        res.canHasSingleName = _arg4;
        res.mustHasCapitalName = _arg5;
        res.canHasNumber = _arg6;
        return res;
    }

    public static OrgItemTermin _new2159(String _arg1, com.pullenti.morph.MorphLang _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2160(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, com.pullenti.ner._org.OrgProfile _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.setProfile(_arg7);
        return res;
    }

    public static OrgItemTermin _new2161(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, com.pullenti.ner._org.OrgProfile _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.setProfile(_arg8);
        return res;
    }

    public static OrgItemTermin _new2165(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.lang = _arg2;
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2166(String _arg1, float _arg2, Types _arg3, String _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.acronym = _arg4;
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        return res;
    }

    public static OrgItemTermin _new2168(String _arg1, float _arg2, Types _arg3, boolean _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.setTyp(_arg3);
        res.canHasLatinName = _arg4;
        res.canHasSingleName = _arg5;
        res.mustHasCapitalName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2169(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7, boolean _arg8) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.mustHasCapitalName = _arg7;
        res.canHasNumber = _arg8;
        return res;
    }

    public static OrgItemTermin _new2175(String _arg1, float _arg2, String _arg3, Types _arg4, boolean _arg5, boolean _arg6, boolean _arg7) {
        OrgItemTermin res = new OrgItemTermin(_arg1, null, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg2;
        res.acronym = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        res.canHasSingleName = _arg6;
        res.canHasNumber = _arg7;
        return res;
    }

    public static OrgItemTermin _new2180(String _arg1, com.pullenti.morph.MorphLang _arg2, float _arg3, Types _arg4, boolean _arg5) {
        OrgItemTermin res = new OrgItemTermin(_arg1, _arg2, com.pullenti.ner._org.OrgProfile.UNDEFINED, com.pullenti.ner._org.OrgProfile.UNDEFINED);
        res.coeff = _arg3;
        res.setTyp(_arg4);
        res.canHasLatinName = _arg5;
        return res;
    }
    public OrgItemTermin() {
        super();
    }
    public static OrgItemTermin _globalInstance;
    
    static {
        _globalInstance = new OrgItemTermin(); 
    }
}
