package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 次回年休付与年月日を取得する
 * @author shuichu_ishida
 */
public interface GetNextAnnLeaGrantDate {

	/**
	 * 次回年休付与年月日を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 次回年休付与年月日
	 */
	// RequestList369
	Optional<GeneralDate> algorithm(String companyId, String employeeId);
}
