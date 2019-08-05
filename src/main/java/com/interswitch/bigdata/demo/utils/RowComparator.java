package com.interswitch.bigdata.demo.utils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public class RowComparator implements Serializable, Comparator<Map> {

    String identityFIeldName;


    public RowComparator(String theidentityFieldName){
        this.identityFIeldName = theidentityFieldName;
    }
    @Override
    public int compare(Map o1, Map o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        Long transid1 = (Long) o1.get(identityFIeldName);
        Long transid2 = (Long) o2.get(identityFIeldName);
        return Long.compare(transid1, transid2);
    }
}
