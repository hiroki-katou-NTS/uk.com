package nts.uk.ctx.alarm.dom.conditionvalue;

public interface ConditionValueLogic<C> {

    String getName();

    Double getValue(C getValueContext);
}
