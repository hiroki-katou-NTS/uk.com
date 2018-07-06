package nts.uk.ctx.at.function.dom.adapter.annualleave;


import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface GetNextAnnLeaGrantDateAdapter {
	/**
	 * 次回年休付与年月日を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 次回年休付与年月日
	 */
	// RequestList369
	public Optional<GeneralDate> algorithm(String companyId, String employeeId);
}
