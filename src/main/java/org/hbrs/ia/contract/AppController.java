package org.hbrs.ia.contract;

import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;

import java.util.List;
import java.util.Scanner;

public class AppController {


    private final Scanner scanner = new Scanner(System.in);

    private final DatabaseModel databaseModel;
    private final AppView appView;

    public AppController(DatabaseModel databaseModel, AppView appView) {
        this.appView = appView;
        this.databaseModel = databaseModel;
        System.out.println("Connecting to mongodb...");

        databaseModel.setupConnection();

        System.out.println("Connected.");

        startConsole();
    }

    void startConsole() {
        System.out.println("Console awaiting input...");
        System.out.println("Enter 'help' for all commands or 'quit' to exit.");

        String input;
        while((input = scanner.nextLine()) != null) {

            String[] parameters = input.split(" ");
            String command = parameters[0];
            String[] values;

            switch (command) {
                case "createSalesman":
                    parameters = input.split(" ", 2);
                    if (isParameterCountLessThan(2, parameters)) break;

                    values = parameters[1].split(",");

                    databaseModel.createSalesMan(createSalesmanFromValues(values));

                    System.out.println("Employee created!");
                    break;

                case "addRecord":
                    parameters = input.split(" ", 3);
                    if (isParameterCountLessThan(3, parameters)) break;
                    values = parameters[2].split(",");

                    int[] integerValues = convertToIntArray(values);

                    databaseModel.addPerformanceRecord(new EvaluationRecord(
                            integerValues[0], integerValues[1], integerValues[2], integerValues[3], integerValues[4], integerValues[5]
                    ), Integer.parseInt(parameters[1]));

                    System.out.println("Added Record to Employee!");
                    break;

                case "readRecord":
                    if(isParameterCountLessThan(2, parameters)) break;

                    EvaluationRecord evaluationRecord = databaseModel.readEvaluationRecords(Integer.parseInt(parameters[1]));
                    appView.printEvaluationRecord(evaluationRecord);
                    break;

                case "readSalesman":
                    if(isParameterCountLessThan(2, parameters)) break;

                    SalesMan salesMan = databaseModel.readSalesMan(Integer.parseInt(parameters[1]));
                    appView.printSalesMan(salesMan);
                    break;

                case "querySalesman":
                    parameters = input.split(" ", 3);
                    if(isParameterCountLessThan(3, parameters)) break;

                    List<SalesMan> salesMen =
                            databaseModel.querySalesMan(parameters[1], parameters[2]);

                    appView.printSalesMen(salesMen);
                    break;

                case "delete":
                    databaseModel.deleteSalesman(Integer.parseInt(parameters[1]));
                    System.out.println("Employee deleted and possible existing record removed.");
                    break;

                case "updateSalesman":
                    parameters = input.split(" ", 3);
                    if (isParameterCountLessThan(3, parameters)) break;
                    values = parameters[2].split(",");

                    databaseModel.updateSalesman(Integer.parseInt(parameters[1]), createSalesmanFromValues(values));

                    System.out.println("Salesman updated.");
                    break;

                case "updateRecord":
                    parameters = input.split(" ", 3);
                    if (isParameterCountLessThan(3, parameters)) break;
                    values = parameters[2].split(",");

                    databaseModel.updateEvaluationRecord(Integer.parseInt(parameters[1]), createEvaluationRecordFromValues(values));

                    System.out.println("Evaluation record updated.");
                    break;

                case "list":
                    salesMen = databaseModel.getSalesmen();
                    List<EvaluationRecord> records = databaseModel.getEvaluationRecords();
                    appView.printSalesMenWithRecords(salesMen, records);
                    break;

                case "quit":
                    System.out.println("Closing connection...");

                    databaseModel.close();
                    scanner.close();

                    System.exit(0);
                    break;

                case "help":
                    appView.printHelp();
                    break;

                default:
                    System.out.println("Command not found. Enter 'help' for all commands.");
                    break;
            }
        }
    }

    private SalesMan createSalesmanFromValues(String[] values) {
        return new SalesMan(values[0], values[1], values[2]);
    }

    private EvaluationRecord createEvaluationRecordFromValues(String[] values) {
        int[] integerValues = convertToIntArray(values);

        return new EvaluationRecord(
                integerValues[0], integerValues[1], integerValues[2], integerValues[3], integerValues[4], integerValues[5]);
    }

    private int[] convertToIntArray(String[] input) {
        int[] integerValues = new int[input.length];

        for(int i = 0; i < input.length; i++) {
            integerValues[i] = Integer.parseInt(input[i]);
        }

        return integerValues;
    }

    private boolean isParameterCountLessThan(int count, Object[] array) {
        if(array.length < count) {
            System.out.println("Enter the parameters.");
            return true;
        }

        return false;
    }

}
