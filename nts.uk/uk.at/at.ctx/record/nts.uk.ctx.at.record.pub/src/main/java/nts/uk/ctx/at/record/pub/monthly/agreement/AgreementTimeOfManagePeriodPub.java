package nts.uk.ctx.at.record.pub.monthly.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

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
	 * 36協定時間を取得する
	 * @param employeeId 社員ID
	 * @param period 期間（年月）
	 * @return 36協定時間マップ
	 */
	// RequestList287
	Map<YearMonth, AttendanceTimeMonth> getTimeByPeriod(String employeeId, YearMonthPeriod period);
	
	/**
	 * 年間の36協定の時間を取得する
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 該当する管理期間の36協定時間
	 */
	// RequestList421
	List<AgreementTimeOfManagePeriod> findByYear(String employeeId, Year year);
}
