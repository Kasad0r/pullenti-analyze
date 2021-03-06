/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.decree.internal;

/**
 * Некоторые полезные функции для НПА
 */
public class DecreeHelper {

    public static java.time.LocalDateTime parseDateTime(String str) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return null;
        try {
            String[] prts = com.pullenti.unisharp.Utils.split(str, String.valueOf('.'), false);
            int y;
            com.pullenti.unisharp.Outargwrapper<Integer> wrapy804 = new com.pullenti.unisharp.Outargwrapper<Integer>();
            boolean inoutres805 = com.pullenti.unisharp.Utils.parseInteger(prts[0], 0, null, wrapy804);
            y = (wrapy804.value != null ? wrapy804.value : 0);
            if (!inoutres805) 
                return null;
            int mon = 0;
            int day = 0;
            if (prts.length > 1) {
                com.pullenti.unisharp.Outargwrapper<Integer> wrapmon802 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                boolean inoutres803 = com.pullenti.unisharp.Utils.parseInteger(prts[1], 0, null, wrapmon802);
                mon = (wrapmon802.value != null ? wrapmon802.value : 0);
                if (inoutres803) {
                    if (prts.length > 2) {
                        com.pullenti.unisharp.Outargwrapper<Integer> wrapday801 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                        com.pullenti.unisharp.Utils.parseInteger(prts[2], 0, null, wrapday801);
                        day = (wrapday801.value != null ? wrapday801.value : 0);
                    }
                }
            }
            if (mon <= 0) 
                mon = 1;
            if (day <= 0) 
                day = 1;
            if (day > com.pullenti.unisharp.Utils.daysInMonth(y, mon)) 
                day = com.pullenti.unisharp.Utils.daysInMonth(y, mon);
            return java.time.LocalDateTime.of(y, mon, day, 0, 0, 0);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Это для оформления ссылок по некоторым стандартам (когда гиперссылкой нужно выделить не всю сущность, 
     *  а лишь некоторую её часть)
     * @param t 
     * @return 
     */
    public static CanonicDecreeRefUri tryCreateCanonicDecreeRefUri(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.ReferentToken))) 
            return null;
        com.pullenti.ner.decree.DecreeReferent dr = (com.pullenti.ner.decree.DecreeReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreeReferent.class);
        CanonicDecreeRefUri res;
        if (dr != null) {
            if (dr.getKind() == com.pullenti.ner.decree.DecreeKind.PUBLISHER) 
                return null;
            res = CanonicDecreeRefUri._new806(t.kit.getSofa().getText(), dr, t.beginChar, t.endChar);
            if ((t.getPrevious() != null && t.getPrevious().isChar('(') && t.getNext() != null) && t.getNext().isChar(')')) 
                return res;
            if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class))).miscAttrs != 0) 
                return res;
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if (rt.getBeginToken().isChar('(') && rt.getEndToken().isChar(')')) {
                res = CanonicDecreeRefUri._new806(t.kit.getSofa().getText(), dr, rt.getBeginToken().getNext().beginChar, rt.getEndToken().getPrevious().endChar);
                return res;
            }
            java.util.ArrayList<DecreeToken> nextDecreeItems = null;
            if ((t.getNext() != null && t.getNext().isCommaAnd() && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreeReferent)) {
                nextDecreeItems = DecreeToken.tryAttachList((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.ReferentToken.class))).getBeginToken(), null, 10, false);
                if (nextDecreeItems != null && nextDecreeItems.size() > 1) {
                    for (int i = 0; i < (nextDecreeItems.size() - 1); i++) {
                        if (nextDecreeItems.get(i).isNewlineAfter()) {
                            for(int jjj = i + 1 + nextDecreeItems.size() - i - 1 - 1, mmm = i + 1; jjj >= mmm; jjj--) nextDecreeItems.remove(jjj);
                            break;
                        }
                    }
                }
            }
            boolean wasTyp = false;
            boolean wasNum = false;
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
                if (tt.beginChar == t.beginChar && tt.isChar('(') && tt.getNext() != null) 
                    res.beginChar = tt.getNext().beginChar;
                if (tt.isChar('(') && tt.getNext() != null && tt.getNext().isValue("ДАЛЕЕ", null)) {
                    if (res.endChar >= tt.beginChar) 
                        res.endChar = tt.getPrevious().endChar;
                    break;
                }
                if (tt.endChar == t.endChar && tt.isChar(')')) {
                    res.endChar = tt.getPrevious().endChar;
                    for (com.pullenti.ner.Token tt1 = tt.getPrevious(); tt1 != null && tt1.beginChar >= res.beginChar; tt1 = tt1.getPrevious()) {
                        if (tt1.isChar('(') && tt1.getPrevious() != null) {
                            if (res.beginChar < tt1.getPrevious().beginChar) 
                                res.endChar = tt1.getPrevious().endChar;
                        }
                    }
                }
                java.util.ArrayList<DecreeToken> li = DecreeToken.tryAttachList(tt, null, 10, false);
                if (li != null && li.size() > 0) {
                    for (int ii = 0; ii < (li.size() - 1); ii++) {
                        if (li.get(ii).typ == DecreeToken.ItemType.TYP && li.get(ii + 1).typ == DecreeToken.ItemType.TERR) 
                            res.typeWithGeo = com.pullenti.ner.core.MiscHelper.getTextValue(li.get(ii).getBeginToken(), li.get(ii + 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE);
                    }
                    if ((nextDecreeItems != null && nextDecreeItems.size() > 1 && (nextDecreeItems.size() < li.size())) && nextDecreeItems.get(0).typ != DecreeToken.ItemType.TYP) {
                        int d = li.size() - nextDecreeItems.size();
                        int j;
                        for (j = 0; j < nextDecreeItems.size(); j++) {
                            if (nextDecreeItems.get(j).typ != li.get(d + j).typ) 
                                break;
                        }
                        if (j >= nextDecreeItems.size()) {
                            for(int jjj = 0 + d - 1, mmm = 0; jjj >= mmm; jjj--) li.remove(jjj);
                            res.beginChar = li.get(0).beginChar;
                        }
                    }
                    else if ((nextDecreeItems != null && nextDecreeItems.size() == 1 && nextDecreeItems.get(0).typ == DecreeToken.ItemType.NAME) && li.size() == 2 && li.get(1).typ == DecreeToken.ItemType.NAME) {
                        res.beginChar = li.get(1).beginChar;
                        res.endChar = li.get(1).endChar;
                        break;
                    }
                    else if ((nextDecreeItems != null && nextDecreeItems.size() == 1 && nextDecreeItems.get(0).typ == DecreeToken.ItemType.NUMBER) && li.get(li.size() - 1).typ == DecreeToken.ItemType.NUMBER) {
                        res.beginChar = li.get(li.size() - 1).beginChar;
                        res.endChar = li.get(li.size() - 1).endChar;
                    }
                    for (int i = 0; i < li.size(); i++) {
                        DecreeToken l = li.get(i);
                        if (l.beginChar > t.endChar) {
                            for(int jjj = i + li.size() - i - 1, mmm = i; jjj >= mmm; jjj--) li.remove(jjj);
                            break;
                        }
                        if (l.typ == DecreeToken.ItemType.NAME) {
                            if (!wasNum) {
                                if (dr.getKind() == com.pullenti.ner.decree.DecreeKind.CONTRACT) 
                                    continue;
                                if (((i + 1) < li.size()) && ((li.get(i + 1).typ == DecreeToken.ItemType.DATE || li.get(i + 1).typ == DecreeToken.ItemType.NUMBER))) 
                                    continue;
                            }
                            int ee = l.getBeginToken().getPrevious().endChar;
                            if (ee > res.beginChar && (ee < res.endChar)) 
                                res.endChar = ee;
                            break;
                        }
                        if (l.typ == DecreeToken.ItemType.NUMBER) 
                            wasNum = true;
                        if (i == 0) {
                            if (l.typ == DecreeToken.ItemType.TYP) 
                                wasTyp = true;
                            else if (l.typ == DecreeToken.ItemType.OWNER || l.typ == DecreeToken.ItemType.ORG) {
                                if (((i + 1) < li.size()) && ((li.get(1).typ == DecreeToken.ItemType.DATE || li.get(1).typ == DecreeToken.ItemType.NUMBER))) 
                                    wasTyp = true;
                            }
                            if (wasTyp) {
                                com.pullenti.ner.Token tt0 = l.getBeginToken().getPrevious();
                                if (tt0 != null && tt0.isChar('.')) 
                                    tt0 = tt0.getPrevious();
                                if (tt0 != null && ((tt0.isValue("УТВЕРЖДЕННЫЙ", null) || tt0.isValue("УТВЕРДИТЬ", null) || tt0.isValue("УТВ", null)))) {
                                    if (l.beginChar > res.beginChar) {
                                        res.beginChar = l.beginChar;
                                        if (res.endChar < res.beginChar) 
                                            res.endChar = t.endChar;
                                        res.isAdopted = true;
                                    }
                                }
                            }
                        }
                    }
                    if (li.size() > 0) {
                        tt = li.get(li.size() - 1).getEndToken();
                        if (tt.isChar(')')) 
                            tt = tt.getPrevious();
                        continue;
                    }
                }
                if (wasTyp) {
                    DecreeToken na = DecreeToken.tryAttachName(tt, dr.getTyp0(), true, false);
                    if (na != null && tt.beginChar > t.beginChar) {
                        com.pullenti.ner.Token tt1 = na.getEndToken().getNext();
                        if (tt1 != null && tt1.isCharOf(",()")) 
                            tt1 = tt1.getNext();
                        if (tt1 != null && (tt1.endChar < t.endChar)) {
                            if (tt1.isValue("УТВЕРЖДЕННЫЙ", null) || tt1.isValue("УТВЕРДИТЬ", null) || tt1.isValue("УТВ", null)) {
                                tt = tt1;
                                continue;
                            }
                        }
                        if (tt.getPrevious() != null && tt.getPrevious().isChar(':') && na.endChar <= res.endChar) {
                            res.beginChar = tt.beginChar;
                            break;
                        }
                        if (tt.getPrevious().endChar > res.beginChar) {
                            res.endChar = tt.getPrevious().endChar;
                            break;
                        }
                    }
                }
            }
            return res;
        }
        com.pullenti.ner.decree.DecreePartReferent dpr = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
        if (dpr == null) 
            return null;
        if ((t.getPrevious() != null && t.getPrevious().isHiphen() && (t.getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken)) && (t.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) {
            if (com.pullenti.ner.decree.DecreePartReferent.createRangeReferent((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getPrevious().getPrevious().getReferent(), com.pullenti.ner.decree.DecreePartReferent.class), dpr) != null) 
                return null;
        }
        com.pullenti.ner.Token t1 = t;
        boolean hasDiap = false;
        com.pullenti.ner.ReferentToken diapRef = null;
        if ((t.getNext() != null && t.getNext().isHiphen() && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) {
            com.pullenti.ner.decree.DecreePartReferent diap = com.pullenti.ner.decree.DecreePartReferent.createRangeReferent((com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(dpr, com.pullenti.ner.decree.DecreePartReferent.class), (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getNext().getNext().getReferent(), com.pullenti.ner.decree.DecreePartReferent.class));
            if (diap != null) {
                dpr = diap;
                hasDiap = true;
                t1 = t.getNext().getNext();
                diapRef = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.ReferentToken.class);
            }
        }
        res = CanonicDecreeRefUri._new808(t.kit.getSofa().getText(), dpr, t.beginChar, t1.endChar, hasDiap);
        if ((t.getPrevious() != null && t.getPrevious().isChar('(') && t1.getNext() != null) && t1.getNext().isChar(')')) 
            return res;
        for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
            if (tt.getReferent() instanceof com.pullenti.ner.decree.DecreeReferent) {
                if (tt.beginChar > t.beginChar) {
                    res.endChar = tt.getPrevious().endChar;
                    if (tt.getPrevious().getMorph()._getClass().isPreposition() && tt.getPrevious().getPrevious() != null) 
                        res.endChar = tt.getPrevious().getPrevious().endChar;
                }
                else if (tt.endChar < t.endChar) 
                    res.beginChar = tt.beginChar;
                break;
            }
        }
        boolean hasSameBefore = _hasSameDecree(t, dpr, true);
        boolean hasSameAfter = _hasSameDecree(t, dpr, false);
        PartToken.ItemType ptmin = PartToken.ItemType.PREFIX;
        PartToken.ItemType ptmin2 = PartToken.ItemType.PREFIX;
        int max = 0;
        int max2 = 0;
        for (com.pullenti.ner.Slot s : dpr.getSlots()) {
            PartToken.ItemType pt = PartToken._getTypeByAttrName(s.getTypeName());
            if (pt == PartToken.ItemType.PREFIX) 
                continue;
            int co = PartToken._getRank(pt);
            if (co < 1) {
                if (pt == PartToken.ItemType.PART && dpr.findSlot(com.pullenti.ner.decree.DecreePartReferent.ATTR_CLAUSE, null, true) != null) 
                    co = PartToken._getRank(PartToken.ItemType.PARAGRAPH);
                else 
                    continue;
            }
            if (co > max) {
                max2 = max;
                ptmin2 = ptmin;
                max = co;
                ptmin = pt;
            }
            else if (co > max2) {
                max2 = co;
                ptmin2 = pt;
            }
        }
        if (ptmin != PartToken.ItemType.PREFIX) {
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= res.endChar; tt = tt.getNext()) {
                if (tt.beginChar >= res.beginChar) {
                    PartToken pt = PartToken.tryAttach(tt, null, false, false);
                    if (pt != null && pt.typ == ptmin) {
                        res.beginChar = pt.beginChar;
                        res.endChar = pt.endChar;
                        if (pt.typ == PartToken.ItemType.APPENDIX && pt.getEndToken().isValue("К", null) && pt.getBeginToken() != pt.getEndToken()) 
                            res.endChar = pt.getEndToken().getPrevious().endChar;
                        if (pt.endChar == t.endChar) {
                            if ((t.getNext() != null && t.getNext().isCommaAnd() && (t.getNext().getNext() instanceof com.pullenti.ner.ReferentToken)) && (t.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent)) {
                                com.pullenti.ner.Token tt1 = (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getNext().getNext(), com.pullenti.ner.ReferentToken.class))).getBeginToken();
                                boolean ok = true;
                                if (tt1.chars.isLetter()) 
                                    ok = false;
                                if (ok) {
                                    for (PartToken.PartValue v : pt.values) {
                                        res.beginChar = v.beginChar;
                                        res.endChar = v.endChar;
                                        break;
                                    }
                                }
                            }
                        }
                        if (!hasDiap) 
                            return res;
                        break;
                    }
                }
            }
            if (hasDiap && diapRef != null) {
                for (com.pullenti.ner.Token tt = diapRef.getBeginToken(); tt != null && tt.endChar <= diapRef.endChar; tt = tt.getNext()) {
                    if (tt.isChar(',')) 
                        break;
                    if (tt != diapRef.getBeginToken() && tt.isWhitespaceBefore()) 
                        break;
                    res.endChar = tt.endChar;
                }
                return res;
            }
        }
        if (((hasSameBefore || hasSameAfter)) && ptmin != PartToken.ItemType.PREFIX) {
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= res.endChar; tt = tt.getNext()) {
                if (tt.beginChar >= res.beginChar) {
                    PartToken pt = (!hasSameBefore ? PartToken.tryAttach(tt, null, false, false) : null);
                    if (pt != null) {
                        if (pt.typ == ptmin) {
                            for (PartToken.PartValue v : pt.values) {
                                res.beginChar = v.beginChar;
                                res.endChar = v.endChar;
                                return res;
                            }
                        }
                        tt = pt.getEndToken();
                        continue;
                    }
                    if ((tt instanceof com.pullenti.ner.NumberToken) && tt.beginChar == res.beginChar) {
                        res.endChar = tt.endChar;
                        for (; tt != null && tt.getNext() != null; ) {
                            if (!tt.getNext().isChar('.') || tt.isWhitespaceAfter() || tt.getNext().isWhitespaceAfter()) 
                                break;
                            if (!((tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken))) 
                                break;
                            tt = tt.getNext().getNext();
                            res.endChar = tt.endChar;
                        }
                        if (tt.getNext() != null && tt.getNext().isHiphen()) {
                            if (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken) {
                                tt = tt.getNext().getNext();
                                res.endChar = tt.endChar;
                                for (; tt != null && tt.getNext() != null; ) {
                                    if (!tt.getNext().isChar('.') || tt.isWhitespaceAfter() || tt.getNext().isWhitespaceAfter()) 
                                        break;
                                    if (!((tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken))) 
                                        break;
                                    tt = tt.getNext().getNext();
                                    res.endChar = tt.endChar;
                                }
                            }
                            else if (tt.getNext().getNext() != null && (tt.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) && hasDiap) 
                                res.endChar = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.MetaToken.class))).getBeginToken().endChar;
                        }
                        return res;
                    }
                    if (com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(tt, true, false) && tt.beginChar == res.beginChar && hasSameBefore) {
                        com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br != null && br.getEndToken().getPrevious() == tt.getNext()) {
                            res.endChar = br.endChar;
                            return res;
                        }
                    }
                }
            }
            return res;
        }
        if (!hasSameBefore && !hasSameAfter && ptmin != PartToken.ItemType.PREFIX) {
            for (com.pullenti.ner.Token tt = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(); tt != null && tt.endChar <= res.endChar; tt = tt.getNext()) {
                if (tt.beginChar >= res.beginChar) {
                    java.util.ArrayList<PartToken> pts = PartToken.tryAttachList(tt, false, 40);
                    if (pts == null || pts.size() == 0) 
                        break;
                    for (int i = 0; i < pts.size(); i++) {
                        if (pts.get(i).typ == ptmin) {
                            res.beginChar = pts.get(i).beginChar;
                            res.endChar = pts.get(i).endChar;
                            tt = pts.get(i).getEndToken();
                            if (tt.getNext() != null && tt.getNext().isHiphen()) {
                                if (tt.getNext().getNext() instanceof com.pullenti.ner.NumberToken) 
                                    res.endChar = tt.getNext().getNext().endChar;
                                else if (tt.getNext().getNext() != null && (tt.getNext().getNext().getReferent() instanceof com.pullenti.ner.decree.DecreePartReferent) && hasDiap) 
                                    res.endChar = (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt.getNext().getNext(), com.pullenti.ner.MetaToken.class))).getBeginToken().endChar;
                            }
                            return res;
                        }
                    }
                }
            }
        }
        return res;
    }

    private static boolean _hasSameDecree(com.pullenti.ner.Token t, com.pullenti.ner.decree.DecreePartReferent dpr, boolean before) {
        if ((((before ? t.getPrevious() : t.getNext()))) == null) 
            return false;
        t = ((before ? t.getPrevious() : t.getNext()));
        if (t.isCommaAnd() || t.getMorph()._getClass().isConjunction()) {
        }
        else 
            return false;
        t = ((before ? t.getPrevious() : t.getNext()));
        if (t == null) 
            return false;
        com.pullenti.ner.decree.DecreePartReferent dpr0 = (com.pullenti.ner.decree.DecreePartReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.decree.DecreePartReferent.class);
        if (dpr0 == null) 
            return false;
        if (dpr0.getOwner() != dpr.getOwner()) 
            return false;
        if (dpr0.getOwner() == null) {
            if (com.pullenti.unisharp.Utils.stringsNe(dpr0.getLocalTyp(), dpr.getLocalTyp())) 
                return false;
        }
        for (com.pullenti.ner.Slot s : dpr0.getSlots()) {
            if (PartToken._getTypeByAttrName(s.getTypeName()) != PartToken.ItemType.PREFIX) {
                if (dpr.findSlot(s.getTypeName(), null, true) == null) 
                    return false;
            }
        }
        for (com.pullenti.ner.Slot s : dpr.getSlots()) {
            if (PartToken._getTypeByAttrName(s.getTypeName()) != PartToken.ItemType.PREFIX) {
                if (dpr0.findSlot(s.getTypeName(), null, true) == null) 
                    return false;
            }
        }
        return true;
    }

    private static String _outMoney(com.pullenti.ner.money.MoneyReferent m) {
        String res = m.toString();
        res = res.replace('.', ' ').replace("RUR", "руб.").replace("RUB", "руб.");
        return res;
    }

    /**
     * Проверка корректности НДС для суммы
     * @param t Указывает на значение, для которой должно далее следовать НДС
     * @param nds 
     * @return 
     */
    public static com.pullenti.ner.MetaToken checkNds(com.pullenti.ner.Token t, double nds, boolean ndsMustbeMoney) {
        if (t == null || nds <= 0) 
            return null;
        com.pullenti.ner.money.MoneyReferent m = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), com.pullenti.ner.money.MoneyReferent.class);
        if (m == null) 
            return null;
        boolean hasNds = false;
        boolean hasNdsPerc = false;
        boolean hasAll = false;
        boolean incl = false;
        com.pullenti.ner.Token tt;
        com.pullenti.ner.money.MoneyReferent m1 = null;
        com.pullenti.ner.Token ndsT0 = null;
        com.pullenti.ner.Token ndsT1 = null;
        for (tt = t.getNext(); tt != null; tt = tt.getNext()) {
            if (tt.isValue("НДС", null)) {
                hasNds = true;
                ndsT0 = (ndsT1 = tt);
                continue;
            }
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                m1 = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.money.MoneyReferent.class);
                break;
            }
            if (tt instanceof com.pullenti.ner.NumberToken) {
                com.pullenti.ner.core.NumberExToken ne = com.pullenti.ner.core.NumberHelper.tryParseNumberWithPostfix(tt);
                if (ne != null && ne.exTyp == com.pullenti.ner.core.NumberExType.PERCENT) {
                    if (Math.abs(ne.getRealValue() - nds) > .0001) {
                        boolean ok = false;
                        if (hasNds) 
                            ok = true;
                        if (ok) 
                            return com.pullenti.ner.MetaToken._new809(tt, ne.getEndToken(), ("Размер НДС должен быть " + nds + "%, а не " + ne.getRealValue() + "%"));
                    }
                    tt = (ndsT1 = ne.getEndToken());
                    hasNdsPerc = true;
                    continue;
                }
            }
            if (tt.isValue("ВСЕГО", null)) {
                hasAll = true;
                continue;
            }
            if (tt.isValue("ТОМ", null) || tt.isValue("ЧИСЛО", null) || tt.isValue("ВКЛЮЧАЯ", null)) {
                incl = true;
                continue;
            }
            if ((tt.isValue("КРОМЕ", null) || tt.isValue("ТОГО", null) || tt.isValue("РАЗМЕР", null)) || tt.isValue("СУММА", null) || tt.isValue("СТАВКА", null)) 
                continue;
            if (((tt.isValue("Т", null) && tt.getNext() != null && tt.getNext().isChar('.')) && tt.getNext().getNext() != null && tt.getNext().getNext().isValue("Ч", null)) && tt.getNext().getNext().getNext() != null && tt.getNext().getNext().getNext().isChar('.')) {
                incl = true;
                tt = tt.getNext().getNext().getNext();
                continue;
            }
            if (!tt.chars.isLetter() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            break;
        }
        if (!hasNds) 
            return null;
        if (m1 == null) {
            if (ndsMustbeMoney) 
                return com.pullenti.ner.MetaToken._new809(ndsT0, ndsT1, "Размер НДС должен быть в денежном выражении");
            return null;
        }
        if (hasAll) 
            return null;
        double mustBe = m.getRealValue();
        mustBe = mustBe * ((nds / (100.0)));
        if (incl) 
            mustBe /= (((1.0) + ((nds / (100.0)))));
        double dd = mustBe * (100.0);
        dd -= ((double)((long)dd));
        dd /= (100.0);
        mustBe -= dd;
        if (dd >= .005) 
            mustBe += .01;
        double real = m1.getRealValue();
        double delta = mustBe - real;
        if (delta < 0) 
            delta = -delta;
        if (delta > .011) {
            if ((delta < 1) && m1.getRest() == 0 && m.getRest() == 0) {
            }
            else {
                com.pullenti.ner.money.MoneyReferent mr = com.pullenti.ner.money.MoneyReferent._new811(m1.getCurrency(), mustBe);
                return com.pullenti.ner.MetaToken._new809(t, tt, ("Размер НДС должен быть " + _outMoney(mr) + ", а не " + _outMoney(m1)));
            }
        }
        if (incl) 
            return null;
        com.pullenti.ner.money.MoneyReferent m2 = null;
        hasAll = false;
        for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
            if (tt instanceof com.pullenti.ner.ReferentToken) {
                m2 = (com.pullenti.ner.money.MoneyReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), com.pullenti.ner.money.MoneyReferent.class);
                break;
            }
            if (!tt.chars.isLetter() || tt.getMorph()._getClass().isPreposition()) 
                continue;
            if (tt.isValue("ВСЕГО", null)) {
                hasAll = true;
                continue;
            }
            if (tt.isValue("НДС", null) || tt.isValue("ВМЕСТЕ", null)) 
                continue;
            break;
        }
        if (m2 != null && hasAll) {
            mustBe = m.getRealValue() + m1.getRealValue();
            delta = mustBe - m2.getRealValue();
            if (delta < 0) 
                delta = -delta;
            if (delta > .01) {
                com.pullenti.ner.money.MoneyReferent mr = com.pullenti.ner.money.MoneyReferent._new811(m1.getCurrency(), mustBe);
                String err = ("Всего с НДС должно быть " + _outMoney(mr) + ", а не " + _outMoney(m2));
                return com.pullenti.ner.MetaToken._new809(t, tt, err);
            }
        }
        return null;
    }
    public DecreeHelper() {
    }
}
