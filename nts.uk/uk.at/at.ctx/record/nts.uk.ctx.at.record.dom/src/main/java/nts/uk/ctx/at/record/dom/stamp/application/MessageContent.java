package nts.uk.ctx.at.record.dom.stamp.application;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 打刻エラー通知メッセージ (by doc UI)
 * エラー有時に促すメッセージ
 * @author phongtq
 *
 */
@StringMaxLength(200)
public class MessageContent extends StringPrimitiveValue<MessageContent>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public MessageContent(String rawValue) {
		super(rawValue);
	}
}