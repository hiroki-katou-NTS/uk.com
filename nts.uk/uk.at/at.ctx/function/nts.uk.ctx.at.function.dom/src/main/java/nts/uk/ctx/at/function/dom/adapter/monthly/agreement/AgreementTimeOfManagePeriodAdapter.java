package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Year;

public interface AgreementTimeOfManagePeriodAdapter {
	/**
	 * 年間の36協定の時間を取得する
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 該当する管理期間の36協定時間
	 */
	// RequestList421
	List<AgreementTimeOfManagePeriodImport> findByYear(String employeeId, Year year);
}
