/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.instrument;

/**
 * Классы частей НПА
 */
public class InstrumentKind implements Comparable<InstrumentKind> {

    public static final InstrumentKind UNDEFINED; // 0

    public static final InstrumentKind DOCUMENT; // 1

    public static final InstrumentKind INTERNALDOCUMENT; // 2

    public static final InstrumentKind HEAD; // 3

    public static final InstrumentKind CONTENT; // 4

    public static final InstrumentKind TAIL; // 5

    public static final InstrumentKind APPENDIX; // 6

    public static final InstrumentKind DOCPART; // 7

    public static final InstrumentKind SECTION; // 8

    public static final InstrumentKind SUBSECTION; // 9

    public static final InstrumentKind CHAPTER; // 10

    public static final InstrumentKind PARAGRAPH; // 11

    public static final InstrumentKind SUBPARAGRAPH; // 12

    public static final InstrumentKind CLAUSE; // 13

    public static final InstrumentKind CLAUSEPART; // 14

    public static final InstrumentKind ITEM; // 15

    public static final InstrumentKind SUBITEM; // 16

    public static final InstrumentKind INDENTION; // 17

    public static final InstrumentKind LISTITEM; // 18

    public static final InstrumentKind LISTHEAD; // 19

    public static final InstrumentKind PREAMBLE; // 20

    public static final InstrumentKind INDEX; // 21

    public static final InstrumentKind INDEXITEM; // 22

    public static final InstrumentKind NOTICE; // 23

    public static final InstrumentKind NUMBER; // 24

    public static final InstrumentKind CASENUMBER; // 25

    public static final InstrumentKind CASEINFO; // 26

    public static final InstrumentKind NAME; // 27

    public static final InstrumentKind TYP; // 28

    public static final InstrumentKind SIGNER; // 29

    public static final InstrumentKind ORGANIZATION; // 30

    public static final InstrumentKind PLACE; // 31

    public static final InstrumentKind DATE; // 32

    public static final InstrumentKind CONTACT; // 33

    public static final InstrumentKind INITIATOR; // 34

    public static final InstrumentKind DIRECTIVE; // 35

    public static final InstrumentKind EDITIONS; // 36

    public static final InstrumentKind APPROVED; // 37

    public static final InstrumentKind DOCREFERENCE; // 38

    public static final InstrumentKind KEYWORD; // 39

    public static final InstrumentKind COMMENT; // 40

    public static final InstrumentKind CITATION; // 41

    public static final InstrumentKind QUESTION; // 42

    public static final InstrumentKind ANSWER; // 43

    public static final InstrumentKind TABLE; // 44

    public static final InstrumentKind TABLEROW; // 45

    public static final InstrumentKind TABLECELL; // 46

    public static final InstrumentKind IGNORED; // 47


