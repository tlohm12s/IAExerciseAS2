import org.hbrs.ia.contract.DatabaseModel;
import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundTrippingTest {

    private static DatabaseModel databaseModel;

    @BeforeAll
    public static void setUp() {
        databaseModel = new DatabaseModel();
        databaseModel.setupConnection();
    }

    @Test
    @DisplayName("Test the Salesman-collection from the database according to the CRUD pattern")
    public void testSalesmanCRUD() {
        //Create
        long beforeSize = databaseModel.getSalesmenSize();

        databaseModel.createSalesMan(new SalesMan("Test1", "Test2", "Test3"));

        assertEquals(beforeSize + 1, databaseModel.getSalesmenSize());

        //Read
        List<SalesMan> salesMen = databaseModel.querySalesMan("name", "Test1");
        int id = salesMen.get(0).getEmployeeID();

        assertEquals("Test1", salesMen.get(0).getName());

        //Update
        databaseModel.updateSalesman(id, new SalesMan("Test4", "Test5", "Test6"));

        //Read
        salesMen = databaseModel.querySalesMan("name", "Test4");

        assertEquals("Test4", salesMen.get(0).getName());

        //Delete
        databaseModel.deleteSalesman(id);

        long afterSize = databaseModel.getSalesmenSize();
        assertEquals(afterSize, beforeSize);
    }

    @Test
    @DisplayName("Test the EvaluationRecord-collection from the database according to the CRUD pattern")
    public void testEvaluationRecordCRUD() {

        //Create Employee for testing
        databaseModel.createSalesMan(new SalesMan("Test1", "Test2", "Test3"));
        List<SalesMan> salesMen = databaseModel.querySalesMan("name", "Test1");
        int sid = salesMen.get(0).getEmployeeID();

        //Create
        long beforeSize = databaseModel.getEvaluationRecordSize();

        databaseModel.addPerformanceRecord(new EvaluationRecord(1,2,3,4,5,6), sid);

        assertEquals(beforeSize + 1, databaseModel.getEvaluationRecordSize());

        //Read
        EvaluationRecord evaluationRecords = databaseModel.readEvaluationRecords(sid);

        assertEquals(4, evaluationRecords.getAttitude());

        //Update
        databaseModel.updateEvaluationRecord(sid, new EvaluationRecord(7,8,9,10,11,12));

        //Read
        evaluationRecords = databaseModel.readEvaluationRecords(sid);

        assertEquals(11, evaluationRecords.getCommunications());

        //Delete
        databaseModel.deleteSalesman(sid);

        long afterSize = databaseModel.getEvaluationRecordSize();
        assertEquals(afterSize, beforeSize);


        //Delete Employee for testing purpose
        databaseModel.deleteSalesman(sid);
    }
}
