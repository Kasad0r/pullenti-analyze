/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Работа с глагольными группами (последовательность из глаголов и наречий)
 */
public class VerbPhraseHelper {

    /**
     * Создать глагольную группу
     * @param t первый токен группы
     * @return группа или null
     */
    public static VerbPhraseToken tryParse(com.pullenti.ner.Token t) {
        if (!((t instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (!t.chars.isLetter()) 
            return null;
        if (t.chars.isCyrillicLetter()) 
            return tryParseRu(t);
        return null;
    }

    private static VerbPhraseToken tryParseRu(com.pullenti.ner.Token t) {
        VerbPhraseToken res = null;
        com.pullenti.ner.Token t0 = t;
        com.pullenti.ner.Token not = null;
        boolean hasVerb = false;
        for (; t != null; t = t.getNext()) {
            if (!((t instanceof com.pullenti.ner.TextToken))) 
                break;
            com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class);
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "НЕ")) {
                not = t;
                continue;
            }
            int ty = 0;
            com.pullenti.morph.MorphClass mc = tt.getMorphClassInDictionary();
            if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "НЕТ")) 
                ty = 1;
            else if (mc.isAdverb()) 
                ty = 2;
            else if (tt.isPureVerb() || tt.isVerbBe()) 
                ty = 1;
            else if (mc.isVerb()) {
                if (mc.isPreposition() || mc.isMisc()) {
                }
                else if (mc.isNoun()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(tt.term, "СТАЛИ")) 
                        ty = 1;
                    else if (!tt.chars.isAllLower() && !MiscHelper.canBeStartOfSentence(tt)) 
                        ty = 1;
                }
                else if (mc.isProper()) {
                    if (tt.chars.isAllLower()) 
                        ty = 1;
                }
                else 
                    ty = 1;
            }
            if (ty == 0) 
                break;
            if (res == null) 
                res = new VerbPhraseToken(t0, t);
            res.setEndToken(t);
            VerbPhraseItemToken it = VerbPhraseItemToken._new638(t, t, new com.pullenti.ner.MorphCollection(t.getMorph()));
            if (not != null) {
                it.setBeginToken(not);
                it.not = true;
                not = null;
            }
            it.isAdverb = ty == 2;
            it.normal = t.getNormalCaseText((ty == 2 ? com.pullenti.morph.MorphClass.ADVERB : com.pullenti.morph.MorphClass.VERB), false, com.pullenti.morph.MorphGender.UNDEFINED, false);
            res.items.add(it);
            if (!hasVerb && ty == 1) {
                res.setMorph(it.getMorph());
                hasVerb = true;
            }
        }
        if (!hasVerb) 
            return null;
        return res;
    }
    public VerbPhraseHelper() {
    }
}
