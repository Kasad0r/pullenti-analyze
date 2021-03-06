/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.person.internal;

public class PersonHelper {

    public static com.pullenti.ner.ReferentToken createReferentToken(com.pullenti.ner.person.PersonReferent p, com.pullenti.ner.Token begin, com.pullenti.ner.Token end, com.pullenti.ner.MorphCollection _morph, java.util.ArrayList<PersonAttrToken> attrs, com.pullenti.ner.person.PersonAnalyzer.PersonAnalyzerData ad, boolean forAttribute, boolean afterBePredicate) {
        if (p == null) 
            return null;
        boolean hasPrefix = false;
        if (attrs != null) {
            for (PersonAttrToken a : attrs) {
                if (a.typ == PersonAttrTerminType.BESTREGARDS) 
                    hasPrefix = true;
                else {
                    if (a.beginChar < begin.beginChar) 
                        begin = a.getBeginToken();
                    if (a.typ != PersonAttrTerminType.PREFIX) {
                        if (a.age != null) 
                            p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_AGE, a.age, false, 0);
                        if (a.getPropRef() == null) 
                            p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, a.value, false, 0);
                        else 
                            p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, a, false, 0);
                    }
                    else if (a.gender == com.pullenti.morph.MorphGender.FEMINIE && !p.isFemale()) 
                        p.setFemale(true);
                    else if (a.gender == com.pullenti.morph.MorphGender.MASCULINE && !p.isMale()) 
                        p.setMale(true);
                }
            }
        }
        else if ((begin.getPrevious() instanceof com.pullenti.ner.TextToken) && (begin.getWhitespacesBeforeCount() < 3)) {
            if (com.pullenti.unisharp.Utils.stringsEq((((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(begin.getPrevious(), com.pullenti.ner.TextToken.class))).term, "ИП")) {
                PersonAttrToken a = new PersonAttrToken(begin.getPrevious(), begin.getPrevious());
                a.setPropRef(new com.pullenti.ner.person.PersonPropertyReferent());
                a.getPropRef().setName("индивидуальный предприниматель");
                p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, a, false, 0);
                begin = begin.getPrevious();
            }
        }
        com.pullenti.ner.MorphCollection m0 = new com.pullenti.ner.MorphCollection(null);
        for (com.pullenti.morph.MorphBaseInfo it : _morph.getItems()) {
            com.pullenti.morph.MorphBaseInfo bi = new com.pullenti.morph.MorphBaseInfo(it);
            bi.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
            if (bi.getGender() == com.pullenti.morph.MorphGender.UNDEFINED) {
                if (p.isMale() && !p.isFemale()) 
                    bi.setGender(com.pullenti.morph.MorphGender.MASCULINE);
                if (!p.isMale() && p.isFemale()) 
                    bi.setGender(com.pullenti.morph.MorphGender.FEMINIE);
            }
            m0.addItem(bi);
        }
        _morph = m0;
        if ((attrs != null && attrs.size() > 0 && !attrs.get(0).getMorph().getCase().isUndefined()) && _morph.getCase().isUndefined()) {
            _morph.setCase(attrs.get(0).getMorph().getCase());
            if (attrs.get(0).getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) 
                _morph.setNumber(com.pullenti.morph.MorphNumber.SINGULAR);
            if (p.isMale() && !p.isFemale()) 
                _morph.setGender(com.pullenti.morph.MorphGender.MASCULINE);
            else if (p.isFemale()) 
                _morph.setGender(com.pullenti.morph.MorphGender.FEMINIE);
        }
        if (begin.getPrevious() != null) {
            com.pullenti.ner.Token ttt = begin.getPrevious();
            if (ttt.isValue("ИМЕНИ", "ІМЕНІ")) 
                forAttribute = true;
            else {
                if (ttt.isChar('.') && ttt.getPrevious() != null) 
                    ttt = ttt.getPrevious();
                if (ttt.getWhitespacesAfterCount() < 3) {
                    if (ttt.isValue("ИМ", "ІМ")) 
                        forAttribute = true;
                }
            }
        }
        if (forAttribute) 
            return com.pullenti.ner.ReferentToken._new2329(p, begin, end, _morph, p.m_PersonIdentityTyp.value());
        if ((begin.getPrevious() != null && begin.getPrevious().isCommaAnd() && (begin.getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken)) && (begin.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner.person.PersonReferent)) {
            com.pullenti.ner.ReferentToken rt00 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(begin.getPrevious().getPrevious(), com.pullenti.ner.ReferentToken.class);
            for (com.pullenti.ner.Token ttt = rt00; ttt != null; ) {
                if (ttt.getPrevious() == null || !((ttt.getPrevious().getPrevious() instanceof com.pullenti.ner.ReferentToken))) 
                    break;
                if (!ttt.getPrevious().isCommaAnd() || !((ttt.getPrevious().getPrevious().getReferent() instanceof com.pullenti.ner.person.PersonReferent))) 
                    break;
                rt00 = (com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(ttt.getPrevious().getPrevious(), com.pullenti.ner.ReferentToken.class);
                ttt = rt00;
            }
            if (rt00.getBeginToken().getReferent() instanceof com.pullenti.ner.person.PersonPropertyReferent) {
                boolean ok = false;
                if ((((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(rt00.getBeginToken(), com.pullenti.ner.ReferentToken.class))).getEndToken().getNext() != null && (((com.pullenti.ner.ReferentToken)com.pullenti.unisharp.Utils.cast(rt00.getBeginToken(), com.pullenti.ner.ReferentToken.class))).getEndToken().getNext().isChar(':')) 
                    ok = true;
                else if (rt00.getBeginToken().getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                    ok = true;
                if (ok) 
                    p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, rt00.getBeginToken().getReferent(), false, 0);
            }
        }
        if (ad != null) {
            if (ad.overflowLevel > 10) 
                return com.pullenti.ner.ReferentToken._new2329(p, begin, end, _morph, p.m_PersonIdentityTyp.value());
            ad.overflowLevel++;
        }
        java.util.ArrayList<PersonAttrToken> attrs1 = null;
        boolean hasPosition = false;
        boolean openBr = false;
        for (com.pullenti.ner.Token t = end.getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore()) {
                if (t.getNewlinesBeforeCount() > 2) 
                    break;
                if (attrs1 != null && attrs1.size() > 0) 
                    break;
                com.pullenti.ner.mail.internal.MailLine ml = com.pullenti.ner.mail.internal.MailLine.parse(t, 0);
                if (ml != null && ml.typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) 
                    break;
                if (t.chars.isCapitalUpper()) {
                    PersonAttrToken attr1 = PersonAttrToken.tryAttach(t, (ad == null ? null : ad.localOntology), PersonAttrToken.PersonAttrAttachAttrs.NO);
                    boolean ok1 = false;
                    if (attr1 != null) {
                        if (hasPrefix || attr1.isNewlineAfter() || ((attr1.getEndToken().getNext() != null && attr1.getEndToken().getNext().isTableControlChar()))) 
                            ok1 = true;
                        else 
                            for (com.pullenti.ner.Token tt2 = t.getNext(); tt2 != null && tt2.endChar <= attr1.endChar; tt2 = tt2.getNext()) {
                                if (tt2.isWhitespaceBefore()) 
                                    ok1 = true;
                            }
                    }
                    else {
                        com.pullenti.ner.Token ttt = correctTailAttributes(p, t);
                        if (ttt != null && ttt != t) {
                            end = (t = ttt);
                            continue;
                        }
                    }
                    if (!ok1) 
                        break;
                }
            }
            if (t.isHiphen() || t.isCharOf("_>|")) 
                continue;
            if (t.isValue("МОДЕЛЬ", null)) 
                break;
            com.pullenti.ner.Token tt = correctTailAttributes(p, t);
            if (tt != t && tt != null) {
                end = (t = tt);
                continue;
            }
            boolean isBe = false;
            if (t.isChar('(') && t == end.getNext()) {
                openBr = true;
                t = t.getNext();
                if (t == null) 
                    break;
                PersonItemToken pit1 = PersonItemToken.tryAttach(t, null, PersonItemToken.ParseAttr.NO, null);
                if ((pit1 != null && t.chars.isCapitalUpper() && pit1.getEndToken().getNext() != null) && (t instanceof com.pullenti.ner.TextToken) && pit1.getEndToken().getNext().isChar(')')) {
                    if (pit1.lastname != null) {
                        com.pullenti.morph.MorphBaseInfo inf = com.pullenti.morph.MorphBaseInfo._new2321(com.pullenti.morph.MorphCase.NOMINATIVE);
                        if (p.isMale()) 
                            inf.setGender(com.pullenti.morph.MorphGender.of((inf.getGender().value()) | (com.pullenti.morph.MorphGender.MASCULINE.value())));
                        if (p.isFemale()) 
                            inf.setGender(com.pullenti.morph.MorphGender.of((inf.getGender().value()) | (com.pullenti.morph.MorphGender.FEMINIE.value())));
                        PersonMorphCollection sur = PersonIdentityToken.createLastname(pit1, inf);
                        if (sur != null) {
                            p.addFioIdentity(sur, null, null);
                            end = (t = pit1.getEndToken().getNext());
                            continue;
                        }
                    }
                }
            }
            else if (t.isComma()) {
                t = t.getNext();
                if ((t instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).isValue("WHO", null)) 
                    continue;
            }
            else if ((t instanceof com.pullenti.ner.TextToken) && (((com.pullenti.ner.TextToken)com.pullenti.unisharp.Utils.cast(t, com.pullenti.ner.TextToken.class))).isVerbBe()) 
                t = t.getNext();
            else if (t.isAnd() && t.isWhitespaceAfter() && !t.isNewlineAfter()) {
                if (t == end.getNext()) 
                    break;
                t = t.getNext();
            }
            else if (t.isHiphen() && t == end.getNext()) 
                t = t.getNext();
            else if (t.isChar('.') && t == end.getNext() && hasPrefix) 
                t = t.getNext();
            com.pullenti.ner.Token ttt2 = createNickname(p, t);
            if (ttt2 != null) {
                t = (end = ttt2);
                continue;
            }
            if (t == null) 
                break;
            PersonAttrToken attr = null;
            attr = PersonAttrToken.tryAttach(t, (ad == null ? null : ad.localOntology), PersonAttrToken.PersonAttrAttachAttrs.NO);
            if (attr == null) {
                if ((t != null && t.getReferent() != null && com.pullenti.unisharp.Utils.stringsEq(t.getReferent().getTypeName(), "GEO")) && attrs1 != null && openBr) 
                    continue;
                if ((t.chars.isCapitalUpper() && openBr && t.getNext() != null) && t.getNext().isChar(')')) {
                    if (p.findSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, null, true) == null) {
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_LASTNAME, t.getSourceText().toUpperCase(), false, 0);
                        t = t.getNext();
                        end = t;
                    }
                }
                if (t != null && t.isValue("КОТОРЫЙ", null) && t.getMorph().getNumber() == com.pullenti.morph.MorphNumber.SINGULAR) {
                    if (!p.isFemale() && t.getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE) {
                        p.setFemale(true);
                        p.correctData();
                    }
                    else if (!p.isMale() && t.getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE) {
                        p.setMale(true);
                        p.correctData();
                    }
                }
                break;
            }
            if (attr.getMorph().getNumber() == com.pullenti.morph.MorphNumber.PLURAL) 
                break;
            if (attr.typ == PersonAttrTerminType.BESTREGARDS) 
                break;
            if (attr.isDoubt) {
                if (hasPrefix) {
                }
                else if (t.isNewlineBefore() && attr.isNewlineAfter()) {
                }
                else if (t.getPrevious() != null && ((t.getPrevious().isHiphen() || t.getPrevious().isChar(':')))) {
                }
                else 
                    break;
            }
            if (!_morph.getCase().isUndefined() && !attr.getMorph().getCase().isUndefined()) {
                if (((com.pullenti.morph.MorphCase.ooBitand(_morph.getCase(), attr.getMorph().getCase()))).isUndefined() && !isBe) 
                    break;
            }
            if (openBr) {
                if (com.pullenti.ner.person.PersonAnalyzer.tryAttachPerson(t, ad, false, 0, true) != null) 
                    break;
            }
            if (attrs1 == null) {
                if (t.getPrevious().isComma() && t.getPrevious() == end.getNext()) {
                    com.pullenti.ner.Token ttt = attr.getEndToken().getNext();
                    if (ttt != null) {
                        if (ttt.getMorph()._getClass().isVerb()) {
                            if (com.pullenti.ner.core.MiscHelper.canBeStartOfSentence(begin)) {
                            }
                            else 
                                break;
                        }
                    }
                }
                attrs1 = new java.util.ArrayList<PersonAttrToken>();
            }
            attrs1.add(attr);
            if (attr.typ == PersonAttrTerminType.POSITION || attr.typ == PersonAttrTerminType.KING) {
                if (!isBe) 
                    hasPosition = true;
            }
            else if (attr.typ != PersonAttrTerminType.PREFIX) {
                if (attr.typ == PersonAttrTerminType.OTHER && attr.age != null) {
                }
                else {
                    attrs1 = null;
                    break;
                }
            }
            t = attr.getEndToken();
        }
        if (attrs1 != null && hasPosition && attrs != null) {
            com.pullenti.ner.Token te1 = attrs.get(attrs.size() - 1).getEndToken().getNext();
            com.pullenti.ner.Token te2 = attrs1.get(0).getBeginToken();
            if (te1.getWhitespacesAfterCount() > te2.getWhitespacesBeforeCount() && (te2.getWhitespacesBeforeCount() < 2)) {
            }
            else if (attrs1.get(0).age != null) {
            }
            else if (((te1.isHiphen() || te1.isChar(':'))) && !attrs1.get(0).isNewlineBefore() && ((te2.getPrevious().isComma() || te2.getPrevious() == end))) {
            }
            else 
                for (PersonAttrToken a : attrs) {
                    if (a.typ == PersonAttrTerminType.POSITION) {
                        com.pullenti.ner.Token te = attrs1.get(attrs1.size() - 1).getEndToken();
                        if (te.getNext() != null) {
                            if (!te.getNext().isChar('.')) {
                                attrs1 = null;
                                break;
                            }
                        }
                    }
                }
        }
        if (attrs1 != null && !hasPrefix) {
            PersonAttrToken attr = attrs1.get(attrs1.size() - 1);
            boolean ok = false;
            if (attr.getEndToken().getNext() != null && attr.getEndToken().getNext().chars.isCapitalUpper()) 
                ok = true;
            else {
                com.pullenti.ner.ReferentToken rt = com.pullenti.ner.person.PersonAnalyzer.tryAttachPerson(attr.getBeginToken(), ad, false, -1, false);
                if (rt != null && (rt.referent instanceof com.pullenti.ner.person.PersonReferent)) 
                    ok = true;
            }
            if (ok) {
                if (attr.getBeginToken().getWhitespacesBeforeCount() > attr.getEndToken().getWhitespacesAfterCount()) 
                    attrs1 = null;
                else if (attr.getBeginToken().getWhitespacesBeforeCount() == attr.getEndToken().getWhitespacesAfterCount()) {
                    com.pullenti.ner.ReferentToken rt1 = com.pullenti.ner.person.PersonAnalyzer.tryAttachPerson(attr.getBeginToken(), ad, false, -1, false);
                    if (rt1 != null) 
                        attrs1 = null;
                }
            }
        }
        if (attrs1 != null) {
            for (PersonAttrToken a : attrs1) {
                if (a.typ != PersonAttrTerminType.PREFIX) {
                    if (a.age != null) 
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_AGE, a.age, true, 0);
                    else if (a.getPropRef() == null) 
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, a.value, false, 0);
                    else 
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, a, false, 0);
                    end = a.getEndToken();
                    if (a.gender != com.pullenti.morph.MorphGender.UNDEFINED && !p.isFemale() && !p.isMale()) {
                        if (a.gender == com.pullenti.morph.MorphGender.MASCULINE && !p.isMale()) {
                            p.setMale(true);
                            p.correctData();
                        }
                        else if (a.gender == com.pullenti.morph.MorphGender.FEMINIE && !p.isFemale()) {
                            p.setFemale(true);
                            p.correctData();
                        }
                    }
                }
            }
            if (openBr) {
                if (end.getNext() != null && end.getNext().isChar(')')) 
                    end = end.getNext();
            }
        }
        int crlfCou = 0;
        for (com.pullenti.ner.Token t = end.getNext(); t != null; t = t.getNext()) {
            if (t.isTableControlChar()) 
                break;
            if (t.isNewlineBefore()) {
                com.pullenti.ner.mail.internal.MailLine ml = com.pullenti.ner.mail.internal.MailLine.parse(t, 0);
                if (ml != null && ml.typ == com.pullenti.ner.mail.internal.MailLine.Types.FROM) 
                    break;
                crlfCou++;
            }
            if (t.isCharOf(":,(") || t.isHiphen()) 
                continue;
            if (t.isChar('.') && t == end.getNext()) 
                continue;
            com.pullenti.ner.Referent r = t.getReferent();
            if (r != null) {
                if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "PHONE") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "URI") || com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ADDRESS")) {
                    String ty = r.getStringValue("SCHEME");
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "URI")) {
                        if ((com.pullenti.unisharp.Utils.stringsNe(ty, "mailto") && com.pullenti.unisharp.Utils.stringsNe(ty, "skype") && com.pullenti.unisharp.Utils.stringsNe(ty, "ICQ")) && com.pullenti.unisharp.Utils.stringsNe(ty, "http")) 
                            break;
                    }
                    p.addContact(r);
                    end = t;
                    crlfCou = 0;
                    continue;
                }
            }
            if (r instanceof com.pullenti.ner.person.PersonIdentityReferent) {
                p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_IDDOC, r, false, 0);
                end = t;
                crlfCou = 0;
                continue;
            }
            if (r != null && com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "ORGANIZATION")) {
                if (t.getNext() != null && t.getNext().getMorph()._getClass().isVerb()) 
                    break;
                if (begin.getPrevious() != null && begin.getPrevious().getMorph()._getClass().isVerb()) 
                    break;
                if (t.getWhitespacesAfterCount() == 1) 
                    break;
                boolean exist = false;
                for (com.pullenti.ner.Slot s : p.getSlots()) {
                    if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR) && (s.getValue() instanceof com.pullenti.ner.person.PersonPropertyReferent)) {
                        com.pullenti.ner.person.PersonPropertyReferent pr = (com.pullenti.ner.person.PersonPropertyReferent)com.pullenti.unisharp.Utils.cast(s.getValue(), com.pullenti.ner.person.PersonPropertyReferent.class);
                        if (pr.findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, true) != null) {
                            exist = true;
                            break;
                        }
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(s.getTypeName(), com.pullenti.ner.person.PersonReferent.ATTR_ATTR) && (s.getValue() instanceof PersonAttrToken)) {
                        PersonAttrToken pr = (PersonAttrToken)com.pullenti.unisharp.Utils.cast(s.getValue(), PersonAttrToken.class);
                        if (pr.referent.findSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, true) != null) {
                            exist = true;
                            break;
                        }
                    }
                }
                if (!exist) {
                    PersonAttrToken pat = new PersonAttrToken(t, t);
                    pat.setPropRef(com.pullenti.ner.person.PersonPropertyReferent._new2291("сотрудник"));
                    pat.getPropRef().addSlot(com.pullenti.ner.person.PersonPropertyReferent.ATTR_REF, r, false, 0);
                    p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_ATTR, pat, false, 0);
                }
                continue;
            }
            if (r != null) 
                break;
            if (!hasPrefix || crlfCou >= 2) 
                break;
            com.pullenti.ner.ReferentToken rt = t.kit.processReferent("PERSON", t);
            if (rt != null) 
                break;
        }
        if (ad != null) 
            ad.overflowLevel--;
        return com.pullenti.ner.ReferentToken._new2329(p, begin, end, _morph, p.m_PersonIdentityTyp.value());
    }

    /**
     * Выделить кличку
     * @param pr 
     * @param t начальный токен
     * @return если не null, то последний токен клички, а в pr запишет саму кличку
     */
    public static com.pullenti.ner.Token createNickname(com.pullenti.ner.person.PersonReferent pr, com.pullenti.ner.Token t) {
        boolean hasKeyw = false;
        boolean isBr = false;
        for (; t != null; t = t.getNext()) {
            if (t.isHiphen() || t.isComma() || t.isCharOf(".:;")) 
                continue;
            if (t.getMorph()._getClass().isPreposition()) 
                continue;
            if (t.isChar('(')) {
                isBr = true;
                continue;
            }
            if ((t.isValue("ПРОЗВИЩЕ", "ПРІЗВИСЬКО") || t.isValue("КЛИЧКА", null) || t.isValue("ПСЕВДОНИМ", "ПСЕВДОНІМ")) || t.isValue("ПСЕВДО", null) || t.isValue("ПОЗЫВНОЙ", "ПОЗИВНИЙ")) {
                hasKeyw = true;
                continue;
            }
            break;
        }
        if (!hasKeyw || t == null) 
            return null;
        if (com.pullenti.ner.core.BracketHelper.isBracket(t, true)) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                String ni = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                if (ni != null) {
                    pr.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, ni, false, 0);
                    t = br.getEndToken();
                    for (com.pullenti.ner.Token tt = t.getNext(); tt != null; tt = tt.getNext()) {
                        if (tt.isCommaAnd()) 
                            continue;
                        if (!com.pullenti.ner.core.BracketHelper.isBracket(tt, true)) 
                            break;
                        br = com.pullenti.ner.core.BracketHelper.tryParse(tt, com.pullenti.ner.core.BracketParseAttr.NO, 100);
                        if (br == null) 
                            break;
                        ni = com.pullenti.ner.core.MiscHelper.getTextValue(br.getBeginToken().getNext(), br.getEndToken().getPrevious(), com.pullenti.ner.core.GetTextAttr.NO);
                        if (ni != null) 
                            pr.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, ni, false, 0);
                        t = (tt = br.getEndToken());
                    }
                    if (isBr && t.getNext() != null && t.getNext().isChar(')')) 
                        t = t.getNext();
                    return t;
                }
            }
        }
        else {
            java.util.ArrayList<PersonItemToken> pli = PersonItemToken.tryAttachList(t, null, PersonItemToken.ParseAttr.NO, 10);
            if (pli != null && ((pli.size() == 1 || pli.size() == 2))) {
                String ni = com.pullenti.ner.core.MiscHelper.getTextValue(pli.get(0).getBeginToken(), pli.get(pli.size() - 1).getEndToken(), com.pullenti.ner.core.GetTextAttr.NO);
                if (ni != null) {
                    pr.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_NICKNAME, ni, false, 0);
                    t = pli.get(pli.size() - 1).getEndToken();
                    if (isBr && t.getNext() != null && t.getNext().isChar(')')) 
                        t = t.getNext();
                    return t;
                }
            }
        }
        return null;
    }

    public static boolean isPersonSayOrAttrAfter(com.pullenti.ner.Token t) {
        if (t == null) 
            return false;
        com.pullenti.ner.Token tt = correctTailAttributes(null, t);
        if (tt != null && tt != t) 
            return true;
        if (t.isComma() && t.getNext() != null) 
            t = t.getNext();
        if (t.chars.isLatinLetter()) {
            if (t.isValue("SAY", null) || t.isValue("ASK", null) || t.isValue("WHO", null)) 
                return true;
        }
        if (t.isChar('.') && (t.getNext() instanceof com.pullenti.ner.TextToken) && ((t.getNext().getMorph()._getClass().isPronoun() || t.getNext().getMorph()._getClass().isPersonalPronoun()))) {
            if (t.getNext().getMorph().getGender() == com.pullenti.morph.MorphGender.FEMINIE || t.getNext().getMorph().getGender() == com.pullenti.morph.MorphGender.MASCULINE) 
                return true;
        }
        if (t.isComma() && t.getNext() != null) 
            t = t.getNext();
        if (PersonAttrToken.tryAttach(t, null, PersonAttrToken.PersonAttrAttachAttrs.NO) != null) 
            return true;
        return false;
    }

    private static com.pullenti.ner.Token correctTailAttributes(com.pullenti.ner.person.PersonReferent p, com.pullenti.ner.Token t0) {
        com.pullenti.ner.Token res = t0;
        com.pullenti.ner.Token t = t0;
        if (t != null && t.isChar(',')) 
            t = t.getNext();
        boolean born = false;
        boolean die = false;
        if (t != null && ((t.isValue("РОДИТЬСЯ", "НАРОДИТИСЯ") || t.isValue("BORN", null)))) {
            t = t.getNext();
            born = true;
        }
        else if (t != null && ((t.isValue("УМЕРЕТЬ", "ПОМЕРТИ") || t.isValue("СКОНЧАТЬСЯ", null) || t.isValue("DIED", null)))) {
            t = t.getNext();
            die = true;
        }
        else if ((t != null && t.isValue("ДАТА", null) && t.getNext() != null) && t.getNext().isValue("РОЖДЕНИЕ", "НАРОДЖЕННЯ")) {
            t = t.getNext().getNext();
            born = true;
        }
        while (t != null) {
            if (t.getMorph()._getClass().isPreposition() || t.isHiphen() || t.isChar(':')) 
                t = t.getNext();
            else 
                break;
        }
        if (t != null && t.getReferent() != null) {
            com.pullenti.ner.Referent r = t.getReferent();
            if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE")) {
                com.pullenti.ner.Token t1 = t;
                if (t.getNext() != null && ((t.getNext().isValue("Р", null) || t.getNext().isValue("РОЖДЕНИЕ", "НАРОДЖЕННЯ")))) {
                    born = true;
                    t1 = t.getNext();
                    if (t1.getNext() != null && t1.getNext().isChar('.')) 
                        t1 = t1.getNext();
                }
                if (born) {
                    if (p != null) 
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_BORN, r, false, 0);
                    res = t1;
                    t = t1;
                }
                else if (die) {
                    if (p != null) 
                        p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_DIE, r, false, 0);
                    res = t1;
                    t = t1;
                }
            }
        }
        if (die && t != null) {
            com.pullenti.ner.NumberToken ag = com.pullenti.ner.core.NumberHelper.tryParseAge(t.getNext());
            if (ag != null) {
                if (p != null) 
                    p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_AGE, ag.getValue().toString(), false, 0);
                t = ag.getEndToken().getNext();
                res = ag.getEndToken();
            }
        }
        if (t == null) 
            return res;
        if (t.isChar('(')) {
            com.pullenti.ner.core.BracketSequenceToken br = com.pullenti.ner.core.BracketHelper.tryParse(t, com.pullenti.ner.core.BracketParseAttr.NO, 100);
            if (br != null) {
                com.pullenti.ner.Token t1 = t.getNext();
                born = false;
                if (t1.isValue("РОД", null)) {
                    born = true;
                    t1 = t1.getNext();
                    if (t1 != null && t1.isChar('.')) 
                        t1 = t1.getNext();
                }
                if (t1 instanceof com.pullenti.ner.ReferentToken) {
                    com.pullenti.ner.Referent r = t1.getReferent();
                    if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATERANGE") && t1.getNext() == br.getEndToken()) {
                        com.pullenti.ner.Referent bd = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(r.getSlotValue("FROM"), com.pullenti.ner.Referent.class);
                        com.pullenti.ner.Referent to = (com.pullenti.ner.Referent)com.pullenti.unisharp.Utils.cast(r.getSlotValue("TO"), com.pullenti.ner.Referent.class);
                        if (bd != null && to != null) {
                            if (p != null) {
                                p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_BORN, bd, false, 0);
                                p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_DIE, to, false, 0);
                            }
                            t = (res = br.getEndToken());
                        }
                    }
                    else if (com.pullenti.unisharp.Utils.stringsEq(r.getTypeName(), "DATE") && t1.getNext() == br.getEndToken()) {
                        if (p != null) 
                            p.addSlot(com.pullenti.ner.person.PersonReferent.ATTR_BORN, r, false, 0);
                        t = (res = br.getEndToken());
                    }
                }
            }
        }
        return res;
    }
    public PersonHelper() {
    }
}
