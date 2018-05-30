package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

/**
 * The Class ItemName.
 */
// 出勤簿上の項目名
@StringRegEx("[a-zA-Z0-9#$%&()~|{}*+?@'<>_/;\\\"\\\\\\[\\]\\`-]{1,20}$")
@StringMaxLength(12)
public class ItemName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new item name.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public ItemName(String rawValue) {
		super(rawValue);
	}

}
