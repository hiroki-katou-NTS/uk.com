package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum CategoryIndicator {
    //支給 = 0
    //控除 = 1
    PAYMENT(0),
    DEDUCTION(1);
    public final int value;
}
