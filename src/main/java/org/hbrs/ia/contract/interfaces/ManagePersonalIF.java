package org.hbrs.ia.contract.interfaces;

import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;

import java.util.List;

public interface ManagePersonalIF {

    //Create
    void createSalesMan(SalesMan record);

    void addPerformanceRecord(EvaluationRecord record, int sid);

    //Read
    SalesMan readSalesMan(int sid);

    List<SalesMan> querySalesMan(String attribute, String key);

    EvaluationRecord readEvaluationRecords(int sid);

}
