package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import lombok.Getter;

/**
 * The Class MonthlyFormatPerformanceImport.
 */
@Getter
public class MonthlyFormatPerformanceImport {
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * フォーマット種類 - Format Type
	 *  権限 - Authority: 0
	 *  勤務種別 - Work Type: 1
	 */
	private int settingUnitType;

	/**
	 * Instantiates a new format performance import.
	 *
	 * @param cid
	 *            the cid
	 * @param settingUnitType
	 *            the setting unit type
	 */
	public MonthlyFormatPerformanceImport(String cid, int settingUnitType) {
		super();
		this.cid = cid;
		this.settingUnitType = settingUnitType;
	}
}
