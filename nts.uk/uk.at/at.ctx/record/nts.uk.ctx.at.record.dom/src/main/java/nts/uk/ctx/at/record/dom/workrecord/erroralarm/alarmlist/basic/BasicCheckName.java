package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 基本チェック名称
 */

@StringMaxLength(20)
public class BasicCheckName extends StringPrimitiveValue<BasicCheckName> {

    public BasicCheckName(String rawValue) {
        super(rawValue);
    }

}
