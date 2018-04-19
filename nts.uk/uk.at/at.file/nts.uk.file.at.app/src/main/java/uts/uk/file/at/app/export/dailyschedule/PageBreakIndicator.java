package uts.uk.file.at.app.export.dailyschedule;

/**
 * 改ページ区分
 * @author HoangNDH
 *
 */
public enum PageBreakIndicator {
	// なし
	NOT_USE(0),
	// 社員
	EMPLOYEE(1),
	// 職場
	WORKPLACE(2);
	
	private final int indicator;

	private PageBreakIndicator(int indicator) {
		this.indicator = indicator;
	}
}
