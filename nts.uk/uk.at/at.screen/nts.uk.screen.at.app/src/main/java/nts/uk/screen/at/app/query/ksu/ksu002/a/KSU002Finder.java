package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceMergingService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.screen.at.app.ksu001.processcommon.ScreenQueryCreateWorkSchedule;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.PlanAndActual;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.ScreenQueryPlanAndActual;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.PeriodListPeriodDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV 
 */

@Stateless
public class KSU002Finder {
	
	@Inject
	private WeekRuleManagementRepo weekRuleManagementRepo;
	
	@Inject
	private ScreenQueryPlanAndActual screenQueryPlanAndActual;
	
	@Inject
	private ScreenQueryCreateWorkSchedule screenQueryCreateWorkSchedule;
	
	/**
	 * @name 予定・実績を取得する
	 */
	public void getPlansResults(DisplayInWorkInfoInput param) {
		
		//1:取得(期間, 曜日)
		PeriodListPeriodDto periodListPeriod = this.getPeriodList(param.getPeriod(), param.getStartWeekDate());
		
		//2:取得する(List<社員ID>, 期間, boolean)
		PlanAndActual planAndActual = screenQueryPlanAndActual.getPlanAndActual(param.listSid, param.getPeriod(), param.actualData);
		
		//3:取得する()
		List<WorkScheduleWorkInforDto> workScheduleWorkInfor = screenQueryCreateWorkSchedule.get(planAndActual.getSchedule(), planAndActual.getDailySchedule(), param.actualData);
		
		//4:集計する(社員ID, 期間, List<期間>, int, int)
		
	}
	
	
	/**
	 * @name 取得期間と週合計期間リストを取得する
	 */
	public PeriodListPeriodDto getPeriodList(DatePeriod period, DayOfWeek startWeekDate) {
		
		String cid = AppContexts.user().companyId();
		//週の管理
		Optional<WeekRuleManagement> weekRuleManagement = weekRuleManagementRepo.find(cid);
		
		if(!weekRuleManagement.isPresent()) {
			return new PeriodListPeriodDto(new ArrayList<DatePeriod>(), period);
		}else if(weekRuleManagement.get().getWeekStart().value != startWeekDate.value){
			return new PeriodListPeriodDto(new ArrayList<DatePeriod>(), period);
		}else {
			GeneralDate start = period.start();
			while (start.dayOfWeekEnum().value != startWeekDate.value) {
				start.addDays(-1);
			}
			GeneralDate end = period.end();
			end.addDays(1);
			while (end.dayOfWeekEnum().value != startWeekDate.value) {
				start.addDays(1);
			}
			end.addDays(-1);
			
			PeriodListPeriodDto result = new PeriodListPeriodDto();
			result.setDatePeriod(new DatePeriod(start, end));
			List<DatePeriod> datePeriodList = new ArrayList<DatePeriod>();
			while (start.before(end)) {
				datePeriodList.add(new DatePeriod(start, start.addDays(7)));
			}
			result.setDatePeriodList(datePeriodList);
			return result;
		}
			
	}
	
	/**
	 * @name 個人スケジュールを集計する
	 */
	public void getPersonalSchedules(String employeeId, DatePeriod period, List<DatePeriod> datePeriodList, Map<ScheManaStatuTempo, Optional<WorkSchedule>> schedule, Map<ScheManaStatuTempo , Optional<IntegrationOfDaily>> dailySchedule) {
		
		/* $予定リスト = 管理状態と勤務予定Map.values: filter: $.isPresent map: $.日別勤怠Workに変換する() */
		List<IntegrationOfDaily> integrationOfDailyList = schedule.values().stream().filter(o -> o.isPresent()).map(c -> c.get().convertToIntegrationOfDaily()).collect(Collectors.toList());
		
		/* $実績リスト = 管理状態と勤務実績Map.values: filter: $.isPresent*/
		List<IntegrationOfDaily> integrationOfDailyListDaily = dailySchedule.values().stream().filter(c->c.isPresent()).map(c->c.get()).collect(Collectors.toList());
		
		//2: 集計する(社員ID, 期間, List<日別勤怠(Work)>, List<日別勤怠(Work)>)
		this.getMonthlyData(employeeId, period, integrationOfDailyList, integrationOfDailyListDaily);
	}
	
	/**
	 * @name 月の集計データを集計する
	 */
	public void getMonthlyData(String employeeId, DatePeriod period, List<IntegrationOfDaily> integrationOfDailyList, List<IntegrationOfDaily> integrationOfDailyListDaily) {
		
		/*1: マージする(List<社員ID>, 期間, List<日別勤怠(Work)>, List<日別勤怠(Work)>) */
		List<IntegrationOfDaily> mergeToFlatList = DailyAttendanceMergingService.mergeToFlatList(Arrays.asList(new EmployeeId(employeeId)), period, integrationOfDailyList, integrationOfDailyListDaily);
		
		/*2: 集計する(List<日別勤怠(Work)>)*/
		Map<EmployeeId, Map<AttendanceTimesForAggregation, BigDecimal>> workingTimeCounter = WorkingTimeCounterService.get(mergeToFlatList);
		
		/*3: 集計する(Require, List<日別勤怠(Work)>) */
		
	}
	
	/**
	 * @name 週の集計データを集計する
	 */
	public void getweeklyData() {}
}
