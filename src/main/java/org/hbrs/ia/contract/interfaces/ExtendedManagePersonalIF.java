package org.hbrs.ia.contract.interfaces;

import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;

import java.util.List;

public interface ExtendedManagePersonalIF extends ManagePersonalIF {

    //Read
    List<EvaluationRecord> getEvaluationRecords();

    List<SalesMan> getSalesmen();

    //Delete

    void deleteSalesman(int sid);

    //Update

    void updateSalesman(int sid, SalesMan salesMan);

    void updateEvaluationRecord(int sid, EvaluationRecord evaluationRecord);

}
