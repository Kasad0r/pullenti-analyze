/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.geo;

/**
 * Семантический анализатор стран
 */
public class GeoAnalyzer extends com.pullenti.ner.Analyzer {

    public static final String ANALYZER_NAME = "GEO";

    @Override
    public String getName() {
        return ANALYZER_NAME;
    }


    @Override
    public String getCaption() {
        return "Страны, регионы, города";
    }


    @Override
    public com.pullenti.ner.Analyzer clone() {
        return new GeoAnalyzer();
    }

    @Override
    public java.util.Collection<com.pullenti.ner.ReferentClass> getTypeSystem() {
        return java.util.Arrays.asList(new com.pullenti.ner.ReferentClass[] {com.pullenti.ner.geo.internal.MetaGeo.globalMeta});
    }


    @Override
    public Iterable<String> getUsedExternObjectTypes() {
        return java.util.Arrays.asList(new String[] {"PHONE"});
    }


    @Override
    public java.util.HashMap<String, byte[]> getImages() {
        java.util.HashMap<String, byte[]> res = new java.util.HashMap<String, byte[]>();
        res.put(com.pullenti.ner.geo.internal.MetaGeo.COUNTRYCITYIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("countrycity.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.COUNTRYIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("country.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.CITYIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("city.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.DISTRICTIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("district.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.REGIONIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("region.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.TERRIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("territory.png"));
        res.put(com.pullenti.ner.geo.internal.MetaGeo.UNIONIMAGEID, com.pullenti.ner.core.internal.ResourceHelper.getBytes("union.png"));
        return res;
    }


    @Override
    public com.pullenti.ner.Referent createReferent(String type) {
        if (com.pullenti.unisharp.Utils.stringsEq(type, GeoReferent.OBJ_TYPENAME)) 
            return new GeoReferent();
        return null;
    }

    @Override
    public int getProgressWeight() {
        return 15;
    }


    public static class GeoAnalyzerDataWithOntology extends com.pullenti.ner.core.AnalyzerDataWithOntology {
    
        private static String[] ends;
    
        @Override
        public com.pullenti.ner.Referent registerReferent(com.pullenti.ner.Referent referent) {
            com.pullenti.ner.geo.GeoReferent g = (com.pullenti.ner.geo.GeoReferent)com.pullenti.unisharp.Utils.cast(referent, com.pullenti.ner.geo.GeoReferent.class);
            if (g != null) {
                if (g.isState()) {
                }
                else if (g.isRegion() || ((g.isCity() && !g.isBigCity()))) {
                    java.util.ArrayList<String> names = new java.util.ArrayList<String>();
                    com.pullenti.morph.MorphGender gen = com.pullenti.morph.MorphGender.UNDEFINED;
                    String basNam = null;
                    for (com.pullenti.ner.Slot s : g.getSlots()) {
                        if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_NAME)) 
                            names.add((String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class));
                        else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.geo.GeoReferent.ATTR_TYPE)) {
                            String typ = (String)com.pullenti.unisharp.Utils.cast(s.getValue(), String.class);
                            if (com.pullenti.morph.LanguageHelper.endsWithEx(typ, "район", "край", "округ", "улус")) 
                                gen = com.pullenti.morph.MorphGender.of((gen.value()) | (com.pullenti.morph.MorphGender.MASCULINE.value()));
                            else if (com.pullenti.morph.LanguageHelper.endsWithEx(typ, "область", "территория", null, null)) 
                                gen = com.pullenti.morph.MorphGender.of((gen.value()) | (com.pullenti.morph.MorphGender.FEMINIE.value()));
                        }
                    }
                    for (int i = 0; i < names.size(); i++) {
                        String n = names.get(i);
                        int ii = n.indexOf(' ');
                        if (ii > 0) {
                            if (g.getSlotValue(com.pullenti.ner.geo.GeoReferent.ATTR_REF) instanceof com.pullenti.ner.Referent) 
                                continue;
                            String nn = (n.substring(ii + 1) + " " + n.substring(0, 0 + ii));
                            if (!names.contains(nn)) {
                                names.add(nn);
                                g.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, nn, false, 0);
                                continue;
                            }
                            continue;
                        }
                        for (String end : ends) {
                            if (com.pullenti.morph.LanguageHelper.endsWith(n, end)) {
                                String nn = n.substring(0, 0 + n.length() - 3);
                                for (String end2 : ends) {
                                    if (com.pullenti.unisharp.Utils.stringsNe(end2, end)) {
                                        if (!names.contains(nn + end2)) {
                                            names.add(nn + end2);
                                            g.addSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, nn + end2, false, 0);
                                        }
                                    }
                                }
                                if (gen == com.pullenti.morph.MorphGender.MASCULINE) {
                                    for (String na : names) {
                                        if (com.pullenti.morph.LanguageHelper.endsWith(na, "ИЙ")) 
                                            basNam = na;
                                    }
                                }
                                else if (gen == com.pullenti.morph.MorphGender.FEMINIE) {
                                    for (String na : names) {
                                        if (com.pullenti.morph.LanguageHelper.endsWith(na, "АЯ")) 
                                            basNam = na;
                                    }
                                }
                                else if (gen == com.pullenti.morph.MorphGender.NEUTER) {
                                    for (String na : names) {
                                        if (com.pullenti.morph.LanguageHelper.endsWith(na, "ОЕ")) 
                                            basNam = na;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    if (basNam != null && names.size() > 0 && com.pullenti.unisharp.Utils.stringsNe(names.get(0), basNam)) {
                        com.pullenti.ner.Slot sl = g.findSlot(com.pullenti.ner.geo.GeoReferent.ATTR_NAME, basNam, true);
                        if (sl != null) {
                            g.getSlots().remove(sl);
                            g.getSlots().add(0, sl);
                        }
                    }
                }
            }
            return super.registerReferent(referent);
        }
    
        public GeoAnalyzerDataWithOntology() {
            super();
        }
        
        static {
            ends = new String[] {"КИЙ", "КОЕ", "КАЯ"};
        }
    }


    @Override
    public com.pullenti.ner.core.AnalyzerData createAnalyzerData() {
        return new GeoAnalyzerDataWithOntology();
    }

    @Override
    public void process(com.pullenti.ner.core.AnalysisKit kit) {
        com.pullenti.ner.core.AnalyzerDataWithOntology ad = (com.pullenti.ner.core.AnalyzerDataWithOntology)com.pullenti.unisharp.Utils.cast(kit.getAnalyzerData(this), com.pullenti.ner.core.AnalyzerDataWithOntology.class);
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            t.setInnerBool(false);
        }
        java.util.ArrayList<GeoReferent> nonRegistered = new java.util.ArrayList<GeoReferent>();
        for (int step = 0; step < 2; step++) {
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (ad.getReferents().size() >= 2000) 
                    break;
                if (step > 0 && (t instanceof com.pullenti.ner.ReferentToken)) {
                    GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class);
                    if (((_geo != null && t.getNext() != null && t.getNext().isChar('(')) && t.getNext().getNext() != null && _geo.canBeEquals(t.getNext().getNext().getReferent(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) && t.getNext().getNext().getNext() != null && t.getNext().getNext().getNext().isChar(')')) {
                        com.pullenti.ner.ReferentToken rt0 = com.pullenti.ner.ReferentToken._new719(_geo, t, t.getNext().getNext().getNext(), t.getMorph());
                        kit.embedToken(rt0);
                        t = rt0;
                        continue;
                    }
                    if ((_geo != null && t.getNext() != null && t.getNext().isHiphen()) && t.getNext().getNext() != null && _geo.canBeEquals(t.getNext().getNext().getReferent(), com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                        com.pullenti.ner.ReferentToken rt0 = com.pullenti.ner.ReferentToken._new719(_geo, t, t.getNext().getNext(), t.getMorph());
                        kit.embedToken(rt0);
                        t = rt0;
                        continue;
                    }
                }
                boolean ok = false;
                if (step == 0 || t.getInnerBool()) 
                    ok = true;
                else if ((t instanceof com.pullenti.ner.TextToken) && t.chars.isLetter() && !t.chars.isAllLower()) 
                    ok = true;
                java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> cli = null;
                if (ok) 
                    cli = com.pullenti.ner.geo.internal.TerrItemToken.tryParseList(t, ad.localOntology, 5);
                if (cli == null) 
                    continue;
                t.setInnerBool(true);
                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, false, null, nonRegistered);
                if ((rt == null && cli.size() == 1 && cli.get(0).isAdjective) && cli.get(0).ontoItem != null) {
                    com.pullenti.ner.Token tt = cli.get(0).getEndToken().getNext();
                    if (tt != null) {
                        if (tt.isChar(',')) 
                            tt = tt.getNext();
                        else if (tt.getMorph()._getClass().isConjunction()) {
                            tt = tt.getNext();
                            if (tt != null && tt.getMorph()._getClass().isConjunction()) 
                                tt = tt.getNext();
                        }
                        java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> cli1 = com.pullenti.ner.geo.internal.TerrItemToken.tryParseList(tt, ad.localOntology, 2);
                        if (cli1 != null && cli1.get(0).ontoItem != null) {
                            GeoReferent g0 = (GeoReferent)com.pullenti.unisharp.Utils.cast(cli.get(0).ontoItem.referent, GeoReferent.class);
                            GeoReferent g1 = (GeoReferent)com.pullenti.unisharp.Utils.cast(cli1.get(0).ontoItem.referent, GeoReferent.class);
                            if ((g0 != null && g1 != null && g0.isRegion()) && g1.isRegion()) {
                                if (g0.isCity() == g1.isCity() || g0.isRegion() == g1.isRegion() || g0.isState() == g1.isState()) 
                                    rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, true, null, null);
                            }
                        }
                        if (rt == null && (((GeoReferent)com.pullenti.unisharp.Utils.cast(cli.get(0).ontoItem.referent, GeoReferent.class))).isState()) {
                            if ((rt == null && tt != null && (tt.getReferent() instanceof GeoReferent)) && tt.getWhitespacesBeforeCount() == 1) {
                                GeoReferent geo2 = (GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), GeoReferent.class);
                                if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher((GeoReferent)com.pullenti.unisharp.Utils.cast(cli.get(0).ontoItem.referent, GeoReferent.class), geo2)) 
                                    rt = com.pullenti.ner.ReferentToken._new719(cli.get(0).ontoItem.referent.clone(), cli.get(0).getBeginToken(), cli.get(0).getEndToken(), cli.get(0).getMorph());
                            }
                            if (rt == null && step == 0) {
                                com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(cli.get(0).getBeginToken(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
                                if (npt != null && npt.endChar >= tt.beginChar) {
                                    java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> cits = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(tt, ad.localOntology, 5);
                                    com.pullenti.ner.ReferentToken rt1 = (cits == null ? null : com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(cits, ad, false));
                                    if (rt1 != null) {
                                        rt1.referent = ad.registerReferent(rt1.referent);
                                        kit.embedToken(rt1);
                                        rt = com.pullenti.ner.ReferentToken._new719(cli.get(0).ontoItem.referent.clone(), cli.get(0).getBeginToken(), cli.get(0).getEndToken(), cli.get(0).getMorph());
                                    }
                                }
                            }
                        }
                    }
                }
                if (rt == null) {
                    java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> cits = this.tryParseCityListBack(t.getPrevious());
                    if (cits != null) 
                        rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, false, cits, null);
                }
                if (rt == null && cli.size() > 1) {
                    com.pullenti.ner.Token te = cli.get(cli.size() - 1).getEndToken().getNext();
                    if (te != null) {
                        if (te.getMorph()._getClass().isPreposition() || te.isChar(',')) 
                            te = te.getNext();
                    }
                    java.util.ArrayList<com.pullenti.ner.address.internal.AddressItemToken> li = com.pullenti.ner.address.internal.AddressItemToken.tryParseList(te, null, 2);
                    if (li != null && li.size() > 0) {
                        if (li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET || li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.KILOMETER || li.get(0).typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.HOUSE) {
                            com.pullenti.ner.address.internal.StreetItemToken ad0 = com.pullenti.ner.address.internal.StreetItemToken.tryParse(cli.get(0).getBeginToken().getPrevious(), null, false, null, false);
                            if (ad0 != null && ad0.typ == com.pullenti.ner.address.internal.StreetItemType.NOUN) {
                            }
                            else if (!cli.get(0).isAdjective) 
                                rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, true, null, null);
                            else {
                                com.pullenti.ner.address.internal.AddressItemToken aaa = com.pullenti.ner.address.internal.AddressItemToken.tryParse(cli.get(0).getBeginToken(), null, false, false, null);
                                if (aaa != null && aaa.typ == com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET) {
                                }
                                else 
                                    rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, true, null, null);
                            }
                        }
                    }
                }
                if ((rt == null && cli.size() > 2 && cli.get(0).terminItem == null) && cli.get(1).terminItem == null && cli.get(2).terminItem != null) {
                    com.pullenti.ner.geo.internal.CityItemToken cit = com.pullenti.ner.geo.internal.CityItemToken.tryParseBack(cli.get(0).getBeginToken().getPrevious());
                    if (cit != null && cit.typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.NOUN) {
                        if (((cli.size() > 4 && cli.get(1).terminItem == null && cli.get(2).terminItem != null) && cli.get(3).terminItem == null && cli.get(4).terminItem != null) && cli.get(2).terminItem.getCanonicText().endsWith(cli.get(4).terminItem.getCanonicText())) {
                        }
                        else {
                            cli.remove(0);
                            rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(cli, ad, true, null, null);
                        }
                    }
                }
                if (rt != null) {
                    GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, GeoReferent.class);
                    if (!_geo.isCity() && !_geo.isState() && _geo.findSlot(GeoReferent.ATTR_TYPE, "республика", true) == null) 
                        nonRegistered.add(_geo);
                    else 
                        rt.referent = ad.registerReferent(_geo);
                    kit.embedToken(rt);
                    t = rt;
                    if (step == 0) {
                        com.pullenti.ner.Token tt = t;
                        while (true) {
                            com.pullenti.ner.ReferentToken rr = this.tryAttachTerritoryBeforeCity(tt, ad);
                            if (rr == null) 
                                break;
                            _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(rr.referent, GeoReferent.class);
                            if (!_geo.isCity() && !_geo.isState()) 
                                nonRegistered.add(_geo);
                            else 
                                rr.referent = ad.registerReferent(_geo);
                            kit.embedToken(rr);
                            tt = rr;
                        }
                        if (t.getNext() != null && ((t.getNext().isComma() || t.getNext().isChar('(')))) {
                            com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachStateUSATerritory(t.getNext().getNext());
                            if (rt1 != null) {
                                rt1.referent = ad.registerReferent(rt1.referent);
                                kit.embedToken(rt1);
                                t = rt1;
                            }
                        }
                    }
                    continue;
                }
            }
            if (step == 0) {
                if (!this.onProgress(1, 4, kit)) 
                    return;
            }
            else {
                if (!this.onProgress(2, 4, kit)) 
                    return;
                if (ad.getReferents().size() == 0 && nonRegistered.size() == 0) 
                    break;
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = (t == null ? null : t.getNext())) {
            GeoReferent g = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class);
            if (g == null) 
                continue;
            if (!((t.getPrevious() instanceof com.pullenti.ner.TextToken))) 
                continue;
            com.pullenti.ner.Token t0 = null;
            if (t.getPrevious().isValue("СОЮЗ", null)) 
                t0 = t.getPrevious();
            else if (t.getPrevious().isValue("ГОСУДАРСТВО", null) && t.getPrevious().getPrevious() != null && t.getPrevious().getPrevious().isValue("СОЮЗНЫЙ", null)) 
                t0 = t.getPrevious().getPrevious();
            if (t0 == null) 
                continue;
            com.pullenti.ner.core.NounPhraseToken npt = com.pullenti.ner.core.NounPhraseHelper.tryParse(t0.getPrevious(), com.pullenti.ner.core.NounPhraseParseAttr.NO, 0);
            if (npt != null && npt.getEndToken() == t.getPrevious()) 
                t0 = t0.getPrevious();
            GeoReferent uni = new GeoReferent();
            String typ = com.pullenti.ner.core.MiscHelper.getTextValue(t0, t.getPrevious(), com.pullenti.ner.core.GetTextAttr.FIRSTNOUNGROUPTONOMINATIVE);
            if (typ == null) 
                continue;
            uni.addTypUnion(t0.kit.baseLanguage);
            uni.addTyp(typ.toLowerCase());
            uni.addSlot(GeoReferent.ATTR_REF, g, false, 0);
            com.pullenti.ner.Token t1 = t;
            int i = 1;
            for (t = t.getNext(); t != null; t = t.getNext()) {
                if (t.isCommaAnd()) 
                    continue;
                if ((((g = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class)))) == null) 
                    break;
                if (uni.findSlot(GeoReferent.ATTR_REF, g, true) != null) 
                    break;
                if (t.isNewlineBefore()) 
                    break;
                t1 = t;
                uni.addSlot(GeoReferent.ATTR_REF, g, false, 0);
                i++;
            }
            if (i < 2) 
                continue;
            uni = (GeoReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(uni), GeoReferent.class);
            com.pullenti.ner.ReferentToken rt = new com.pullenti.ner.ReferentToken(uni, t0, t1, null);
            kit.embedToken(rt);
            t = rt;
        }
        boolean newCities = false;
        boolean isCityBefore = false;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (t.isCharOf(".,")) 
                continue;
            java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> li = null;
            li = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(t, ad.localOntology, 5);
            com.pullenti.ner.ReferentToken rt;
            if (li != null) {
                if ((((rt = com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(li, ad, false)))) != null) {
                    com.pullenti.ner.Token tt = t.getPrevious();
                    if (tt != null && tt.isComma()) 
                        tt = tt.getPrevious();
                    if (tt != null && (tt.getReferent() instanceof GeoReferent)) {
                        if (tt.getReferent().canBeEquals(rt.referent, com.pullenti.ner.Referent.EqualType.WITHINONETEXT)) {
                            rt.setBeginToken(tt);
                            rt.referent = ad.registerReferent(rt.referent);
                            kit.embedToken(rt);
                            t = rt;
                            continue;
                        }
                    }
                    if (ad.getReferents().size() > 2000) 
                        break;
                    rt.referent = (GeoReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(rt.referent), GeoReferent.class);
                    kit.embedToken(rt);
                    t = rt;
                    isCityBefore = true;
                    newCities = true;
                    tt = t;
                    while (true) {
                        com.pullenti.ner.ReferentToken rr = this.tryAttachTerritoryBeforeCity(tt, ad);
                        if (rr == null) 
                            break;
                        GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(rr.referent, GeoReferent.class);
                        if (!_geo.isCity() && !_geo.isState()) 
                            nonRegistered.add(_geo);
                        else 
                            rr.referent = ad.registerReferent(_geo);
                        kit.embedToken(rr);
                        tt = rr;
                    }
                    rt = this.tryAttachTerritoryAfterCity(t, ad);
                    if (rt != null) {
                        rt.referent = ad.registerReferent(rt.referent);
                        kit.embedToken(rt);
                        t = rt;
                    }
                    continue;
                }
            }
            if (!t.getInnerBool()) {
                isCityBefore = false;
                continue;
            }
            if (!isCityBefore) 
                continue;
            java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> tts = com.pullenti.ner.geo.internal.TerrItemToken.tryParseList(t, ad.localOntology, 5);
            if (tts != null && tts.size() > 1 && ((tts.get(0).terminItem != null || tts.get(1).terminItem != null))) {
                if ((((rt = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(tts, ad, true, null, null)))) != null) {
                    GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, GeoReferent.class);
                    if (!_geo.isCity() && !_geo.isState()) 
                        nonRegistered.add(_geo);
                    else 
                        rt.referent = ad.registerReferent(_geo);
                    kit.embedToken(rt);
                    t = rt;
                    continue;
                }
            }
            isCityBefore = false;
        }
        if (newCities && ad.localOntology.getItems().size() > 0) {
            for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
                if (!((t instanceof com.pullenti.ner.TextToken))) 
                    continue;
                if (t.chars.isAllLower()) 
                    continue;
                java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> li = ad.localOntology.tryAttach(t, null, false);
                if (li == null) 
                    continue;
                com.pullenti.morph.MorphClass mc = t.getMorphClassInDictionary();
                if (mc.isProperSurname() || mc.isProperName() || mc.isProperSecname()) 
                    continue;
                if (t.getMorph()._getClass().isAdjective()) 
                    continue;
                GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(li.get(0).item.referent, GeoReferent.class);
                if (_geo != null) {
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(_geo, li.get(0).getBeginToken(), li.get(0).getEndToken(), t.getMorph());
                    if (rt.getBeginToken() == rt.getEndToken()) 
                        _geo.addName((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).term);
                    if (rt.getBeginToken().getPrevious() != null && rt.getBeginToken().getPrevious().isValue("СЕЛО", null) && _geo.isCity()) {
                        rt.setBeginToken(rt.getBeginToken().getPrevious());
                        rt.setMorph(rt.getBeginToken().getMorph());
                        _geo.addSlot(GeoReferent.ATTR_TYPE, "село", true, 0);
                    }
                    kit.embedToken(rt);
                    t = li.get(0).getEndToken();
                }
            }
        }
        boolean goBack = false;
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            if (goBack) {
                goBack = false;
                if (t.getPrevious() != null) 
                    t = t.getPrevious();
            }
            GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class);
            if (_geo == null) 
                continue;
            GeoReferent geo1 = null;
            com.pullenti.ner.Token tt = t.getNext();
            boolean bra = false;
            boolean comma1 = false;
            boolean comma2 = false;
            boolean inp = false;
            boolean adj = false;
            for (; tt != null; tt = tt.getNext()) {
                if (tt.isCharOf(",")) {
                    comma1 = true;
                    continue;
                }
                if (tt.isValue("IN", null) || tt.isValue("В", null)) {
                    inp = true;
                    continue;
                }
                if (com.pullenti.ner.core.MiscHelper.isEngAdjSuffix(tt)) {
                    adj = true;
                    tt = tt.getNext();
                    continue;
                }
                com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachDetail(tt);
                if (det != null) {
                    tt = det.getEndToken();
                    comma1 = true;
                    continue;
                }
                if (tt.getMorph()._getClass().isPreposition()) 
                    continue;
                if (tt.isChar('(') && tt == t.getNext()) {
                    bra = true;
                    continue;
                }
                if ((tt instanceof com.pullenti.ner.TextToken) && com.pullenti.ner.core.BracketHelper.isBracket(tt, true)) 
                    continue;
                geo1 = (GeoReferent)com.pullenti.unisharp.Utils.cast(tt.getReferent(), GeoReferent.class);
                break;
            }
            if (geo1 == null) 
                continue;
            if (tt.getWhitespacesBeforeCount() > 15) 
                continue;
            com.pullenti.ner.Token ttt = tt.getNext();
            GeoReferent geo2 = null;
            for (; ttt != null; ttt = ttt.getNext()) {
                if (ttt.isCommaAnd()) {
                    comma2 = true;
                    continue;
                }
                com.pullenti.ner.address.internal.AddressItemToken det = com.pullenti.ner.address.internal.AddressItemToken.tryAttachDetail(ttt);
                if (det != null) {
                    ttt = det.getEndToken();
                    comma2 = true;
                    continue;
                }
                if (ttt.getMorph()._getClass().isPreposition()) 
                    continue;
                geo2 = (GeoReferent)com.pullenti.unisharp.Utils.cast(ttt.getReferent(), GeoReferent.class);
                break;
            }
            if (ttt != null && ttt.getWhitespacesBeforeCount() > 15) 
                geo2 = null;
            if (geo2 != null) {
                if ((comma1 && comma2 && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(t, tt)) && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(tt, ttt)) {
                    geo2.setHigher(geo1);
                    geo1.setHigher(_geo);
                    com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(geo2, t, ttt, ttt.getMorph());
                    kit.embedToken(rt);
                    t = rt;
                    goBack = true;
                    continue;
                }
                else if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(ttt, tt)) {
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(t, ttt)) {
                        geo2.setHigher(_geo);
                        geo1.setHigher(geo2);
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(geo1, t, ttt, t.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                        goBack = true;
                        continue;
                    }
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(ttt, t) && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(t, tt)) {
                        _geo.setHigher(geo2);
                        geo1.setHigher(_geo);
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(geo1, t, ttt, tt.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                        goBack = true;
                        continue;
                    }
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(tt, t)) {
                        _geo.setHigher(geo1);
                        geo1.setHigher(geo2);
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(_geo, t, ttt, t.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                        goBack = true;
                        continue;
                    }
                }
                if (comma2) 
                    continue;
            }
            if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(t, tt) && ((!com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(tt, t) || adj))) {
                geo1.setHigher(_geo);
                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(geo1, t, tt, tt.getMorph());
                if ((geo1.isCity() && !_geo.isCity() && t.getPrevious() != null) && t.getPrevious().isValue("СТОЛИЦА", "СТОЛИЦЯ")) {
                    rt.setBeginToken(t.getPrevious());
                    rt.setMorph(t.getPrevious().getMorph());
                }
                kit.embedToken(rt);
                t = rt;
                goBack = true;
                continue;
            }
            if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(tt, t) && ((!com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigherToken(t, tt) || inp))) {
                if (_geo.getHigher() == null) 
                    _geo.setHigher(geo1);
                else if (geo1.getHigher() == null && com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(_geo.getHigher(), geo1) && !com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo1, _geo.getHigher())) {
                    geo1.setHigher(_geo.getHigher());
                    _geo.setHigher(geo1);
                }
                else 
                    _geo.setHigher(geo1);
                if (bra && tt.getNext() != null && tt.getNext().isChar(')')) 
                    tt = tt.getNext();
                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(_geo, t, tt, t.getMorph());
                kit.embedToken(rt);
                t = rt;
                goBack = true;
                continue;
            }
            if ((!tt.getMorph()._getClass().isAdjective() && !t.getMorph()._getClass().isAdjective() && tt.chars.isCyrillicLetter()) && t.chars.isCyrillicLetter() && !tt.getMorph().getCase().isInstrumental()) {
                for (GeoReferent geo0 = _geo; geo0 != null; geo0 = geo0.getHigher()) {
                    if (com.pullenti.ner.geo.internal.GeoOwnerHelper.canBeHigher(geo1, geo0)) {
                        geo0.setHigher(geo1);
                        com.pullenti.ner.ReferentToken rt = com.pullenti.ner.ReferentToken._new719(_geo, t, tt, t.getMorph());
                        kit.embedToken(rt);
                        t = rt;
                        goBack = true;
                        break;
                    }
                }
            }
        }
        if (nonRegistered.size() == 0) 
            return;
        for (int k = 0; k < nonRegistered.size(); k++) {
            boolean ch = false;
            for (int i = 0; i < (nonRegistered.size() - 1); i++) {
                if (geoComp(nonRegistered.get(i), nonRegistered.get(i + 1)) > 0) {
                    ch = true;
                    GeoReferent v = nonRegistered.get(i);
                    com.pullenti.unisharp.Utils.putArrayValue(nonRegistered, i, nonRegistered.get(i + 1));
                    com.pullenti.unisharp.Utils.putArrayValue(nonRegistered, i + 1, v);
                }
            }
            if (!ch) 
                break;
        }
        for (GeoReferent g : nonRegistered) {
            g.setTag(null);
        }
        for (GeoReferent ng : nonRegistered) {
            for (com.pullenti.ner.Slot s : ng.getSlots()) {
                if (s.getValue() instanceof GeoReferent) {
                    if ((((GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), GeoReferent.class))).getTag() instanceof GeoReferent) 
                        ng.uploadSlot(s, (GeoReferent)com.pullenti.unisharp.Utils.cast((((GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), GeoReferent.class))).getTag(), GeoReferent.class));
                }
            }
            GeoReferent rg = (GeoReferent)com.pullenti.unisharp.Utils.cast(ad.registerReferent(ng), GeoReferent.class);
            if (rg == ng) 
                continue;
            ng.setTag(rg);
            for (com.pullenti.ner.TextAnnotation oc : ng.getOccurrence()) {
                oc.setOccurenceOf(rg);
                rg.addOccurence(oc);
            }
        }
        for (com.pullenti.ner.Token t = kit.firstToken; t != null; t = t.getNext()) {
            GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class);
            if (_geo == null) 
                continue;
            _replaceTerrs((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
        }
    }

    private static void _replaceTerrs(com.pullenti.ner.ReferentToken mt) {
        if (mt == null) 
            return;
        GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(mt.referent, GeoReferent.class);
        if (_geo != null && (_geo.getTag() instanceof GeoReferent)) 
            mt.referent = (GeoReferent)com.pullenti.unisharp.Utils.cast(_geo.getTag(), GeoReferent.class);
        if (_geo != null) {
            for (com.pullenti.ner.Slot s : _geo.getSlots()) {
                if (s.getValue() instanceof GeoReferent) {
                    GeoReferent g = (GeoReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), GeoReferent.class);
                    if (g.getTag() instanceof GeoReferent) 
                        _geo.uploadSlot(s, g.getTag());
                }
            }
        }
        for (com.pullenti.ner.Token t = mt.getBeginToken(); t != null; t = t.getNext()) {
            if (t.endChar > mt.getEndToken().endChar) 
                break;
            else {
                if (t instanceof com.pullenti.ner.ReferentToken) 
                    _replaceTerrs((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.ReferentToken.class));
                if (t == mt.getEndToken()) 
                    break;
            }
        }
    }

    private static int geoComp(GeoReferent x, GeoReferent y) {
        int xcou = 0;
        for (GeoReferent g = x.getHigher(); g != null; g = g.getHigher()) {
            xcou++;
        }
        int ycou = 0;
        for (GeoReferent g = y.getHigher(); g != null; g = g.getHigher()) {
            ycou++;
        }
        if (xcou < ycou) 
            return -1;
        if (xcou > ycou) 
            return 1;
        return com.pullenti.unisharp.Utils.stringsCompare(x.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0), y.toString(true, com.pullenti.morph.MorphLang.UNKNOWN, 0), false);
    }

    private java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> tryParseCityListBack(com.pullenti.ner.Token t) {
        if (t == null) 
            return null;
        while (t != null && ((t.getMorph()._getClass().isPreposition() || t.isCharOf(",.") || t.getMorph()._getClass().isConjunction()))) {
            t = t.getPrevious();
        }
        if (t == null) 
            return null;
        java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> res = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            if (!((tt instanceof com.pullenti.ner.TextToken))) 
                break;
            if (tt.getPrevious() != null && tt.getPrevious().isHiphen() && (tt.getPrevious().getPrevious() instanceof com.pullenti.ner.TextToken)) {
                if (!tt.isWhitespaceBefore() && !tt.getPrevious().isWhitespaceBefore()) 
                    tt = tt.getPrevious().getPrevious();
            }
            java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> ci = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(tt, null, 5);
            if (ci == null && tt.getPrevious() != null) 
                ci = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(tt.getPrevious(), null, 5);
            if (ci == null) 
                break;
            if (ci.get(ci.size() - 1).getEndToken() == t) 
                res = ci;
        }
        if (res != null) 
            java.util.Collections.reverse(res);
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachTerritoryBeforeCity(com.pullenti.ner.Token t, com.pullenti.ner.core.AnalyzerDataWithOntology ad) {
        if (t instanceof com.pullenti.ner.ReferentToken) 
            t = t.getPrevious();
        for (; t != null; t = t.getPrevious()) {
            if (!t.isCharOf(",.") && !t.getMorph()._getClass().isPreposition()) 
                break;
        }
        if (t == null) 
            return null;
        int i = 0;
        com.pullenti.ner.ReferentToken res = null;
        for (com.pullenti.ner.Token tt = t; tt != null; tt = tt.getPrevious()) {
            i++;
            if (tt.isNewlineAfter() && !tt.getInnerBool()) 
                break;
            if (i > 10) 
                break;
            java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> tits0 = com.pullenti.ner.geo.internal.TerrItemToken.tryParseList(tt, ad.localOntology, 5);
            if (tits0 == null) 
                continue;
            if (tits0.get(tits0.size() - 1).getEndToken() != t) 
                break;
            java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> tits1 = com.pullenti.ner.geo.internal.TerrItemToken.tryParseList(tt.getPrevious(), ad.localOntology, 5);
            if (tits1 != null && tits1.get(tits1.size() - 1).getEndToken() == t && tits1.size() == tits0.size()) 
                tits0 = tits1;
            com.pullenti.ner.ReferentToken rr = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(tits0, ad, false, null, null);
            if (rr != null) 
                res = rr;
        }
        return res;
    }

    private com.pullenti.ner.ReferentToken tryAttachTerritoryAfterCity(com.pullenti.ner.Token t, com.pullenti.ner.core.AnalyzerDataWithOntology ad) {
        if (t == null) 
            return null;
        GeoReferent city = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.getReferent(), GeoReferent.class);
        if (city == null) 
            return null;
        if (!city.isCity()) 
            return null;
        if (t.getNext() == null || !t.getNext().isComma() || t.getNext().getWhitespacesAfterCount() > 1) 
            return null;
        com.pullenti.ner.Token tt = t.getNext().getNext();
        if (tt == null || !tt.chars.isCapitalUpper() || !((tt instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (tt.chars.isLatinLetter()) {
            com.pullenti.ner.ReferentToken re1 = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachStateUSATerritory(tt);
            if (re1 != null) 
                return re1;
        }
        com.pullenti.ner.Token t0 = tt;
        com.pullenti.ner.Token t1 = tt;
        for (int i = 0; i < 2; i++) {
            com.pullenti.ner.geo.internal.TerrItemToken tit0 = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(tt, ad.localOntology, false, false);
            if (tit0 == null || tit0.terminItem != null) {
                if (i == 0) 
                    return null;
            }
            com.pullenti.ner.geo.internal.CityItemToken cit0 = com.pullenti.ner.geo.internal.CityItemToken.tryParse(tt, ad.localOntology, false, null);
            if (cit0 == null || cit0.typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.NOUN) {
                if (i == 0) 
                    return null;
            }
            com.pullenti.ner.address.internal.AddressItemToken ait0 = com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt, null, false, false, null);
            if (ait0 != null) 
                return null;
            if (tit0 == null) {
                if (!tt.chars.isCyrillicLetter()) 
                    return null;
                com.pullenti.morph.MorphClass cla = tt.getMorphClassInDictionary();
                if (!cla.isNoun() && !cla.isAdjective()) 
                    return null;
                t1 = tt;
            }
            else 
                t1 = (tt = tit0.getEndToken());
            if (tt.getNext() == null) 
                return null;
            if (tt.getNext().isComma()) {
                tt = tt.getNext().getNext();
                break;
            }
            if (i > 0) 
                return null;
            tt = tt.getNext();
        }
        com.pullenti.ner.address.internal.AddressItemToken ait = com.pullenti.ner.address.internal.AddressItemToken.tryParse(tt, null, false, false, null);
        if (ait == null) 
            return null;
        if (ait.typ != com.pullenti.ner.address.internal.AddressItemToken.ItemType.STREET || ait.refToken != null) 
            return null;
        GeoReferent reg = new GeoReferent();
        reg.addTyp("муниципальный район");
        reg.addName(com.pullenti.ner.core.MiscHelper.getTextValue(t0, t1, com.pullenti.ner.core.GetTextAttr.NO));
        return new com.pullenti.ner.ReferentToken(reg, t0, t1, null);
    }

    /**
     * Это привязка стран к прилагательным (например, "французский лидер")
     * @param begin 
     * @param end 
     * @return 
     */
    @Override
    public com.pullenti.ner.ReferentToken processReferent(com.pullenti.ner.Token begin, com.pullenti.ner.Token end) {
        if (!((begin instanceof com.pullenti.ner.TextToken))) 
            return null;
        if (begin.kit.recurseLevel > 3) 
            return null;
        begin.kit.recurseLevel++;
        java.util.ArrayList<com.pullenti.ner.core.TerminToken> toks = com.pullenti.ner.geo.internal.CityItemToken.M_CITYADJECTIVES.tryParseAll(begin, com.pullenti.ner.core.TerminParseAttr.FULLWORDSONLY);
        begin.kit.recurseLevel--;
        if (toks != null) {
            for (com.pullenti.ner.core.TerminToken tok : toks) {
                com.pullenti.ner.core.IntOntologyItem cit = (com.pullenti.ner.core.IntOntologyItem)com.pullenti.unisharp.Utils.cast(tok.termin.tag, com.pullenti.ner.core.IntOntologyItem.class);
                if (cit == null) 
                    continue;
                GeoReferent city = new GeoReferent();
                city.addName(cit.getCanonicText());
                city.addTypCity(begin.kit.baseLanguage);
                return com.pullenti.ner.ReferentToken._new1237(city, tok.getBeginToken(), tok.getEndToken(), tok.getMorph(), begin.kit.getAnalyzerData(this));
            }
            return null;
        }
        com.pullenti.ner.core.AnalyzerDataWithOntology ad = (com.pullenti.ner.core.AnalyzerDataWithOntology)com.pullenti.unisharp.Utils.cast(begin.kit.getAnalyzerData(this), com.pullenti.ner.core.AnalyzerDataWithOntology.class);
        if (!begin.getMorph()._getClass().isAdjective()) {
            com.pullenti.ner.TextToken te = (com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(begin, com.pullenti.ner.TextToken.class);
            if ((te.chars.isAllUpper() && te.chars.isCyrillicLetter() && te.getLengthChar() == 2) && te.getMorphClassInDictionary().isUndefined()) {
                String abbr = te.term;
                GeoReferent geo0 = null;
                int cou = 0;
                for (com.pullenti.ner.core.IntOntologyItem t : ad.localOntology.getItems()) {
                    GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(t.referent, GeoReferent.class);
                    if (_geo == null) 
                        continue;
                    if (!_geo.isRegion() && !_geo.isState()) 
                        continue;
                    if (_geo.checkAbbr(abbr)) {
                        cou++;
                        geo0 = _geo;
                    }
                }
                if (cou == 1) 
                    return com.pullenti.ner.ReferentToken._new114(geo0, begin, begin, ad);
            }
            com.pullenti.ner.geo.internal.TerrItemToken tt0 = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(begin, ad.localOntology, true, false);
            if (tt0 != null && tt0.terminItem != null && com.pullenti.unisharp.Utils.stringsEq(tt0.terminItem.getCanonicText(), "РАЙОН")) {
                com.pullenti.ner.geo.internal.TerrItemToken tt1 = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(tt0.getEndToken().getNext(), ad.localOntology, true, false);
                if ((tt1 != null && tt1.chars.isCapitalUpper() && tt1.terminItem == null) && tt1.ontoItem == null) {
                    java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> li = new java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken>();
                    li.add(tt0);
                    li.add(tt1);
                    com.pullenti.ner.ReferentToken res = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(li, ad, true, null, null);
                    if (res == null) 
                        return null;
                    res.setMorph(begin.getMorph());
                    res.data = ad;
                    return res;
                }
            }
            begin.kit.recurseLevel++;
            java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> ctoks = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(begin, null, 3);
            if (ctoks == null && begin.getMorph()._getClass().isPreposition()) 
                ctoks = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(begin.getNext(), null, 3);
            begin.kit.recurseLevel--;
            if (ctoks != null) {
                if (((ctoks.size() == 2 && ctoks.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.NOUN && ctoks.get(1).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.PROPERNAME)) || ((ctoks.size() == 1 && ctoks.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.CITY))) {
                    if (ctoks.size() == 1 && ctoks.get(0).getBeginToken().getMorphClassInDictionary().isProperSurname()) {
                        begin.kit.recurseLevel++;
                        com.pullenti.ner.ReferentToken kk = begin.kit.processReferent("PERSON", ctoks.get(0).getBeginToken());
                        begin.kit.recurseLevel--;
                        if (kk != null) 
                            return null;
                    }
                    com.pullenti.ner.ReferentToken res = com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(ctoks, ad, true);
                    if (res != null) {
                        res.data = ad;
                        return res;
                    }
                }
            }
            if ((ctoks != null && ctoks.size() == 1 && ctoks.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.NOUN) && com.pullenti.unisharp.Utils.stringsEq(ctoks.get(0).value, "ГОРОД")) {
                int cou = 0;
                for (com.pullenti.ner.Token t = begin.getPrevious(); t != null; t = t.getPrevious()) {
                    if ((++cou) > 500) 
                        break;
                    if (!((t instanceof com.pullenti.ner.ReferentToken))) 
                        continue;
                    java.util.ArrayList<com.pullenti.ner.Referent> geos = t.getReferents();
                    if (geos == null) 
                        continue;
                    for (com.pullenti.ner.Referent g : geos) {
                        GeoReferent gg = (GeoReferent)com.pullenti.unisharp.Utils.cast(g, GeoReferent.class);
                        if (gg != null) {
                            if (gg.isCity()) 
                                return com.pullenti.ner.ReferentToken._new1237(gg, begin, ctoks.get(0).getEndToken(), ctoks.get(0).getMorph(), ad);
                            if (gg.getHigher() != null && gg.getHigher().isCity()) 
                                return com.pullenti.ner.ReferentToken._new1237(gg.getHigher(), begin, ctoks.get(0).getEndToken(), ctoks.get(0).getMorph(), ad);
                        }
                    }
                }
            }
            if (tt0 != null && tt0.ontoItem != null) {
            }
            else 
                return null;
        }
        begin.kit.recurseLevel++;
        com.pullenti.ner.geo.internal.TerrItemToken tt = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(begin, ad.localOntology, true, false);
        begin.kit.recurseLevel--;
        if (tt == null || tt.ontoItem == null) {
            java.util.ArrayList<com.pullenti.ner.core.IntOntologyToken> tok = com.pullenti.ner.geo.internal.TerrItemToken.m_TerrOntology.tryAttach(begin, null, false);
            if ((tok != null && tok.get(0).item != null && (tok.get(0).item.referent instanceof GeoReferent)) && (((GeoReferent)com.pullenti.unisharp.Utils.cast(tok.get(0).item.referent, GeoReferent.class))).isState()) 
                tt = com.pullenti.ner.geo.internal.TerrItemToken._new1146(tok.get(0).getBeginToken(), tok.get(0).getEndToken(), tok.get(0).item);
        }
        if (tt == null) 
            return null;
        if (tt.ontoItem != null) {
            java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> li = new java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken>();
            li.add(tt);
            com.pullenti.ner.ReferentToken res = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(li, ad, true, null, null);
            if (res == null) 
                tt.ontoItem = null;
            else {
                if (res.getBeginToken() == res.getEndToken()) {
                    com.pullenti.morph.MorphClass mc = res.getBeginToken().getMorphClassInDictionary();
                    if (mc.isAdjective()) {
                        GeoReferent _geo = (GeoReferent)com.pullenti.unisharp.Utils.cast(tt.ontoItem.referent, GeoReferent.class);
                        if (_geo.isCity() || _geo.isState()) {
                        }
                        else if (_geo.findSlot(GeoReferent.ATTR_TYPE, "федеральный округ", true) != null) 
                            return null;
                    }
                }
                res.data = ad;
                return res;
            }
        }
        if (!tt.isAdjective) 
            return null;
        if (tt.ontoItem == null) {
            com.pullenti.ner.Token t1 = tt.getEndToken().getNext();
            if (t1 == null) 
                return null;
            begin.kit.recurseLevel++;
            com.pullenti.ner.geo.internal.TerrItemToken ttyp = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(t1, ad.localOntology, true, true);
            begin.kit.recurseLevel--;
            if (ttyp == null || ttyp.terminItem == null) {
                java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> cits = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(begin, null, 2);
                if (cits != null && cits.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.CITY) 
                    return com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(cits, ad, true);
                return null;
            }
            if (t1.getMorphClassInDictionary().isAdjective()) 
                return null;
            java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken> li = new java.util.ArrayList<com.pullenti.ner.geo.internal.TerrItemToken>();
            li.add(tt);
            li.add(ttyp);
            com.pullenti.ner.ReferentToken res = com.pullenti.ner.geo.internal.TerrAttachHelper.tryAttachTerritory(li, ad, true, null, null);
            if (res == null) 
                return null;
            res.setMorph(ttyp.getMorph());
            res.data = ad;
            return res;
        }
        return null;
    }

    public com.pullenti.ner.ReferentToken processCitizen(com.pullenti.ner.Token begin) {
        if (!((begin instanceof com.pullenti.ner.TextToken))) 
            return null;
        com.pullenti.ner.core.TerminToken tok = com.pullenti.ner.geo.internal.TerrItemToken.m_MansByState.tryParse(begin, com.pullenti.ner.core.TerminParseAttr.FULLWORDSONLY);
        if (tok != null) 
            tok.getMorph().setGender(tok.termin.getGender());
        if (tok == null) 
            return null;
        GeoReferent geo0 = (GeoReferent)com.pullenti.unisharp.Utils.cast(tok.termin.tag, GeoReferent.class);
        if (geo0 == null) 
            return null;
        GeoReferent _geo = new GeoReferent();
        _geo.mergeSlots2(geo0, begin.kit.baseLanguage);
        com.pullenti.ner.ReferentToken res = new com.pullenti.ner.ReferentToken(_geo, tok.getBeginToken(), tok.getEndToken(), null);
        res.setMorph(tok.getMorph());
        com.pullenti.ner.core.AnalyzerDataWithOntology ad = (com.pullenti.ner.core.AnalyzerDataWithOntology)com.pullenti.unisharp.Utils.cast(begin.kit.getAnalyzerData(this), com.pullenti.ner.core.AnalyzerDataWithOntology.class);
        res.data = ad;
        return res;
    }

    @Override
    public com.pullenti.ner.ReferentToken processOntologyItem(com.pullenti.ner.Token begin) {
        java.util.ArrayList<com.pullenti.ner.geo.internal.CityItemToken> li = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(begin, null, 4);
        if (li != null && li.size() > 1 && li.get(0).typ == com.pullenti.ner.geo.internal.CityItemToken.ItemType.NOUN) {
            com.pullenti.ner.ReferentToken rt = com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(li, null, true);
            if (rt == null) 
                return null;
            GeoReferent city = (GeoReferent)com.pullenti.unisharp.Utils.cast(rt.referent, GeoReferent.class);
            for (com.pullenti.ner.Token t = rt.getEndToken().getNext(); t != null; t = t.getNext()) {
                if (!t.isChar(';')) 
                    continue;
                t = t.getNext();
                if (t == null) 
                    break;
                li = com.pullenti.ner.geo.internal.CityItemToken.tryParseList(t, null, 4);
                com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.geo.internal.CityAttachHelper.tryAttachCity(li, null, false);
                if (rt1 != null) {
                    t = rt.setEndToken(rt1.getEndToken());
                    city.mergeSlots2(rt1.referent, begin.kit.baseLanguage);
                }
                else {
                    com.pullenti.ner.Token tt = null;
                    for (com.pullenti.ner.Token ttt = t; ttt != null; ttt = ttt.getNext()) {
                        if (ttt.isChar(';')) 
                            break;
                        else 
                            tt = ttt;
                    }
                    if (tt != null) {
                        String str = com.pullenti.ner.core.MiscHelper.getTextValue(t, tt, com.pullenti.ner.core.GetTextAttr.NO);
                        if (str != null) 
                            city.addName(str);
                        t = rt.setEndToken(tt);
                    }
                }
            }
            return rt;
        }
        String typ = null;
        GeoReferent terr = null;
        com.pullenti.ner.Token te = null;
        for (com.pullenti.ner.Token t = begin; t != null; t = t.getNext()) {
            com.pullenti.ner.Token t0 = t;
            com.pullenti.ner.Token t1 = null;
            com.pullenti.ner.Token tn0 = null;
            com.pullenti.ner.Token tn1 = null;
            for (com.pullenti.ner.Token tt = t0; tt != null; tt = tt.getNext()) {
                if (tt.isCharOf(";")) 
                    break;
                com.pullenti.ner.geo.internal.TerrItemToken tit = com.pullenti.ner.geo.internal.TerrItemToken.tryParse(tt, null, false, false);
                if (tit != null && tit.terminItem != null) {
                    if (!tit.isAdjective) {
                        if (typ == null) 
                            typ = tit.terminItem.getCanonicText();
                        tt = tit.getEndToken();
                        t1 = tt;
                        continue;
                    }
                }
                else if (tit != null && tit.ontoItem != null) {
                }
                if (tn0 == null) 
                    tn0 = tt;
                if (tit != null) 
                    tt = tit.getEndToken();
                t1 = (tn1 = tt);
            }
            if (t1 == null) 
                continue;
            if (terr == null) 
                terr = new GeoReferent();
            if (tn0 != null) 
                terr.addName(com.pullenti.ner.core.MiscHelper.getTextValue(tn0, tn1, com.pullenti.ner.core.GetTextAttr.NO));
            t = (te = t1);
        }
        if (terr == null || te == null) 
            return null;
        if (typ != null) 
            terr.addTyp(typ);
        if (!terr.isCity() && !terr.isRegion() && !terr.isState()) 
            terr.addTypReg(begin.kit.baseLanguage);
        return new com.pullenti.ner.ReferentToken(terr, begin, te, null);
    }

    /**
     * Получить список всех стран из внутреннего словаря
     * @return 
     */
    public static java.util.ArrayList<com.pullenti.ner.Referent> getAllCountries() {
        return com.pullenti.ner.geo.internal.TerrItemToken.m_AllStates;
    }

    private static boolean m_Initialized = false;

    public static void initialize() throws Exception {
        if (m_Initialized) 
            return;
        m_Initialized = true;
        com.pullenti.ner.geo.internal.MetaGeo.initialize();
        com.pullenti.ner.address.internal.MetaAddress.initialize();
        com.pullenti.ner.address.internal.MetaStreet.initialize();
        try {
            com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = true;
            com.pullenti.ner.geo.internal.MiscLocationHelper.initialize();
            com.pullenti.ner.geo.internal.TerrItemToken.initialize();
            com.pullenti.ner.geo.internal.CityItemToken.initialize();
            com.pullenti.ner.address.AddressAnalyzer.initialize();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage(), ex);
        }
        com.pullenti.ner.core.Termin.ASSIGNALLTEXTSASNORMAL = false;
        com.pullenti.ner.ProcessorService.registerAnalyzer(new GeoAnalyzer());
    }
    public GeoAnalyzer() {
        super();
    }
    public static GeoAnalyzer _globalInstance;
    
    static {
        _globalInstance = new GeoAnalyzer(); 
    }
}
