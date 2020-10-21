package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;

/**
 * PrimitiveValue: 年間勤務台帳の出力項目名称
 */
@StringMaxLength(3)
public class OutputItemNameOfAnnualWorkLedger extends StringPrimitiveValue<OutputItemNameOfAnnualWorkLedger> {
    public OutputItemNameOfAnnualWorkLedger(String rawValue) {
        super(rawValue);
    }
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
}
