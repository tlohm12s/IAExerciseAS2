package org.hbrs.ia.contract;

import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;

import java.util.List;

public class AppView {

    public void printSalesMan(SalesMan salesMan) {
        System.out.println(
                "ID: " + salesMan.getEmployeeID() + "\n" +
                        "Name: " + salesMan.getName() + "\n" +
                        "Department: " + salesMan.getDepartment() + "\n" +
                        "Job Title: " + salesMan.getJobTitle() + "\n");
    }

    public void printSalesMen(List<SalesMan> salesMen) {
        for (SalesMan salesMan : salesMen) {
            printSalesMan(salesMan);
        }
    }

    public void printHelp() {
        System.out.println(
                        "list - List all collections and entries \n" +
                        "quit - Closes the program\n\n" +

                        "createSalesman Name,Department,Job_Title - Creates an employee \n" +
                        "[Example: createSalesman Max Mustermann,IT,Programmer]\n" +
                        "addRecord id leaderShipCompetence,openness,social,attitude,communications,integrity - Adds a record to an employee \n" +
                        "[Example: addRecord 1 1,2,3,4,5,6]\n\n" +

                        "delete id - Deletes a employee and the evaluation record \n" +
                        "[Example: delete 1]\n\n" +

                        "readSalesman id - Returns a salesman associated with its ID \n" +
                        "readRecord id - Returns a record associated with its ID\n\n" +

                        "updateSalesman id Name,Department,Job_Title - Queries for Employee and his evaluation record \n" +
                        "[Example: updateSalesman 1 Max Mustermann,IT,Programmer]\n" +
                        "updateRecord id leaderShipCompetence,openness,social,attitude,communications,integrity - Queries for Employee and his evaluation record \n");
    }

    public void printSalesMenWithRecords(List<SalesMan> salesMen, List<EvaluationRecord> records) {

        for (SalesMan salesMan : salesMen) {
            if (records != null) {
                printSalesMan(salesMan);

                EvaluationRecord evaluationRecord = findRecordFromSalesMan(salesMan, records);
                if (evaluationRecord != null) {
                    printEvaluationRecord(evaluationRecord);
                }
            } else {
                printSalesMan(salesMan);
            }
        }
    }

    private EvaluationRecord findRecordFromSalesMan(SalesMan salesMan, List<EvaluationRecord> records) {
        for (EvaluationRecord evaluationRecord : records) {
            if (salesMan.getEmployeeID() == evaluationRecord.getEmployeeID()) {
                return evaluationRecord;
            }
        }

        return null;
    }

    public void printEvaluationRecord(EvaluationRecord evaluationRecord) {
        System.out.println(
                "Record of ID: " +evaluationRecord.getEmployeeID() + "\n" +
                        "Leadership Competence: " + evaluationRecord.getLeadershipCompetence() + "\n" +
                        "Openness: " + evaluationRecord.getOpenness() + "\n" +
                        "Attitude: " + evaluationRecord.getAttitude() + "\n" +
                        "Integrity: " + evaluationRecord.getIntegrity() + "\n" +
                        "Sociability: " + evaluationRecord.getSocial() + "\n" +
                        "Communicability: " + evaluationRecord.getCommunications() + "\n");
    }

}
