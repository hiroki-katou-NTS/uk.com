package nts.uk.file.at.app.export.dailyschedule;

/**
 * 改ページ区分.
 *
 * @author HoangNDH
 */
public enum PageBreakIndicator {
	
	// なし
	NOT_USE(0),
	
	// 社員
	EMPLOYEE(1),
	
	// 職場
	WORKPLACE(2);
	
	/** The indicator. */
	private final int indicator;

	/**
	 * Instantiates a new page break indicator.
	 *
	 * @param indicator the indicator
	 */
	private PageBreakIndicator(int indicator) {
		this.indicator = indicator;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the page break indicator
	 */
	public static PageBreakIndicator valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PageBreakIndicator val : PageBreakIndicator.values()) {
			if (val.indicator == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
