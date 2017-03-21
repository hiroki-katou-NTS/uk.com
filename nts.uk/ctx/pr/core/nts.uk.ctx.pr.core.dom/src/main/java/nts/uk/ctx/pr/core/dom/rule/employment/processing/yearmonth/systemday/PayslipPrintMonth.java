package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.systemday;

import java.util.HashMap;

public enum PayslipPrintMonth {
	PREVIEW_MONTH(-1), CURRENT_MONTH(0);

	public final int value;

	private static HashMap<Integer, PayslipPrintMonth> map = new HashMap<>();

	static {
		for (PayslipPrintMonth item : PayslipPrintMonth.values()) {
			map.put(item.value, item);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param 賃金対象区分の値
	 */
	PayslipPrintMonth(int value) {
		this.value = value;
	}

	/**
	 * convert to enum PayslipPrintMonthAtr by value
	 * 
	 * @param value
	 * @return
	 */
	public static PayslipPrintMonth valueOf(int value) {
		return map.get(value);
	}

}
