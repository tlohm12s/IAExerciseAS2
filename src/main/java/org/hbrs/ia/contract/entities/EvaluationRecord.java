package org.hbrs.ia.contract.entities;

public class EvaluationRecord {

    private int leadershipCompetence;
    private int openness;
    private int social;
    private int attitude;
    private int communications;
    private int integrity;

    private int employeeID;

    public EvaluationRecord(int leadershipCompetence, int openness, int social, int attitude, int communications, int integrity) {
        this.leadershipCompetence = leadershipCompetence;
        this.openness = openness;
        this.social = social;
        this.attitude = attitude;
        this.communications = communications;
        this.integrity = integrity;
    }

    public EvaluationRecord(int leadershipCompetence, int openness, int social, int attitude, int communications, int integrity, int employeeID) {
        this.leadershipCompetence = leadershipCompetence;
        this.openness = openness;
        this.social = social;
        this.attitude = attitude;
        this.communications = communications;
        this.integrity = integrity;

        this.employeeID = employeeID;
    }

    public int getLeadershipCompetence() {
        return leadershipCompetence;
    }

    public int getOpenness() {
        return openness;
    }

    public int getSocial() {
        return social;
    }

    public int getAttitude() {
        return attitude;
    }

    public int getCommunications() {
        return communications;
    }

    public int getIntegrity() {
        return integrity;
    }

    public int getEmployeeID() { return employeeID; }

}
