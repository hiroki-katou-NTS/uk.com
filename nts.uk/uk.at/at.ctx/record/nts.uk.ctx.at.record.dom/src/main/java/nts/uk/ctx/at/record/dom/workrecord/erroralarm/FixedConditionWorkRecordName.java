package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * エラーアラームメッセージ
 * @author tutk
 *
 */
@StringMaxLength(400)
public class FixedConditionWorkRecordName extends StringPrimitiveValue<FixedConditionWorkRecordName> {

	private static final long serialVersionUID = 1L;
	public FixedConditionWorkRecordName(String rawValue) {
		super(rawValue);
	}
	
}
