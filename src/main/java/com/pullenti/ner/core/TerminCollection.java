/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.core;

/**
 * Коллекций некоторых обозначений, терминов
 */
public class TerminCollection {

    /**
     * Добавить термин. После добавления в термин нельзя вносить изменений, 
     *  кроме как в значения Tag и Tag2 (иначе потом нужно вызвать Reindex)
     * @param term 
     */
    public void add(Termin term) {
        termins.add(term);
        m_HashCanonic = null;
        this.reindex(term);
    }

    /**
     * Добавить строку вместе с морфологическими вариантами
     * @param _termins 
     * @param tag 
     * @param lang 
     * @return 
     */
    public Termin addStr(String _termins, Object tag, com.pullenti.morph.MorphLang lang, boolean isNormalText) {
        Termin t = new Termin(_termins, lang, isNormalText || allAddStrsNormalized);
        t.tag = tag;
        if (tag != null && t.terms.size() == 1) {
        }
        this.add(t);
        return t;
    }

    public java.util.ArrayList<Termin> termins = new java.util.ArrayList<Termin>();

    public boolean allAddStrsNormalized = false;

    public static class CharNode {
    
        public java.util.HashMap<Short, CharNode> children;
    
        public java.util.ArrayList<com.pullenti.ner.core.Termin> termins;
        public CharNode() {
        }
    }


    private CharNode m_Root;

    private CharNode m_RootUa;

    private CharNode _getRoot(com.pullenti.morph.MorphLang lang, boolean isLat) {
        if (lang != null && lang.isUa() && !lang.isRu()) 
            return m_RootUa;
        return m_Root;
    }

    private java.util.HashMap<Short, java.util.ArrayList<Termin>> m_Hash1 = new java.util.HashMap<Short, java.util.ArrayList<Termin>>();

    private java.util.HashMap<String, java.util.ArrayList<Termin>> m_HashCanonic = null;

    /**
     * Переиндексировать термин (если после добавления у него что-либо поменялось)
     * @param t 
     */
    public void reindex(Termin t) {
        if (t == null) 
            return;
        if (t.terms.size() > 20) {
        }
        if (t.acronymSmart != null) 
            this.addToHash1((short)t.acronymSmart.charAt(0), t);
        if (t.abridges != null) {
            for (Termin.Abridge a : t.abridges) {
                if (a.parts.get(0).value.length() == 1) 
                    this.addToHash1((short)a.parts.get(0).value.charAt(0), t);
            }
        }
        for (String v : t.getHashVariants()) {
            this._AddToTree(v, t);
        }
        if (t.additionalVars != null) {
            for (Termin av : t.additionalVars) {
                av.ignoreTermsOrder = t.ignoreTermsOrder;
                for (String v : av.getHashVariants()) {
                    this._AddToTree(v, t);
                }
            }
        }
    }

    public void remove(Termin t) {
        for (String v : t.getHashVariants()) {
            this._RemoveFromTree(v, t);
        }
        for (java.util.ArrayList<Termin> li : m_Hash1.values()) {
            for (Termin tt : li) {
                if (tt == t) {
                    li.remove(tt);
                    break;
                }
            }
        }
        int i = termins.indexOf(t);
        if (i >= 0) 
            termins.remove(i);
    }

