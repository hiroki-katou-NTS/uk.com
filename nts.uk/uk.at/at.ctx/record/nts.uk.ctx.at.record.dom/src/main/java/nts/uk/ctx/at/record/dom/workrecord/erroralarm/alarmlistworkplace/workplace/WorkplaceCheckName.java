package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 職場チェック名称
 */

@StringMaxLength(20)
public class WorkplaceCheckName extends StringPrimitiveValue<WorkplaceCheckName> {

    public WorkplaceCheckName(String rawValue) {
        super(rawValue);
    }

}
