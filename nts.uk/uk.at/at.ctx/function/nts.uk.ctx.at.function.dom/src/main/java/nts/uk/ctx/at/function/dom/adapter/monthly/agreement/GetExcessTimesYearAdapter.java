package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import nts.uk.ctx.at.shared.dom.common.Year;

public interface GetExcessTimesYearAdapter {
	/**
	 * 年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	int algorithm(String employeeId, Year year);
}
