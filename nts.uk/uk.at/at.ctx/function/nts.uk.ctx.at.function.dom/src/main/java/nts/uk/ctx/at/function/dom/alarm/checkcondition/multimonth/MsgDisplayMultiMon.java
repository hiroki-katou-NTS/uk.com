package nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * エラーアラームメッセージ
 * @author tutk
 *
 */
@StringMaxLength(100)
public class MsgDisplayMultiMon extends StringPrimitiveValue<MsgDisplayMultiMon>{

	public MsgDisplayMultiMon(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
