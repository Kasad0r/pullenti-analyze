/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Внешняя онтология
 */
public class ExtOntology {

    /**
     * Добавить элемент
     * @param extId произвольный объект
     * @param typeName имя типа сущности
     * @param _definition текстовое определение. Определение может содержать несколько  
     *  отдельных фрагментов, которые разделяются точкой с запятой. 
     *  Например, Министерство Обороны России; Минобороны
     * @return если null, то не получилось...
     */
    public ExtOntologyItem add(Object extId, String typeName, String _definition) {
        if (typeName == null || _definition == null) 
            return null;
        Referent r = this._createReferent(typeName, _definition);
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2673(extId, r, typeName);
        items.add(res);
        return res;
    }

    /**
     * Добавить готовую сущность
     * @param extId произвольный объект
     * @param referent готовая сущность (например, сфомированная явно)
     * @return 
     */
    public ExtOntologyItem addReferent(Object extId, Referent referent) {
        if (referent == null) 
            return null;
        m_Hash = null;
        ExtOntologyItem res = ExtOntologyItem._new2673(extId, referent, referent.getTypeName());
        items.add(res);
        return res;
    }

    private Referent _createReferent(String typeName, String _definition) {
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer2675 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2676 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapanalyzer2675);
        analyzer = wrapanalyzer2675.value;
        if (!inoutres2676) 
            return null;
        SourceOfAnalysis sf = new SourceOfAnalysis(_definition);
        AnalysisResult ar = m_Processor._process(sf, true, true, null, null);
        if (ar == null || ar.firstToken == null) 
            return null;
        Referent r0 = ar.firstToken.getReferent();
        Token t = null;
        if (r0 != null) {
            if (com.pullenti.unisharp.Utils.stringsNe(r0.getTypeName(), typeName)) 
                r0 = null;
        }
        if (r0 != null) 
            t = ar.firstToken;
        else {
            ReferentToken rt = analyzer.processOntologyItem(ar.firstToken);
            if (rt == null) 
                return null;
            r0 = rt.referent;
            t = rt.getEndToken();
        }
        for (t = t.getNext(); t != null; t = t.getNext()) {
            if (t.isChar(';') && t.getNext() != null) {
                Referent r1 = t.getNext().getReferent();
                if (r1 == null) {
                    ReferentToken rt = analyzer.processOntologyItem(t.getNext());
                    if (rt == null) 
                        continue;
                    t = rt.getEndToken();
                    r1 = rt.referent;
                }
                if (com.pullenti.unisharp.Utils.stringsEq(r1.getTypeName(), typeName)) 
                    r0.mergeSlots(r1, true);
            }
        }
        if (r0 != null) 
            r0 = analyzer.persistAnalizerData.registerReferent(r0);
        return r0;
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param _definition новое определение
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, String _definition) {
        if (item == null) 
            return false;
        Referent r = this._createReferent(item.typeName, _definition);
        return this.refresh(item, r);
    }

    /**
     * Обновить существующий элемент онтологии
     * @param item 
     * @param newReferent 
     * @return 
     */
    public boolean refresh(ExtOntologyItem item, Referent newReferent) {
        if (item == null) 
            return false;
        Analyzer analyzer = null;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapanalyzer2677 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2678 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, item.typeName, wrapanalyzer2677);
        analyzer = wrapanalyzer2677.value;
        if (!inoutres2678) 
            return false;
        if (analyzer.persistAnalizerData == null) 
            return true;
        if (item.referent != null) 
            analyzer.persistAnalizerData.removeReferent(item.referent);
        Referent oldReferent = item.referent;
        newReferent = analyzer.persistAnalizerData.registerReferent(newReferent);
        item.referent = newReferent;
        m_Hash = null;
        if (oldReferent != null && newReferent != null) {
            for (Analyzer a : m_Processor.getAnalyzers()) {
                if (a.persistAnalizerData != null) {
                    for (Referent rr : a.persistAnalizerData.getReferents()) {
                        for (Slot s : newReferent.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                newReferent.uploadSlot(s, rr);
                        }
                        for (Slot s : rr.getSlots()) {
                            if (s.getValue() == oldReferent) 
                                rr.uploadSlot(s, newReferent);
                        }
                    }
                }
            }
        }
        return true;
    }

    public java.util.ArrayList<ExtOntologyItem> items = new java.util.ArrayList<ExtOntologyItem>();

    public ExtOntology(String specNames) {
        m_Processor = ProcessorService.createSpecificProcessor(specNames);
        m_AnalByType = new java.util.HashMap<String, Analyzer>();
        for (Analyzer a : m_Processor.getAnalyzers()) {
            a.setPersistReferentsRegim(true);
            if (com.pullenti.unisharp.Utils.stringsEq(a.getName(), "DENOMINATION")) 
                a.setIgnoreThisAnalyzer(true);
            else 
                for (ReferentClass t : a.getTypeSystem()) {
                    if (!m_AnalByType.containsKey(t.getName())) 
                        m_AnalByType.put(t.getName(), a);
                }
        }
    }

    private Processor m_Processor;

    private java.util.HashMap<String, Analyzer> m_AnalByType;

    /**
     * Используется внутренним образом
     * @param typeName 
     * @return 
     */
    public com.pullenti.ner.core.AnalyzerData _getAnalyzerData(String typeName) {
        Analyzer a;
        com.pullenti.unisharp.Outargwrapper<Analyzer> wrapa2679 = new com.pullenti.unisharp.Outargwrapper<Analyzer>();
        boolean inoutres2680 = com.pullenti.unisharp.Utils.tryGetValue(m_AnalByType, typeName, wrapa2679);
        a = wrapa2679.value;
        if (!inoutres2680) 
            return null;
        return a.persistAnalizerData;
    }

    private java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection> m_Hash = null;

    private void _initHash() {
        m_Hash = new java.util.HashMap<String, com.pullenti.ner.core.IntOntologyCollection>();
        for (ExtOntologyItem it : items) {
            if (it.referent != null) 
                it.referent.ontologyItems = null;
        }
        for (ExtOntologyItem it : items) {
            if (it.referent != null) {
                com.pullenti.ner.core.IntOntologyCollection ont;
                com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wrapont2682 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
                boolean inoutres2683 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, it.referent.getTypeName(), wrapont2682);
                ont = wrapont2682.value;
                if (!inoutres2683) 
                    m_Hash.put(it.referent.getTypeName(), (ont = com.pullenti.ner.core.IntOntologyCollection._new2681(true)));
                if (it.referent.ontologyItems == null) 
                    it.referent.ontologyItems = new java.util.ArrayList<ExtOntologyItem>();
                it.referent.ontologyItems.add(it);
                it.referent.intOntologyItem = null;
                ont.addReferent(it.referent);
            }
        }
    }

    /**
     * Привязать сущность
     * @param r 
     * @return null или список подходящих элементов
     */
    public java.util.ArrayList<ExtOntologyItem> attachReferent(Referent r) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto2684 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres2685 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, r.getTypeName(), wraponto2684);
        onto = wraponto2684.value;
        if (!inoutres2685) 
            return null;
        java.util.ArrayList<Referent> li = onto.tryAttachByReferent(r, null, false);
        if (li == null || li.size() == 0) 
            return null;
        java.util.ArrayList<ExtOntologyItem> res = null;
        for (Referent rr : li) {
            if (rr.ontologyItems != null) {
                if (res == null) 
                    res = new java.util.ArrayList<ExtOntologyItem>();
                com.pullenti.unisharp.Utils.addToArrayList(res, rr.ontologyItems);
            }
        }
        return res;
    }

    /**
     * Используется внутренним образом
     * @param typeName 
     * @param t 
     * @return 
     */
    public java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> attachToken(String typeName, Token t) {
        if (m_Hash == null) 
            this._initHash();
        com.pullenti.ner.core.IntOntologyCollection onto;
        com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection> wraponto2686 = new com.pullenti.unisharp.Outargwrapper<com.pullenti.ner.core.IntOntologyCollection>();
        boolean inoutres2687 = com.pullenti.unisharp.Utils.tryGetValue(m_Hash, typeName, wraponto2686);
        onto = wraponto2686.value;
        if (!inoutres2687) 
            return null;
        return onto.tryAttach(t, null, false);
    }
    public ExtOntology() {
        this(null);
    }
}
