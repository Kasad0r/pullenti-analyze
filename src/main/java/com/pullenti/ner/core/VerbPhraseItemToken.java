/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Элемент глагольной группы
 */
public class VerbPhraseItemToken extends com.pullenti.ner.MetaToken {

    public VerbPhraseItemToken(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        super(begin, end, null);
    }

    public boolean not;

    public boolean isAdverb;

    /**
     * [Get] Причастие
     */
    public boolean isVerbAdjective() {
        if (m_IsVerbAdjective >= 0) 
            return m_IsVerbAdjective > 0;
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isAdjective() && (f instanceof com.pullenti.morph.MorphWordForm) && !(((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getAttrs().contains("к.ф.")) 
                return true;
        }
        m_IsVerbAdjective = 0;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(getEndToken(), com.pullenti.ner.TextToken.class);
        if (tt != null && tt.term.endsWith("СЯ")) {
            com.pullenti.morph.MorphBaseInfo mb = com.pullenti.morph.Morphology.getWordBaseInfo(tt.term.substring(0, 0 + tt.term.length() - 2), null, false, false);
            if (mb != null) {
                if (mb._getClass().isAdjective()) 
                    m_IsVerbAdjective = 1;
            }
        }
        return m_IsVerbAdjective > 0;
    }

    /**
     * [Set] Причастие
     */
    public boolean setVerbAdjective(boolean value) {
        m_IsVerbAdjective = (value ? 1 : 0);
        return value;
    }


    private int m_IsVerbAdjective = -1;

    /**
     * [Get] Глагол-инфиниитив
     */
    public boolean isVerbInfinitive() {
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm) && (((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getAttrs().contains("инф.")) 
                return true;
        }
        return false;
    }


    public String normal;

    /**
     * [Get] Полное морф.информация о глаголе глагола
     */
    public com.pullenti.morph.MorphWordForm getVerbMorph() {
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm) && ((((((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class))).misc.getPerson().value()) & (com.pullenti.morph.MorphPerson.THIRD.value()))) != (com.pullenti.morph.MorphPerson.UNDEFINED.value())) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class));
        }
        for (com.pullenti.morph.MorphBaseInfo f : getMorph().getItems()) {
            if (f._getClass().isVerb() && (f instanceof com.pullenti.morph.MorphWordForm)) 
                return ((com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(f, com.pullenti.morph.MorphWordForm.class));
        }
        return null;
    }


    @Override
    public String toString() {
        return (((not ? "НЕ " : ""))) + normal;
    }

    public static VerbPhraseItemToken _new638(com.pullenti.ner.Token _arg1, com.pullenti.ner.Token _arg2, com.pullenti.ner.MorphCollection _arg3) {
        VerbPhraseItemToken res = new VerbPhraseItemToken(_arg1, _arg2);
        res.setMorph(_arg3);
        return res;
    }
    public VerbPhraseItemToken() {
        super();
    }
}
