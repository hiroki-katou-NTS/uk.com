package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 週開始を取得する
 * @author shuichu_ishida
 */
public interface GetWeekStart {

	/**
	 * 週開始を取得する
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @param workingSystem 労働制
	 * @return 週開始
	 */
	Optional<WeekStart> algorithm(String companyId, String employmentCd, String employeeId,
				GeneralDate baseDate, WorkingSystem workingSystem);
}
