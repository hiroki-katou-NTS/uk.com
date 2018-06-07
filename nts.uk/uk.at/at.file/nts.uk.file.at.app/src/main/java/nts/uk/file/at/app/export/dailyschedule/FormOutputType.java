package nts.uk.file.at.app.export.dailyschedule;

import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;

/**
 * 帳票出力種類.
 *
 * @author HoangNDH
 */
public enum FormOutputType {
	
	// 日付別
	BY_DATE(1),
	
	// 個人別
	BY_EMPLOYEE(0);
	
	/** The output type. */
	public final int outputType;
	
	/**
	 * Instantiates a new form output type.
	 *
	 * @param outputType the output type
	 */
	private FormOutputType(int outputType) {
		this.outputType = outputType;
	}
	
	public static FormOutputType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FormOutputType val : FormOutputType.values()) {
			if (val.outputType == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
