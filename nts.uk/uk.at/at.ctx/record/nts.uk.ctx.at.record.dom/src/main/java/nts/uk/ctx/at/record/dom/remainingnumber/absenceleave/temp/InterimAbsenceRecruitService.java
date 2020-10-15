package nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * 暫定振休・振出管理データ
 * @author shuichu_ishida
 */
public interface InterimAbsenceRecruitService {

	/**
	 * 作成
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param workInfoOfDailyList 日別実績の勤務情報リスト
	 */
	void create(String companyId, String employeeId, DatePeriod period,
			Optional<List<WorkInfoOfDailyPerformance>> workInfoOfDailyList);
	
	/**
	 * 削除
	 * @param employeeId 社員ID
	 * @param period 期間
	 */
	void remove(String employeeId, DatePeriod period);
}
