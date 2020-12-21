package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.*;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class OutputSettingCode.
 */
//出力項目設定コード
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class ExportSettingCode extends StringPrimitiveValue<PrimitiveValue<String>> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new output setting code.
	 *
	 * @param rawValue the raw value
	 */
	public ExportSettingCode(String rawValue) {
		super(rawValue);
	}

}
