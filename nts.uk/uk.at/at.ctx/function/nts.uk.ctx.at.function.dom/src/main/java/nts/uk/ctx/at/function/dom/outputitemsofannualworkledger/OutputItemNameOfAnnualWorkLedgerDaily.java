package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


/**
 * PrimitiveValue: 年間勤務台帳の出力項目名称
 */
@StringMaxLength(6)
public class OutputItemNameOfAnnualWorkLedgerDaily  extends StringPrimitiveValue<OutputItemNameOfAnnualWorkLedgerDaily> {
    public OutputItemNameOfAnnualWorkLedgerDaily(String rawValue) {
        super(rawValue);
    }
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
}