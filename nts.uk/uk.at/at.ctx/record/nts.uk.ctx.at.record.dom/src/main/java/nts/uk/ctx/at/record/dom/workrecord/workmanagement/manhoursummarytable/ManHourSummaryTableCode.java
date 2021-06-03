package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 工数集計表コード
 */
@StringMaxLength(3)
public class ManHourSummaryTableCode extends StringPrimitiveValue<ManHourSummaryTableCode> {
    private static final long serialVersionUID = 1L;

    public ManHourSummaryTableCode(String rawValue) {
        super(rawValue);
    }
}
