package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum SalBonusCate {
    //給与 = 0
    //賞与 = 1
    SALARY(0),
    BONUSES(1);
    public final int value;
}
