package com.interswitch.bigdata.demo.Serialization;


import java.io.Serializable;
import java.util.Map;

public interface RowSerializer extends Serializable {

     Object serialize(Map row);
}
