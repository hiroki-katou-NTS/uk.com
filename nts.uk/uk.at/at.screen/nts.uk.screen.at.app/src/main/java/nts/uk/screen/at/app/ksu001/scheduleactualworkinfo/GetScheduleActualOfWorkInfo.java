/**
 * 
 */
package nts.uk.screen.at.app.ksu001.scheduleactualworkinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.AggrerateScheduleDto;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.ScreenQueryAggrerateSchedule;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.processcommon.ScreenQueryCreateWorkSchedule;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.PlanAndActual;
import nts.uk.screen.at.app.ksu001.processcommon.nextorderdschedule.ScreenQueryPlanAndActual;

/**
 * @author laitv
 * ScreenQuery : 予定・実績を勤務情報で取得する
 */
@Stateless
public class GetScheduleActualOfWorkInfo {
	
	@Inject
	private GetScheduleOfWorkInfo getScheduleOfWorkInfo;
	@Inject
	private GetWorkActualOfWorkInfo getWorkActualOfWorkInfo;
	
	
	@Inject
	private ScreenQueryPlanAndActual screenQueryPlanAndActual;
	
	@Inject
	private ScreenQueryAggrerateSchedule screenQueryAggrerateSchedule;
	
	@Inject
	private ScreenQueryCreateWorkSchedule screenQueryCreateWorkSchedule;
	
	public List<WorkScheduleWorkInforDto> getDataScheduleAndAactualOfWorkInfo(DisplayInWorkInfoParam param) {
		
		// lay data Schedule
		List<WorkScheduleWorkInforDto> listDataSchedule = getScheduleOfWorkInfo.getDataScheduleOfWorkInfo(param);
		
		if (param.getActualData) {
			// lay data Daily
			List<WorkScheduleWorkInforDto> listDataDaily = getWorkActualOfWorkInfo.getDataActualOfWorkInfo(param);
			// merge
			List<WorkScheduleWorkInforDto> listToRemove = new ArrayList<WorkScheduleWorkInforDto>();
			List<WorkScheduleWorkInforDto> listToAdd = new ArrayList<WorkScheduleWorkInforDto>();
			for (WorkScheduleWorkInforDto dataSchedule : listDataSchedule) {
				String sid = dataSchedule.employeeId;
				GeneralDate date = dataSchedule.date;
				Optional<WorkScheduleWorkInforDto> dataDaily = listDataDaily.stream().filter(data -> {
					if (data.employeeId.equals(sid) && data.date.equals(date))
						return true;
					return false;
				}).findFirst();
				if (dataDaily.isPresent()) {
					listToRemove.add(dataSchedule);
					listToAdd.add(dataDaily.get());
				}
			}
			listDataSchedule.removeAll(listToRemove);
			listDataSchedule.addAll(listToAdd);
		}
		return listDataSchedule;
	}
	
	/**
	 * 予定・実績を勤務情報で取得する（未発注）
	 * 
	 * @param sids // 社員リスト
	 * @param datePeriod // 期間
	 * @param closeDate // 締め日
	 * @param isAchievement // 実績も取得するか
	 * @param targetOrg // 対象組織
	 * @param personalCounterOp // 個人計カテゴリ
	 * @param workplaceCounterOp // 職場計カテゴリ
	 * @return
	 */
	public ScheduleActualOfWorkOutput getDataScheduleAndAactualOfWorkInfoNew(
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
		List<WorkScheduleWorkInforDto> workScheduleWorkInforDtos = 
				screenQueryCreateWorkSchedule.get(
					planAndActual.getSchedule(),
					planAndActual.getDailySchedule(),
					isAchievement);
		
		// 3 集計する(List<社員ID>, 期間, 日付, , , 対象組織識別情報, Optional<個人計カテゴリ>, Optional<職場計カテゴリ>, boolean)
		AggrerateScheduleDto aggrerateSchedule =
				screenQueryAggrerateSchedule.aggrerateSchedule(
						sids,
						datePeriod,
						closeDate,
						planAndActual.getSchedule(),
						planAndActual.getDailySchedule(),
						targetOrg,
						personalCounterOp,
						workplaceCounterOp,
						false); // シフト表示か = false
		
		return new ScheduleActualOfWorkOutput(
				workScheduleWorkInforDtos,
				aggrerateSchedule.getAggreratePersonal(),
				aggrerateSchedule.getAggrerateWorkplace()
				);
	}
}
