package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 予定管理状態に応じて日別実績を取得する	
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * 
 * @author Hieult
 *
 */
public class DailyResultAccordScheduleStatus {
	

	/**
	 * 	[1] 取得する	
	 * @param require
	 * @param employeeID
	 * @param datePeriod
	 * @return Map<ScheManaStatuTempo, Optional<IntegrationOfDaily>> --- Map<社員の予定管理状態, Optional<日別勤怠(Work)>>
	 */
	public static Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> get(Require require, String employeeID , DatePeriod datePeriod ){
		Map<ScheManaStatuTempo, Optional<IntegrationOfDaily>> map = new HashMap<>();
	
		return map;
	}

	/**
	 * 	[prv-1] 社員別に取得する	
	 * @param employeeID
	 * @param datePeriod
	 * @return Map<ScheManaStatuTempo, Optional<IntegrationOfDaily>> --- Map<社員の予定管理状態, Optional<日別勤怠(Work)>>
	 */
	private Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> getByEmp(String employeeID , DatePeriod datePeriod){
		Map<ScheManaStatuTempo, Optional<IntegrationOfDaily>> map = new HashMap<>();
		return map;
	}

	public static interface Require  {
		// 社員と日付を指定して日別勤怠(Work)を取得するアルゴリズムを利用する（1次の処理に存在するはず） 
		// Tài liệu chưa chỉ rõ thuật toán 
	}
}
