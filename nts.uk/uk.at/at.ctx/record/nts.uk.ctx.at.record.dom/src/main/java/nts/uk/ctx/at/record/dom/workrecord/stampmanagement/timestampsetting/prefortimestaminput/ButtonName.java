package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 打刻ボタン名称
 * @author phongtq
 *
 */

@StringMaxLength(20)
public class ButtonName extends StringPrimitiveValue<ButtonName>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public ButtonName(String rawValue) {
		super(rawValue);
	}
}