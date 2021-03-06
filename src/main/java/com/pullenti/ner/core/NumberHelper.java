/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Работа с числовыми значениями
 */
public class NumberHelper {

    /**
     * Попробовать создать числительное (без знака, целочисленное). 
     *  Внимание! Этот метод всегда вызывается процессором при формировании цепочки токенов, 
     *  так что все NumberToken уже созданы.
     * @param token 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseNumber(com.pullenti.ner.Token token) {
        return _TryParse(token, null);
    }

    private static com.pullenti.ner.NumberToken _TryParse(com.pullenti.ner.Token token, com.pullenti.ner.NumberToken prevVal) {
        if (token instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        com.pullenti.ner.TextToken et = tt;
        String val = null;
        com.pullenti.ner.NumberSpellingType typ = com.pullenti.ner.NumberSpellingType.DIGIT;
        String term = tt.term;
        int i;
        int j;
        if (Character.isDigit(term.charAt(0))) 
            val = term;
        if (val != null) {
            boolean hiph = false;
            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isHiphen()) {
                if ((et.getWhitespacesAfterCount() < 2) && (et.getNext().getWhitespacesAfterCount() < 2)) {
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    hiph = true;
                }
            }
            com.pullenti.ner.MorphCollection mc = null;
            if (hiph || !et.isWhitespaceAfter()) {
                com.pullenti.ner.MetaToken rr = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), val);
                if (rr == null) 
                    et = tt;
                else {
                    mc = rr.getMorph();
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(rr.getEndToken(), com.pullenti.ner.TextToken.class);
                }
            }
            else 
                et = tt;
            if (et.getNext() != null && et.getNext().isChar('(')) {
                com.pullenti.ner.NumberToken num2 = tryParseNumber(et.getNext().getNext());
                if ((num2 != null && com.pullenti.unisharp.Utils.stringsEq(num2.getValue(), val) && num2.getEndToken().getNext() != null) && num2.getEndToken().getNext().isChar(')')) 
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(num2.getEndToken().getNext(), com.pullenti.ner.TextToken.class);
            }
            while ((et.getNext() instanceof com.pullenti.ner.TextToken) && !((et.getPrevious() instanceof com.pullenti.ner.NumberToken)) && et.isWhitespaceBefore()) {
                if (et.getWhitespacesAfterCount() != 1) 
                    break;
                String sss = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class))).term;
                if (com.pullenti.unisharp.Utils.stringsEq(sss, "000")) {
                    val = val + "000";
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    continue;
                }
                if (Character.isDigit(sss.charAt(0)) && sss.length() == 3) {
                    String val2 = val;
                    for (com.pullenti.ner.Token ttt = et.getNext(); ttt != null; ttt = ttt.getNext()) {
                        String ss = ttt.getSourceText();
                        if (ttt.getWhitespacesBeforeCount() == 1 && ttt.getLengthChar() == 3 && Character.isDigit(ss.charAt(0))) {
                            int ii;
                            com.pullenti.unisharp.Outargwrapper<Integer> wrapii554 = new com.pullenti.unisharp.Outargwrapper<Integer>();
                            boolean inoutres555 = com.pullenti.unisharp.Utils.parseInteger(ss, 0, null, wrapii554);
                            ii = (wrapii554.value != null ? wrapii554.value : 0);
                            if (!inoutres555) 
                                break;
                            val2 += ss;
                            continue;
                        }
                        if ((ttt.isCharOf(".,") && !ttt.isWhitespaceBefore() && !ttt.isWhitespaceAfter()) && ttt.getNext() != null && Character.isDigit(ttt.getNext().getSourceText().charAt(0))) {
                            if (ttt.getNext().isWhitespaceAfter() && (ttt.getPrevious() instanceof com.pullenti.ner.TextToken)) {
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ttt.getPrevious(), com.pullenti.ner.TextToken.class);
                                val = val2;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            for (int k = 0; k < 3; k++) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().chars.isLetter()) {
                    tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                    com.pullenti.ner.Token t0 = et;
                    String coef = null;
                    if (k == 0) {
                        coef = "000000000";
                        if (tt.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt.isValue("BILLION", null) || tt.isValue("BN", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("МЛРД", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            continue;
                    }
                    else if (k == 1) {
                        coef = "000000";
                        if (tt.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt.isValue("MILLION", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("МЛН", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "M")) {
                            if (NumberHelper.isMoneyChar(et.getPrevious()) != null) {
                                et = tt;
                                val += coef;
                            }
                            else 
                                break;
                        }
                        else 
                            continue;
                    }
                    else {
                        coef = "000";
                        if (tt.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt.isValue("THOUSAND", null)) {
                            et = tt;
                            val += coef;
                        }
                        else if (tt.isValue("ТЫС", null) || tt.isValue("ТИС", null)) {
                            et = tt;
                            val += coef;
                            if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                                et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class);
                        }
                        else 
                            break;
                    }
                    if (((t0 == token && t0.getLengthChar() <= 3 && t0.getPrevious() != null) && !t0.isWhitespaceBefore() && t0.getPrevious().isCharOf(",.")) && !t0.getPrevious().isWhitespaceBefore() && (((t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) || prevVal != null))) {
                        if (t0.getLengthChar() == 1) 
                            val = val.substring(0, 0 + val.length() - 1);
                        else if (t0.getLengthChar() == 2) 
                            val = val.substring(0, 0 + val.length() - 2);
                        else 
                            val = val.substring(0, 0 + val.length() - 3);
                        String hi = (t0.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken ? (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t0.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class))).getValue() : prevVal.getValue());
                        int cou = coef.length() - val.length();
                        for (; cou > 0; cou--) {
                            hi = hi + "0";
                        }
                        val = hi + val;
                        token = t0.getPrevious().getPrevious();
                    }
                    com.pullenti.ner.NumberToken next = _TryParse(et.getNext(), null);
                    if (next == null || next.getValue().length() > coef.length()) 
                        break;
                    com.pullenti.ner.Token tt1 = next.getEndToken();
                    if (((tt1.getNext() instanceof com.pullenti.ner.TextToken) && !tt1.isWhitespaceAfter() && tt1.getNext().isCharOf(".,")) && !tt1.getNext().isWhitespaceAfter()) {
                        com.pullenti.ner.NumberToken re1 = _TryParse(tt1.getNext().getNext(), next);
                        if (re1 != null && re1.getBeginToken() == next.getBeginToken()) 
                            next = re1;
                    }
                    if (val.length() > next.getValue().length()) 
                        val = val.substring(0, 0 + val.length() - next.getValue().length());
                    val += next.getValue();
                    et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(next.getEndToken(), com.pullenti.ner.TextToken.class);
                    break;
                }
            }
            com.pullenti.ner.NumberToken res = com.pullenti.ner.NumberToken._new556(token, et, val, typ, mc);
            if (et.getNext() != null && (res.getValue().length() < 4) && ((et.getNext().isHiphen() || et.getNext().isValue("ДО", null)))) {
                for (com.pullenti.ner.Token tt1 = et.getNext().getNext(); tt1 != null; tt1 = tt1.getNext()) {
                    if (!((tt1 instanceof com.pullenti.ner.TextToken))) 
                        break;
                    if (Character.isDigit((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt1, com.pullenti.ner.TextToken.class))).term.charAt(0))) 
                        continue;
                    if (tt1.isCharOf(",.") || NumberHelper.isMoneyChar(tt1) != null) 
                        continue;
                    if (tt1.isValue("МИЛЛИОН", "МІЛЬЙОН") || tt1.isValue("МЛН", null) || tt1.isValue("MILLION", null)) 
                        res.setValue(res.getValue() + "000000");
                    else if ((tt1.isValue("МИЛЛИАРД", "МІЛЬЯРД") || tt1.isValue("МЛРД", null) || tt1.isValue("BILLION", null)) || tt1.isValue("BN", null)) 
                        res.setValue(res.getValue() + "000000000");
                    else if (tt1.isValue("ТЫСЯЧА", "ТИСЯЧА") || tt1.isValue("ТЫС", "ТИС") || tt1.isValue("THOUSAND", null)) 
                        res.setValue(res.getValue() + "1000");
                    break;
                }
            }
            return res;
        }
        int intVal = 0;
        et = null;
        int locValue = 0;
        boolean isAdj = false;
        int jPrev = -1;
        for (com.pullenti.ner.TextToken t = tt; t != null; t = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class)) {
            if (t != tt && t.getNewlinesBeforeCount() > 1) 
                break;
            term = t.term;
            if (!Character.isLetter(term.charAt(0))) 
                break;
            TerminToken num = m_Nums.tryParse(t, TerminParseAttr.FULLWORDSONLY);
            if (num == null) 
                break;
            j = (int)num.termin.tag;
            if (jPrev > 0 && (jPrev < 20) && (j < 20)) 
                break;
            isAdj = ((j & prilNumTagBit)) != 0;
            j &= (~prilNumTagBit);
            if (isAdj && t != tt) {
                if ((t.isValue("ДЕСЯТЫЙ", null) || t.isValue("СОТЫЙ", null) || t.isValue("ТЫСЯЧНЫЙ", null)) || t.isValue("ДЕСЯТИТЫСЯЧНЫЙ", null) || t.isValue("МИЛЛИОННЫЙ", null)) 
                    break;
            }
            if (j >= 1000) {
                if (locValue == 0) 
                    locValue = 1;
                intVal += (locValue * j);
                locValue = 0;
            }
            else {
                if (locValue > 0 && locValue <= j) 
                    break;
                locValue += j;
            }
            et = t;
            if (j == 1000 || j == 1000000) {
                if ((et.getNext() instanceof com.pullenti.ner.TextToken) && et.getNext().isChar('.')) 
                    t = (et = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class));
            }
            jPrev = j;
        }
        if (locValue > 0) 
            intVal += locValue;
        if (intVal == 0 || et == null) 
            return null;
        com.pullenti.ner.NumberToken nt = new com.pullenti.ner.NumberToken(tt, et, ((Integer)intVal).toString(), com.pullenti.ner.NumberSpellingType.WORDS, null);
        if (et.getMorph() != null) {
            nt.setMorph(new com.pullenti.ner.MorphCollection(et.getMorph()));
            for (com.pullenti.morph.MorphBaseInfo wff : et.getMorph().getItems()) {
                com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wff, com.pullenti.morph.MorphWordForm.class);
                if (wf != null && wf.misc != null && wf.misc.getAttrs().contains("собир.")) {
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
                    break;
                }
            }
            if (!isAdj) {
                nt.getMorph().removeItems(com.pullenti.morph.MorphClass.ooBitor(com.pullenti.morph.MorphClass.ADJECTIVE, com.pullenti.morph.MorphClass.NOUN), false);
                if (nt.getMorph()._getClass().isUndefined()) 
                    nt.getMorph()._setClass(com.pullenti.morph.MorphClass.NOUN);
            }
            if (et.chars.isLatinLetter() && isAdj) 
                nt.getMorph()._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
        }
        return nt;
    }

    /**
     * Попробовать выделить римскую цифру
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseRoman(com.pullenti.ner.Token t) {
        if (t instanceof com.pullenti.ner.NumberToken) 
            return (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null || !t.chars.isLetter()) 
            return null;
        String term = tt.term;
        if (!_isRomVal(term)) 
            return null;
        if (tt.getMorph()._getClass().isPreposition()) {
            if (tt.chars.isAllLower()) 
                return null;
        }
        com.pullenti.ner.NumberToken res = new com.pullenti.ner.NumberToken(t, t, "", com.pullenti.ner.NumberSpellingType.ROMAN, null);
        java.util.ArrayList<Integer> nums = new java.util.ArrayList<Integer>();
        int val = 0;
        for (; t != null; t = t.getNext()) {
            if (t != res.getBeginToken() && t.isWhitespaceBefore()) 
                break;
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                break;
            term = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            if (!_isRomVal(term)) 
                break;
            for (char s : term.toCharArray()) {
                int i = _romVal(s);
                if (i > 0) 
                    nums.add(i);
            }
            res.setEndToken(t);
        }
        if (nums.size() == 0) 
            return null;
        for (int i = 0; i < nums.size(); i++) {
            if ((i + 1) < nums.size()) {
                if (nums.get(i) == 1 && nums.get(i + 1) == 5) {
                    val += 4;
                    i++;
                }
                else if (nums.get(i) == 1 && nums.get(i + 1) == 10) {
                    val += 9;
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 50) {
                    val += 40;
                    i++;
                }
                else if (nums.get(i) == 10 && nums.get(i + 1) == 100) {
                    val += 90;
                    i++;
                }
                else 
                    val += nums.get(i);
            }
            else 
                val += nums.get(i);
        }
        res.setIntValue(val);
        boolean hiph = false;
        com.pullenti.ner.Token et = res.getEndToken().getNext();
        if (et == null) 
            return res;
        if (et.getNext() != null && et.getNext().isHiphen()) {
            et = et.getNext();
            hiph = true;
        }
        if (hiph || !et.isWhitespaceAfter()) {
            com.pullenti.ner.MetaToken mc = analizeNumberTail((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(et.getNext(), com.pullenti.ner.TextToken.class), res.getValue());
            if (mc != null) {
                res.setEndToken(mc.getEndToken());
                res.setMorph(mc.getMorph());
            }
        }
        if ((res.getBeginToken() == res.getEndToken() && val == 1 && res.getBeginToken().chars.isAllLower()) && res.getBeginToken().getMorph().getLanguage().isUa()) 
            return null;
        return res;
    }

    private static int _romVal(char ch) {
        if (ch == 'Х' || ch == 'X') 
            return 10;
        if (ch == 'І' || ch == 'I') 
            return 1;
        if (ch == 'V') 
            return 5;
        if (ch == 'L') 
            return 50;
        if (ch == 'C' || ch == 'С') 
            return 100;
        return 0;
    }

    private static boolean _isRomVal(String str) {
        for (char ch : str.toCharArray()) {
            if (_romVal(ch) < 1) 
                return false;
        }
        return true;
    }

    /**
     * Выделить римскую цифру с token в обратном порядке
     * @param token 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseRomanBack(com.pullenti.ner.Token token) {
        com.pullenti.ner.Token t = token;
        if (t == null) 
            return null;
        if ((t.chars.isAllLower() && t.getPrevious() != null && t.getPrevious().isHiphen()) && t.getPrevious().getPrevious() != null) 
            t = token.getPrevious().getPrevious();
        com.pullenti.ner.NumberToken res = null;
        for (; t != null; t = t.getPrevious()) {
            com.pullenti.ner.NumberToken nt = tryParseRoman(t);
            if (nt != null) {
                if (nt.getEndToken() == token) 
                    res = nt;
                else 
                    break;
            }
            if (t.isWhitespaceAfter()) 
                break;
        }
        return res;
    }

    /**
     * Это выделение числительных типа 16-летие, 50-летний
     * @param t 
     * @return 
     */
    public static com.pullenti.ner.NumberToken tryParseAge(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token ntNext = null;
        if (nt != null) 
            ntNext = nt.getNext();
        else {
            if (t.isValue("AGED", null) && (t.getNext() instanceof com.pullenti.ner.NumberToken)) 
                return new com.pullenti.ner.NumberToken(t, t.getNext(), (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.NumberToken.class))).getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            if ((((nt = tryParseRoman(t)))) != null) 
                ntNext = nt.getEndToken().getNext();
        }
        if (nt != null) {
            if (ntNext != null) {
                com.pullenti.ner.Token t1 = ntNext;
                if (t1.isHiphen()) 
                    t1 = t1.getNext();
                if (t1 instanceof com.pullenti.ner.TextToken) {
                    String v = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
                    if ((com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЕ")) || com.pullenti.unisharp.Utils.stringsEq(v, "РІЧЧЯ")) 
                        return com.pullenti.ner.NumberToken._new556(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (t1.isValue("ЛЕТНИЙ", "РІЧНИЙ")) 
                        return com.pullenti.ner.NumberToken._new556(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, t1.getMorph());
                    if (com.pullenti.unisharp.Utils.stringsEq(v, "Л") || ((com.pullenti.unisharp.Utils.stringsEq(v, "Р") && nt.getMorph().getLanguage().isUa()))) 
                        return new com.pullenti.ner.NumberToken(t, (t1.getNext() != null && t1.getNext().isChar('.') ? t1.getNext() : t1), nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
                }
            }
            return null;
        }
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return null;
        String s = tt.term;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ЛЕТИЕ", "ЛЕТИЯ", "РІЧЧЯ", null)) {
            Termin term = m_Nums.find(s.substring(0, 0 + s.length() - 5));
            if (term != null) 
                return com.pullenti.ner.NumberToken._new556(tt, tt, term.tag.toString(), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
        }
        s = tt.lemma;
        if (com.pullenti.morph.LanguageHelper.endsWithEx(s, "ЛЕТНИЙ", "РІЧНИЙ", null, null)) {
            Termin term = m_Nums.find(s.substring(0, 0 + s.length() - 6));
            if (term != null) 
                return com.pullenti.ner.NumberToken._new556(tt, tt, term.tag.toString(), com.pullenti.ner.NumberSpellingType.AGE, tt.getMorph());
        }
        return null;
    }

    /**
     * Выделение годовщин и летий (XX-летие) ...
     */
    public static com.pullenti.ner.NumberToken tryParseAnniversary(com.pullenti.ner.Token t) {
        com.pullenti.ner.NumberToken nt = (com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class);
        com.pullenti.ner.Token t1 = null;
        if (nt != null) 
            t1 = nt.getNext();
        else {
            if ((((nt = tryParseRoman(t)))) == null) {
                if (t instanceof com.pullenti.ner.TextToken) {
                    String v = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                    int num = 0;
                    if (v.endsWith("ЛЕТИЯ") || v.endsWith("ЛЕТИЕ")) {
                        if (v.startsWith("ВОСЕМЬСОТ") || v.startsWith("ВОСЬМИСОТ")) 
                            num = 800;
                    }
                    if (num > 0) 
                        return new com.pullenti.ner.NumberToken(t, t, ((Integer)num).toString(), com.pullenti.ner.NumberSpellingType.AGE, null);
                }
                return null;
            }
            t1 = nt.getEndToken().getNext();
        }
        if (t1 == null) 
            return null;
        if (t1.isHiphen()) 
            t1 = t1.getNext();
        if (t1 instanceof com.pullenti.ner.TextToken) {
            String v = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term;
            if ((com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЯ") || com.pullenti.unisharp.Utils.stringsEq(v, "ЛЕТИЕ")) || t1.isValue("ГОДОВЩИНА", null)) 
                return new com.pullenti.ner.NumberToken(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            if (t1.getMorph().getLanguage().isUa()) {
                if (com.pullenti.unisharp.Utils.stringsEq(v, "РОКІВ") || com.pullenti.unisharp.Utils.stringsEq(v, "РІЧЧЯ") || t1.isValue("РІЧНИЦЯ", null)) 
                    return new com.pullenti.ner.NumberToken(t, t1, nt.getValue(), com.pullenti.ner.NumberSpellingType.AGE, null);
            }
        }
        return null;
    }

    private static String[] m_Samples;

    private static com.pullenti.ner.MetaToken analizeNumberTail(com.pullenti.ner.TextToken tt, String val) {
        if (!((tt instanceof com.pullenti.ner.TextToken))) 
            return null;
        String s = tt.term;
        com.pullenti.ner.MorphCollection mc = null;
        if (!tt.chars.isLetter()) {
            if (((com.pullenti.unisharp.Utils.stringsEq(s, "<") || com.pullenti.unisharp.Utils.stringsEq(s, "("))) && (tt.getNext() instanceof com.pullenti.ner.TextToken)) {
                s = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt.getNext(), com.pullenti.ner.TextToken.class))).term;
                if ((com.pullenti.unisharp.Utils.stringsEq(s, "TH") || com.pullenti.unisharp.Utils.stringsEq(s, "ST") || com.pullenti.unisharp.Utils.stringsEq(s, "RD")) || com.pullenti.unisharp.Utils.stringsEq(s, "ND")) {
                    if (tt.getNext().getNext() != null && tt.getNext().getNext().isCharOf(">)")) {
                        mc = new com.pullenti.ner.MorphCollection(null);
                        mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
                        mc.setLanguage(com.pullenti.morph.MorphLang.EN);
                        return com.pullenti.ner.MetaToken._new561(tt, tt.getNext().getNext(), mc);
                    }
                }
            }
            return null;
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(s, "TH") || com.pullenti.unisharp.Utils.stringsEq(s, "ST") || com.pullenti.unisharp.Utils.stringsEq(s, "RD")) || com.pullenti.unisharp.Utils.stringsEq(s, "ND")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc._setClass(com.pullenti.morph.MorphClass.ADJECTIVE);
            mc.setLanguage(com.pullenti.morph.MorphLang.EN);
            return com.pullenti.ner.MetaToken._new561(tt, tt, mc);
        }
        if (!tt.chars.isCyrillicLetter()) 
            return null;
        if (!tt.isWhitespaceAfter()) {
            if (tt.getNext() != null && tt.getNext().chars.isLetter()) 
                return null;
            if (tt.getLengthChar() == 1 && ((tt.isValue("X", null) || tt.isValue("Х", null)))) 
                return null;
        }
        if (!tt.chars.isAllLower()) {
            String ss = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term;
            if (com.pullenti.unisharp.Utils.stringsEq(ss, "Я") || com.pullenti.unisharp.Utils.stringsEq(ss, "Й") || com.pullenti.unisharp.Utils.stringsEq(ss, "Е")) {
            }
            else if (ss.length() == 2 && ((ss.charAt(1) == 'Я' || ss.charAt(1) == 'Й' || ss.charAt(1) == 'Е'))) {
            }
            else 
                return null;
        }
        if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term, "М")) {
            if (tt.getPrevious() == null || !tt.getPrevious().isHiphen()) 
                return null;
        }
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(val)) 
            return null;
        int dig = (((int)val.charAt(val.length() - 1)) - ((int)'0'));
        if ((dig < 0) || dig >= 10) 
            return null;
        java.util.ArrayList<com.pullenti.morph.MorphWordForm> vars = com.pullenti.morph.Morphology.getAllWordforms(m_Samples[dig], null);
        if (vars == null || vars.size() == 0) 
            return null;
        for (com.pullenti.morph.MorphWordForm v : vars) {
            if (v._getClass().isAdjective() && com.pullenti.morph.LanguageHelper.endsWith(v.normalCase, s) && v.getNumber() != com.pullenti.morph.MorphNumber.UNDEFINED) {
                if (mc == null) 
                    mc = new com.pullenti.ner.MorphCollection(null);
                boolean ok = false;
                for (com.pullenti.morph.MorphBaseInfo it : mc.getItems()) {
                    if (com.pullenti.morph.MorphClass.ooEq(it._getClass(), v._getClass()) && it.getNumber() == v.getNumber() && ((it.getGender() == v.getGender() || v.getNumber() == com.pullenti.morph.MorphNumber.PLURAL))) {
                        it.setCase(com.pullenti.morph.MorphCase.ooBitor(it.getCase(), v.getCase()));
                        ok = true;
                        break;
                    }
                }
                if (!ok) 
                    mc.addItem(new com.pullenti.morph.MorphBaseInfo(v));
            }
        }
        if (tt.getMorph().getLanguage().isUa() && mc == null && com.pullenti.unisharp.Utils.stringsEq(s, "Ї")) {
            mc = new com.pullenti.ner.MorphCollection(null);
            mc.addItem(com.pullenti.morph.MorphBaseInfo._new563(com.pullenti.morph.MorphClass.ADJECTIVE));
        }
        if (mc != null) 
            return com.pullenti.ner.MetaToken._new561(tt, tt, mc);
        if ((((s.length() < 3) && !tt.isWhitespaceBefore() && tt.getPrevious() != null) && tt.getPrevious().isHiphen() && !tt.getPrevious().isWhitespaceBefore()) && tt.getWhitespacesAfterCount() == 1 && com.pullenti.unisharp.Utils.stringsNe(s, "А")) 
            return com.pullenti.ner.MetaToken._new561(tt, tt, com.pullenti.ner.MorphCollection._new565(com.pullenti.morph.MorphClass.ADJECTIVE));
        return null;
    }

    private static com.pullenti.ner.Token _tryParseFloat(com.pullenti.ner.NumberToken t, com.pullenti.unisharp.Outargwrapper<Double> d) {
        d.value = 0.0;
        if (t == null || t.getNext() == null || t.typ != com.pullenti.ner.NumberSpellingType.DIGIT) 
            return null;
        for (com.pullenti.ner.Token tt = t.getBeginToken(); tt != null && tt.endChar <= t.endChar; tt = tt.getNext()) {
            if ((tt instanceof com.pullenti.ner.TextToken) && tt.chars.isLetter()) 
                return null;
        }
        AnalysisKit kit = t.kit;
        java.util.ArrayList<com.pullenti.ner.NumberToken> ns = null;
        java.util.ArrayList<Character> sps = null;
        for (com.pullenti.ner.Token t1 = t; t1 != null; t1 = t1.getNext()) {
            if (t1.getNext() == null) 
                break;
            if (((t1.getNext() instanceof com.pullenti.ner.NumberToken) && (t1.getWhitespacesAfterCount() < 3) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && t1.getNext().getLengthChar() == 3) {
                if (ns == null) {
                    ns = new java.util.ArrayList<com.pullenti.ner.NumberToken>();
                    ns.add(t);
                    sps = new java.util.ArrayList<Character>();
                }
                else if (sps.get(0) != ' ') 
                    return null;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.NumberToken.class));
                sps.add(' ');
                continue;
            }
            if ((t1.getNext().isCharOf(",.") && (t1.getNext().getNext() instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class))).typ == com.pullenti.ner.NumberSpellingType.DIGIT) && (t1.getWhitespacesAfterCount() < 2) && (t1.getNext().getWhitespacesAfterCount() < 2)) {
                if (ns == null) {
                    ns = new java.util.ArrayList<com.pullenti.ner.NumberToken>();
                    ns.add(t);
                    sps = new java.util.ArrayList<Character>();
                }
                else if (t1.getNext().isWhitespaceAfter() && t1.getNext().getNext().getLengthChar() != 3 && (((t1.getNext().isChar('.') ? '.' : ','))) == sps.get(sps.size() - 1)) 
                    break;
                ns.add((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1.getNext().getNext(), com.pullenti.ner.NumberToken.class));
                sps.add((t1.getNext().isChar('.') ? '.' : ','));
                t1 = t1.getNext();
                continue;
            }
            break;
        }
        if (sps == null) 
            return null;
        boolean isLastDrob = false;
        boolean notSetDrob = false;
        boolean merge = false;
        char m_PrevPointChar = '.';
        if (sps.size() == 1) {
            if (sps.get(0) == ' ') 
                isLastDrob = false;
            else if (ns.get(1).getLengthChar() != 3) {
                isLastDrob = true;
                if (ns.size() == 2) {
                    if (ns.get(1).getEndToken().chars.isLetter()) 
                        merge = true;
                    else if (ns.get(1).getEndToken().isChar('.') && ns.get(1).getEndToken().getPrevious() != null && ns.get(1).getEndToken().getPrevious().chars.isLetter()) 
                        merge = true;
                    if (ns.get(1).isWhitespaceBefore()) {
                        if ((ns.get(1).getEndToken() instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(ns.get(1).getEndToken(), com.pullenti.ner.TextToken.class))).term.endsWith("000")) 
                            return null;
                    }
                }
            }
            else if (ns.get(0).getLengthChar() > 3 || ns.get(0).getRealValue() == 0) 
                isLastDrob = true;
            else {
                boolean ok = true;
                if (ns.size() == 2 && ns.get(1).getLengthChar() == 3) {
                    TerminToken ttt = com.pullenti.ner.core.internal.NumberExHelper.m_Postfixes.tryParse(ns.get(1).getEndToken().getNext(), TerminParseAttr.NO);
                    if (ttt != null && ((NumberExType)ttt.termin.tag) == NumberExType.MONEY) {
                        isLastDrob = false;
                        ok = false;
                        notSetDrob = false;
                    }
                    else if (ns.get(1).getEndToken().getNext() != null && ns.get(1).getEndToken().getNext().isChar('(') && (ns.get(1).getEndToken().getNext().getNext() instanceof com.pullenti.ner.NumberToken)) {
                        com.pullenti.ner.NumberToken nt1 = ((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(ns.get(1).getEndToken().getNext().getNext(), com.pullenti.ner.NumberToken.class));
                        if (nt1.getRealValue() == (((ns.get(0).getRealValue() * (1000.0)) + ns.get(1).getRealValue()))) {
                            isLastDrob = false;
                            ok = false;
                            notSetDrob = false;
                        }
                    }
                }
                if (ok) {
                    if (t.kit.miscData.containsKey("pt")) 
                        m_PrevPointChar = (char)t.kit.miscData.get("pt");
                    if (m_PrevPointChar == sps.get(0)) {
                        isLastDrob = true;
                        notSetDrob = true;
                    }
                    else {
                        isLastDrob = false;
                        notSetDrob = true;
                    }
                }
            }
        }
        else {
            char last = sps.get(sps.size() - 1);
            if (last == ' ' && sps.get(0) != last) 
                return null;
            for (int i = 0; i < (sps.size() - 1); i++) {
                if (sps.get(i) != sps.get(0)) 
                    return null;
                else if (ns.get(i + 1).getLengthChar() != 3) 
                    return null;
            }
            if (sps.get(0) != last) 
                isLastDrob = true;
            else if (ns.get(ns.size() - 1).getLengthChar() != 3) 
                return null;
        }
        for (int i = 0; i < ns.size(); i++) {
            if ((i < (ns.size() - 1)) || !isLastDrob) {
                if (i == 0) 
                    d.value = ns.get(i).getRealValue();
                else 
                    d.value = (d.value * (1000.0)) + ns.get(i).getRealValue();
                if (i == (ns.size() - 1) && !notSetDrob) {
                    if (sps.get(sps.size() - 1) == ',') 
                        m_PrevPointChar = '.';
                    else if (sps.get(sps.size() - 1) == '.') 
                        m_PrevPointChar = ',';
                }
            }
            else {
                if (!notSetDrob) {
                    m_PrevPointChar = sps.get(sps.size() - 1);
                    if (m_PrevPointChar == ',') {
                    }
                }
                double f2;
                if (merge) {
                    String sss = ns.get(i).getValue().toString();
                    int kkk;
                    for (kkk = 0; kkk < (sss.length() - ns.get(i).getBeginToken().getLengthChar()); kkk++) {
                        d.value *= (10.0);
                    }
                    f2 = ns.get(i).getRealValue();
                    for (kkk = 0; kkk < ns.get(i).getBeginToken().getLengthChar(); kkk++) {
                        f2 /= (10.0);
                    }
                    d.value += f2;
                }
                else {
                    f2 = ns.get(i).getRealValue();
                    for (int kkk = 0; kkk < ns.get(i).getLengthChar(); kkk++) {
                        f2 /= (10.0);
                    }
                    d.value += f2;
                }
            }
        }
        if (kit.miscData.containsKey("pt")) 
            kit.miscData.put("pt", m_PrevPointChar);
        else 
            kit.miscData.put("pt", m_PrevPointChar);
        return ns.get(ns.size() - 1);
    }

    /**
     * Это разделитель дроби по-умолчанию, используется для случаев, когда невозможно принять однозначного решения. 
     *  Устанавливается на основе последнего успешного анализа.
     * Выделить действительное число, знак также выделяется, 
     *  разделители дроби могут быть точка или запятая, разделителями тысячных 
     *  могут быть точки, пробелы и запятые.
     * @param t начальный токен
     * @param canBeInteger число должно быть целым
     * @return результат или null
     */
    public static com.pullenti.ner.NumberToken tryParseRealNumber(com.pullenti.ner.Token t, boolean canBeInteger) {
        boolean isNot = false;
        com.pullenti.ner.Token t0 = t;
        if (t != null) {
            if (t.isHiphen() || t.isValue("МИНУС", null)) {
                t = t.getNext();
                isNot = true;
            }
            else if (t.isChar('+') || t.isValue("ПЛЮС", null)) 
                t = t.getNext();
        }
        if ((t instanceof com.pullenti.ner.TextToken) && ((t.isValue("НОЛЬ", null) || t.isValue("НУЛЬ", null))) && t.getNext() != null) {
            if (t.getNext().isValue("ЦЕЛЫЙ", null)) 
                t = t.getNext();
            com.pullenti.ner.NumberToken res0 = new com.pullenti.ner.NumberToken(t, t.getNext(), "0", com.pullenti.ner.NumberSpellingType.WORDS, null);
            t = t.getNext();
            if ((t instanceof com.pullenti.ner.NumberToken) && (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue() != null) {
                int val = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getIntValue();
                if (t.getNext() != null && val > 0) {
                    if (t.getNext().isValue("ДЕСЯТЫЙ", null)) {
                        res0.setEndToken(t.getNext());
                        res0.setRealValue((((double)val)) / (10.0));
                    }
                    else if (t.getNext().isValue("СОТЫЙ", null)) {
                        res0.setEndToken(t.getNext());
                        res0.setRealValue((((double)val)) / (100.0));
                    }
                    else if (t.getNext().isValue("ТЫСЯЧНЫЙ", null)) {
                        res0.setEndToken(t.getNext());
                        res0.setRealValue((((double)val)) / (1000.0));
                    }
                }
                if (res0.getRealValue() == 0) {
                    res0.setEndToken(t);
                    res0.setValue(("0." + val));
                }
            }
            return res0;
        }
        if (t instanceof com.pullenti.ner.TextToken) {
            TerminToken tok = m_AfterPoints.tryParse(t, TerminParseAttr.NO);
            if (tok != null) {
                NumberExToken res0 = new NumberExToken(t, tok.getEndToken(), null, com.pullenti.ner.NumberSpellingType.WORDS, NumberExType.UNDEFINED);
                res0.setRealValue((double)((tok.termin.tag)));
                return res0;
            }
        }
        if (!((t instanceof com.pullenti.ner.NumberToken))) 
            return null;
        if (t.getNext() != null && t.getNext().isValue("ЦЕЛЫЙ", null) && (((t.getNext().getNext() instanceof com.pullenti.ner.NumberToken) || (((t.getNext().getNext() instanceof com.pullenti.ner.TextToken) && t.getNext().getNext().isValue("НОЛЬ", null)))))) {
            NumberExToken res0 = new NumberExToken(t, t.getNext(), (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getValue(), com.pullenti.ner.NumberSpellingType.WORDS, NumberExType.UNDEFINED);
            t = t.getNext().getNext();
            double val = 0.0;
            if (t instanceof com.pullenti.ner.TextToken) {
                res0.setEndToken(t);
                t = t.getNext();
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                res0.setEndToken(t);
                val = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getRealValue();
                t = t.getNext();
            }
            if (t != null) {
                if (t.isValue("ДЕСЯТЫЙ", null)) {
                    res0.setEndToken(t);
                    res0.setRealValue((((val) / (10.0))) + res0.getRealValue());
                }
                else if (t.isValue("СОТЫЙ", null)) {
                    res0.setEndToken(t);
                    res0.setRealValue((((val) / (100.0))) + res0.getRealValue());
                }
                else if (t.isValue("ТЫСЯЧНЫЙ", null)) {
                    res0.setEndToken(t);
                    res0.setRealValue((((val) / (1000.0))) + res0.getRealValue());
                }
            }
            if (res0.getRealValue() == 0) {
                String str = ("0." + val);
                double dd = 0.0;
                com.pullenti.unisharp.Outargwrapper<Double> wrapdd569 = new com.pullenti.unisharp.Outargwrapper<Double>();
                boolean inoutres570 = com.pullenti.unisharp.Utils.parseDouble(str, null, wrapdd569);
                dd = (wrapdd569.value != null ? wrapdd569.value : 0);
                if (inoutres570) {
                }
                else {
                    com.pullenti.unisharp.Outargwrapper<Double> wrapdd567 = new com.pullenti.unisharp.Outargwrapper<Double>();
                    boolean inoutres568 = com.pullenti.unisharp.Utils.parseDouble(str.replace('.', ','), null, wrapdd567);
                    dd = (wrapdd567.value != null ? wrapdd567.value : 0);
                    if (inoutres568) {
                    }
                    else 
                        return null;
                }
                res0.setRealValue(dd + res0.getRealValue());
            }
            return res0;
        }
        double d;
        com.pullenti.unisharp.Outargwrapper<Double> wrapd572 = new com.pullenti.unisharp.Outargwrapper<Double>();
        com.pullenti.ner.Token tt = _tryParseFloat((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class), wrapd572);
        d = (wrapd572.value != null ? wrapd572.value : 0);
        if (tt == null) {
            if ((t.getNext() == null || t.isWhitespaceAfter() || t.getNext().chars.isLetter()) || canBeInteger) {
                tt = t;
                d = (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getRealValue();
            }
            else 
                return null;
        }
        if (isNot) 
            d = -d;
        return NumberExToken._new571(t0, tt, "", com.pullenti.ner.NumberSpellingType.DIGIT, NumberExType.UNDEFINED, d);
    }

    /**
     * Преобразовать число в числительное, записанное буквами, в соотв. роде и числе. 
     *  Например, 5 жен.ед. - ПЯТАЯ,  26 мн. - ДВАДЦАТЬ ШЕСТЫЕ
     * @param value значение
     * @param gender род
     * @param num число
     * @return 
     */
    public static String getNumberAdjective(int value, com.pullenti.morph.MorphGender gender, com.pullenti.morph.MorphNumber num) {
        if ((value < 1) || value >= 100) 
            return null;
        String[] words = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            words = m_PluralNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            words = m_WomanNumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            words = m_NeutralNumberWords;
        else 
            words = m_ManNumberWords;
        if (value < 20) 
            return words[value - 1];
        int i = value / 10;
        int j = value % 10;
        i -= 2;
        if (i >= m_DecDumberWords.length) 
            return null;
        if (j > 0) 
            return (m_DecDumberWords[i] + " " + words[j - 1]);
        String[] decs = null;
        if (num == com.pullenti.morph.MorphNumber.PLURAL) 
            decs = m_PluralDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.FEMINIE) 
            decs = m_WomanDecDumberWords;
        else if (gender == com.pullenti.morph.MorphGender.NEUTER) 
            decs = m_NeutralDecDumberWords;
        else 
            decs = m_ManDecDumberWords;
        return decs[i];
    }

    private static String[] m_ManNumberWords;

    private static String[] m_NeutralNumberWords;

    private static String[] m_WomanNumberWords;

    private static String[] m_PluralNumberWords;

    private static String[] m_DecDumberWords;

    private static String[] m_ManDecDumberWords;

    private static String[] m_WomanDecDumberWords;

    private static String[] m_NeutralDecDumberWords;

    private static String[] m_PluralDecDumberWords;

    public static String[] m_Romans;

    /**
     * Получить для числа римскую запись
     * @param val 
     * @return 
     */
    public static String getNumberRoman(int val) {
        if (val > 0 && val <= m_Romans.length) 
            return m_Romans[val - 1];
        return ((Integer)val).toString();
    }

    /**
     * Выделение стандартных мер, типа: 10 кв.м.
     * @param t начальный токен
     * @return 
     */
    public static NumberExToken tryParseNumberWithPostfix(com.pullenti.ner.Token t) {
        return com.pullenti.ner.core.internal.NumberExHelper.tryParseNumberWithPostfix(t);
    }

    /**
     * Это попробовать только тип (постфикс) без самого числа. 
     *  Например, куб.м.
     * @param t 
     * @return 
     */
    public static NumberExToken tryParsePostfixOnly(com.pullenti.ner.Token t) {
        return com.pullenti.ner.core.internal.NumberExHelper.tryAttachPostfixOnly(t);
    }

    /**
     * Если этообозначение денежной единицы (н-р, $), то возвращает код валюты
     * @param t 
     * @return 
     */
    public static String isMoneyChar(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken)) || t.getLengthChar() != 1) 
            return null;
        char ch = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.charAt(0);
        if (ch == '$') 
            return "USD";
        if (ch == '£' || ch == ((char)0xA3) || ch == ((char)0x20A4)) 
            return "GBP";
        if (ch == '€') 
            return "EUR";
        if (ch == '¥' || ch == ((char)0xA5)) 
            return "JPY";
        if (ch == ((char)0x20A9)) 
            return "KRW";
        if (ch == ((char)0xFFE5) || ch == 'Ұ' || ch == 'Ұ') 
            return "CNY";
        if (ch == ((char)0x20BD)) 
            return "RUB";
        if (ch == ((char)0x20B4)) 
            return "UAH";
        if (ch == ((char)0x20AB)) 
            return "VND";
        if (ch == ((char)0x20AD)) 
            return "LAK";
        if (ch == ((char)0x20BA)) 
            return "TRY";
        if (ch == ((char)0x20B1)) 
            return "PHP";
        if (ch == ((char)0x17DB)) 
            return "KHR";
        if (ch == ((char)0x20B9)) 
            return "INR";
        if (ch == ((char)0x20A8)) 
            return "IDR";
        if (ch == ((char)0x20B5)) 
            return "GHS";
        if (ch == ((char)0x09F3)) 
            return "BDT";
        if (ch == ((char)0x20B8)) 
            return "KZT";
        if (ch == ((char)0x20AE)) 
            return "MNT";
        if (ch == ((char)0x0192)) 
            return "HUF";
        if (ch == ((char)0x20AA)) 
            return "ILS";
        return null;
    }

    /**
     * Для парсинга действительного числа из строки используйте эту функцию, 
     *  которая работает назависимо от локализьных настроек и на всех языках программирования
     * @param str строка
     * @return число
     */
    public static Double stringToDouble(String str) {
        double res;
        com.pullenti.unisharp.Outargwrapper<Double> wrapres575 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres576 = com.pullenti.unisharp.Utils.parseDouble(str, null, wrapres575);
        res = (wrapres575.value != null ? wrapres575.value : 0);
        if (inoutres576) 
            return res;
        com.pullenti.unisharp.Outargwrapper<Double> wrapres573 = new com.pullenti.unisharp.Outargwrapper<Double>();
        boolean inoutres574 = com.pullenti.unisharp.Utils.parseDouble(str.replace('.', ','), null, wrapres573);
        res = (wrapres573.value != null ? wrapres573.value : 0);
        if (inoutres574) 
            return res;
        return null;
    }

    /**
     * Независимо от языка и настроек выводит действиельное число в строку, 
     *  разделитель - точка. Ситуация типа 1.0000000001 или 23.7299999999999, 
     *  случающиеся на разных языках, округляются куда надо.
     * @param d число
     * @return результат
     */
    public static String doubleToString(double d) {
        String res = null;
        if (com.pullenti.unisharp.Utils.mathTruncate(d) == .0) 
            res = ((Double)d).toString().replace(",", ".");
        else {
            double rest = Math.abs(d - com.pullenti.unisharp.Utils.mathTruncate(d));
            if ((rest < .000000001) && rest > 0) {
                res = ((Double)com.pullenti.unisharp.Utils.mathTruncate(d)).toString();
                if ((res.indexOf('E') < 0) && (res.indexOf('e') < 0)) {
                    int ii = res.indexOf('.');
                    if (ii < 0) 
                        ii = res.indexOf(',');
                    if (ii > 0) 
                        return res.substring(0, 0 + ii);
                    else 
                        return res;
                }
            }
            else 
                res = ((Double)d).toString().replace(",", ".");
        }
        if (res.endsWith(".0")) 
            res = res.substring(0, 0 + res.length() - 2);
        int i = res.indexOf('e');
        if (i < 0) 
            i = res.indexOf('E');
        if (i > 0) {
            int exp = 0;
            boolean neg = false;
            for (int jj = i + 1; jj < res.length(); jj++) {
                if (res.charAt(jj) == '+') {
                }
                else if (res.charAt(jj) == '-') 
                    neg = true;
                else 
                    exp = (exp * 10) + ((((int)res.charAt(jj)) - ((int)'0')));
            }
            res = res.substring(0, 0 + i);
            if (res.endsWith(".0")) 
                res = res.substring(0, 0 + res.length() - 2);
            boolean nneg = false;
            if (res.charAt(0) == '-') {
                nneg = true;
                res = res.substring(1);
            }
            StringBuilder v1 = new StringBuilder();
            StringBuilder v2 = new StringBuilder();
            i = res.indexOf('.');
            if (i < 0) 
                v1.append(res);
            else {
                v1.append(res.substring(0, 0 + i));
                v2.append(res.substring(i + 1));
            }
            for (; exp > 0; exp--) {
                if (neg) {
                    if (v1.length() > 0) {
                        v2.insert(0, v1.charAt(v1.length() - 1));
                        v1.setLength(v1.length() - 1);
                    }
                    else 
                        v2.insert(0, '0');
                }
                else if (v2.length() > 0) {
                    v1.append(v2.charAt(0));
                    v2.delete(0, 0 + 1);
                }
                else 
                    v1.append('0');
            }
            if (v2.length() == 0) 
                res = v1.toString();
            else if (v1.length() == 0) 
                res = "0." + v2;
            else 
                res = (v1.toString() + "." + v2.toString());
            if (nneg) 
                res = "-" + res;
        }
        i = res.indexOf('.');
        if (i < 0) 
            return res;
        i++;
        int j;
        for (j = i + 1; j < res.length(); j++) {
            if (res.charAt(j) == '9') {
                int k = 0;
                int jj;
                for (jj = j; jj < res.length(); jj++) {
                    if (res.charAt(jj) != '9') 
                        break;
                    else 
                        k++;
                }
                if (jj >= res.length() || ((jj == (res.length() - 1) && res.charAt(jj) == '8'))) {
                    if (k > 5) {
                        for (; j > i; j--) {
                            if (res.charAt(j) != '9') {
                                if (res.charAt(j) != '.') 
                                    return (res.substring(0, 0 + j) + ((((((int)res.charAt(j)) - ((int)'0')))) + 1));
                            }
                        }
                        break;
                    }
                }
            }
        }
        return res;
    }

    private static final int prilNumTagBit = 0x40000000;

    public static void initialize() {
        if (m_Nums != null) 
            return;
        m_Nums = new TerminCollection();
        m_Nums.allAddStrsNormalized = true;
        m_Nums.addStr("ОДИН", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ПЕРВЫЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОДИН", 1, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ПЕРШИЙ", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОДНА", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОДНО", 1, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("FIRST", 1 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("SEMEL", 1, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ONE", 1, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ДВА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ВТОРОЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВОЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВУХ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОБА", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ОБЕ", 2, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДРУГИЙ", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВІ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ДВОХ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОБОЄ", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ОБИДВА", 2, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("SECOND", 2 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("BIS", 2, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("TWO", 2, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ТРИ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРЕТИЙ", 3 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРЕХ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРОЕ", 3, com.pullenti.morph.MorphLang.RU, true);
        m_Nums.addStr("ТРИ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРЕТІЙ", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРЬОХ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("ТРОЄ", 3, com.pullenti.morph.MorphLang.UA, true);
        m_Nums.addStr("THIRD", 3 | prilNumTagBit, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("TER", 3, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("THREE", 3, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ЧЕТЫРЕ", 4, null, false);
        m_Nums.addStr("ЧЕТВЕРТЫЙ", 4 | prilNumTagBit, null, false);
        m_Nums.addStr("ЧЕТЫРЕХ", 4, null, false);
        m_Nums.addStr("ЧЕТВЕРО", 4, null, false);
        m_Nums.addStr("ЧОТИРИ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧЕТВЕРТИЙ", 4 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРЬОХ", 4, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FORTH", 4 | prilNumTagBit, null, false);
        m_Nums.addStr("QUATER", 4, null, false);
        m_Nums.addStr("FOUR", 4, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ПЯТЬ", 5, null, false);
        m_Nums.addStr("ПЯТЫЙ", 5 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТИ", 5, null, false);
        m_Nums.addStr("ПЯТЕРО", 5, null, false);
        m_Nums.addStr("ПЯТЬ", 5, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТИЙ", 5 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTH", 5 | prilNumTagBit, null, false);
        m_Nums.addStr("QUINQUIES", 5, null, false);
        m_Nums.addStr("FIVE", 5, com.pullenti.morph.MorphLang.EN, true);
        m_Nums.addStr("ШЕСТЬ", 6, null, false);
        m_Nums.addStr("ШЕСТОЙ", 6 | prilNumTagBit, null, false);
        m_Nums.addStr("ШЕСТИ", 6, null, false);
        m_Nums.addStr("ШЕСТЕРО", 6, null, false);
        m_Nums.addStr("ШІСТЬ", 6, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШОСТИЙ", 6 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIX", 6, com.pullenti.morph.MorphLang.EN, false);
        m_Nums.addStr("SIXTH", 6 | prilNumTagBit, null, false);
        m_Nums.addStr("SEXIES ", 6, null, false);
        m_Nums.addStr("СЕМЬ", 7, null, false);
        m_Nums.addStr("СЕДЬМОЙ", 7 | prilNumTagBit, null, false);
        m_Nums.addStr("СЕМИ", 7, null, false);
        m_Nums.addStr("СЕМЕРО", 7, null, false);
        m_Nums.addStr("СІМ", 7, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЬОМИЙ", 7 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVEN", 7, null, false);
        m_Nums.addStr("SEVENTH", 7 | prilNumTagBit, null, false);
        m_Nums.addStr("SEPTIES", 7, null, false);
        m_Nums.addStr("ВОСЕМЬ", 8, null, false);
        m_Nums.addStr("ВОСЬМОЙ", 8 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЬМИ", 8, null, false);
        m_Nums.addStr("ВОСЬМЕРО", 8, null, false);
        m_Nums.addStr("ВІСІМ", 8, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИЙ", 8 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHT", 8, null, false);
        m_Nums.addStr("EIGHTH", 8 | prilNumTagBit, null, false);
        m_Nums.addStr("OCTIES", 8, null, false);
        m_Nums.addStr("ДЕВЯТЬ", 9, null, false);
        m_Nums.addStr("ДЕВЯТЫЙ", 9 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТИ", 9, null, false);
        m_Nums.addStr("ДЕВЯТЕРО", 9, null, false);
        m_Nums.addStr("ДЕВЯТЬ", 9, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТИЙ", 9 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINE", 9, null, false);
        m_Nums.addStr("NINTH", 9 | prilNumTagBit, null, false);
        m_Nums.addStr("NOVIES", 9, null, false);
        m_Nums.addStr("ДЕСЯТЬ", 10, null, false);
        m_Nums.addStr("ДЕСЯТЫЙ", 10 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕСЯТИ", 10, null, false);
        m_Nums.addStr("ДЕСЯТИРО", 10, null, false);
        m_Nums.addStr("ДЕСЯТЬ", 10, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕСЯТИЙ", 10 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TEN", 10, null, false);
        m_Nums.addStr("TENTH", 10 | prilNumTagBit, null, false);
        m_Nums.addStr("DECIES", 10, null, false);
        m_Nums.addStr("ОДИННАДЦАТЬ", 11, null, false);
        m_Nums.addStr("ОДИНАДЦАТЬ", 11, null, false);
        m_Nums.addStr("ОДИННАДЦАТЫЙ", 11 | prilNumTagBit, null, false);
        m_Nums.addStr("ОДИННАДЦАТИ", 11, null, false);
        m_Nums.addStr("ОДИННАДЦАТИРО", 11, null, false);
        m_Nums.addStr("ОДИНАДЦЯТЬ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ОДИНАДЦЯТИЙ", 11 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ОДИНАДЦЯТИ", 11, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ELEVEN", 11, null, false);
        m_Nums.addStr("ELEVENTH", 11 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВЕНАДЦАТЬ", 12, null, false);
        m_Nums.addStr("ДВЕНАДЦАТЫЙ", 12 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВЕНАДЦАТИ", 12, null, false);
        m_Nums.addStr("ДВАНАДЦЯТЬ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАНАДЦЯТИЙ", 12 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАНАДЦЯТИ", 12, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TWELVE", 12, null, false);
        m_Nums.addStr("TWELFTH", 12 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРИНАДЦАТЬ", 13, null, false);
        m_Nums.addStr("ТРИНАДЦАТЫЙ", 13 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРИНАДЦАТИ", 13, null, false);
        m_Nums.addStr("ТРИНАДЦЯТЬ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИНАДЦЯТИЙ", 13 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИНАДЦЯТИ", 13, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("THIRTEEN", 13, null, false);
        m_Nums.addStr("THIRTEENTH", 13 | prilNumTagBit, null, false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТЬ", 14, null, false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТЫЙ", 14 | prilNumTagBit, null, false);
        m_Nums.addStr("ЧЕТЫРНАДЦАТИ", 14, null, false);
        m_Nums.addStr("ЧОТИРНАДЦЯТЬ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРНАДЦЯТИЙ", 14 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРНАДЦЯТИ", 14, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FOURTEEN", 14, null, false);
        m_Nums.addStr("FOURTEENTH", 14 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТНАДЦАТЬ", 15, null, false);
        m_Nums.addStr("ПЯТНАДЦАТЫЙ", 15 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТНАДЦАТИ", 15, null, false);
        m_Nums.addStr("ПЯТНАДЦЯТЬ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТНАДЦЯТИЙ", 15 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТНАДЦЯТИ", 15, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTEEN", 15, null, false);
        m_Nums.addStr("FIFTEENTH", 15 | prilNumTagBit, null, false);
        m_Nums.addStr("ШЕСТНАДЦАТЬ", 16, null, false);
        m_Nums.addStr("ШЕСТНАДЦАТЫЙ", 16 | prilNumTagBit, null, false);
        m_Nums.addStr("ШЕСТНАДЦАТИ", 16, null, false);
        m_Nums.addStr("ШІСТНАДЦЯТЬ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТНАДЦЯТИЙ", 16 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТНАДЦЯТИ", 16, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIXTEEN", 16, null, false);
        m_Nums.addStr("SIXTEENTH", 16 | prilNumTagBit, null, false);
        m_Nums.addStr("СЕМНАДЦАТЬ", 17, null, false);
        m_Nums.addStr("СЕМНАДЦАТЫЙ", 17 | prilNumTagBit, null, false);
        m_Nums.addStr("СЕМНАДЦАТИ", 17, null, false);
        m_Nums.addStr("СІМНАДЦЯТЬ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМНАДЦЯТИЙ", 17 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМНАДЦЯТИ", 17, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVENTEEN", 17, null, false);
        m_Nums.addStr("SEVENTEENTH", 17 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЕМНАДЦАТЬ", 18, null, false);
        m_Nums.addStr("ВОСЕМНАДЦАТЫЙ", 18 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЕМНАДЦАТИ", 18, null, false);
        m_Nums.addStr("ВІСІМНАДЦЯТЬ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМНАДЦЯТИЙ", 18 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМНАДЦЯТИ", 18, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHTEEN", 18, null, false);
        m_Nums.addStr("EIGHTEENTH", 18 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТНАДЦАТЬ", 19, null, false);
        m_Nums.addStr("ДЕВЯТНАДЦАТЫЙ", 19 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТНАДЦАТИ", 19, null, false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТЬ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТИЙ", 19 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТНАДЦЯТИ", 19, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINETEEN", 19, null, false);
        m_Nums.addStr("NINETEENTH", 19 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВАДЦАТЬ", 20, null, false);
        m_Nums.addStr("ДВАДЦАТЫЙ", 20 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВАДЦАТИ", 20, null, false);
        m_Nums.addStr("ДВАДЦЯТЬ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАДЦЯТИЙ", 20 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВАДЦЯТИ", 20, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("TWENTY", 20, null, false);
        m_Nums.addStr("TWENTIETH", 20 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРИДЦАТЬ", 30, null, false);
        m_Nums.addStr("ТРИДЦАТЫЙ", 30 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРИДЦАТИ", 30, null, false);
        m_Nums.addStr("ТРИДЦЯТЬ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИДЦЯТИЙ", 30 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИДЦЯТИ", 30, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("THIRTY", 30, null, false);
        m_Nums.addStr("THIRTIETH", 30 | prilNumTagBit, null, false);
        m_Nums.addStr("СОРОК", 40, null, false);
        m_Nums.addStr("СОРОКОВОЙ", 40 | prilNumTagBit, null, false);
        m_Nums.addStr("СОРОКА", 40, null, false);
        m_Nums.addStr("СОРОК", 40, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СОРОКОВИЙ", 40 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FORTY", 40, null, false);
        m_Nums.addStr("FORTIETH", 40 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТЬДЕСЯТ", 50, null, false);
        m_Nums.addStr("ПЯТИДЕСЯТЫЙ", 50 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТИДЕСЯТИ", 50, null, false);
        m_Nums.addStr("ПЯТДЕСЯТ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТДЕСЯТИЙ", 50 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТДЕСЯТИ", 50, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("FIFTY", 50, null, false);
        m_Nums.addStr("FIFTIETH", 50 | prilNumTagBit, null, false);
        m_Nums.addStr("ШЕСТЬДЕСЯТ", 60, null, false);
        m_Nums.addStr("ШЕСТИДЕСЯТЫЙ", 60 | prilNumTagBit, null, false);
        m_Nums.addStr("ШЕСТИДЕСЯТИ", 60, null, false);
        m_Nums.addStr("ШІСТДЕСЯТ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСИДЕСЯТЫЙ", 60 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШІСТДЕСЯТИ", 60, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SIXTY", 60, null, false);
        m_Nums.addStr("SIXTIETH", 60 | prilNumTagBit, null, false);
        m_Nums.addStr("СЕМЬДЕСЯТ", 70, null, false);
        m_Nums.addStr("СЕМИДЕСЯТЫЙ", 70 | prilNumTagBit, null, false);
        m_Nums.addStr("СЕМИДЕСЯТИ", 70, null, false);
        m_Nums.addStr("СІМДЕСЯТ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМДЕСЯТИЙ", 70 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СІМДЕСЯТИ", 70, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("SEVENTY", 70, null, false);
        m_Nums.addStr("SEVENTIETH", 70 | prilNumTagBit, null, false);
        m_Nums.addStr("SEVENTIES", 70 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЕМЬДЕСЯТ", 80, null, false);
        m_Nums.addStr("ВОСЬМИДЕСЯТЫЙ", 80 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЬМИДЕСЯТИ", 80, null, false);
        m_Nums.addStr("ВІСІМДЕСЯТ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИДЕСЯТИЙ", 80 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВІСІМДЕСЯТИ", 80, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("EIGHTY", 80, null, false);
        m_Nums.addStr("EIGHTIETH", 80 | prilNumTagBit, null, false);
        m_Nums.addStr("EIGHTIES", 80 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯНОСТО", 90, null, false);
        m_Nums.addStr("ДЕВЯНОСТЫЙ", 90 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯНОСТО", 90, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯНОСТИЙ", 90 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("NINETY", 90, null, false);
        m_Nums.addStr("NINETIETH", 90 | prilNumTagBit, null, false);
        m_Nums.addStr("NINETIES", 90 | prilNumTagBit, null, false);
        m_Nums.addStr("СТО", 100, null, false);
        m_Nums.addStr("СОТЫЙ", 100 | prilNumTagBit, null, false);
        m_Nums.addStr("СТА", 100, null, false);
        m_Nums.addStr("СТО", 100, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СОТИЙ", 100 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("HUNDRED", 100, null, false);
        m_Nums.addStr("HUNDREDTH", 100 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВЕСТИ", 200, null, false);
        m_Nums.addStr("ДВУХСОТЫЙ", 200 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВУХСОТ", 200, null, false);
        m_Nums.addStr("ДВІСТІ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВОХСОТИЙ", 200 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВОХСОТ", 200, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРИСТА", 300, null, false);
        m_Nums.addStr("ТРЕХСОТЫЙ", 300 | prilNumTagBit, null, false);
        m_Nums.addStr("ТРЕХСОТ", 300, null, false);
        m_Nums.addStr("ТРИСТА", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРЬОХСОТИЙ", 300 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТРЬОХСОТ", 300, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧЕТЫРЕСТА", 400, null, false);
        m_Nums.addStr("ЧЕТЫРЕХСОТЫЙ", 400 | prilNumTagBit, null, false);
        m_Nums.addStr("ЧОТИРИСТА", 400, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ЧОТИРЬОХСОТИЙ", 400 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТЬСОТ", 500, null, false);
        m_Nums.addStr("ПЯТИСОТЫЙ", 500 | prilNumTagBit, null, false);
        m_Nums.addStr("ПЯТСОТ", 500, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ПЯТИСОТИЙ", 500 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСТЬСОТ", 600, null, false);
        m_Nums.addStr("ШЕСТИСОТЫЙ", 600 | prilNumTagBit, null, false);
        m_Nums.addStr("ШІСТСОТ", 600, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ШЕСТИСОТИЙ", 600 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЕМЬСОТ", 700, null, false);
        m_Nums.addStr("СЕМИСОТЫЙ", 700 | prilNumTagBit, null, false);
        m_Nums.addStr("СІМСОТ", 700, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("СЕМИСОТИЙ", 700 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЕМЬСОТ", 800, null, false);
        m_Nums.addStr("ВОСЕМЬСОТЫЙ", 800 | prilNumTagBit, null, false);
        m_Nums.addStr("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, null, false);
        m_Nums.addStr("ВІСІМСОТ", 800, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ВОСЬМИСОТЫЙ", 800 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТЬСОТ", 900, null, false);
        m_Nums.addStr("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТИСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТСОТ", 900, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДЕВЯТЬСОТЫЙ", 900 | prilNumTagBit, null, false);
        m_Nums.addStr("ДЕВЯТИСОТИЙ", 900 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТЫС", 1000, null, false);
        m_Nums.addStr("ТЫСЯЧА", 1000, null, false);
        m_Nums.addStr("ТЫСЯЧНЫЙ", 1000 | prilNumTagBit, null, false);
        m_Nums.addStr("ТИС", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТИСЯЧА", 1000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ТИСЯЧНИЙ", 1000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("ДВУХТЫСЯЧНЫЙ", 2000 | prilNumTagBit, null, false);
        m_Nums.addStr("ДВОХТИСЯЧНИЙ", 2000 | prilNumTagBit, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("МИЛЛИОН", 1000000, null, false);
        m_Nums.addStr("МЛН", 1000000, null, false);
        m_Nums.addStr("МІЛЬЙОН", 1000000, com.pullenti.morph.MorphLang.UA, false);
        m_Nums.addStr("МИЛЛИАРД", 1000000000, null, false);
        m_Nums.addStr("МІЛЬЯРД", 1000000000, com.pullenti.morph.MorphLang.UA, false);
        m_AfterPoints = new TerminCollection();
        Termin t = Termin._new117("ПОЛОВИНА", .5);
        t.addVariant("ОДНА ВТОРАЯ", false);
        t.addVariant("ПОЛ", false);
        m_AfterPoints.add(t);
        t = Termin._new117("ТРЕТЬ", .33);
        t.addVariant("ОДНА ТРЕТЬ", false);
        m_AfterPoints.add(t);
        t = Termin._new117("ЧЕТВЕРТЬ", .25);
        t.addVariant("ОДНА ЧЕТВЕРТАЯ", false);
        m_AfterPoints.add(t);
        t = Termin._new117("ПЯТАЯ ЧАСТЬ", .2);
        t.addVariant("ОДНА ПЯТАЯ", false);
        m_AfterPoints.add(t);
    }

    public static TerminCollection m_Nums;

    private static TerminCollection m_AfterPoints;

    public NumberHelper() {
    }
    
    static {
        m_Samples = new String[] {"ДЕСЯТЫЙ", "ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ"};
        m_ManNumberWords = new String[] {"ПЕРВЫЙ", "ВТОРОЙ", "ТРЕТИЙ", "ЧЕТВЕРТЫЙ", "ПЯТЫЙ", "ШЕСТОЙ", "СЕДЬМОЙ", "ВОСЬМОЙ", "ДЕВЯТЫЙ", "ДЕСЯТЫЙ", "ОДИННАДЦАТЫЙ", "ДВЕНАДЦАТЫЙ", "ТРИНАДЦАТЫЙ", "ЧЕТЫРНАДЦАТЫЙ", "ПЯТНАДЦАТЫЙ", "ШЕСТНАДЦАТЫЙ", "СЕМНАДЦАТЫЙ", "ВОСЕМНАДЦАТЫЙ", "ДЕВЯТНАДЦАТЫЙ"};
        m_NeutralNumberWords = new String[] {"ПЕРВОЕ", "ВТОРОЕ", "ТРЕТЬЕ", "ЧЕТВЕРТОЕ", "ПЯТОЕ", "ШЕСТОЕ", "СЕДЬМОЕ", "ВОСЬМОЕ", "ДЕВЯТОЕ", "ДЕСЯТОЕ", "ОДИННАДЦАТОЕ", "ДВЕНАДЦАТОЕ", "ТРИНАДЦАТОЕ", "ЧЕТЫРНАДЦАТОЕ", "ПЯТНАДЦАТОЕ", "ШЕСТНАДЦАТОЕ", "СЕМНАДЦАТОЕ", "ВОСЕМНАДЦАТОЕ", "ДЕВЯТНАДЦАТОЕ"};
        m_WomanNumberWords = new String[] {"ПЕРВАЯ", "ВТОРАЯ", "ТРЕТЬЯ", "ЧЕТВЕРТАЯ", "ПЯТАЯ", "ШЕСТАЯ", "СЕДЬМАЯ", "ВОСЬМАЯ", "ДЕВЯТАЯ", "ДЕСЯТАЯ", "ОДИННАДЦАТАЯ", "ДВЕНАДЦАТАЯ", "ТРИНАДЦАТАЯ", "ЧЕТЫРНАДЦАТАЯ", "ПЯТНАДЦАТАЯ", "ШЕСТНАДЦАТАЯ", "СЕМНАДЦАТАЯ", "ВОСЕМНАДЦАТАЯ", "ДЕВЯТНАДЦАТАЯ"};
        m_PluralNumberWords = new String[] {"ПЕРВЫЕ", "ВТОРЫЕ", "ТРЕТЬИ", "ЧЕТВЕРТЫЕ", "ПЯТЫЕ", "ШЕСТЫЕ", "СЕДЬМЫЕ", "ВОСЬМЫЕ", "ДЕВЯТЫЕ", "ДЕСЯТЫЕ", "ОДИННАДЦАТЫЕ", "ДВЕНАДЦАТЫЕ", "ТРИНАДЦАТЫЕ", "ЧЕТЫРНАДЦАТЫЕ", "ПЯТНАДЦАТЫЕ", "ШЕСТНАДЦАТЫЕ", "СЕМНАДЦАТЫЕ", "ВОСЕМНАДЦАТЫЕ", "ДЕВЯТНАДЦАТЫЕ"};
        m_DecDumberWords = new String[] {"ДВАДЦАТЬ", "ТРИДЦАТЬ", "СОРОК", "ПЯТЬДЕСЯТ", "ШЕСТЬДЕСЯТ", "СЕМЬДЕСЯТ", "ВОСЕМЬДЕСЯТ", "ДЕВЯНОСТО"};
        m_ManDecDumberWords = new String[] {"ДВАДЦАТЫЙ", "ТРИДЦАТЫЙ", "СОРОКОВОЙ", "ПЯТЬДЕСЯТЫЙ", "ШЕСТЬДЕСЯТЫЙ", "СЕМЬДЕСЯТЫЙ", "ВОСЕМЬДЕСЯТЫЙ", "ДЕВЯНОСТЫЙ"};
        m_WomanDecDumberWords = new String[] {"ДВАДЦАТАЯ", "ТРИДЦАТАЯ", "СОРОКОВАЯ", "ПЯТЬДЕСЯТАЯ", "ШЕСТЬДЕСЯТАЯ", "СЕМЬДЕСЯТАЯ", "ВОСЕМЬДЕСЯТАЯ", "ДЕВЯНОСТАЯ"};
        m_NeutralDecDumberWords = new String[] {"ДВАДЦАТОЕ", "ТРИДЦАТОЕ", "СОРОКОВОЕ", "ПЯТЬДЕСЯТОЕ", "ШЕСТЬДЕСЯТОЕ", "СЕМЬДЕСЯТОЕ", "ВОСЕМЬДЕСЯТОЕ", "ДЕВЯНОСТОЕ"};
        m_PluralDecDumberWords = new String[] {"ДВАДЦАТЫЕ", "ТРИДЦАТЫЕ", "СОРОКОВЫЕ", "ПЯТЬДЕСЯТЫЕ", "ШЕСТЬДЕСЯТЫЕ", "СЕМЬДЕСЯТЫЕ", "ВОСЕМЬДЕСЯТЫЕ", "ДЕВЯНОСТЫЕ"};
        m_Romans = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX"};
    }
}
