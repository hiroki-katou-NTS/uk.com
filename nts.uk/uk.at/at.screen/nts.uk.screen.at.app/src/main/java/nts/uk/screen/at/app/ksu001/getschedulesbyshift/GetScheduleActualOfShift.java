/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.AggregateScheduleDto;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.ScreenQueryAggrerateSchedule;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;
import nts.uk.screen.at.app.ksu001.getworkactualshift.GetActualOfShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetScheduleOfShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftResult;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleShiftBaseResult;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.PlanAndActual;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.ScreenQueryPlanAndActual;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.ScreenQueryWorkScheduleShift;

/**
 * @author laitv
 * <<ScreenQuery>> 予定・実績をシフトで取得する
 *
 */
@Stateless
public class GetScheduleActualOfShift {
	
	@Inject
	private GetScheduleOfShift getWorkScheduleShift;
	@Inject
	private GetActualOfShift getActualOfShift;
	
	@Inject
	private ScreenQueryPlanAndActual screenQueryPlanAndActual;
	
	@Inject
	private ScreenQueryWorkScheduleShift screenQueryWorkScheduleShift;
	
	@Inject
	private ScreenQueryAggrerateSchedule screenQueryAggrerateSchedule;
	
	public SchedulesbyShiftDataResult getData(SchedulesbyShiftParam param) {
		
		// step 1 call ScreenQuery 勤務予定（シフト）を取得する
		ScheduleOfShiftParam param1 = new ScheduleOfShiftParam(param.listShiftMasterNotNeedGetNew, param.listSid, param.startDate, param.endDate);
		ScheduleOfShiftResult resultStep51 = getWorkScheduleShift.getWorkScheduleShift(param1);
		List<ScheduleOfShiftDto> listWorkScheduleShift = resultStep51.listWorkScheduleShift;
		
		if (param.getActualData) {
			// lấy data thực tế
			// call ScreenQuery 勤務実績（シフト）を取得する
			ScheduleOfShiftParam param2 = new ScheduleOfShiftParam(resultStep51.shiftMasterWithWorkStyleLst, param.listSid, param.startDate, param.endDate);
			ScheduleOfShiftResult resultStep52 =  getActualOfShift.getActualOfShift(param2);
			List<ScheduleOfShiftDto> listDataDailyShift = resultStep52.listWorkScheduleShift;
			// merge
			List<ScheduleOfShiftDto> listToRemove = new ArrayList<ScheduleOfShiftDto>();
			List<ScheduleOfShiftDto> listToAdd = new ArrayList<ScheduleOfShiftDto>();
			for (ScheduleOfShiftDto dataSchedule : listWorkScheduleShift) {
				String sid = dataSchedule.employeeId;
				GeneralDate date = dataSchedule.date;
				Optional<ScheduleOfShiftDto> dataDaily = listDataDailyShift.stream().filter(data -> {
					if (data.employeeId.equals(sid) && data.date.equals(date))
						return true;
					return false;
				}).findFirst();
				if (dataDaily.isPresent()) {
					listToRemove.add(dataSchedule);
					listToAdd.add(dataDaily.get());
				}
			}
			listWorkScheduleShift.removeAll(listToRemove);
			listWorkScheduleShift.addAll(listToAdd);
			
			return new SchedulesbyShiftDataResult(listWorkScheduleShift , resultStep52.shiftMasterWithWorkStyleLst);
		}
		return new SchedulesbyShiftDataResult(listWorkScheduleShift , resultStep51.shiftMasterWithWorkStyleLst);
	}
	
	
	/**
	 * 予定・実績をシフトで取得する（未発注）
	 *  UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正(職場別).メニュー別OCD.Aa：シフト表示
	 * @param listShiftMasterNotNeedGet ・新たに取得する必要のないシフト一覧
	 * @param sids ・社員リスト　　　：List<社員ID>
	 * @param datePeriod ・期間　　　　　　：期間
	 * @param closeDate ・締め日　　　　　：日付
	 * @param isAchievement ・実績も取得するか：boolean
	 * @param targetOrg ・対象組織　　　　：対象組織識別情報
	 * @param personalCounterOp ・個人計カテゴリ　：Optional<個人計カテゴリ>
	 * @param workplaceCounterOp ・職場計カテゴリ　：Optional<職場計カテゴリ>
	 */
	public SchedulesbyShiftDataResult_New getDataNew(
			List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGet,
			List<String> sids,
			DatePeriod datePeriod,
			DateInMonth closeDate,
			boolean isAchievement,
			TargetOrgIdenInfor targetOrg,
			Optional<PersonalCounterCategory> personalCounterOp,
			Optional<WorkplaceCounterCategory> workplaceCounterOp
			) {
		// 1: 取得する(List<社員ID>, 期間, boolean)
		PlanAndActual planAndActual = screenQueryPlanAndActual.getPlanAndActual(
				sids,
				datePeriod,
				isAchievement);
		//2 取得する()
		WorkScheduleShiftBaseResult workScheduleShiftBaseResult =
				screenQueryWorkScheduleShift.create(
						listShiftMasterNotNeedGet,
						planAndActual.getSchedule(),
						planAndActual.getDailySchedule(),
						isAchievement);
		
		// 3 集計する(List<社員ID>, 期間, 日付, , , 対象組織識別情報, Optional<個人計カテゴリ>, Optional<職場計カテゴリ>, boolean)
		AggregateScheduleDto aggrerateSchedule =
				screenQueryAggrerateSchedule.aggrerateSchedule(
						sids,
						datePeriod,
						closeDate,
						planAndActual.getSchedule(),
						planAndActual.getDailySchedule(),
						targetOrg,
						personalCounterOp,
						workplaceCounterOp,
						true); // シフト表示か = true
		return new SchedulesbyShiftDataResult_New(
				workScheduleShiftBaseResult.getListWorkScheduleShift(),
				workScheduleShiftBaseResult.getMapShiftMasterWithWorkStyle()
				   .entrySet()
				   .stream()
				   .collect(Collectors.toMap(e -> new ShiftMasterDto(e.getKey()), e -> e.getValue().map(x -> x.value).orElse(null))),
				aggrerateSchedule.getAggreratePersonal(),
				aggrerateSchedule.getAggrerateWorkplace()
				);
	}
}
