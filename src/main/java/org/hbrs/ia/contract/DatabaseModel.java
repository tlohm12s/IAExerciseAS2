package org.hbrs.ia.contract;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.hbrs.ia.contract.entities.EvaluationRecord;
import org.hbrs.ia.contract.entities.SalesMan;
import org.hbrs.ia.contract.interfaces.ExtendedManagePersonalIF;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModel implements ExtendedManagePersonalIF {

    public static final String HOST_ADDRESS = "localhost";
    public static final String DATABASE = "mydb";

    public static final String EMPLOYEE_COLLECTION = "employees";
    public static final String RECORD_COLLECTION = "records";
    public static final String COUNTER_COLLECTION = "counters";

    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_DEPARTMENT = "department";
    public static final String EMPLOYEE_JOBTITLE = "job_title";
    public static final String EMPLOYEE_ID = "_id";


    public static final String RECORD_ID = "_id";
    public static final String RECORD_EMPLOYEE_ID = "employee_id";
    public static final String RECORD_OPENNESS = "openness";
    public static final String RECORD_LEADERSHIP_COMPETENCE = "leadership";
    public static final String RECORD_SOCIAL = "social";
    public static final String RECORD_ATTITUDE = "attitude";
    public static final String RECORD_COMMUNICATIONS = "communications";
    public static final String RECORD_INTEGRITY = "integrity";

    public static final String COUNTER_ID = "_id";
    public static final String COUNTER_SEQUENCE = "seq";

    private MongoClient mongoClient;

    private MongoCollection<Document> evaluationRecordsCollection;
    private MongoCollection<Document> employeeCollection;
    private MongoCollection<Document> counterCollection;

    public void setupConnection() {
        mongoClient = new MongoClient(HOST_ADDRESS);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);

        evaluationRecordsCollection = mongoDatabase.getCollection(RECORD_COLLECTION);
        employeeCollection = mongoDatabase.getCollection(EMPLOYEE_COLLECTION);
        counterCollection = mongoDatabase.getCollection(COUNTER_COLLECTION);
    }

    private int getNextSequence(String name) {
        Document returnDocument = counterCollection
                .findOneAndUpdate(
                        Filters.eq(COUNTER_ID, name),
                        Updates.inc(COUNTER_SEQUENCE, 1),
                        new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));

        return returnDocument.getInteger(COUNTER_SEQUENCE);
    }

    private Document createDocumentFromSalesman(SalesMan salesMan, boolean update) {

        Document newDocument = new Document();

        if(!update) newDocument.append(EMPLOYEE_ID, getNextSequence(EMPLOYEE_COLLECTION));

        newDocument.append(EMPLOYEE_NAME, salesMan.getName())
                .append(EMPLOYEE_DEPARTMENT, salesMan.getDepartment())
                .append(EMPLOYEE_JOBTITLE, salesMan.getJobTitle());

        return newDocument;
    }

    public long getSalesmenSize() {
        return employeeCollection.count();
    }

    public long getEvaluationRecordSize() {
        return evaluationRecordsCollection.count();
    }

    private Document createDocumentFromEvaluationRecord(EvaluationRecord evaluationRecord, int sid, boolean update) {

        Document newDocument = new Document();

        if(!update) newDocument.append(RECORD_ID, getNextSequence(RECORD_COLLECTION));

        newDocument.append(RECORD_ATTITUDE, evaluationRecord.getAttitude())
                .append(RECORD_COMMUNICATIONS, evaluationRecord.getCommunications())
                .append(RECORD_INTEGRITY, evaluationRecord.getIntegrity())
                .append(RECORD_LEADERSHIP_COMPETENCE, evaluationRecord.getLeadershipCompetence())
                .append(RECORD_OPENNESS, evaluationRecord.getOpenness())
                .append(RECORD_SOCIAL, evaluationRecord.getSocial())
                .append(RECORD_EMPLOYEE_ID, sid);

        return newDocument;
    }

    private SalesMan createSalesmanFromDocument(Document document) {
        return new SalesMan(
                document.getString(EMPLOYEE_NAME),
                document.getString(EMPLOYEE_DEPARTMENT),
                document.getString(EMPLOYEE_JOBTITLE),
                document.getInteger(EMPLOYEE_ID));
    }

    private EvaluationRecord createEvaluationRecordFromDocument(Document document) {

        return new EvaluationRecord(
            document.getInteger(RECORD_LEADERSHIP_COMPETENCE),
            document.getInteger(RECORD_OPENNESS),
            document.getInteger(RECORD_SOCIAL),
            document.getInteger(RECORD_ATTITUDE),
            document.getInteger(RECORD_COMMUNICATIONS),
            document.getInteger(RECORD_INTEGRITY),
            document.getInteger(RECORD_EMPLOYEE_ID));
    }

    @Override
    public void createSalesMan(SalesMan salesMan) {
        Document doc = createDocumentFromSalesman(salesMan, false);

        employeeCollection.insertOne(doc);
    }

    @Override
    public void addPerformanceRecord(EvaluationRecord record, int sid) {
        Document doc = createDocumentFromEvaluationRecord(record, sid, false);

        evaluationRecordsCollection.insertOne(doc);
    }

    @Override
    public SalesMan readSalesMan(int sid) {

        Document employeeDocument = employeeCollection.find(Filters.eq(EMPLOYEE_ID, sid)).first();
        return createSalesmanFromDocument(employeeDocument);
    }

    @Override
    public List<SalesMan> querySalesMan(String attribute, String key) {
        List<SalesMan> salesMen = new ArrayList<>();

        for (Object object : employeeCollection.find(Filters.eq(attribute, key))) {
            Document document = (Document) object;

            salesMen.add(createSalesmanFromDocument(document));
        }

        return salesMen;
    }

    @Override
    public EvaluationRecord readEvaluationRecords(int sid) {
        Document recordDocument = evaluationRecordsCollection.find(Filters.eq(RECORD_EMPLOYEE_ID, sid)).first();
        return createEvaluationRecordFromDocument(recordDocument);
    }

    @Override
    public List<EvaluationRecord> getEvaluationRecords() {

        List<EvaluationRecord> evaluationRecords = new ArrayList<>();

        for (Document document : evaluationRecordsCollection.find()) {
            EvaluationRecord evaluationRecord
                    = createEvaluationRecordFromDocument(document);
            evaluationRecords.add(evaluationRecord);
        }

        return evaluationRecords;
    }

    @Override
    public List<SalesMan> getSalesmen() {

        List<SalesMan> salesMen = new ArrayList<>();

        for (Document document : employeeCollection.find()) {
            salesMen.add(createSalesmanFromDocument(document));
        }

        return salesMen;
    }

    @Override
    public void deleteSalesman(int sid) {
        employeeCollection.deleteOne(Filters.eq(EMPLOYEE_ID, sid));
        evaluationRecordsCollection.deleteOne(Filters.eq(RECORD_EMPLOYEE_ID, sid));
    }

    @Override
    public void updateSalesman(int sid, SalesMan salesMan) {
        employeeCollection.replaceOne(Filters.eq(EMPLOYEE_ID, sid), createDocumentFromSalesman(salesMan, true));
    }

    @Override
    public void updateEvaluationRecord(int sid, EvaluationRecord evaluationRecord) {
        evaluationRecordsCollection.replaceOne(Filters.eq(RECORD_EMPLOYEE_ID, sid), createDocumentFromEvaluationRecord(evaluationRecord, sid, true));
    }

    public void close() {
        mongoClient.close();
    }
}
