package nts.uk.ctx.at.record.dom.remainingnumber.annualleave;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：暫定年休管理データ
 * @author shuichu_ishida
 */
public interface TempAnnualLeaveMngService {

	/**
	 * 暫定年休管理データを作成する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param mode モード
	 * @return 暫定年休管理データリスト
	 */
	List<TempAnnualLeaveManagement> create(String companyId, String employeeId, DatePeriod period,
			TempAnnualLeaveMngMode mode);
}
