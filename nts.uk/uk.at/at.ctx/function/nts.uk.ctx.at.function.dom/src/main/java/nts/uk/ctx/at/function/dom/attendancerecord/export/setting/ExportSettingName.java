package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class AttendanceItemRecordName.
 */
//出力項目設定名称
@StringMaxLength(20)
public class ExportSettingName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance item record name.
	 *
	 * @param rawValue the raw value
	 */
	public ExportSettingName(String rawValue) {
		super(rawValue);
	}

}
