package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * エラーアラームメッセージ
 * @author tutk
 *
 */
@StringMaxLength(200)
public class MessageDisplay extends StringPrimitiveValue<MessageDisplay>{

	public MessageDisplay(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