    public int value() { return m_val; }
    private int m_val;
    private String m_str;
    private InstrumentKind(int val, String str) { m_val = val; m_str = str; }
    @Override
    public String toString() {
        if(m_str != null) return m_str;
        return ((Integer)m_val).toString();
    }
    @Override
    public int hashCode() {
        return (int)m_val;
    }
    @Override
    public int compareTo(InstrumentKind v) {
        if(m_val < v.m_val) return -1;
        if(m_val > v.m_val) return 1;
        return 0;
    }
    private static java.util.HashMap<Integer, InstrumentKind> mapIntToEnum; 
    private static java.util.HashMap<String, InstrumentKind> mapStringToEnum; 
    private static InstrumentKind[] m_Values; 
    private static java.util.Collection<Integer> m_Keys; 
    public static InstrumentKind of(int val) {
        if (mapIntToEnum.containsKey(val)) return mapIntToEnum.get(val);
        InstrumentKind item = new InstrumentKind(val, ((Integer)val).toString());
        mapIntToEnum.put(val, item);
        mapStringToEnum.put(item.m_str.toUpperCase(), item);
        return item; 
    }
    public static InstrumentKind of(String str) {
        str = str.toUpperCase(); 
        if (mapStringToEnum.containsKey(str)) return mapStringToEnum.get(str);
        return null; 
    }
    public static boolean isDefined(Object val) {
        if(val instanceof Integer) return m_Keys.contains((Integer)val); 
        return false; 
    }
    public static InstrumentKind[] getValues() {
        return m_Values; 
    }
    static {
        mapIntToEnum = new java.util.HashMap<Integer, InstrumentKind>();
        mapStringToEnum = new java.util.HashMap<String, InstrumentKind>();
        UNDEFINED = new InstrumentKind(0, "UNDEFINED");
        mapIntToEnum.put(UNDEFINED.value(), UNDEFINED);
        mapStringToEnum.put(UNDEFINED.m_str.toUpperCase(), UNDEFINED);
        DOCUMENT = new InstrumentKind(1, "DOCUMENT");
        mapIntToEnum.put(DOCUMENT.value(), DOCUMENT);
        mapStringToEnum.put(DOCUMENT.m_str.toUpperCase(), DOCUMENT);
        INTERNALDOCUMENT = new InstrumentKind(2, "INTERNALDOCUMENT");
        mapIntToEnum.put(INTERNALDOCUMENT.value(), INTERNALDOCUMENT);
        mapStringToEnum.put(INTERNALDOCUMENT.m_str.toUpperCase(), INTERNALDOCUMENT);
        HEAD = new InstrumentKind(3, "HEAD");
        mapIntToEnum.put(HEAD.value(), HEAD);
        mapStringToEnum.put(HEAD.m_str.toUpperCase(), HEAD);
        CONTENT = new InstrumentKind(4, "CONTENT");
        mapIntToEnum.put(CONTENT.value(), CONTENT);
        mapStringToEnum.put(CONTENT.m_str.toUpperCase(), CONTENT);
        TAIL = new InstrumentKind(5, "TAIL");
        mapIntToEnum.put(TAIL.value(), TAIL);
        mapStringToEnum.put(TAIL.m_str.toUpperCase(), TAIL);
        APPENDIX = new InstrumentKind(6, "APPENDIX");
        mapIntToEnum.put(APPENDIX.value(), APPENDIX);
        mapStringToEnum.put(APPENDIX.m_str.toUpperCase(), APPENDIX);
        DOCPART = new InstrumentKind(7, "DOCPART");
        mapIntToEnum.put(DOCPART.value(), DOCPART);
        mapStringToEnum.put(DOCPART.m_str.toUpperCase(), DOCPART);
        SECTION = new InstrumentKind(8, "SECTION");
        mapIntToEnum.put(SECTION.value(), SECTION);
        mapStringToEnum.put(SECTION.m_str.toUpperCase(), SECTION);
        SUBSECTION = new InstrumentKind(9, "SUBSECTION");
        mapIntToEnum.put(SUBSECTION.value(), SUBSECTION);
        mapStringToEnum.put(SUBSECTION.m_str.toUpperCase(), SUBSECTION);
        CHAPTER = new InstrumentKind(10, "CHAPTER");
        mapIntToEnum.put(CHAPTER.value(), CHAPTER);
        mapStringToEnum.put(CHAPTER.m_str.toUpperCase(), CHAPTER);
        PARAGRAPH = new InstrumentKind(11, "PARAGRAPH");
        mapIntToEnum.put(PARAGRAPH.value(), PARAGRAPH);
        mapStringToEnum.put(PARAGRAPH.m_str.toUpperCase(), PARAGRAPH);
        SUBPARAGRAPH = new InstrumentKind(12, "SUBPARAGRAPH");
        mapIntToEnum.put(SUBPARAGRAPH.value(), SUBPARAGRAPH);
        mapStringToEnum.put(SUBPARAGRAPH.m_str.toUpperCase(), SUBPARAGRAPH);
        CLAUSE = new InstrumentKind(13, "CLAUSE");
        mapIntToEnum.put(CLAUSE.value(), CLAUSE);
        mapStringToEnum.put(CLAUSE.m_str.toUpperCase(), CLAUSE);
        CLAUSEPART = new InstrumentKind(14, "CLAUSEPART");
        mapIntToEnum.put(CLAUSEPART.value(), CLAUSEPART);
        mapStringToEnum.put(CLAUSEPART.m_str.toUpperCase(), CLAUSEPART);
        ITEM = new InstrumentKind(15, "ITEM");
        mapIntToEnum.put(ITEM.value(), ITEM);
        mapStringToEnum.put(ITEM.m_str.toUpperCase(), ITEM);
        SUBITEM = new InstrumentKind(16, "SUBITEM");
        mapIntToEnum.put(SUBITEM.value(), SUBITEM);
        mapStringToEnum.put(SUBITEM.m_str.toUpperCase(), SUBITEM);
        INDENTION = new InstrumentKind(17, "INDENTION");
        mapIntToEnum.put(INDENTION.value(), INDENTION);
        mapStringToEnum.put(INDENTION.m_str.toUpperCase(), INDENTION);
        LISTITEM = new InstrumentKind(18, "LISTITEM");
        mapIntToEnum.put(LISTITEM.value(), LISTITEM);
        mapStringToEnum.put(LISTITEM.m_str.toUpperCase(), LISTITEM);
        LISTHEAD = new InstrumentKind(19, "LISTHEAD");
        mapIntToEnum.put(LISTHEAD.value(), LISTHEAD);
        mapStringToEnum.put(LISTHEAD.m_str.toUpperCase(), LISTHEAD);
        PREAMBLE = new InstrumentKind(20, "PREAMBLE");
        mapIntToEnum.put(PREAMBLE.value(), PREAMBLE);
        mapStringToEnum.put(PREAMBLE.m_str.toUpperCase(), PREAMBLE);
        INDEX = new InstrumentKind(21, "INDEX");
        mapIntToEnum.put(INDEX.value(), INDEX);
        mapStringToEnum.put(INDEX.m_str.toUpperCase(), INDEX);
        INDEXITEM = new InstrumentKind(22, "INDEXITEM");
        mapIntToEnum.put(INDEXITEM.value(), INDEXITEM);
        mapStringToEnum.put(INDEXITEM.m_str.toUpperCase(), INDEXITEM);
        NOTICE = new InstrumentKind(23, "NOTICE");
        mapIntToEnum.put(NOTICE.value(), NOTICE);
        mapStringToEnum.put(NOTICE.m_str.toUpperCase(), NOTICE);
        NUMBER = new InstrumentKind(24, "NUMBER");
        mapIntToEnum.put(NUMBER.value(), NUMBER);
        mapStringToEnum.put(NUMBER.m_str.toUpperCase(), NUMBER);
        CASENUMBER = new InstrumentKind(25, "CASENUMBER");
        mapIntToEnum.put(CASENUMBER.value(), CASENUMBER);
        mapStringToEnum.put(CASENUMBER.m_str.toUpperCase(), CASENUMBER);
        CASEINFO = new InstrumentKind(26, "CASEINFO");
        mapIntToEnum.put(CASEINFO.value(), CASEINFO);
        mapStringToEnum.put(CASEINFO.m_str.toUpperCase(), CASEINFO);
        NAME = new InstrumentKind(27, "NAME");
        mapIntToEnum.put(NAME.value(), NAME);
        mapStringToEnum.put(NAME.m_str.toUpperCase(), NAME);
        TYP = new InstrumentKind(28, "TYP");
        mapIntToEnum.put(TYP.value(), TYP);
        mapStringToEnum.put(TYP.m_str.toUpperCase(), TYP);
        SIGNER = new InstrumentKind(29, "SIGNER");
        mapIntToEnum.put(SIGNER.value(), SIGNER);
        mapStringToEnum.put(SIGNER.m_str.toUpperCase(), SIGNER);
        ORGANIZATION = new InstrumentKind(30, "ORGANIZATION");
        mapIntToEnum.put(ORGANIZATION.value(), ORGANIZATION);
        mapStringToEnum.put(ORGANIZATION.m_str.toUpperCase(), ORGANIZATION);
        PLACE = new InstrumentKind(31, "PLACE");
        mapIntToEnum.put(PLACE.value(), PLACE);
        mapStringToEnum.put(PLACE.m_str.toUpperCase(), PLACE);
        DATE = new InstrumentKind(32, "DATE");
        mapIntToEnum.put(DATE.value(), DATE);
        mapStringToEnum.put(DATE.m_str.toUpperCase(), DATE);
        CONTACT = new InstrumentKind(33, "CONTACT");
        mapIntToEnum.put(CONTACT.value(), CONTACT);
        mapStringToEnum.put(CONTACT.m_str.toUpperCase(), CONTACT);
        INITIATOR = new InstrumentKind(34, "INITIATOR");
        mapIntToEnum.put(INITIATOR.value(), INITIATOR);
        mapStringToEnum.put(INITIATOR.m_str.toUpperCase(), INITIATOR);
        DIRECTIVE = new InstrumentKind(35, "DIRECTIVE");
        mapIntToEnum.put(DIRECTIVE.value(), DIRECTIVE);
        mapStringToEnum.put(DIRECTIVE.m_str.toUpperCase(), DIRECTIVE);
        EDITIONS = new InstrumentKind(36, "EDITIONS");
        mapIntToEnum.put(EDITIONS.value(), EDITIONS);
        mapStringToEnum.put(EDITIONS.m_str.toUpperCase(), EDITIONS);
        APPROVED = new InstrumentKind(37, "APPROVED");
        mapIntToEnum.put(APPROVED.value(), APPROVED);
        mapStringToEnum.put(APPROVED.m_str.toUpperCase(), APPROVED);
        DOCREFERENCE = new InstrumentKind(38, "DOCREFERENCE");
        mapIntToEnum.put(DOCREFERENCE.value(), DOCREFERENCE);
        mapStringToEnum.put(DOCREFERENCE.m_str.toUpperCase(), DOCREFERENCE);
        KEYWORD = new InstrumentKind(39, "KEYWORD");
        mapIntToEnum.put(KEYWORD.value(), KEYWORD);
        mapStringToEnum.put(KEYWORD.m_str.toUpperCase(), KEYWORD);
        COMMENT = new InstrumentKind(40, "COMMENT");
        mapIntToEnum.put(COMMENT.value(), COMMENT);
        mapStringToEnum.put(COMMENT.m_str.toUpperCase(), COMMENT);
        CITATION = new InstrumentKind(41, "CITATION");
        mapIntToEnum.put(CITATION.value(), CITATION);
        mapStringToEnum.put(CITATION.m_str.toUpperCase(), CITATION);
        QUESTION = new InstrumentKind(42, "QUESTION");
        mapIntToEnum.put(QUESTION.value(), QUESTION);
        mapStringToEnum.put(QUESTION.m_str.toUpperCase(), QUESTION);
        ANSWER = new InstrumentKind(43, "ANSWER");
        mapIntToEnum.put(ANSWER.value(), ANSWER);
        mapStringToEnum.put(ANSWER.m_str.toUpperCase(), ANSWER);
        TABLE = new InstrumentKind(44, "TABLE");
        mapIntToEnum.put(TABLE.value(), TABLE);
        mapStringToEnum.put(TABLE.m_str.toUpperCase(), TABLE);
        TABLEROW = new InstrumentKind(45, "TABLEROW");
        mapIntToEnum.put(TABLEROW.value(), TABLEROW);
        mapStringToEnum.put(TABLEROW.m_str.toUpperCase(), TABLEROW);
        TABLECELL = new InstrumentKind(46, "TABLECELL");
        mapIntToEnum.put(TABLECELL.value(), TABLECELL);
        mapStringToEnum.put(TABLECELL.m_str.toUpperCase(), TABLECELL);
        IGNORED = new InstrumentKind(47, "IGNORED");
        mapIntToEnum.put(IGNORED.value(), IGNORED);
        mapStringToEnum.put(IGNORED.m_str.toUpperCase(), IGNORED);
        java.util.Collection<InstrumentKind> col = mapIntToEnum.values();
        m_Values = new InstrumentKind[col.size()];
        col.toArray(m_Values);
        m_Keys = new java.util.ArrayList<Integer>(mapIntToEnum.keySet());
    }
}
