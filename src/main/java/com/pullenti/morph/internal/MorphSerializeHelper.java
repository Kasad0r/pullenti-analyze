/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class MorphSerializeHelper {

    public static void deflateGzip(com.pullenti.unisharp.Stream str, com.pullenti.unisharp.Stream res) throws Exception, java.io.IOException {
        try (com.pullenti.unisharp.GZipStream deflate = new com.pullenti.unisharp.GZipStream(str, false)) {
            byte[] buf = new byte[100000];
            while (true) {
                int i = -1;
                try {
                    for (int ii = 0; ii < buf.length; ii++) {
                        buf[ii] = (byte)0;
                    }
                    i = deflate.read(buf, 0, buf.length);
                } catch (Exception ex) {
                    for (i = buf.length - 1; i >= 0; i--) {
                        if (buf[i] != ((byte)0)) {
                            res.write(buf, 0, i + 1);
                            break;
                        }
                    }
                    break;
                }
                if (i < 1) 
                    break;
                res.write(buf, 0, i);
            }
        }
    }

    public static ByteArrayWrapper deserializeAll(com.pullenti.unisharp.Stream str0, MorphEngine me, boolean ignoreRevTree, boolean lazyLoad) throws java.io.IOException, Exception {
        com.pullenti.unisharp.MemoryStream tmp = new com.pullenti.unisharp.MemoryStream();
        deflateGzip(str0, tmp);
        ByteArrayWrapper buf = new ByteArrayWrapper(tmp.toByteArray());
        me.m_Vars.clear();
        me.m_Rules.clear();
        me.m_Root = new MorphTreeNode();
        me.m_RootReverce = new MorphTreeNode();
        int cou = buf.deserializeInt();
        for (; cou > 0; cou--) {
            com.pullenti.morph.MorphMiscInfo mi = new com.pullenti.morph.MorphMiscInfo();
            deserializeMorphMiscInfo(buf, mi);
            me.m_Vars.add(mi);
        }
        cou = buf.deserializeInt();
        for (; cou > 0; cou--) {
            int p1 = buf.deserializeInt();
            MorphRule r = new MorphRule();
            if (lazyLoad) {
                r.lazyPos = buf.getPosition();
                buf.seek(p1);
            }
            else 
                deserializeMorphRule(buf, r, me);
            me.m_Rules.add(r);
        }
        if (lazyLoad) 
            deserializeMorphTreeNodeLazy(buf, me.m_Root, me);
        else 
            deserializeMorphTreeNode(buf, me.m_Root, me);
        if (!ignoreRevTree) {
            if (lazyLoad) 
                deserializeMorphTreeNodeLazy(buf, me.m_RootReverce, me);
            else 
                deserializeMorphTreeNode(buf, me.m_RootReverce, me);
        }
        tmp.close();
        return buf;
    }

    private static void serializeMorphMiscInfo(com.pullenti.unisharp.Stream res, com.pullenti.morph.MorphMiscInfo mi) throws java.io.IOException {
        serializeShort(res, (int)mi.m_Value);
        for (String a : mi.getAttrs()) {
            serializeString(res, a);
        }
        res.write((byte)0xFF);
    }

    private static void deserializeMorphMiscInfo(ByteArrayWrapper str, com.pullenti.morph.MorphMiscInfo mi) {
        mi.m_Value = (short)str.deserializeShort();
        while (true) {
            String s = str.deserializeString();
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(s)) 
                break;
            mi.getAttrs().add(s);
        }
    }

    private static void serializeByte(com.pullenti.unisharp.Stream res, byte val) throws java.io.IOException {
        res.write(val);
    }

    private static void serializeShort(com.pullenti.unisharp.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
    }

    private static void serializeInt(com.pullenti.unisharp.Stream res, int val) throws java.io.IOException {
        res.write((byte)val);
        res.write((byte)((val >> 8)));
        res.write((byte)((val >> 16)));
        res.write((byte)((val >> 24)));
    }

    private static void serializeString(com.pullenti.unisharp.Stream res, String s) throws java.io.IOException {
        if (s == null) 
            res.write((byte)0xFF);
        else if (s.length() == 0) 
            res.write((byte)0);
        else {
            byte[] data = com.pullenti.unisharp.Utils.encodeCharset(java.nio.charset.Charset.forName("UTF-8"), s);
            res.write((byte)data.length);
            res.write(data, 0, data.length);
        }
    }

    private static void serializeMorphRule(com.pullenti.unisharp.Stream res, MorphRule r) throws java.io.IOException {
        serializeShort(res, r.id);
        for (java.util.Map.Entry<String, java.util.ArrayList<MorphRuleVariant>> v : r.variants.entrySet()) {
            serializeString(res, v.getKey());
            for (MorphRuleVariant m : v.getValue()) {
                serializeMorphRuleVariant(res, m);
            }
            serializeShort(res, 0);
        }
        res.write((byte)0xFF);
    }

    private static void deserializeMorphRule(ByteArrayWrapper str, MorphRule r, MorphEngine me) {
        r.id = str.deserializeShort();
        while (!str.isEOF()) {
            byte b = str.deserializeByte();
            if (b == ((byte)0xFF)) 
                break;
            str.back();
            String key = (String)com.pullenti.unisharp.Utils.notnull(str.deserializeString(), "");
            java.util.ArrayList<MorphRuleVariant> li = new java.util.ArrayList<MorphRuleVariant>();
            r.variants.put(key, li);
            r.variantsKey.add(key);
            r.variantsList.add(li);
            while (!str.isEOF()) {
                MorphRuleVariant mrv = deserializeMorphRuleVariant(str, me);
                if (mrv == null) 
                    break;
                mrv.tail = key;
                mrv.rule = r;
                li.add(mrv);
            }
        }
    }

    private static void serializeMorphRuleVariant(com.pullenti.unisharp.Stream res, MorphRuleVariant v) throws java.io.IOException {
        serializeShort(res, v.miscInfo.id);
        serializeShort(res, (int)(v._getClass().value));
        serializeByte(res, (byte)v.getGender().value());
        serializeByte(res, (byte)v.getNumber().value());
        serializeByte(res, (byte)v.getCase().value);
        serializeString(res, v.normalTail);
        serializeString(res, v.fullNormalTail);
    }

    private static MorphRuleVariant deserializeMorphRuleVariant(ByteArrayWrapper str, MorphEngine me) {
        int id = str.deserializeShort() - 1;
        if ((id < 0) || id >= me.m_Vars.size()) 
            return null;
        MorphRuleVariant mrv = MorphRuleVariant._new36(me.m_Vars.get(id));
        com.pullenti.morph.MorphClass mc = new com.pullenti.morph.MorphClass(null);
        mc.value = (short)str.deserializeShort();
        if (mc.isMisc() && mc.isProper()) 
            mc.setMisc(false);
        mrv._setClass(mc);
        mrv.setGender(com.pullenti.morph.MorphGender.of(Byte.toUnsignedInt(str.deserializeByte())));
        mrv.setNumber(com.pullenti.morph.MorphNumber.of(Byte.toUnsignedInt(str.deserializeByte())));
        com.pullenti.morph.MorphCase mca = new com.pullenti.morph.MorphCase(null);
        mca.value = (short)Byte.toUnsignedInt(str.deserializeByte());
        mrv.setCase(mca);
        mrv.normalTail = str.deserializeString();
        mrv.fullNormalTail = str.deserializeString();
        return mrv;
    }

    private static void serializeMorphTreeNode(com.pullenti.unisharp.Stream res, MorphTreeNode tn) throws java.io.IOException {
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                serializeShort(res, r.id);
            }
        }
        serializeShort(res, 0);
        if (tn.reverceVariants != null) {
            java.util.Collections.sort(tn.reverceVariants);
            for (MorphRuleVariant v : tn.reverceVariants) {
                serializeString(res, (v.tail != null ? v.tail : ""));
                if (v.rule != null) {
                }
                serializeShort(res, (v.rule == null ? 0 : v.rule.id));
                serializeShort(res, (int)v.coef);
                serializeMorphRuleVariant(res, v);
            }
        }
        serializeString(res, null);
        if (tn.nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> n : tn.nodes.entrySet()) {
                serializeShort(res, (int)(short)n.getKey());
                int p0 = (int)res.getPosition();
                serializeInt(res, 0);
                serializeMorphTreeNode(res, n.getValue());
                int p1 = (int)res.getPosition();
                res.setPosition((long)p0);
                serializeInt(res, p1);
                res.setPosition((long)p1);
            }
        }
        serializeShort(res, 0xFFFF);
    }

    private static void deserializeMorphTreeNodeBase(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me) {
        while (!str.isEOF()) {
            int i = str.deserializeShort();
            i--;
            if ((i < 0) || i >= me.m_Rules.size()) 
                break;
            MorphRule r = me.m_Rules.get(i);
            if (tn.rules == null) 
                tn.rules = new java.util.ArrayList<MorphRule>();
            tn.rules.add(r);
        }
        while (!str.isEOF()) {
            String tail = str.deserializeString();
            if (tail == null) 
                break;
            int ruleId = str.deserializeShort();
            int coef = str.deserializeShort();
            MorphRuleVariant v = deserializeMorphRuleVariant(str, me);
            if (v == null) 
                break;
            v.tail = tail;
            if (ruleId > 0 && ruleId <= me.m_Rules.size()) 
                v.rule = me.m_Rules.get(ruleId - 1);
            else {
            }
            if (tn.reverceVariants == null) 
                tn.reverceVariants = new java.util.ArrayList<MorphRuleVariant>();
            v.coef = (short)coef;
            tn.reverceVariants.add(v);
        }
    }

    public static void deserializeMorphTreeNodeLazy(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me) {
        deserializeMorphTreeNodeBase(str, tn, me);
        while (!str.isEOF()) {
            int i = str.deserializeShort();
            if (i == 0xFFFF) 
                break;
            int pos = str.deserializeInt();
            MorphTreeNode child = new MorphTreeNode();
            child.lazyPos = str.getPosition();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
            tn.nodes.put((short)i, child);
            str.seek(pos);
        }
        int p = str.getPosition();
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                if (r.lazyPos > 0) {
                    str.seek(r.lazyPos);
                    deserializeMorphRule(str, r, me);
                    r.lazyPos = 0;
                }
            }
            str.seek(p);
        }
    }

    private static int deserializeMorphTreeNode(ByteArrayWrapper str, MorphTreeNode tn, MorphEngine me) {
        int res = 0;
        deserializeMorphTreeNodeBase(str, tn, me);
        while (!str.isEOF()) {
            int i = str.deserializeShort();
            if (i == 0xFFFF) 
                break;
            int pos = str.deserializeInt();
            MorphTreeNode child = new MorphTreeNode();
            if (tn.nodes == null) 
                tn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
            tn.nodes.put((short)i, child);
            res++;
            res += deserializeMorphTreeNode(str, child, me);
        }
        return res;
    }

    public static int MAXVARIANTS = 0;

    private static void _manageReverceNodes(MorphTreeNode root, MorphTreeNode tn, String term) {
        if (tn.rules != null) {
            for (MorphRule r : tn.rules) {
                for (java.util.Map.Entry<String, java.util.ArrayList<MorphRuleVariant>> v : r.variants.entrySet()) {
                    String wf = term + v.getKey();
                    if (wf.length() <= minTailLen) 
                        continue;
                    MorphTreeNode rtn = root;
                    for (int lev = 0; lev < maxTailLen; lev++) {
                        int i = wf.length() - 1 - lev;
                        if (i < 0) 
                            break;
                        short ch = (short)wf.charAt(i);
                        if (rtn.nodes == null) 
                            rtn.nodes = new java.util.HashMap<Short, MorphTreeNode>();
                        MorphTreeNode next = null;
                        com.pullenti.unisharp.Outargwrapper<MorphTreeNode> wrapnext37 = new com.pullenti.unisharp.Outargwrapper<MorphTreeNode>();
                        boolean inoutres38 = com.pullenti.unisharp.Utils.tryGetValue(rtn.nodes, ch, wrapnext37);
                        next = wrapnext37.value;
                        if (!inoutres38) {
                            next = new MorphTreeNode();
                            rtn.nodes.put(ch, next);
                        }
                        rtn = next;
                        if ((lev + 1) < minTailLen) 
                            continue;
                        if (rtn.reverceVariants == null) 
                            rtn.reverceVariants = new java.util.ArrayList<MorphRuleVariant>();
                        for (MorphRuleVariant mrf : v.getValue()) {
                            boolean has = false;
                            for (MorphRuleVariant mfv0 : rtn.reverceVariants) {
                                if (mfv0.compare(mrf)) {
                                    mfv0.coef++;
                                    has = true;
                                    break;
                                }
                            }
                            if (!has) {
                                MorphRuleVariant mrf0 = new MorphRuleVariant(mrf);
                                mrf0.coef = (short)1;
                                rtn.reverceVariants.add(mrf0);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (tn.nodes != null) {
            for (java.util.Map.Entry<Short, MorphTreeNode> tch : tn.nodes.entrySet()) {
                _manageReverceNodes(root, tch.getValue(), (term + (((char)(short)tch.getKey()))));
            }
        }
    }

    private static final int minTailLen = 4;

    private static final int maxTailLen = 7;
    public MorphSerializeHelper() {
    }
}
