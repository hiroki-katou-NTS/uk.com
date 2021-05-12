package nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.DailyResultAccordScheduleStatusService;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
/**
 * 予定・実績を取得する
 * @author hoangnd
 *
 */
@Stateless
public class ScreenQueryPlanAndActual {

	@Injectable
	private WorkScheManaStatusService.Require require1;
	
	@Injectable
	private DailyResultAccordScheduleStatusService.Require require2;
	
	/**
	 * 
	 * @param sids 社員リスト
	 * @param datePeriod 期間
	 * @param isAchievement 実績も取得するか
	 */
	public PlanAndActual getPlanAndActual(
			List<String> sids,	
			DatePeriod datePeriod,
			Boolean isAchievement
			) {
		PlanAndActual output = new PlanAndActual();
		//1: 取得する(Require, List<社員ID>, 期間)
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> schedule = 
					WorkScheManaStatusService.getScheduleManagement(require1, sids, datePeriod);
		
		output.setSchedule(schedule);
		output.setDailySchedule(new HashMap<ScheManaStatuTempo, Optional<IntegrationOfDaily>>());
		
		// 実績も取得するか == true &&  Input．期間．開始 < システム日付
		if (isAchievement && datePeriod.start().before(GeneralDate.today())) {
			
			/**
			 * 引数の期間＝
				　　case システム日付 <= Input．期間．終了日：
				　　　　Input．期間．開始日 ～ システム日付-1
				　　case それ以外：
				　　　　Input．期間

			 */
			if (datePeriod.end().afterOrEquals(GeneralDate.today())) {
				datePeriod = datePeriod.cutOffWithNewEnd(GeneralDate.today());			
			}
			// 2: 取得する(Require, List<社員ID>, 期間)
			Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> dailySchedule = 
					DailyResultAccordScheduleStatusService.get(require2, sids, datePeriod);
			
			output.setDailySchedule(dailySchedule);
			
		}
		
		return output;
		
	}
}
