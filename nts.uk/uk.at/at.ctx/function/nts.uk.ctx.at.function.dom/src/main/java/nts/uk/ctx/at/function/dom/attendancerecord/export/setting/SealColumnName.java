package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class SealColumnName.
 */
//印鑑欄名
@StringMaxLength(8)
public class SealColumnName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new seal column name.
	 *
	 * @param rawValue the raw value
	 */
	public SealColumnName(String rawValue) {
		super(rawValue);
	}
}
