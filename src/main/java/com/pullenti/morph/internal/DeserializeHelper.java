/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class DeserializeHelper {

    public static ByteArrayWrapper deserializeDD(com.pullenti.unisharp.Stream str, DerivateDictionary dic, boolean lazyLoad) throws Exception, java.io.IOException {
        ByteArrayWrapper wr = null;
        try (com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream()) {
            MorphSerializeHelper.deflateGzip(str, tmp);
            wr = new ByteArrayWrapper(tmp.toByteArray());
            int cou = wr.deserializeInt();
            for (; cou > 0; cou--) {
                int p1 = wr.deserializeInt();
                com.pullenti.morph.DerivateGroup ew = new com.pullenti.morph.DerivateGroup();
                if (lazyLoad) {
                    ew.lazyPos = wr.getPosition();
                    wr.seek(p1);
                }
                else 
                    deserializeDerivateGroup(wr, ew);
                dic.m_AllGroups.add(ew);
            }
            dic.m_Root = new ExplanTreeNode();
            deserializeTreeNode(wr, dic, dic.m_Root, lazyLoad);
        }
        return wr;
    }

    public static void deserializeDerivateGroup(ByteArrayWrapper str, com.pullenti.morph.DerivateGroup dg) {
        int attr = str.deserializeShort();
        if (((attr & 1)) != 0) 
            dg.isDummy = true;
        if (((attr & 2)) != 0) 
            dg.notGenerate = true;
        if (((attr & 4)) != 0) 
            dg.m_Transitive = 0;
        if (((attr & 8)) != 0) 
            dg.m_Transitive = 1;
        dg.prefix = str.deserializeString();
        int cou = str.deserializeShort();
        for (; cou > 0; cou--) {
            com.pullenti.morph.DerivateWord w = new com.pullenti.morph.DerivateWord(dg);
            w.spelling = str.deserializeString();
            w._class = new com.pullenti.morph.MorphClass(null);
            w._class.value = (short)str.deserializeShort();
            w.lang = com.pullenti.morph.MorphLang._new5((short)str.deserializeShort());
            w.attrs.value = (short)str.deserializeShort();
            dg.words.add(w);
        }
        cou = str.deserializeShort();
        for (; cou > 0; cou--) {
            String pref = (String)com.pullenti.unisharp.Utils.notnull(str.deserializeString(), "");
            com.pullenti.morph.MorphCase cas = new com.pullenti.morph.MorphCase(null);
            cas.value = (short)str.deserializeShort();
            if (dg.nexts == null) 
                dg.nexts = new java.util.HashMap<String, com.pullenti.morph.MorphCase>();
            dg.nexts.put(pref, cas);
        }
    }

    public static void deserializeTreeNode(ByteArrayWrapper str, DerivateDictionary dic, ExplanTreeNode tn, boolean lazyLoad) {
        int cou = str.deserializeShort();
        java.util.ArrayList<com.pullenti.morph.DerivateGroup> li = (cou > 1 ? new java.util.ArrayList<com.pullenti.morph.DerivateGroup>() : null);
        for (; cou > 0; cou--) {
            int id = str.deserializeInt();
            if (id > 0 && id <= dic.m_AllGroups.size()) {
                com.pullenti.morph.DerivateGroup gr = dic.m_AllGroups.get(id - 1);
                if (gr.lazyPos > 0) {
                    int p0 = str.getPosition();
                    str.seek(gr.lazyPos);
                    deserializeDerivateGroup(str, gr);
                    gr.lazyPos = 0;
                    str.seek(p0);
                }
                if (li != null) 
                    li.add(gr);
                else 
                    tn.groups = gr;
            }
        }
        if (li != null) 
            tn.groups = li;
        cou = str.deserializeShort();
        if (cou == 0) 
            return;
        for (; cou > 0; cou--) {
            short ke = (short)str.deserializeShort();
            int p1 = str.deserializeInt();
            ExplanTreeNode tn1 = new ExplanTreeNode();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, ExplanTreeNode>();
            if (!tn.nodes.containsKey(ke)) 
                tn.nodes.put(ke, tn1);
            if (lazyLoad) {
                tn1.lazyPos = str.getPosition();
                str.seek(p1);
            }
            else 
                deserializeTreeNode(str, dic, tn1, false);
        }
    }
    public DeserializeHelper() {
    }
}
