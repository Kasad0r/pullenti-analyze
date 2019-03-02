/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner.phone.internal;

public class PhoneHelper {

    public static void initialize() throws Exception {
        if (m_PhoneRoot != null) 
            return;
        m_PhoneRoot = new PhoneNode();
        m_AllCountryCodes = new java.util.HashMap<String, String>();
        String str = com.pullenti.ner.bank.internal.ResourceHelper.getString("CountryPhoneCodes.txt");
        if (str == null) 
            throw new Exception(("Can't file resource file " + "CountryPhoneCodes.txt" + " in Organization analyzer"));
        for (String line0 : com.pullenti.unisharp.Utils.split(str, String.valueOf('\n'), false)) {
            String line = line0.trim();
            if (com.pullenti.unisharp.Utils.isNullOrEmpty(line)) 
                continue;
            if (line.length() < 2) 
                continue;
            String country = line.substring(0, 0 + 2);
            String cod = line.substring(2).trim();
            if (cod.length() < 1) 
                continue;
            if (!m_AllCountryCodes.containsKey(country)) 
                m_AllCountryCodes.put(country, cod);
            PhoneNode tn = m_PhoneRoot;
            for (int i = 0; i < cod.length(); i++) {
                char dig = cod.charAt(i);
                PhoneNode nn;
                com.pullenti.unisharp.Outargwrapper<PhoneNode> wrapnn2462 = new com.pullenti.unisharp.Outargwrapper<PhoneNode>();
                boolean inoutres2463 = com.pullenti.unisharp.Utils.tryGetValue(tn.children, dig, wrapnn2462);
                nn = wrapnn2462.value;
                if (!inoutres2463) {
                    nn = new PhoneNode();
                    nn.pref = cod.substring(0, 0 + i + 1);
                    tn.children.put(dig, nn);
                }
                tn = nn;
            }
            if (tn.countries == null) 
                tn.countries = new java.util.ArrayList<String>();
            tn.countries.add(country);
        }
    }

    private static java.util.HashMap<String, String> m_AllCountryCodes;

    public static java.util.HashMap<String, String> getAllCountryCodes() {
        return m_AllCountryCodes;
    }

    public static class PhoneNode {
    
        public String pref;
    
        public java.util.HashMap<Character, PhoneNode> children = new java.util.HashMap<Character, PhoneNode>();
    
        public java.util.ArrayList<String> countries;
    
        @Override
        public String toString() {
            if (countries == null) 
                return pref;
            StringBuilder res = new StringBuilder(pref);
            for (String c : countries) {
                res.append(" ").append(c);
            }
            return res.toString();
        }
        public PhoneNode() {
        }
    }


    private static PhoneNode m_PhoneRoot;

    /**
     * Выделить телефонный префикс из "полного" номера
     * @param fullNumber 
     * @return 
     */
    public static String getCountryPrefix(String fullNumber) {
        if (fullNumber == null) 
            return null;
        PhoneNode nod = m_PhoneRoot;
        int maxInd = -1;
        for (int i = 0; i < fullNumber.length(); i++) {
            char dig = fullNumber.charAt(i);
            PhoneNode nn;
            com.pullenti.unisharp.Outargwrapper<PhoneNode> wrapnn2464 = new com.pullenti.unisharp.Outargwrapper<PhoneNode>();
            boolean inoutres2465 = com.pullenti.unisharp.Utils.tryGetValue(nod.children, dig, wrapnn2464);
            nn = wrapnn2464.value;
            if (!inoutres2465) 
                break;
            if (nn.countries != null && nn.countries.size() > 0) 
                maxInd = i;
            nod = nn;
        }
        if (maxInd < 0) 
            return null;
        else 
            return fullNumber.substring(0, 0 + maxInd + 1);
    }
    public PhoneHelper() {
    }
    public static PhoneHelper _globalInstance;
    
    static {
        _globalInstance = new PhoneHelper(); 
    }
}
