package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.period;

import java.util.List;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 週集計期間を取得する
 * @author shuichu_ishida
 */
public interface GetWeekPeriod {

	/**
	 * 処理期間の各週の開始日、終了日を判断
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param period 期間　（集計期間）
	 * @param workingSystem 労働制
	 * @param closureId 締めID
	 * @return 期間リスト
	 */
	List<DatePeriod> algorithm(String companyId, String employmentCd, String employeeId,
			DatePeriod period, WorkingSystem workingSystem, ClosureId closureId);
}
