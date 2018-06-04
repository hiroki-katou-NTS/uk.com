package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.*;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class OutputSettingCode.
 */
//出力項目設定コード
@LongMinValue(0)
@LongMaxValue(99)
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class ExportSettingCode extends LongPrimitiveValue<PrimitiveValue<Long>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new output setting code.
	 *
	 * @param rawValue the raw value
	 */
	public ExportSettingCode(Long rawValue) {
		super(rawValue);
	}

}