    private void _AddToTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = this._getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                nod.children = new java.util.HashMap<Short, CharNode>();
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn612 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres613 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn612);
            nn = wrapnn612.value;
            if (!inoutres613) 
                nod.children.put(ch, (nn = new CharNode()));
            nod = nn;
        }
        if (nod.termins == null) 
            nod.termins = new java.util.ArrayList<Termin>();
        if (!nod.termins.contains(t)) 
            nod.termins.add(t);
    }

    private void _RemoveFromTree(String key, Termin t) {
        if (key == null) 
            return;
        CharNode nod = this._getRoot(t.lang, t.lang.isUndefined() && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                return;
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn614 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres615 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn614);
            nn = wrapnn614.value;
            if (!inoutres615) 
                return;
            nod = nn;
        }
        if (nod.termins == null) 
            return;
        if (nod.termins.contains(t)) 
            nod.termins.remove(t);
    }

    private java.util.ArrayList<Termin> _FindInTree(String key, com.pullenti.morph.MorphLang lang) {
        if (key == null) 
            return null;
        CharNode nod = this._getRoot(lang, ((lang == null || lang.isUndefined())) && com.pullenti.morph.LanguageHelper.isLatin(key));
        for (int i = 0; i < key.length(); i++) {
            short ch = (short)key.charAt(i);
            if (nod.children == null) 
                return null;
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn616 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres617 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn616);
            nn = wrapnn616.value;
            if (!inoutres617) 
                return null;
            nod = nn;
        }
        return nod.termins;
    }

    private void addToHash1(short key, Termin t) {
        java.util.ArrayList<Termin> li = null;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapli618 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
        boolean inoutres619 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash1, key, wrapli618);
        li = wrapli618.value;
        if (!inoutres619) 
            m_Hash1.put(key, (li = new java.util.ArrayList<Termin>()));
        if (!li.contains(t)) 
            li.add(t);
    }

    public Termin find(String key) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(key)) 
            return null;
        java.util.ArrayList<Termin> li;
        if (com.pullenti.morph.LanguageHelper.isLatinChar(key.charAt(0))) 
            li = this._FindInTree(key, com.pullenti.morph.MorphLang.EN);
        else {
            li = this._FindInTree(key, com.pullenti.morph.MorphLang.RU);
            if (li == null) 
                li = this._FindInTree(key, com.pullenti.morph.MorphLang.UA);
        }
        return (li != null && li.size() > 0 ? li.get(0) : null);
    }

    /**
     * Попытка привязать к аналитическому контейнеру с указанной позиции
     * @param token начальная позиция
     * @param pars параметры выделения
     * @return 
     */
    public TerminToken tryParse(com.pullenti.ner.Token token, TerminParseAttr pars) {
        if (termins.size() == 0) 
            return null;
        java.util.ArrayList<TerminToken> li = this.tryParseAll(token, pars);
        if (li != null) 
            return li.get(0);
        else 
            return null;
    }

    /**
     * Попытка привязать все возможные варианты
     * @param token 
     * @param pars параметры выделения
     * @return 
     */
    public java.util.ArrayList<TerminToken> tryParseAll(com.pullenti.ner.Token token, TerminParseAttr pars) {
        if (token == null) 
            return null;
        java.util.ArrayList<TerminToken> re = this._TryAttachAll_(token, pars, false);
        if (re == null && token.getMorph().getLanguage().isUa()) 
            re = this._TryAttachAll_(token, pars, true);
        return re;
    }

    private java.util.ArrayList<TerminToken> _TryAttachAll_(com.pullenti.ner.Token token, TerminParseAttr pars, boolean mainRoot) {
        if (termins.size() == 0 || token == null) 
            return null;
        String s = null;
        com.pullenti.ner.TextToken tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.TextToken.class);
        if (tt == null && (token instanceof com.pullenti.ner.ReferentToken)) 
            tt = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.ReferentToken.class))).getBeginToken(), com.pullenti.ner.TextToken.class);
        java.util.ArrayList<TerminToken> res = null;
        boolean wasVars = false;
        CharNode root = (mainRoot ? m_Root : this._getRoot(token.getMorph().getLanguage(), token.chars.isLatinLetter()));
        if (tt != null) {
            s = tt.term;
            CharNode nod = root;
            boolean noVars = false;
            int len0 = 0;
            if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) {
            }
            else if (tt.invariantPrefixLength <= s.length()) {
                len0 = (int)tt.invariantPrefixLength;
                for (int i = 0; i < tt.invariantPrefixLength; i++) {
                    short ch = (short)s.charAt(i);
                    if (nod.children == null) {
                        noVars = true;
                        break;
                    }
                    CharNode nn;
                    com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn620 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
                    boolean inoutres621 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn620);
                    nn = wrapnn620.value;
                    if (!inoutres621) {
                        noVars = true;
                        break;
                    }
                    nod = nn;
                }
            }
            if (!noVars) {
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres626 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                boolean inoutres627 = this._manageVar(token, pars, s, nod, len0, wrapres626);
                res = wrapres626.value;
                if (inoutres627) 
                    wasVars = true;
                for (int i = 0; i < tt.getMorph().getItemsCount(); i++) {
                    if ((((pars.value()) & (TerminParseAttr.TERMONLY.value()))) != (TerminParseAttr.NO.value())) 
                        continue;
                    com.pullenti.morph.MorphWordForm wf = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(i), com.pullenti.morph.MorphWordForm.class);
                    if (wf == null) 
                        continue;
                    if ((((pars.value()) & (TerminParseAttr.INDICTIONARYONLY.value()))) != (TerminParseAttr.NO.value())) {
                        if (!wf.isInDictionary()) 
                            continue;
                    }
                    int j;
                    boolean ok = true;
                    if (wf.normalCase == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalCase, s)) 
                        ok = false;
                    else {
                        for (j = 0; j < i; j++) {
                            com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                            if (wf2 != null) {
                                if (com.pullenti.unisharp.Utils.stringsEq(wf2.normalCase, wf.normalCase) || com.pullenti.unisharp.Utils.stringsEq(wf2.normalFull, wf.normalCase)) 
                                    break;
                            }
                        }
                        if (j < i) 
                            ok = false;
                    }
                    if (ok) {
                        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres622 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                        boolean inoutres623 = this._manageVar(token, pars, wf.normalCase, nod, (int)tt.invariantPrefixLength, wrapres622);
                        res = wrapres622.value;
                        if (inoutres623) 
                            wasVars = true;
                    }
                    if (wf.normalFull == null || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, wf.normalCase) || com.pullenti.unisharp.Utils.stringsEq(wf.normalFull, s)) 
                        continue;
                    for (j = 0; j < i; j++) {
                        com.pullenti.morph.MorphWordForm wf2 = (com.pullenti.morph.MorphWordForm)com.pullenti.unisharp.Utils.cast(tt.getMorph().getIndexerItem(j), com.pullenti.morph.MorphWordForm.class);
                        if (wf2 != null && com.pullenti.unisharp.Utils.stringsEq(wf2.normalFull, wf.normalFull)) 
                            break;
                    }
                    if (j < i) 
                        continue;
                    com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres624 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
                    boolean inoutres625 = this._manageVar(token, pars, wf.normalFull, nod, (int)tt.invariantPrefixLength, wrapres624);
                    res = wrapres624.value;
                    if (inoutres625) 
                        wasVars = true;
                }
            }
        }
        else if (token instanceof com.pullenti.ner.NumberToken) {
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> wrapres628 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>>(res);
            boolean inoutres629 = this._manageVar(token, pars, (((com.pullenti.ner.NumberToken)com.pullenti.unisharp.Utils.cast(token, com.pullenti.ner.NumberToken.class))).getValue().toString(), root, 0, wrapres628);
            res = wrapres628.value;
            if (inoutres629) 
                wasVars = true;
        }
        else 
            return null;
        if (!wasVars && s != null && s.length() == 1) {
            java.util.ArrayList<Termin> vars;
            com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapvars630 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
            boolean inoutres631 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash1, (short)s.charAt(0), wrapvars630);
            vars = wrapvars630.value;
            if (inoutres631) {
                for (Termin t : vars) {
                    if (!t.lang.isUndefined()) {
                        if (!token.getMorph().getLanguage().isUndefined()) {
                            if (((com.pullenti.morph.MorphLang.ooBitand(token.getMorph().getLanguage(), t.lang))).isUndefined()) 
                                continue;
                        }
                    }
                    TerminToken ar = t.tryParse(tt, TerminParseAttr.NO);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res == null) {
                        res = new java.util.ArrayList<TerminToken>();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() > res.get(0).getTokensCount()) {
                        res.clear();
                        res.add(ar);
                    }
                    else if (ar.getTokensCount() == res.get(0).getTokensCount()) 
                        res.add(ar);
                }
            }
        }
        if (res != null) {
            int ii = 0;
            int max = 0;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i).getLengthChar() > max) {
                    max = res.get(i).getLengthChar();
                    ii = i;
                }
            }
            if (ii > 0) {
                TerminToken v = res.get(ii);
                res.remove(ii);
                res.add(0, v);
            }
        }
        return res;
    }

    private boolean _manageVar(com.pullenti.ner.Token token, TerminParseAttr pars, String v, CharNode nod, int i0, com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<TerminToken>> res) {
        for (int i = i0; i < v.length(); i++) {
            short ch = (short)v.charAt(i);
            if (nod.children == null) 
                return false;
            CharNode nn;
            com.pullenti.unisharp.Outargwrapper<CharNode> wrapnn632 = new com.pullenti.unisharp.Outargwrapper<CharNode>();
            boolean inoutres633 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, ch, wrapnn632);
            nn = wrapnn632.value;
            if (!inoutres633) 
                return false;
            nod = nn;
        }
        java.util.ArrayList<Termin> vars = nod.termins;
        if (vars == null || vars.size() == 0) 
            return false;
        for (Termin t : vars) {
            TerminToken ar = t.tryParse(token, pars);
            if (ar != null) {
                ar.termin = t;
                if (res.value == null) {
                    res.value = new java.util.ArrayList<TerminToken>();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                    res.value.clear();
                    res.value.add(ar);
                }
                else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                    int j;
                    for (j = 0; j < res.value.size(); j++) {
                        if (res.value.get(j).termin == ar.termin) 
                            break;
                    }
                    if (j >= res.value.size()) 
                        res.value.add(ar);
                }
            }
            if (t.additionalVars != null) {
                for (Termin av : t.additionalVars) {
                    ar = av.tryParse(token, pars);
                    if (ar == null) 
                        continue;
                    ar.termin = t;
                    if (res.value == null) {
                        res.value = new java.util.ArrayList<TerminToken>();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() > res.value.get(0).getTokensCount()) {
                        res.value.clear();
                        res.value.add(ar);
                    }
                    else if (ar.getTokensCount() == res.value.get(0).getTokensCount()) {
                        int j;
                        for (j = 0; j < res.value.size(); j++) {
                            if (res.value.get(j).termin == ar.termin) 
                                break;
                        }
                        if (j >= res.value.size()) 
                            res.value.add(ar);
                    }
                }
            }
        }
        return v.length() > 1;
    }

    /**
     * Поискать эквивалентные термины
     * @param termin 
     * @return 
     */
    public java.util.ArrayList<Termin> tryAttach(Termin termin) {
        java.util.ArrayList<Termin> res = null;
        for (String v : termin.getHashVariants()) {
            java.util.ArrayList<Termin> vars = this._FindInTree(v, termin.lang);
            if (vars == null) 
                continue;
            for (Termin t : vars) {
                if (t.isEqual(termin)) {
                    if (res == null) 
                        res = new java.util.ArrayList<Termin>();
                    if (!res.contains(t)) 
                        res.add(t);
                }
            }
        }
        return res;
    }

    public java.util.ArrayList<Termin> tryAttachStr(String termin, com.pullenti.morph.MorphLang lang) {
        return this._FindInTree(termin, lang);
    }

    public java.util.ArrayList<Termin> findTerminByCanonicText(String text) {
        if (m_HashCanonic == null) {
            m_HashCanonic = new java.util.HashMap<String, java.util.ArrayList<Termin>>();
            for (Termin t : termins) {
                String ct = t.getCanonicText();
                java.util.ArrayList<Termin> li;
                com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapli634 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
                boolean inoutres635 = com.pullenti.unisharp.Utils.tryGetValue(m_HashCanonic, ct, wrapli634);
                li = wrapli634.value;
                if (!inoutres635) 
                    m_HashCanonic.put(ct, (li = new java.util.ArrayList<Termin>()));
                if (!li.contains(t)) 
                    li.add(t);
            }
        }
        java.util.ArrayList<Termin> res;
        com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>> wrapres636 = new com.pullenti.unisharp.Outargwrapper<java.util.ArrayList<Termin>>();
        boolean inoutres637 = com.pullenti.unisharp.Utils.tryGetValue(m_HashCanonic, text, wrapres636);
        res = wrapres636.value;
        if (!inoutres637) 
            return null;
        else 
            return res;
    }
    public TerminCollection() {
        if(_globalInstance == null) return;
        m_Root = new CharNode();
        m_RootUa = new CharNode();
    }
    public static TerminCollection _globalInstance;
    
    static {
        _globalInstance = new TerminCollection(); 
    }
}
