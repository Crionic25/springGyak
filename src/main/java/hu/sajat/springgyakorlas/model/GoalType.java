package hu.sajat.springgyakorlas.model;

public enum GoalType {
    ENVIRONMENT(0.03,0),
    BUSINESS(0.03,0.05),
    COMMUNITY(0.03,0),
    COMPETITION(0.03,0.05),
    CREATIVE(0.03,0.02),
    EDUCATION(0.03,0.01),
    EMERGENCY(0.02,0),
    EVENT(0.03,0.01),
    FAITH(0.03,0.05),
    FAMILY(0.03,0),
    MEDICAL(0.02,0),
    NONPROFIT(0.02,0.01),
    SPORTS(0.03,0.05),
    TRAVEL(0.03,0.03),
    VOLUNTEER(0.03,0),
    WISHES(0.03,0.01);

    public final double transactionFee;
    public final double fundingFee;

    GoalType(double transactionFee, double fundingFee) {
        this.transactionFee = transactionFee;
        this.fundingFee = fundingFee;
    }
}
