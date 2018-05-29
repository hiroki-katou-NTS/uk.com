package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 管理期間の36協定時間の取得
 * @author shuichu_ishida
 */
public interface AgreementTimeOfManagePeriodPub {

	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @return 該当する管理期間の36協定時間
	 */
	Optional<AgreementTimeOfManagePeriod> find(String employeeId, YearMonth yearMonth);

	/**
	 * 検索　（期間）
	 * @param employeeId 社員ID
	 * @param range 期間
	 * @return 該当する管理期間の36協定時間　（年月順）
	 */
	List<AgreementTimeOfManagePeriod> findByRange(String employeeId, DatePeriod range);
}
