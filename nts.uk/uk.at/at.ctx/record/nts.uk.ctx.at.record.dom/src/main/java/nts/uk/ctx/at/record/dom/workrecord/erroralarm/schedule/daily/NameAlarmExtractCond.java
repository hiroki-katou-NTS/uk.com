package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * アラームリスト抽出条件の名称
 *
 */

@StringMaxLength(20)
public class NameAlarmExtractCond extends StringPrimitiveValue<NameAlarmExtractCond> {

	private static final long serialVersionUID = 1L;
	
	public NameAlarmExtractCond(String rawValue) {
		super(rawValue);
	}

}
