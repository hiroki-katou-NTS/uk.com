package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import nts.uk.ctx.at.shared.dom.common.Year;

public interface ExcessTimesYearAdapter {
	/**
	 * 年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	AgreementExcessInfoImport getExcessTimesYear(String employeeId, Year year);
}
