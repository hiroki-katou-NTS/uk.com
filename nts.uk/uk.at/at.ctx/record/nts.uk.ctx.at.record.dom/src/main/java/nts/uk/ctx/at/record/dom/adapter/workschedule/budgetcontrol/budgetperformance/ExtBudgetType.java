package nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance;

public enum ExtBudgetType {
    MONEY(0),
    NUM_PERSON(1),
    NUM_VAL(2),
    TIME(3),
    UNIT_PRICE(4);

    public int value;

    ExtBudgetType(int type) {
        this.value = type;
    }
}
