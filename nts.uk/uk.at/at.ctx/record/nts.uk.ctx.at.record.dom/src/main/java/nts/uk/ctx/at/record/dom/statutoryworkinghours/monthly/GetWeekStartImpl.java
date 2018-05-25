package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 実装：週開始を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekStartImpl implements GetWeekStart {

	/** 日の法定労働時間 */
	@Inject
	private DailyStatutoryWorkingHours dailyStatutoryWorkingHours;
	
	/** 週開始を取得する */
	@Override
	public Optional<WeekStart> algorithm(String companyId, String employmentCd, String employeeId,
				GeneralDate baseDate, WorkingSystem workingSystem) {
		
		// 時間設定を取得
		val workTimeSetOpt = this.dailyStatutoryWorkingHours.getWorkingTimeSetting(
				companyId, employmentCd, employeeId, baseDate, workingSystem);
		if (!workTimeSetOpt.isPresent()) return Optional.empty();
		
		// 「週開始」を返す
		if (workTimeSetOpt.get().getWeeklyTime() == null) return Optional.empty();
		return Optional.ofNullable(workTimeSetOpt.get().getWeeklyTime().getStart());
	}
}
