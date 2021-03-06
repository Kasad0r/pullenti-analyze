/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Разные полезные процедурки
 */
public class MiscHelper {

    /**
     * Сравнение, чтобы не было больше одной ошибки в написании. 
     *  Ошибка - это замена буквы или пропуск буквы.
     * @param value правильное написание
     * @param t проверяемый токен
     * @return 
     */
    public static boolean isNotMoreThanOneError(String value, com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t instanceof com.pullenti.ner.TextToken) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (t.isValue(value, null)) 
                return true;
            if (_isNotMoreThanOneError(value, tt.term, true)) 
                return true;
            for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                if (wf instanceof com.pullenti.morph.MorphWordForm) {
                    if (_isNotMoreThanOneError(value, (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase, true)) 
                        return true;
                }
            }
        }
        else if ((t instanceof com.pullenti.ner.MetaToken) && (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken() == (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getEndToken()) 
            return isNotMoreThanOneError(value, (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken());
        else if (_isNotMoreThanOneError(value, t.getNormalCaseText(null, false, com.pullenti.morph.MorphGender.UNDEFINED, false), true)) 
            return true;
        return false;
    }

    private static boolean _isNotMoreThanOneError(String pattern, String test, boolean tmp) {
        if (test == null || pattern == null) 
            return false;
        if (test.length() == pattern.length()) {
            int cou = 0;
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != test.charAt(i)) {
                    if ((++cou) > 1) 
                        return false;
                }
            }
            return true;
        }
        if (test.length() == (pattern.length() - 1)) {
            int i;
            for (i = 0; i < test.length(); i++) {
                if (pattern.charAt(i) != test.charAt(i)) 
                    break;
            }
            if (i < 2) 
                return false;
            if (i == test.length()) 
                return true;
            for (; i < test.length(); i++) {
                if (pattern.charAt(i + 1) != test.charAt(i)) 
                    return false;
            }
            return true;
        }
        if (!tmp && (test.length() - 1) == pattern.length()) {
            int i;
            for (i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != test.charAt(i)) 
                    break;
            }
            if (i < 2) 
                return false;
            if (i == pattern.length()) 
                return true;
            for (; i < pattern.length(); i++) {
                if (pattern.charAt(i) != test.charAt(i + 1)) 
                    return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Проверить написание слова вразбивку по буквам (например:   П Р И К А З)
     * @param word проверяемое слово
     * @param t начальный токен
     * @param useMorphVariants перебирать ли падежи у слова
     * @return токен последней буквы или null
     */
    public static com.pullenti.ner.Token tryAttachWordByLetters(String word, com.pullenti.ner.Token t, boolean useMorphVariants) {
        com.pullenti.ner.TextToken t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (t1 == null) 
            return null;
        int i = 0;
        int j;
        for (; t1 != null; t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1.getNext(), com.pullenti.ner.TextToken.class)) {
            String s = t1.term;
            for (j = 0; (j < s.length()) && ((i + j) < word.length()); j++) {
                if (word.charAt(i + j) != s.charAt(j)) 
                    break;
            }
            if (j < s.length()) {
                if (!useMorphVariants) 
                    return null;
                if (i < 7) 
                    return null;
                StringBuilder tmp = new StringBuilder();
                tmp.append(word.substring(0, 0 + i));
                for (com.pullenti.ner.Token tt = t1; tt != null; tt = tt.getNext()) {
                    if (!((tt instanceof com.pullenti.ner.TextToken)) || !tt.chars.isLetter() || tt.isNewlineBefore()) 
                        break;
                    t1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(tt, com.pullenti.ner.TextToken.class);
                    tmp.append(t1.term);
                }
                java.util.ArrayList<com.pullenti.morph.MorphToken> li = com.pullenti.morph.Morphology.process(tmp.toString(), t.getMorph().getLanguage(), null);
                if (li != null) {
                    for (com.pullenti.morph.MorphToken l : li) {
                        if (l.wordForms != null) {
                            for (com.pullenti.morph.MorphWordForm wf : l.wordForms) {
                                if (com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, word) || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, word)) 
                                    return t1;
                            }
                        }
                    }
                }
                return null;
            }
            i += j;
            if (i == word.length()) 
                return t1;
        }
        return null;
    }

    /**
     * Сравнение 2-х строк на предмет равенства с учётом морфологии и пунктуации (то есть инвариантно относительно них). 
     *  Функция довольно трудоёмка, не использовать без крайней необходимости. 
     *  ВНИМАНИЕ! Вместо этой функции теперь используйте CanBeEqualsEx.
     * @param s1 первая строка
     * @param s2 вторая строка
     * @param ignoreNonletters игнорировать небуквенные символы
     * @param ignoreCase игнорировать регистр символов
     * @param checkMorphEquAfterFirstNoun после первого существительного слова должны полностью совпадать
     * @return 
     */
    public static boolean canBeEquals(String s1, String s2, boolean ignoreNonletters, boolean ignoreCase, boolean checkMorphEquAfterFirstNoun) {
        CanBeEqualsAttrs attrs = CanBeEqualsAttrs.NO;
        if (ignoreNonletters) 
            attrs = CanBeEqualsAttrs.of((attrs.value()) | (CanBeEqualsAttrs.IGNORENONLETTERS.value()));
        if (ignoreCase) 
            attrs = CanBeEqualsAttrs.of((attrs.value()) | (CanBeEqualsAttrs.IGNOREUPPERCASE.value()));
        if (checkMorphEquAfterFirstNoun) 
            attrs = CanBeEqualsAttrs.of((attrs.value()) | (CanBeEqualsAttrs.CHECKMORPHEQUAFTERFIRSTNOUN.value()));
        return canBeEqualsEx(s1, s2, attrs);
    }

    /**
     * Сравнение 2-х строк на предмет равенства с учётом морфологии и пунктуации (то есть инвариантно относительно них). 
     *  Функция довольно трудоёмка, не использовать без крайней необходимости.
     * @param s1 первая строка
     * @param s2 вторая строка
     * @param attrs дополнительные атрибуты
     * @return 
     */
    public static boolean canBeEqualsEx(String s1, String s2, CanBeEqualsAttrs attrs) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(s1) || com.pullenti.unisharp.Utils.isNullOrEmpty(s2)) 
            return false;
        if (com.pullenti.unisharp.Utils.stringsEq(s1, s2)) 
            return true;
        AnalysisKit ak1 = new AnalysisKit(new com.pullenti.ner.SourceOfAnalysis(s1), false, null, null);
        AnalysisKit ak2 = new AnalysisKit(new com.pullenti.ner.SourceOfAnalysis(s2), false, null, null);
        com.pullenti.ner.Token t1 = ak1.firstToken;
        com.pullenti.ner.Token t2 = ak2.firstToken;
        boolean wasNoun = false;
        while (t1 != null || t2 != null) {
            if (t1 != null) {
                if (t1 instanceof com.pullenti.ner.TextToken) {
                    if (!t1.chars.isLetter()) {
                        if (BracketHelper.isBracket(t1, false) && (((attrs.value()) & (CanBeEqualsAttrs.USEBRACKETS.value()))) != (CanBeEqualsAttrs.NO.value())) {
                        }
                        else {
                            if (t1.isHiphen()) 
                                wasNoun = false;
                            if (((!t1.isCharOf("()") && !t1.isHiphen())) || (((attrs.value()) & (CanBeEqualsAttrs.IGNORENONLETTERS.value()))) != (CanBeEqualsAttrs.NO.value())) {
                                t1 = t1.getNext();
                                continue;
                            }
                        }
                    }
                }
            }
            if (t2 != null) {
                if (t2 instanceof com.pullenti.ner.TextToken) {
                    if (!t2.chars.isLetter()) {
                        if (BracketHelper.isBracket(t2, false) && (((attrs.value()) & (CanBeEqualsAttrs.USEBRACKETS.value()))) != (CanBeEqualsAttrs.NO.value())) {
                        }
                        else {
                            if (t2.isHiphen()) 
                                wasNoun = false;
                            if (((!t2.isCharOf("()") && !t2.isHiphen())) || (((attrs.value()) & (CanBeEqualsAttrs.IGNORENONLETTERS.value()))) != (CanBeEqualsAttrs.NO.value())) {
                                t2 = t2.getNext();
                                continue;
                            }
                        }
                    }
                }
            }
            if (t1 instanceof com.pullenti.ner.NumberToken) {
                if (!((t2 instanceof com.pullenti.ner.NumberToken))) 
                    break;
                if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.NumberToken.class))).getValue(), (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.NumberToken.class))).getValue())) 
                    break;
                t1 = t1.getNext();
                t2 = t2.getNext();
                continue;
            }
            if (!((t1 instanceof com.pullenti.ner.TextToken)) || !((t2 instanceof com.pullenti.ner.TextToken))) 
                break;
            if ((((attrs.value()) & (CanBeEqualsAttrs.IGNOREUPPERCASE.value()))) == (CanBeEqualsAttrs.NO.value())) {
                if (t1.getPrevious() == null && (((attrs.value()) & (CanBeEqualsAttrs.IGNOREUPPERCASEFIRSTWORD.value()))) != (CanBeEqualsAttrs.NO.value())) {
                }
                else if (com.pullenti.morph.CharsInfo.ooNoteq(t1.chars, t2.chars)) 
                    return false;
            }
            if (!t1.chars.isLetter()) {
                boolean bs1 = BracketHelper.canBeStartOfSequence(t1, false, false);
                boolean bs2 = BracketHelper.canBeStartOfSequence(t2, false, false);
                if (bs1 != bs2) 
                    return false;
                if (bs1) {
                    t1 = t1.getNext();
                    t2 = t2.getNext();
                    continue;
                }
                bs1 = BracketHelper.canBeEndOfSequence(t1, false, null, false);
                bs2 = BracketHelper.canBeEndOfSequence(t2, false, null, false);
                if (bs1 != bs2) 
                    return false;
                if (bs1) {
                    t1 = t1.getNext();
                    t2 = t2.getNext();
                    continue;
                }
                if (t1.isHiphen() && t2.isHiphen()) {
                }
                else if (com.pullenti.unisharp.Utils.stringsNe((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.TextToken.class))).term)) 
                    return false;
                t1 = t1.getNext();
                t2 = t2.getNext();
                continue;
            }
            boolean ok = false;
            if (wasNoun && (((attrs.value()) & (CanBeEqualsAttrs.CHECKMORPHEQUAFTERFIRSTNOUN.value()))) != (CanBeEqualsAttrs.NO.value())) {
                if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class))).term, (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.TextToken.class))).term)) 
                    ok = true;
            }
            else {
                com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
                for (com.pullenti.morph.MorphBaseInfo it : tt.getMorph().getItems()) {
                    if (it instanceof com.pullenti.morph.MorphWordForm) {
                        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                        if (t2.isValue(wf.normalCase, null) || t2.isValue(wf.normalFull, null)) {
                            ok = true;
                            break;
                        }
                    }
                }
                if (tt.getMorphClassInDictionary().isNoun()) 
                    wasNoun = true;
                if (!ok && t1.isHiphen() && t2.isHiphen()) 
                    ok = true;
                if (!ok) {
                    if (t2.isValue(tt.term, null) || t2.isValue(tt.lemma, null)) 
                        ok = true;
                }
            }
            if (ok) {
                t1 = t1.getNext();
                t2 = t2.getNext();
                continue;
            }
            break;
        }
        if ((((attrs.value()) & (CanBeEqualsAttrs.FIRSTCANBESHORTER.value()))) != (CanBeEqualsAttrs.NO.value())) {
            if (t1 == null) 
                return true;
        }
        return t1 == null && t2 == null;
    }

    /**
     * Проверка того, может ли здесь начинаться новое предложение
     * @param t токен начала предложения
     * @return 
     */
    public static boolean canBeStartOfSentence(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (t.getPrevious() == null) 
            return true;
        if (!t.isWhitespaceBefore()) {
            if (t.getPrevious() != null && t.getPrevious().isTableControlChar()) {
            }
            else 
                return false;
        }
        if (t.chars.isLetter() && t.chars.isAllLower()) {
            if (t.getPrevious().chars.isLetter() && t.getPrevious().chars.isAllLower()) 
                return false;
            if (((t.getPrevious().isHiphen() || t.getPrevious().isComma())) && !t.getPrevious().isWhitespaceBefore() && t.getPrevious().getPrevious() != null) {
                if (t.getPrevious().getPrevious().chars.isLetter() && t.getPrevious().getPrevious().chars.isAllLower()) 
                    return false;
            }
        }
        if (t.getWhitespacesBeforeCount() > 25 || t.getNewlinesBeforeCount() > 2) 
            return true;
        if (t.getPrevious().isCommaAnd() || t.getPrevious().getMorph()._getClass().isConjunction()) 
            return false;
        if (MiscHelper.isEngArticle(t.getPrevious())) 
            return false;
        if (t.getPrevious().isChar(':')) 
            return false;
        if (t.getPrevious().isChar(';') && t.isNewlineBefore()) 
            return true;
        if (t.getPrevious().isHiphen()) {
            if (t.getPrevious().isNewlineBefore()) 
                return true;
            com.pullenti.ner.Token pp = t.getPrevious().getPrevious();
            if (pp != null && pp.isChar('.')) 
                return true;
        }
        if (t.chars.isLetter() && t.chars.isAllLower()) 
            return false;
        if (t.isNewlineBefore()) 
            return true;
        if (t.getPrevious().isCharOf("!?") || t.getPrevious().isTableControlChar()) 
            return true;
        if (t.getPrevious().isChar('.') || (((t.getPrevious() instanceof com.pullenti.ner.ReferentToken) && (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t.getPrevious(), com.pullenti.ner.ReferentToken.class))).getEndToken().isChar('.')))) {
            if (t.getWhitespacesBeforeCount() > 1) 
                return true;
            if (t.getNext() != null && t.getNext().isChar('.')) {
                if ((t.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken) && t.getPrevious().getPrevious().chars.isAllLower()) {
                }
                else if (t.getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken) {
                }
                else 
                    return false;
            }
            if ((t.getPrevious().getPrevious() instanceof com.pullenti.ner.NumberToken) && t.getPrevious().isWhitespaceBefore()) {
                if ((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t.getPrevious().getPrevious(), com.pullenti.ner.NumberToken.class))).typ != com.pullenti.ner.NumberSpellingType.WORDS) 
                    return false;
            }
            return true;
        }
        if (MiscHelper.isEngArticle(t)) 
            return true;
        return false;
    }

    /**
     * Переместиться на конец предложения
     * @param t токен, с которого идёт поиск
     * @return последний токен предложения (не обязательно точка!)
     */
    public static com.pullenti.ner.Token findEndOfSentence(com.pullenti.ner.Token t) {
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getNext()) {
            if (tt.getNext() == null) 
                return tt;
            else if (canBeStartOfSentence(tt)) 
                return (tt == t ? t : tt.getPrevious());
        }
        return null;
    }

    /**
     * Привязка различных способов написания ключевых слов для номеров (ном., №, рег.номер и пр.)
     * @param t начало префикса
     * @return null, если не префикс, или токен, следующий сразу за префиксом номера
     */
    public static com.pullenti.ner.Token checkNumberPrefix(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        String s = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        com.pullenti.ner.Token t1 = null;
        if (t.isValue("ПО", null) && t.getNext() != null) 
            t = t.getNext();
        if ((((t.isValue("РЕГИСТРАЦИОННЫЙ", "РЕЄСТРАЦІЙНИЙ") || t.isValue("ГОСУДАРСТВЕННЫЙ", "ДЕРЖАВНИЙ") || t.isValue("ТРАНЗИТНЫЙ", "ТРАНЗИТНИЙ")) || t.isValue("ДЕЛО", null) || t.isValue("СПРАВА", null))) && (t.getNext() instanceof com.pullenti.ner.TextToken)) {
            t = t.getNext();
            s = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(s, "РЕГ") || com.pullenti.unisharp.Utils.stringsEq(s, "ГОС") || com.pullenti.unisharp.Utils.stringsEq(s, "ТРАНЗ")) {
            if (t.getNext() != null && t.getNext().isChar('.')) 
                t = t.getNext();
            if (t.getNext() instanceof com.pullenti.ner.TextToken) {
                t = t.getNext();
                s = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
            }
            else 
                return null;
        }
        if ((com.pullenti.unisharp.Utils.stringsEq(s, "НОМЕР") || com.pullenti.unisharp.Utils.stringsEq(s, "№") || com.pullenti.unisharp.Utils.stringsEq(s, "N")) || com.pullenti.unisharp.Utils.stringsEq(s, "NO") || com.pullenti.unisharp.Utils.stringsEq(s, "NN")) {
            t1 = t.getNext();
            if (t1 != null && ((t1.isCharOf("°№") || t1.isValue("О", null)))) 
                t1 = t1.getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
            if (t1 != null && t1.isChar(':')) 
                t1 = t1.getNext();
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(s, "НОМ")) {
            t1 = t.getNext();
            if (t1 != null && t1.isChar('.')) 
                t1 = t1.getNext();
        }
        while (t1 != null) {
            if (t1.isValue("ЗАПИСЬ", null)) 
                t1 = t1.getNext();
            else if (t1.isValue("В", null) && t1.getNext() != null && t1.getNext().isValue("РЕЕСТР", null)) 
                t1 = t1.getNext().getNext();
            else 
                break;
        }
        return t1;
    }

    public static String _corrXmlText(String txt) {
        if (txt == null) 
            return "";
        for (char c : txt.toCharArray()) {
            if (((((int)c) < 0x20) && c != '\r' && c != '\n') && c != '\t') {
                StringBuilder tmp = new StringBuilder(txt);
                for (int i = 0; i < tmp.length(); i++) {
                    char ch = tmp.charAt(i);
                    if (((((int)ch) < 0x20) && ch != '\r' && ch != '\n') && ch != '\t') 
                        tmp.setCharAt(i, ' ');
                }
                return tmp.toString();
            }
        }
        return txt;
    }

    /**
     * Преобразовать строку чтобы первая буква стала большой, остальные маленькие
     * @param str 
     * @return 
     */
    public static String convertFirstCharUpperAndOtherLower(String str) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return str;
        StringBuilder fStrTmp = new StringBuilder();
        fStrTmp.append(str.toLowerCase());
        int i;
        boolean up = true;
        com.pullenti.unisharp.Utils.replace(fStrTmp, " .", ".");
        for (i = 0; i < fStrTmp.length(); i++) {
            if (Character.isLetter(fStrTmp.charAt(i))) {
                if (up) {
                    if (((i + 1) >= fStrTmp.length() || Character.isLetter(fStrTmp.charAt(i + 1)) || ((fStrTmp.charAt(i + 1) == '.' || fStrTmp.charAt(i + 1) == '-'))) || i == 0) 
                        fStrTmp.setCharAt(i, Character.toUpperCase(fStrTmp.charAt(i)));
                }
                up = false;
            }
            else if (!Character.isDigit(fStrTmp.charAt(i))) 
                up = true;
        }
        com.pullenti.unisharp.Utils.replace(fStrTmp, " - ", "-");
        return fStrTmp.toString();
    }

    /**
     * Сделать аббревиатуру для строки из нескольких слов
     * @param name 
     * @return 
     */
    public static String getAbbreviation(String name) {
        StringBuilder abbr = new StringBuilder();
        int i;
        int j;
        for (i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) 
                break;
            else if (Character.isLetter(name.charAt(i))) {
                for (j = i + 1; j < name.length(); j++) {
                    if (!Character.isLetter(name.charAt(j))) 
                        break;
                }
                if ((j - i) > 2) {
                    String w = name.substring(i, i + j - i);
                    if (com.pullenti.unisharp.Utils.stringsNe(w, "ПРИ")) 
                        abbr.append(name.charAt(i));
                }
                i = j;
            }
        }
        if (abbr.length() < 2) 
            return null;
        return abbr.toString().toUpperCase();
    }

    /**
     * Получить аббревиатуру (уже не помню, какую именно...)
     * @param name 
     * @return 
     */
    public static String getTailAbbreviation(String name) {
        int i;
        int j = 0;
        for (i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') 
                j++;
        }
        if (j < 2) 
            return null;
        char a0 = (char)0;
        char a1 = (char)0;
        j = 0;
        for (i = name.length() - 2; i > 0; i--) {
            if (name.charAt(i) == ' ') {
                int le = 0;
                for (int jj = i + 1; jj < name.length(); jj++) {
                    if (name.charAt(jj) == ' ') 
                        break;
                    else 
                        le++;
                }
                if (le < 4) 
                    break;
                if (j == 0) 
                    a1 = name.charAt(i + 1);
                else if (j == 1) {
                    a0 = name.charAt(i + 1);
                    if (Character.isLetter(a0) && Character.isLetter(a1)) 
                        return (name.substring(0, 0 + i) + " " + a0 + a1);
                    break;
                }
                j++;
            }
        }
        return null;
    }

    /**
     * Попытка через транслитеральную замену сделать альтернативное написание строки 
     *  Например, А-10 => A-10  (здесь латиница и кириллица)
     * @param str 
     * @return если null, то не получается
     */
    public static String createCyrLatAlternative(String str) {
        if (str == null) 
            return null;
        int cyr = 0;
        int cyrToLat = 0;
        int lat = 0;
        int latToCyr = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (com.pullenti.morph.LanguageHelper.isLatinChar(ch)) {
                lat++;
                if (com.pullenti.morph.LanguageHelper.getCyrForLat(ch) != ((char)0)) 
                    latToCyr++;
            }
            else if (com.pullenti.morph.LanguageHelper.isCyrillicChar(ch)) {
                cyr++;
                if (com.pullenti.morph.LanguageHelper.getLatForCyr(ch) != ((char)0)) 
                    cyrToLat++;
            }
        }
        if (cyr > 0 && cyrToLat == cyr) {
            if (lat > 0) 
                return null;
            StringBuilder tmp = new StringBuilder(str);
            for (int i = 0; i < tmp.length(); i++) {
                if (com.pullenti.morph.LanguageHelper.isCyrillicChar(tmp.charAt(i))) 
                    tmp.setCharAt(i, com.pullenti.morph.LanguageHelper.getLatForCyr(tmp.charAt(i)));
            }
            return tmp.toString();
        }
        if (lat > 0 && latToCyr == lat) {
            if (cyr > 0) 
                return null;
            StringBuilder tmp = new StringBuilder(str);
            for (int i = 0; i < tmp.length(); i++) {
                if (com.pullenti.morph.LanguageHelper.isLatinChar(tmp.charAt(i))) 
                    tmp.setCharAt(i, com.pullenti.morph.LanguageHelper.getCyrForLat(tmp.charAt(i)));
            }
            return tmp.toString();
        }
        return null;
    }

    /**
     * Преобразовать слово, написанное по латыни, в варианты на русском языке. 
     *  Например, "Mikhail" -> "Михаил"
     * @param str Строка на латыни
     * @return Варианты на русском языке
     */
    public static java.util.ArrayList<String> convertLatinWordToRussianVariants(String str) {
        return _ConvertWord(str, true);
    }

    /**
     * Преобразовать слово, написанное в кириллице, в варианты на латинице.
     * @param str Строка на кириллице
     * @return Варианты на латинице
     */
    public static java.util.ArrayList<String> convertRussianWordToLatinVariants(String str) {
        return _ConvertWord(str, false);
    }

    private static java.util.ArrayList<String> _ConvertWord(String str, boolean latinToRus) {
        if (str == null) 
            return null;
        if (str.length() == 0) 
            return null;
        str = str.toUpperCase();
        java.util.ArrayList<String> res = new java.util.ArrayList<String>();
        java.util.ArrayList<java.util.ArrayList<String>> vars = new java.util.ArrayList<java.util.ArrayList<String>>();
        int i;
        int j;
        for (i = 0; i < str.length(); i++) {
            java.util.ArrayList<String> v = new java.util.ArrayList<String>();
            if (latinToRus) 
                j = com.pullenti.ner.core.internal.RusLatAccord.findAccordsLatToRus(str, i, v);
            else 
                j = com.pullenti.ner.core.internal.RusLatAccord.findAccordsRusToLat(str, i, v);
            if (j < 1) {
                j = 1;
                v.add(str.substring(i, i + 1));
            }
            vars.add(v);
            i += (j - 1);
        }
        if (latinToRus && ("AEIJOUY".indexOf(str.charAt(str.length() - 1)) < 0)) {
            java.util.ArrayList<String> v = new java.util.ArrayList<String>();
            v.add("");
            v.add("Ь");
            vars.add(v);
        }
        StringBuilder fStrTmp = new StringBuilder();
        java.util.ArrayList<Integer> inds = new java.util.ArrayList<Integer>(vars.size());
        for (i = 0; i < vars.size(); i++) {
            inds.add(0);
        }
        while (true) {
            fStrTmp.setLength(0);
            for (i = 0; i < vars.size(); i++) {
                if (vars.get(i).size() > 0) 
                    fStrTmp.append(vars.get(i).get(inds.get(i)));
            }
            res.add(fStrTmp.toString());
            for (i = inds.size() - 1; i >= 0; i--) {
                com.pullenti.unisharp.Utils.putArrayValue(inds, i, inds.get(i) + 1);
                if (inds.get(i) < vars.get(i).size()) 
                    break;
                com.pullenti.unisharp.Utils.putArrayValue(inds, i, 0);
            }
            if (i < 0) 
                break;
        }
        return res;
    }

    /**
     * Получение абсолютного нормализованного значения (с учётом гласных, удалением невидимых знаков и т.п.). 
     *  Используется для сравнений различных вариантов написаний. 
     *  Преобразования:  гласные заменяются на *, Щ на Ш, Х на Г, одинаковые соседние буквы сливаются, 
     *  Ъ и Ь выбрасываются. 
     *  Например, ХАБИБУЛЛИН -  Г*Б*Б*Л*Н
     * @param str страка
     * @return если null, то не удалось нормализовать (слишком короткий)
     */
    public static String getAbsoluteNormalValue(String str, boolean getAlways) {
        StringBuilder res = new StringBuilder();
        int k = 0;
        for (int i = 0; i < str.length(); i++) {
            if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(str.charAt(i)) || str.charAt(i) == 'Й' || com.pullenti.morph.LanguageHelper.isLatinVowel(str.charAt(i))) {
                if (res.length() > 0 && res.charAt(res.length() - 1) == '*') {
                }
                else 
                    res.append('*');
            }
            else if (str.charAt(i) != 'Ь' && str.charAt(i) != 'Ъ') {
                char ch = str.charAt(i);
                if (ch == 'Щ') 
                    ch = 'Ш';
                if (ch == 'Х') 
                    ch = 'Г';
                if (ch == ' ') 
                    ch = '-';
                res.append(ch);
                k++;
            }
        }
        if (res.length() > 0 && res.charAt(res.length() - 1) == '*') 
            res.setLength(res.length() - 1);
        for (int i = res.length() - 1; i > 0; i--) {
            if (res.charAt(i) == res.charAt(i - 1) && res.charAt(i) != '*') 
                res.delete(i, i + 1);
        }
        for (int i = res.length() - 1; i > 0; i--) {
            if (res.charAt(i - 1) == '*' && res.charAt(i) == '-') 
                res.delete(i - 1, i - 1 + 1);
        }
        if (!getAlways) {
            if ((res.length() < 3) || (k < 2)) 
                return null;
        }
        return res.toString();
    }

    /**
     * Проверка, что хотя бы одно из слов внутри заданного диапазона находится в морфологическом словаре
     * @param begin 
     * @param end 
     * @param cla 
     * @return 
     */
    public static boolean isExistsInDictionary(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, com.pullenti.morph.MorphClass cla) {
        boolean ret = false;
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt != null) {
                if (tt.isHiphen()) 
                    ret = false;
                for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
                    if (cla.value == ((short)0) || ((((int)cla.value) & ((int)wf._getClass().value))) != 0) {
                        if ((wf instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).isInDictionary()) {
                            ret = true;
                            break;
                        }
                    }
                }
            }
            if (t == end) 
                break;
        }
        return ret;
    }

    /**
     * Проверка, что все в заданном диапазоне в нижнем регистре
     * @param begin 
     * @param end 
     * @param errorIfNotText 
     * @return 
     */
    public static boolean isAllCharactersLower(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, boolean errorIfNotText) {
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) {
                if (errorIfNotText) 
                    return false;
            }
            else if (!tt.chars.isAllLower()) 
                return false;
            if (t == end) 
                break;
        }
        return true;
    }

    /**
     * Текстовой токен должен иметь гласную
     * @param t токен
     * @return 
     */
    public static boolean hasVowel(com.pullenti.ner.TextToken t) {
        if (t == null) 
            return false;
        String tmp = java.text.Normalizer.normalize(t.term, java.text.Normalizer.Form.NFD);
        for (char ch : tmp.toCharArray()) {
            if (com.pullenti.morph.LanguageHelper.isCyrillicVowel(ch) || com.pullenti.morph.LanguageHelper.isLatinVowel(ch)) 
                return true;
        }
        return false;
    }

    /**
     * Проверка акронима, что из первых букв слов диапазона может получиться проверяемый акроним. 
     *  Например,  РФ = Российская Федерация, ГосПлан = государственный план
     * @param acr акроним
     * @param begin начало диапазона
     * @param end конец диапазона
     * @return 
     */
    public static boolean testAcronym(com.pullenti.ner.Token acr, com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (!((acr instanceof com.pullenti.ner.TextToken))) 
            return false;
        if (begin == null || end == null || begin.endChar >= end.beginChar) 
            return false;
        String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(acr, com.pullenti.ner.TextToken.class))).term;
        int i = 0;
        for (com.pullenti.ner.Token t = begin; t != null && t.getPrevious() != end; t = t.getNext()) {
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (tt == null) 
                break;
            if (i >= str.length()) 
                return false;
            String s = tt.term;
            if (s.charAt(0) != str.charAt(i)) 
                return false;
            i++;
        }
        if (i >= str.length()) 
            return true;
        return false;
    }

    public static class CyrLatWord {
    
        public String cyrWord;
    
        public String latWord;
    
        @Override
        public String toString() {
            if (cyrWord != null && latWord != null) 
                return (cyrWord + "\\" + latWord);
            else if (cyrWord != null) 
                return cyrWord;
            else 
                return (latWord != null ? latWord : "?");
        }
    
        public int getLength() {
            return (cyrWord != null ? cyrWord.length() : ((latWord != null ? latWord.length() : 0)));
        }
    
        public CyrLatWord() {
        }
    }


    /**
     * Получить вариант на кириллице и\или латинице
     * @param t 
     * @param maxLen 
     * @return 
     */
    public static CyrLatWord getCyrLatWord(com.pullenti.ner.Token t, int maxLen) {
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) {
            com.pullenti.ner.ReferentToken rt = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class);
            if ((rt != null && (rt.getLengthChar() < 3) && rt.getBeginToken() == rt.getEndToken()) && (rt.getBeginToken() instanceof com.pullenti.ner.TextToken)) 
                tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(rt.getBeginToken(), com.pullenti.ner.TextToken.class);
            else 
                return null;
        }
        if (!tt.chars.isLetter()) 
            return null;
        String str = tt.getSourceText();
        if (maxLen > 0 && str.length() > maxLen) 
            return null;
        StringBuilder cyr = new StringBuilder();
        StringBuilder lat = new StringBuilder();
        for (char s : str.toCharArray()) {
            if (com.pullenti.morph.LanguageHelper.isLatinChar(s)) {
                if (lat != null) 
                    lat.append(s);
                int i = m_Lat.indexOf(s);
                if (i < 0) 
                    cyr = null;
                else if (cyr != null) 
                    cyr.append(m_Cyr.charAt(i));
            }
            else if (com.pullenti.morph.LanguageHelper.isCyrillicChar(s)) {
                if (cyr != null) 
                    cyr.append(s);
                int i = m_Cyr.indexOf(s);
                if (i < 0) 
                    lat = null;
                else if (lat != null) 
                    lat.append(m_Lat.charAt(i));
            }
            else 
                return null;
        }
        if (cyr == null && lat == null) 
            return null;
        CyrLatWord res = new CyrLatWord();
        if (cyr != null) 
            res.cyrWord = cyr.toString().toUpperCase();
        if (lat != null) 
            res.latWord = lat.toString().toUpperCase();
        return res;
    }

    private static String m_Cyr = "АВДЕКМНОРСТХаекорсух";

    private static String m_Lat = "ABDEKMHOPCTXaekopcyx";

    /**
     * Проверка на возможную эквивалентность русского и латинского написания одного и того же слова
     * @param t 
     * @param str 
     * @return 
     */
    public static boolean canBeEqualCyrAndLatTS(com.pullenti.ner.Token t, String str) {
        if (t == null || com.pullenti.unisharp.Utils.isNullOrEmpty(str)) 
            return false;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
        if (tt == null) 
            return false;
        if (canBeEqualCyrAndLatSS(tt.term, str)) 
            return true;
        for (com.pullenti.morph.MorphBaseInfo wf : tt.getMorph().getItems()) {
            if ((wf instanceof com.pullenti.morph.MorphWordForm) && canBeEqualCyrAndLatSS((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase, str)) 
                return true;
        }
        return false;
    }

    /**
     * Проверка на возможную эквивалентность русского и латинского написания одного и того же слова. 
     *  Например,  ИКЕЯ ? IKEA
     * @param t1 токен на одном языке
     * @param t2 токен на другом языке
     * @return 
     */
    public static boolean canBeEqualCyrAndLatTT(com.pullenti.ner.Token t1, com.pullenti.ner.Token t2) {
        com.pullenti.ner.TextToken tt1 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t1, com.pullenti.ner.TextToken.class);
        com.pullenti.ner.TextToken tt2 = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t2, com.pullenti.ner.TextToken.class);
        if (tt1 == null || tt2 == null) 
            return false;
        if (canBeEqualCyrAndLatTS(t2, tt1.term)) 
            return true;
        for (com.pullenti.morph.MorphBaseInfo wf : tt1.getMorph().getItems()) {
            if ((wf instanceof com.pullenti.morph.MorphWordForm) && canBeEqualCyrAndLatTS(t2, (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(wf, com.pullenti.morph.MorphWordForm.class))).normalCase)) 
                return true;
        }
        return false;
    }

    /**
     * Проверка на возможную эквивалентность русского и латинского написания одного и того же слова. 
     *  Например,  ИКЕЯ ? IKEA
     * @param str1 слово на одном языке
     * @param str2 слово на другом языке
     * @return 
     */
    public static boolean canBeEqualCyrAndLatSS(String str1, String str2) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(str1) || com.pullenti.unisharp.Utils.isNullOrEmpty(str2)) 
            return false;
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(str1.charAt(0)) && com.pullenti.morph.LanguageHelper.isLatinChar(str2.charAt(0))) 
            return com.pullenti.ner.core.internal.RusLatAccord.canBeEquals(str1, str2);
        if (com.pullenti.morph.LanguageHelper.isCyrillicChar(str2.charAt(0)) && com.pullenti.morph.LanguageHelper.isLatinChar(str1.charAt(0))) 
            return com.pullenti.ner.core.internal.RusLatAccord.canBeEquals(str2, str1);
        return false;
    }

    /**
     * Получить текст, покрываемый метатокеном
     * @param mt метатокен
     * @param attrs атрибуты преобразования текста
     * @return результат
     */
    public static String getTextValueOfMetaToken(com.pullenti.ner.MetaToken mt, GetTextAttr attrs) {
        if (mt == null) 
            return null;
        return _getTextValue_(mt.getBeginToken(), mt.getEndToken(), attrs, mt.getReferent());
    }

    /**
     * Получить текст, задаваемый диапазоном токенов
     * @param begin начальный токен
     * @param end конечный токен
     * @param attrs атрибуты преобразования текста
     * @return результат
     */
    public static String getTextValue(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, GetTextAttr attrs) {
        return _getTextValue_(begin, end, attrs, null);
    }

    private static String _getTextValue_(com.pullenti.ner.Token begin, com.pullenti.ner.Token end, GetTextAttr attrs, com.pullenti.ner.Referent r) {
        if (begin == null || end == null || begin.endChar > end.endChar) 
            return null;
        if ((((attrs.value()) & (GetTextAttr.KEEPQUOTES.value()))) == (GetTextAttr.NO.value())) {
            for (; begin != null && begin.endChar <= end.endChar; begin = begin.getNext()) {
                if (BracketHelper.isBracket(begin, true)) {
                }
                else 
                    break;
            }
        }
        StringBuilder res = new StringBuilder();
        if ((begin instanceof com.pullenti.ner.MetaToken) && !((begin instanceof com.pullenti.ner.NumberToken))) {
            String str = _getTextValue_((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(begin, com.pullenti.ner.MetaToken.class))).getBeginToken(), (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(begin, com.pullenti.ner.MetaToken.class))).getEndToken(), attrs, begin.getReferent());
            if (str != null) {
                if (end == begin) 
                    return str;
                if ((end instanceof com.pullenti.ner.MetaToken) && !((end instanceof com.pullenti.ner.NumberToken)) && begin.getNext() == end) {
                    if ((((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) || (((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value())) {
                        GetTextAttr attrs1 = attrs;
                        if ((((attrs1.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value())) 
                            attrs1 = GetTextAttr.of((attrs1.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
                        if ((((attrs1.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value())) 
                            attrs1 = GetTextAttr.of((attrs1.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
                        String str0 = _getTextValue_((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(begin, com.pullenti.ner.MetaToken.class))).getBeginToken(), (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(begin, com.pullenti.ner.MetaToken.class))).getEndToken(), attrs1, begin.getReferent());
                        String str1 = _getTextValue_((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(end, com.pullenti.ner.MetaToken.class))).getBeginToken(), (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(end, com.pullenti.ner.MetaToken.class))).getEndToken(), attrs1, begin.getReferent());
                        com.pullenti.ner.AnalysisResult ar0 = com.pullenti.ner.ProcessorService.getEmptyProcessor().process(new com.pullenti.ner.SourceOfAnalysis((str0 + " " + str1)), null, null);
                        NounPhraseToken npt1 = NounPhraseHelper.tryParse(ar0.firstToken, NounPhraseParseAttr.NO, 0);
                        if (npt1 != null && npt1.getEndToken().getNext() == null) 
                            return _getTextValue_(npt1.getBeginToken(), npt1.getEndToken(), attrs, r);
                    }
                }
                res.append(str);
                begin = begin.getNext();
                if ((((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value())) 
                    attrs = GetTextAttr.of((attrs.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
                if ((((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value())) 
                    attrs = GetTextAttr.of((attrs.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
            }
        }
        boolean keepChars = (((attrs.value()) & (GetTextAttr.KEEPREGISTER.value()))) != (GetTextAttr.NO.value());
        if (keepChars) {
        }
        int restoreCharsEndPos = -1;
        if ((((attrs.value()) & (GetTextAttr.RESTOREREGISTER.value()))) != (GetTextAttr.NO.value())) {
            if (!hasNotAllUpper(begin, end)) 
                restoreCharsEndPos = end.endChar;
            else 
                for (com.pullenti.ner.Token tt1 = begin; tt1 != null && (tt1.endChar < end.endChar); tt1 = tt1.getNext()) {
                    if (tt1.isNewlineAfter()) {
                        if (!hasNotAllUpper(begin, tt1)) 
                            restoreCharsEndPos = tt1.endChar;
                        break;
                    }
                }
        }
        if ((((attrs.value()) & (((GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()) | (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))))) != (GetTextAttr.NO.value())) {
            NounPhraseToken npt = NounPhraseHelper.tryParse(begin, NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.endChar <= end.endChar) {
                String str = npt.getNormalCaseText(null, (((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))) != (GetTextAttr.NO.value()), npt.getMorph().getGender(), keepChars);
                if (str != null) {
                    begin = npt.getEndToken().getNext();
                    res.append(str);
                    com.pullenti.ner.Token te = npt.getEndToken().getNext();
                    if (((te != null && te.getNext() != null && te.isComma()) && (te.getNext() instanceof com.pullenti.ner.TextToken) && te.getNext().endChar <= end.endChar) && te.getNext().getMorph()._getClass().isVerb() && te.getNext().getMorph()._getClass().isAdjective()) {
                        for (com.pullenti.morph.MorphBaseInfo it : te.getNext().getMorph().getItems()) {
                            if (it.getGender() == npt.getMorph().getGender() || (((it.getGender().value()) & (npt.getMorph().getGender().value()))) != (com.pullenti.morph.MorphGender.UNDEFINED.value())) {
                                if (!((com.pullenti.morph.MorphCase.ooBitand(it.getCase(), npt.getMorph().getCase()))).isUndefined()) {
                                    if (it.getNumber() == npt.getMorph().getNumber() || (((it.getNumber().value()) & (npt.getMorph().getNumber().value()))) != (com.pullenti.morph.MorphNumber.UNDEFINED.value())) {
                                        String var = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(te.getNext(), com.pullenti.ner.TextToken.class))).term;
                                        if (it instanceof com.pullenti.morph.MorphWordForm) 
                                            var = (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class))).normalCase;
                                        com.pullenti.morph.MorphBaseInfo bi = com.pullenti.morph.MorphBaseInfo._new549(com.pullenti.morph.MorphClass.ADJECTIVE, npt.getMorph().getGender(), npt.getMorph().getNumber(), npt.getMorph().getLanguage());
                                        var = com.pullenti.morph.Morphology.getWordform(var, bi);
                                        if (var != null) {
                                            var = corrChars(var, te.getNext().chars, keepChars, (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(te.getNext(), com.pullenti.ner.TextToken.class));
                                            res.append(", ").append(var);
                                            te = te.getNext().getNext();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    begin = te;
                }
            }
            if ((((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value())) 
                attrs = GetTextAttr.of((attrs.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE.value()));
            if ((((attrs.value()) & (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()))) == (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value())) 
                attrs = GetTextAttr.of((attrs.value()) ^ (GetTextAttr.FIRSTNOUNGROUPTONOMINATIVESINGLE.value()));
        }
        if (begin == null || begin.endChar > end.endChar) 
            return res.toString();
        for (com.pullenti.ner.Token t = begin; t != null && t.endChar <= end.endChar; t = t.getNext()) {
            char last = (res.length() > 0 ? res.charAt(res.length() - 1) : ' ');
            if (t.isWhitespaceBefore() && res.length() > 0) {
                if (t.isHiphen() && t.isWhitespaceAfter() && last != ' ') {
                    res.append(" - ");
                    continue;
                }
                if ((last != ' ' && !t.isHiphen() && last != '-') && !BracketHelper.canBeStartOfSequence(t.getPrevious(), false, false)) 
                    res.append(' ');
            }
            if (t.isTableControlChar()) {
                if (res.length() > 0 && res.charAt(res.length() - 1) == ' ') {
                }
                else 
                    res.append(' ');
                continue;
            }
            if ((((attrs.value()) & (GetTextAttr.IGNOREARTICLES.value()))) != (GetTextAttr.NO.value())) {
                if (isEngAdjSuffix(t)) {
                    t = t.getNext();
                    continue;
                }
                if (isEngArticle(t)) {
                    if (t.isWhitespaceAfter()) 
                        continue;
                }
            }
            if ((((attrs.value()) & (GetTextAttr.KEEPQUOTES.value()))) == (GetTextAttr.NO.value())) {
                if (BracketHelper.isBracket(t, true)) {
                    if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') 
                        res.append(' ');
                    continue;
                }
            }
            if ((((attrs.value()) & (GetTextAttr.IGNOREGEOREFERENT.value()))) != (GetTextAttr.NO.value())) {
                if ((t instanceof com.pullenti.ner.ReferentToken) && t.getReferent() != null) {
                    if (com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "GEO")) 
                        continue;
                }
            }
            if (t instanceof com.pullenti.ner.NumberToken) {
                if ((((attrs.value()) & (GetTextAttr.NORMALIZENUMBERS.value()))) != (GetTextAttr.NO.value())) {
                    if (res.length() > 0 && Character.isDigit(res.charAt(res.length() - 1))) 
                        res.append(' ');
                    res.append((((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.NumberToken.class))).getValue());
                    continue;
                }
            }
            if (t instanceof com.pullenti.ner.MetaToken) {
                String str = _getTextValue_((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(), (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getEndToken(), attrs, t.getReferent());
                if (!com.pullenti.unisharp.Utils.isNullOrEmpty(str)) {
                    if (Character.isDigit(str.charAt(0)) && res.length() > 0 && Character.isDigit(res.charAt(res.length() - 1))) 
                        res.append(' ');
                    res.append(str);
                }
                else 
                    res.append(t.getSourceText());
                continue;
            }
            if (!((t instanceof com.pullenti.ner.TextToken))) {
                res.append(t.getSourceText());
                continue;
            }
            if (t.chars.isLetter()) {
                String str = (t.endChar <= restoreCharsEndPos ? restChars((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class), r) : corrChars((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term, t.chars, keepChars, (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class)));
                res.append(str);
                continue;
            }
            if (last == ' ' && res.length() > 0) {
                if (((t.isHiphen() && !t.isWhitespaceAfter())) || t.isCharOf(",.;!?") || BracketHelper.canBeEndOfSequence(t, false, null, false)) 
                    res.setLength(res.length() - 1);
            }
            if (t.isHiphen()) {
                res.append('-');
                if (t.isWhitespaceBefore() && t.isWhitespaceAfter()) 
                    res.append(' ');
            }
            else 
                res.append((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
        }
        for (int i = res.length() - 1; i >= 0; i--) {
            if (res.charAt(i) == '*' || com.pullenti.unisharp.Utils.isWhitespace(res.charAt(i))) 
                res.setLength(res.length() - 1);
            else if (res.charAt(i) == '>' && (((attrs.value()) & (GetTextAttr.KEEPQUOTES.value()))) == (GetTextAttr.NO.value())) {
                if (res.charAt(0) == '<') {
                    res.setLength(res.length() - 1);
                    res.delete(0, 0 + 1);
                    i--;
                }
                else if (begin.getPrevious() != null && begin.getPrevious().isChar('<')) 
                    res.setLength(res.length() - 1);
                else 
                    break;
            }
            else if (res.charAt(i) == ')' && (((attrs.value()) & (GetTextAttr.KEEPQUOTES.value()))) == (GetTextAttr.NO.value())) {
                if (res.charAt(0) == '(') {
                    res.setLength(res.length() - 1);
                    res.delete(0, 0 + 1);
                    i--;
                }
                else if (begin.getPrevious() != null && begin.getPrevious().isChar('(')) 
                    res.setLength(res.length() - 1);
                else 
                    break;
            }
            else 
                break;
        }
        return res.toString();
    }

    /**
     * Проверка, что это суффикс прилагательного (street's)
     * @param t 
     * @return 
     */
    public static boolean isEngAdjSuffix(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        if (!BracketHelper.isBracket(t, true)) 
            return false;
        if ((t.getNext() instanceof com.pullenti.ner.TextToken) && com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t.getNext(), com.pullenti.ner.TextToken.class))).term, "S")) 
            return true;
        return false;
    }

    public static boolean isEngArticle(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken)) || !t.chars.isLatinLetter()) 
            return false;
        String str = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
        return ((com.pullenti.unisharp.Utils.stringsEq(str, "THE") || com.pullenti.unisharp.Utils.stringsEq(str, "A") || com.pullenti.unisharp.Utils.stringsEq(str, "AN")) || com.pullenti.unisharp.Utils.stringsEq(str, "DER") || com.pullenti.unisharp.Utils.stringsEq(str, "DIE")) || com.pullenti.unisharp.Utils.stringsEq(str, "DAS");
    }

    private static boolean hasNotAllUpper(com.pullenti.ner.Token b, com.pullenti.ner.Token e) {
        for (com.pullenti.ner.Token t = b; t != null && t.endChar <= e.endChar; t = t.getNext()) {
            if (t instanceof com.pullenti.ner.TextToken) {
                if (t.chars.isLetter() && !t.chars.isAllUpper()) 
                    return true;
            }
            else if (t instanceof com.pullenti.ner.MetaToken) {
                if (hasNotAllUpper((((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getBeginToken(), (((com.pullenti.ner.MetaToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.MetaToken.class))).getEndToken())) 
                    return true;
            }
        }
        return false;
    }

    private static String corrChars(String str, com.pullenti.morph.CharsInfo ci, boolean keepChars, com.pullenti.ner.TextToken t) {
        if (!keepChars) 
            return str;
        if (ci.isAllLower()) 
            return str.toLowerCase();
        if (ci.isCapitalUpper()) 
            return MiscHelper.convertFirstCharUpperAndOtherLower(str);
        if (ci.isAllUpper() || t == null) 
            return str;
        String src = t.getSourceText();
        if (src.length() == str.length()) {
            StringBuilder tmp = new StringBuilder(str);
            for (int i = 0; i < tmp.length(); i++) {
                if (Character.isLetter(src.charAt(i)) && Character.isLowerCase(src.charAt(i))) 
                    tmp.setCharAt(i, Character.toLowerCase(tmp.charAt(i)));
            }
            str = tmp.toString();
        }
        return str;
    }

    private static String restChars(com.pullenti.ner.TextToken t, com.pullenti.ner.Referent r) {
        if (!t.chars.isAllUpper() || !t.chars.isLetter()) 
            return corrChars(t.term, t.chars, true, t);
        if (com.pullenti.unisharp.Utils.stringsEq(t.term, "Г") || com.pullenti.unisharp.Utils.stringsEq(t.term, "ГГ")) {
            if (t.getPrevious() instanceof com.pullenti.ner.NumberToken) 
                return t.term.toLowerCase();
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(t.term, "X")) {
            if ((t.getPrevious() instanceof com.pullenti.ner.NumberToken) || ((t.getPrevious() != null && t.getPrevious().isHiphen()))) 
                return t.term.toLowerCase();
        }
        else if (com.pullenti.unisharp.Utils.stringsEq(t.term, "N") || com.pullenti.unisharp.Utils.stringsEq(t.term, "№")) 
            return t.term;
        boolean canCapUp = false;
        if (BracketHelper.canBeStartOfSequence(t.getPrevious(), true, false)) 
            canCapUp = true;
        else if (t.getPrevious() != null && t.getPrevious().isChar('.') && t.isWhitespaceBefore()) 
            canCapUp = true;
        StatisticCollection.WordInfo stat = t.kit.statistics.getWordInfo(t);
        if (stat == null || ((r != null && ((com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE")))))) 
            return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        if (stat.lowerCount > 0) 
            return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
        if (mc.isNoun()) {
            if (((t.isValue("СОЗДАНИЕ", null) || t.isValue("РАЗВИТИЕ", null) || t.isValue("ВНЕСЕНИЕ", null)) || t.isValue("ИЗМЕНЕНИЕ", null) || t.isValue("УТВЕРЖДЕНИЕ", null)) || t.isValue("ПРИНЯТИЕ", null)) 
                return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        }
        if (((mc.isVerb() || mc.isAdverb() || mc.isConjunction()) || mc.isPreposition() || mc.isPronoun()) || mc.isPersonalPronoun()) 
            return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        if (stat.capitalCount > 0) 
            return MiscHelper.convertFirstCharUpperAndOtherLower(t.term);
        if (mc.isProper()) 
            return MiscHelper.convertFirstCharUpperAndOtherLower(t.term);
        if (mc.isAdjective()) 
            return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        if (com.pullenti.morph.MorphClass.ooEq(mc, com.pullenti.morph.MorphClass.NOUN)) 
            return (canCapUp ? MiscHelper.convertFirstCharUpperAndOtherLower(t.term) : t.term.toLowerCase());
        return t.term;
    }

    /**
     * Преобразовать строку в нужный род, число и падеж (точнее, преобразуется 
     *  первая именная группа), регистр определяется соответствующими символами примера. 
     *  Морфология определяется по первой именной группе примера. 
     *  Фукнция полезна при замене по тексту одной комбинации на другую с учётом 
     *  морфологии и регистра.
     * @param txt преобразуемая строка
     * @param beginSample начало фрагмента примера
     * @param useMopthSample использовать именную группу примера для морфологии
     * @param useRegisterSample регистр определять по фрагменту пример, при false регистр исходной строки
     * @return результат, в худшем случае вернёт исходную строку
     */
    public static String getTextMorphVarBySample(String txt, com.pullenti.ner.Token beginSample, boolean useMorphSample, boolean useRegisterSample) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(txt)) 
            return txt;
        NounPhraseToken npt = NounPhraseHelper.tryParse(beginSample, NounPhraseParseAttr.NO, 0);
        if (npt != null && beginSample.getPrevious() != null) {
            for (com.pullenti.ner.Token tt = beginSample.getPrevious(); tt != null; tt = tt.getPrevious()) {
                if (tt.getWhitespacesAfterCount() > 2) 
                    break;
                NounPhraseToken npt0 = NounPhraseHelper.tryParse(tt, NounPhraseParseAttr.NO, 0);
                if (npt0 != null) {
                    if (npt0.getEndToken() == npt.getEndToken()) 
                        npt.setMorph(npt0.getMorph());
                    else {
                        if (tt == beginSample.getPrevious() && npt.getBeginToken() == npt.getEndToken() && npt.getMorph().getCase().isGenitive()) 
                            npt.getMorph().removeItems(com.pullenti.morph.MorphCase.GENITIVE);
                        break;
                    }
                }
            }
        }
        com.pullenti.ner.AnalysisResult ar = com.pullenti.ner.ProcessorService.getEmptyProcessor().process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
        if (ar == null || ar.firstToken == null) 
            return txt;
        NounPhraseToken npt1 = NounPhraseHelper.tryParse(ar.firstToken, NounPhraseParseAttr.NO, 0);
        com.pullenti.ner.Token t0 = beginSample;
        StringBuilder res = new StringBuilder();
        for (com.pullenti.ner.Token t = ar.firstToken; t != null; t = t.getNext(),t0 = (t0 == null ? null : t0.getNext())) {
            if (t.isWhitespaceBefore() && t != ar.firstToken) 
                res.append(' ');
            String word = null;
            if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter()) {
                word = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term;
                if ((npt1 != null && t.endChar <= npt1.endChar && npt != null) && useMorphSample) {
                    com.pullenti.morph.MorphBaseInfo bi = new com.pullenti.morph.MorphBaseInfo();
                    bi.setNumber(npt.getMorph().getNumber());
                    bi.setCase(npt.getMorph().getCase());
                    bi.setGender(npt1.getMorph().getGender());
                    String ww = com.pullenti.morph.Morphology.getWordform(word, bi);
                    if (ww != null) 
                        word = ww;
                }
                com.pullenti.morph.CharsInfo ci;
                if (useRegisterSample && t0 != null) 
                    ci = t0.chars;
                else 
                    ci = t.chars;
                if (ci.isAllLower()) 
                    word = word.toLowerCase();
                else if (ci.isCapitalUpper()) 
                    word = convertFirstCharUpperAndOtherLower(word);
            }
            else 
                word = t.getSourceText();
            res.append(word);
        }
        return res.toString();
    }

    /**
     * Преобразовать строку к нужному падежу (и числу). 
     *  Преобразуется только начало строки, содержащей в начале именную группу или персону
     * @param txt исходная строка
     * @param cas падеж
     * @param pluralNumber множественное число
     * @return результат (в крайнем случае, вернёт исходную строку, если ничего не получилось)
     */
    public static String getTextMorphVarByCase(String txt, com.pullenti.morph.MorphCase cas, boolean pluralNumber) {
        com.pullenti.ner.AnalysisResult ar = com.pullenti.ner.ProcessorService.getEmptyProcessor().process(new com.pullenti.ner.SourceOfAnalysis(txt), null, null);
        if (ar == null || ar.firstToken == null) 
            return txt;
        StringBuilder res = new StringBuilder();
        com.pullenti.ner.Token t0 = ar.firstToken;
        NounPhraseToken npt = NounPhraseHelper.tryParse(ar.firstToken, NounPhraseParseAttr.NO, 0);
        if (npt != null) {
            for (com.pullenti.ner.Token t = npt.getBeginToken(); t != null && t.endChar <= npt.endChar; t = t.getNext()) {
                boolean isNoun = t.beginChar >= npt.noun.beginChar;
                String word = null;
                if (t instanceof com.pullenti.ner.NumberToken) 
                    word = t.getSourceText();
                else if (t instanceof com.pullenti.ner.TextToken) {
                    for (com.pullenti.morph.MorphBaseInfo it : t.getMorph().getItems()) {
                        com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(it, com.pullenti.morph.MorphWordForm.class);
                        if (wf == null) 
                            continue;
                        if (!npt.getMorph().getCase().isUndefined()) {
                            if (((com.pullenti.morph.MorphCase.ooBitand(npt.getMorph().getCase(), wf.getCase()))).isUndefined()) 
                                continue;
                        }
                        if (isNoun) {
                            if ((wf._getClass().isNoun() || wf._getClass().isPersonalPronoun() || wf._getClass().isPronoun()) || wf._getClass().isProper()) {
                                word = wf.normalCase;
                                break;
                            }
                        }
                        else if (wf._getClass().isAdjective() || wf._getClass().isPronoun() || wf._getClass().isPersonalPronoun()) {
                            word = wf.normalCase;
                            break;
                        }
                    }
                    if (word == null) 
                        word = (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).lemma;
                    if (!t.chars.isLetter()) {
                    }
                    else if ((t.getNext() != null && t.getNext().isHiphen() && t.isValue("ГЕНЕРАЛ", null)) || t.isValue("КАПИТАН", null)) {
                    }
                    else {
                        com.pullenti.morph.MorphBaseInfo mbi = com.pullenti.morph.MorphBaseInfo._new550(npt.getMorph().getGender(), cas, com.pullenti.morph.MorphNumber.SINGULAR);
                        if (pluralNumber) 
                            mbi.setNumber(com.pullenti.morph.MorphNumber.PLURAL);
                        String wcas = com.pullenti.morph.Morphology.getWordform(word, mbi);
                        if (wcas != null) 
                            word = wcas;
                    }
                }
                if (t.chars.isAllLower()) 
                    word = word.toLowerCase();
                else if (t.chars.isCapitalUpper()) 
                    word = convertFirstCharUpperAndOtherLower(word);
                if (t != ar.firstToken && t.isWhitespaceBefore()) 
                    res.append(' ');
                res.append(word);
                t0 = t.getNext();
            }
        }
        if (t0 == ar.firstToken) 
            return txt;
        if (t0 != null) {
            if (t0.isWhitespaceBefore()) 
                res.append(' ');
            res.append(txt.substring(t0.beginChar));
        }
        return res.toString();
    }
    public MiscHelper() {
    }
    public static MiscHelper _globalInstance;
    
    static {
        _globalInstance = new MiscHelper(); 
    }
}
