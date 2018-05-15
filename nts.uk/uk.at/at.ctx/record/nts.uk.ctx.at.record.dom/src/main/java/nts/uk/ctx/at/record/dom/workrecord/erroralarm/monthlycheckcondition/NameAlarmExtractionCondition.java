package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * アラームリスト抽出条件の名称
 *
 */

@StringMaxLength(20)
public class NameAlarmExtractionCondition extends StringPrimitiveValue<NameAlarmExtractionCondition> {

	private static final long serialVersionUID = 1L;
	
	public NameAlarmExtractionCondition(String rawValue) {
		super(rawValue);
	}

}
