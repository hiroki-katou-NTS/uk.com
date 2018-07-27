package nts.uk.file.at.app.export.dailyschedule;

/**
 * File output type (0: Excel, 1: PDF)
 *
 * @author HoangNDH
 */
public enum FileOutputType {
	
	/** The file type excel. */
	FILE_TYPE_EXCEL(0),
	
	/** The file type pdf. */
	FILE_TYPE_PDF(1);
	
	/** The value. */
	public int value;

	/**
	 * Instantiates a new file output type.
	 *
	 * @param value the value
	 */
	private FileOutputType(int value) {
		this.value = value;
	}
	
	public static FileOutputType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FileOutputType val : FileOutputType.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
