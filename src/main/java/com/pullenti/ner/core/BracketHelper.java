/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Поддержка анализа скобок и кавычек
 */
public class BracketHelper {

    /**
     * Проверка, что с этого терма может начинаться последовательность
     * @param t проверяемый токен
     * @param quotesOnly должны быть именно кавычка, а не скобка
     * @return 
     */
    public static boolean canBeStartOfSequence(com.pullenti.ner.Token t, boolean quotesOnly, boolean ignoreWhitespaces) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null || tt.getNext() == null) 
            return false;
        char ch = tt.term.charAt(0);
        if (Character.isLetterOrDigit(ch)) 
            return false;
        if (quotesOnly && (m_Quotes.indexOf(ch) < 0)) 
            return false;
        if (t.getNext() == null) 
            return false;
        if (m_OpenChars.indexOf(ch) < 0) 
            return false;
        if (!ignoreWhitespaces) {
            if (t.isWhitespaceAfter()) {
                if (!t.isWhitespaceBefore()) {
                    if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
                    }
                    else 
                        return false;
                }
                if (t.isNewlineAfter()) 
                    return false;
            }
            else if (!t.isWhitespaceBefore()) {
                if (Character.isLetterOrDigit(t.kit.getTextCharacter(t.beginChar - 1))) {
                    if (t.getNext() != null && ((t.getNext().chars.isAllLower() || !t.getNext().chars.isLetter()))) {
                        if (ch != '(') 
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Проверка, что на этом терме может заканчиваться последовательность
     * @param t закрывающая кавычка
     * @param quotesOnly должны быть именно кавычка, а не скобка
     * @param openT это ссылка на токен, который мог быть открывающим
     * @return 
     */
    public static boolean canBeEndOfSequence(com.pullenti.ner.Token t, boolean quotesOnly, com.pullenti.ner.Token openT, boolean ignoreWhitespaces) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        char ch = tt.term.charAt(0);
        if (Character.isLetterOrDigit(ch)) 
            return false;
        if (t.getPrevious() == null) 
            return false;
        if (m_CloseChars.indexOf(ch) < 0) 
            return false;
        if (quotesOnly) {
            if (m_Quotes.indexOf(ch) < 0) 
                return false;
        }
        if (!ignoreWhitespaces) {
            if (!t.isWhitespaceAfter()) {
                if (t.isWhitespaceBefore()) {
                    if (t.getNext() != null && t.getNext().isTableControlChar()) {
                    }
                    else 
                        return false;
                }
                if (t.isNewlineBefore()) 
                    return false;
            }
            else if (t.isWhitespaceBefore()) {
                if (Character.isLetterOrDigit(t.kit.getTextCharacter(t.endChar + 1))) 
                    return false;
                if (!t.isWhitespaceAfter()) 
                    return false;
            }
        }
        if (openT instanceof com.pullenti.ner.TextToken) {
            char ch0 = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(openT, com.pullenti.ner.TextToken.class))).term.charAt(0);
            int i = m_OpenChars.indexOf(ch0);
            if (i < 0) 
                return m_CloseChars.indexOf(ch) < 0;
            int ii = m_CloseChars.indexOf(ch);
            return ii == i;
        }
        return true;
    }

    /**
     * Проверка символа, что он может быть скобкой или кавычкой
     * @param ch 
     * @param quotsOnly 
     * @return 
     */
    public static boolean isBracketChar(char ch, boolean quotsOnly) {
        if (m_OpenChars.indexOf(ch) >= 0 || m_CloseChars.indexOf(ch) >= 0) {
            if (!quotsOnly) 
                return true;
            return m_Quotes.indexOf(ch) >= 0;
        }
        return false;
    }

    /**
     * Проверка токена, что он является скобкой или кавычкой
     * @param t 
     * @param quotsOnly 
     * @return 
     */
    public static boolean isBracket(com.pullenti.ner.Token t, boolean quotsOnly) {
        if (t == null) 
            return false;
        if (t.isCharOf(m_OpenChars)) {
            if (quotsOnly) {
                if (t instanceof com.pullenti.ner.TextToken) {
                    if (m_Quotes.indexOf((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.charAt(0)) < 0) 
                        return false;
                }
            }
            return true;
        }
        if (t.isCharOf(m_CloseChars)) {
            if (quotsOnly) {
                if (t instanceof com.pullenti.ner.TextToken) {
                    if (m_Quotes.indexOf((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.charAt(0)) < 0) 
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    public static class Bracket {
    
        public Bracket(com.pullenti.ner.Token t) {
            source = t;
            if (t instanceof com.pullenti.ner.TextToken) 
                _char = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term.charAt(0);
            canBeOpen = com.pullenti.ner.core.BracketHelper.canBeStartOfSequence(t, false, false);
            canBeClose = com.pullenti.ner.core.BracketHelper.canBeEndOfSequence(t, false, null, false);
        }
    
        public com.pullenti.ner.Token source;
    
        public char _char;
    
        public boolean canBeOpen;
    
        public boolean canBeClose;
    
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append("!").append(_char).append(" ");
            if (canBeOpen) 
                res.append(" Open");
            if (canBeClose) 
                res.append(" Close");
            return res.toString();
        }
        public Bracket() {
        }
    }


    /**
     * Попробовать восстановить последовательность, обрамляемой кавычками
     * @param t 
     * @param typ параметры выделения
     * @param maxTokens максимально токенов (вдруг забыли закрывающую ккавычку)
     * @return 
     */
    public static BracketSequenceToken tryParse(com.pullenti.ner.Token t, BracketParseAttr typ, int maxTokens) {
        com.pullenti.ner.Token t0 = t;
        int cou = 0;
        if (!canBeStartOfSequence(t0, false, false)) 
            return null;
        java.util.ArrayList<Bracket> brList = new java.util.ArrayList<Bracket>();
        brList.add(new Bracket(t0));
        cou = 0;
        int crlf = 0;
        com.pullenti.ner.Token last = null;
        int lev = 1;
        boolean isAssim = brList.get(0)._char != '«' && m_AssymOPenChars.indexOf(brList.get(0)._char) >= 0;
        for (t = t0.getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            last = t;
            if (t.isCharOf(m_OpenChars) || t.isCharOf(m_CloseChars)) {
                if (t.isNewlineBefore() && (((typ.value()) & (BracketParseAttr.CANBEMANYLINES.value()))) == (BracketParseAttr.NO.value())) {
                    if (t.getWhitespacesBeforeCount() > 10 || canBeStartOfSequence(t, false, false)) {
                        if (t.isChar('(') && !t0.isChar('(')) {
                        }
                        else {
                            last = t.getPrevious();
                            break;
                        }
                    }
                }
                Bracket bb = new Bracket(t);
                brList.add(bb);
                if (brList.size() > 20) 
                    break;
                if ((brList.size() == 3 && brList.get(1).canBeOpen && bb.canBeClose) && mustBeCloseChar(bb._char, brList.get(1)._char) && mustBeCloseChar(bb._char, brList.get(0)._char)) {
                    boolean ok = false;
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isNewlineBefore()) 
                            break;
                        if (tt.isChar(',')) 
                            break;
                        if (tt.isChar('.')) {
                            for (tt = tt.getNext(); tt != null; tt = tt.getNext()) {
                                if (tt.isNewlineBefore()) 
                                    break;
                                else if (tt.isCharOf(m_OpenChars) || tt.isCharOf(m_CloseChars)) {
                                    Bracket bb2 = new Bracket(tt);
                                    if (BracketHelper.canBeEndOfSequence(tt, false, null, false) && canBeCloseChar(bb2._char, brList.get(0)._char)) 
                                        ok = true;
                                    break;
                                }
                            }
                            break;
                        }
                        if (t.isCharOf(m_OpenChars) || t.isCharOf(m_CloseChars)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) 
                        break;
                }
                if (isAssim) {
                    if (bb.canBeOpen && !bb.canBeClose && bb._char == brList.get(0)._char) 
                        lev++;
                    else if (bb.canBeClose && !bb.canBeOpen && m_OpenChars.indexOf(brList.get(0)._char) == m_CloseChars.indexOf(bb._char)) {
                        lev--;
                        if (lev == 0) 
                            break;
                    }
                }
            }
            else {
                if ((++cou) > maxTokens) 
                    break;
                if ((((typ.value()) & (BracketParseAttr.CANCONTAINSVERBS.value()))) == (BracketParseAttr.NO.value())) {
                    if (t.getMorph().getLanguage().isCyrillic()) {
                        if (com.pullenti.morph.MorphClass.ooEq(t.getMorphClassInDictionary(), com.pullenti.morph.MorphClass.VERB)) {
                            if (!t.getMorph()._getClass().isAdjective() && !t.getMorph().containsAttr("страд.з.", null)) {
                                if (t.chars.isAllLower()) {
                                    String norm = t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false);
                                    if (!com.pullenti.morph.LanguageHelper.endsWith(norm, "СЯ")) {
                                        if (brList.size() > 1) 
                                            break;
                                        if (brList.get(0)._char != '(') 
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    else if (t.getMorph().getLanguage().isEn()) {
                        if (com.pullenti.morph.MorphClass.ooEq(t.getMorph()._getClass(), com.pullenti.morph.MorphClass.VERB) && t.chars.isAllLower()) 
                            break;
                    }
                    com.pullenti.ner.Referent r = t.getReferent();
                    if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ADDRESS")) {
                        if (!t0.isChar('(')) 
                            break;
                    }
                }
            }
            if ((((typ.value()) & (BracketParseAttr.CANBEMANYLINES.value()))) != (BracketParseAttr.NO.value())) {
                if (t.isNewlineBefore()) {
                    if (t.getNewlinesBeforeCount() > 1) 
                        break;
                    crlf++;
                }
                continue;
            }
            if (t.isNewlineBefore()) {
                if (t.getWhitespacesBeforeCount() > 15) 
                    break;
                crlf++;
                if (!t.chars.isAllLower()) {
                    if (t.getPrevious() != null && t.getPrevious().isChar('.')) 
                        break;
                }
                if ((t.getPrevious() instanceof com.pullenti.ner.MetaToken) && canBeEndOfSequence((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.MetaToken.class))).getEndToken(), false, null, false)) 
                    break;
            }
            if (crlf > 1) {
                if (brList.size() > 1) 
                    break;
                if (crlf > 10) 
                    break;
            }
            if (t.isChar(';') && t.isNewlineAfter()) 
                break;
        }
        if ((brList.size() == 1 && brList.get(0).canBeOpen && (last instanceof com.pullenti.ner.MetaToken)) && last.isNewlineAfter()) {
            if (BracketHelper.canBeEndOfSequence((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(last, com.pullenti.ner.MetaToken.class))).getEndToken(), false, null, false)) 
                return new BracketSequenceToken(t0, last);
        }
        if (brList.size() < 1) 
            return null;
        for (int i = 1; i < (brList.size() - 1); i++) {
            if (brList.get(i)._char == '<' && brList.get(i + 1)._char == '>') {
                brList.get(i).canBeOpen = true;
                brList.get(i + 1).canBeClose = true;
            }
        }
        java.util.ArrayList<BracketSequenceToken> internals = null;
        while (brList.size() > 3) {
            int i = brList.size() - 1;
            if ((brList.get(i).canBeClose && brList.get(i - 1).canBeOpen && !canBeCloseChar(brList.get(i)._char, brList.get(0)._char)) && canBeCloseChar(brList.get(i)._char, brList.get(i - 1)._char)) {
                for(int jjj = brList.size() - 2 + 2 - 1, mmm = brList.size() - 2; jjj >= mmm; jjj--) brList.remove(jjj);
                continue;
            }
            break;
        }
        while (brList.size() >= 4) {
            boolean changed = false;
            for (int i = 1; i < (brList.size() - 2); i++) {
                if ((brList.get(i).canBeOpen && !brList.get(i).canBeClose && brList.get(i + 1).canBeClose) && !brList.get(i + 1).canBeOpen) {
                    boolean ok = false;
                    if (mustBeCloseChar(brList.get(i + 1)._char, brList.get(i)._char) || brList.get(i)._char != brList.get(0)._char) {
                        ok = true;
                        if ((i == 1 && ((i + 2) < brList.size()) && brList.get(i + 2)._char == ')') && brList.get(i + 1)._char != ')' && canBeCloseChar(brList.get(i + 1)._char, brList.get(i - 1)._char)) 
                            com.pullenti.unisharp.Utils.putArrayValue(brList, i + 2, brList.get(i + 1));
                    }
                    else if (i > 1 && ((i + 2) < brList.size()) && mustBeCloseChar(brList.get(i + 2)._char, brList.get(i - 1)._char)) 
                        ok = true;
                    if (ok) {
                        if (internals == null) 
                            internals = new java.util.ArrayList<BracketSequenceToken>();
                        internals.add(new BracketSequenceToken(brList.get(i).source, brList.get(i + 1).source));
                        for(int jjj = i + 2 - 1, mmm = i; jjj >= mmm; jjj--) brList.remove(jjj);
                        changed = true;
                        break;
                    }
                }
            }
            if (!changed) 
                break;
        }
        BracketSequenceToken res = null;
        if ((brList.size() >= 4 && brList.get(1).canBeOpen && brList.get(2).canBeClose) && brList.get(3).canBeClose && !brList.get(3).canBeOpen) {
            if (canBeCloseChar(brList.get(3)._char, brList.get(0)._char)) {
                res = new BracketSequenceToken(brList.get(0).source, brList.get(3).source);
                if (brList.get(0).source.getNext() != brList.get(1).source || brList.get(2).source.getNext() != brList.get(3).source) 
                    res.internal.add(new BracketSequenceToken(brList.get(1).source, brList.get(2).source));
                if (internals != null) 
                    com.pullenti.unisharp.Utils.addToArrayList(res.internal, internals);
            }
        }
        if ((res == null && brList.size() >= 3 && brList.get(2).canBeClose) && !brList.get(2).canBeOpen) {
            if ((((typ.value()) & (BracketParseAttr.NEARCLOSEBRACKET.value()))) != (BracketParseAttr.NO.value())) {
                if (canBeCloseChar(brList.get(1)._char, brList.get(0)._char)) 
                    return new BracketSequenceToken(brList.get(0).source, brList.get(1).source);
            }
            boolean ok = true;
            if (canBeCloseChar(brList.get(2)._char, brList.get(0)._char) && canBeCloseChar(brList.get(1)._char, brList.get(0)._char) && brList.get(1).canBeClose) {
                for (t = brList.get(1).source; t != brList.get(2).source && t != null; t = t.getNext()) {
                    if (t.isNewlineBefore()) {
                        ok = false;
                        break;
                    }
                    if (t.chars.isLetter() && t.chars.isAllLower()) {
                        ok = false;
                        break;
                    }
                    NounPhraseToken npt = NounPhraseHelper.tryParse(t, NounPhraseParseAttr.NO, 0);
                    if (npt != null) 
                        t = npt.getEndToken();
                }
                if (ok) {
                    for (t = brList.get(0).source.getNext(); t != brList.get(1).source && t != null; t = t.getNext()) {
                        if (t.isNewlineBefore()) 
                            return new BracketSequenceToken(brList.get(0).source, t.getPrevious());
                    }
                }
                int lev1 = 0;
                for (com.pullenti.ner.Token tt = brList.get(0).source.getPrevious(); tt != null; tt = tt.getPrevious()) {
                    if (tt.isNewlineAfter() || tt.isTableControlChar()) 
                        break;
                    if (!((tt instanceof com.pullenti.ner.TextToken))) 
                        continue;
                    if (tt.chars.isLetter() || tt.getLengthChar() > 1) 
                        continue;
                    char ch = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class))).term.charAt(0);
                    if (canBeCloseChar(ch, brList.get(0)._char)) 
                        lev1++;
                    else if (canBeCloseChar(brList.get(1)._char, ch)) {
                        lev1--;
                        if (lev1 < 0) 
                            return new BracketSequenceToken(brList.get(0).source, brList.get(1).source);
                    }
                }
            }
            if (ok && canBeCloseChar(brList.get(2)._char, brList.get(0)._char)) {
                BracketSequenceToken intern = new BracketSequenceToken(brList.get(1).source, brList.get(2).source);
                res = new BracketSequenceToken(brList.get(0).source, brList.get(2).source);
                res.internal.add(intern);
            }
            else if (ok && canBeCloseChar(brList.get(2)._char, brList.get(1)._char) && brList.get(0).canBeOpen) {
                if (canBeCloseChar(brList.get(2)._char, brList.get(0)._char)) {
                    BracketSequenceToken intern = new BracketSequenceToken(brList.get(1).source, brList.get(2).source);
                    res = new BracketSequenceToken(brList.get(0).source, brList.get(2).source);
                    res.internal.add(intern);
                }
                else if (brList.size() == 3) 
                    return null;
            }
        }
        if (res == null && brList.size() > 1 && brList.get(1).canBeClose) 
            res = new BracketSequenceToken(brList.get(0).source, brList.get(1).source);
        if (res == null && brList.size() > 1 && canBeCloseChar(brList.get(1)._char, brList.get(0)._char)) 
            res = new BracketSequenceToken(brList.get(0).source, brList.get(1).source);
        if (res == null && brList.size() == 2 && brList.get(0)._char == brList.get(1)._char) 
            res = new BracketSequenceToken(brList.get(0).source, brList.get(1).source);
        if (res != null && internals != null) {
            for (BracketSequenceToken i : internals) {
                if (i.beginChar < res.endChar) 
                    res.internal.add(i);
            }
        }
        if (res == null) {
            cou = 0;
            for (com.pullenti.ner.Token tt = t0.getNext(); tt != null; tt = tt.getNext(),cou++) {
                if (tt.isTableControlChar()) 
                    break;
                if (MiscHelper.canBeStartOfSentence(tt)) 
                    break;
                if (maxTokens > 0 && cou > maxTokens) 
                    break;
                com.pullenti.ner.MetaToken mt = (com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.MetaToken.class);
                if (mt == null) 
                    continue;
                if (mt.getEndToken() instanceof com.pullenti.ner.TextToken) {
                    if ((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(mt.getEndToken(), com.pullenti.ner.TextToken.class))).isCharOf(m_CloseChars)) {
                        Bracket bb = new Bracket((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(mt.getEndToken(), com.pullenti.ner.TextToken.class));
                        if (bb.canBeClose && canBeCloseChar(bb._char, brList.get(0)._char)) 
                            return new BracketSequenceToken(t0, tt);
                    }
                }
            }
        }
        return res;
    }

    private static String m_OpenChars = "\"'`’<{([«“„”";

    private static String m_CloseChars = "\"'`’>})]»”“";

    private static String m_Quotes = "\"'`’«“<”„»>";

    private static String m_AssymOPenChars = "<{([«";

    private static boolean canBeCloseChar(char close, char open) {
        int i = m_OpenChars.indexOf(open);
        if (i < 0) 
            return false;
        int j = m_CloseChars.indexOf(close);
        return i == j;
    }

    private static boolean mustBeCloseChar(char close, char open) {
        if (m_AssymOPenChars.indexOf(open) < 0) 
            return false;
        int i = m_OpenChars.indexOf(open);
        int j = m_CloseChars.indexOf(close);
        return i == j;
    }
    public BracketHelper() {
    }
    public static BracketHelper _globalInstance;
    
    static {
        _globalInstance = new BracketHelper(); 
    }
}
