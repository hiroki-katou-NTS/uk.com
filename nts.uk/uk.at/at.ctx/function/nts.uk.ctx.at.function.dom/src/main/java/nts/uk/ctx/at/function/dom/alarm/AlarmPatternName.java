package nts.uk.ctx.at.function.dom.alarm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author dxthuong
 * アラームリストパターン名称
 */
@StringMaxLength(20)
public class AlarmPatternName extends StringPrimitiveValue<AlarmPatternName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new role name.
	 *
	 * @param rawValue the raw value
	 */
	public AlarmPatternName(String rawValue) {
		super(rawValue);
	}
}
