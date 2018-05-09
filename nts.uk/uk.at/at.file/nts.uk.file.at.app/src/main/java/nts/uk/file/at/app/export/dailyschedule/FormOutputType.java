package nts.uk.file.at.app.export.dailyschedule;

/**
 * 帳票出力種類.
 *
 * @author HoangNDH
 */
public enum FormOutputType {
	
	// 日付別
	BY_DATE(0),
	
	// 個人別
	BY_EMPLOYEE(1);
	
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
}
