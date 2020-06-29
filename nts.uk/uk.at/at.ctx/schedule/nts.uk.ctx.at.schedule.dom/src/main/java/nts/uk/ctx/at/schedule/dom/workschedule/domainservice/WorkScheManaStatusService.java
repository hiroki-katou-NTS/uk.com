package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

/**
 * «DomainService» 予定管理状態に応じて勤務予定を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * 
 * @author HieuLt
 *
 */
public class WorkScheManaStatusService {

	/**
	 * [1] 取得する
	 * @param require
	 * @param lstEmployeeID
	 * @return Map<社員の予定管理状態, Optional<勤務予定>>
	 */
	public static Map<ScheManaStatuTempo, Optional<WorkSchedule>> getScheduleManagement(Require require,
			List<String> lstEmployeeID) {
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> map = new HashMap<>();
		return map;
	}

	/**
	 * [prv-1] 社員別に取得する
	 * @param employeeID
	 * @param datePeriod
	 * @return Map<社員の予定管理状態, Optional<勤務予定>>
	 */
	private Map<ScheManaStatuTempo, Optional<WorkSchedule>> getByEmployee(String employeeID, DatePeriod datePeriod) {
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> map = new HashMap<>();
		return map;
	}

	public static interface Require {
		/**
		 * R-1] 勤務予定を取得する
		 * @param employeeID
		 * @param ymd
		 * @return
		 */
		Optional<WorkSchedule> get(String employeeID, GeneralDate ymd);
	}
}
