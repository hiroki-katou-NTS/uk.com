package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountNumberOfPeopleByEachWorkMethodService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.NumberOfPeopleByEachWorkMethod;
import nts.uk.ctx.at.shared.app.query.worktime.GetWorkingHoursInformationQuery;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AggregationUnitOfWorkMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務方法ごとに人数を集計する
 * @author hoangnd
 *
 */
@Stateless 
public class ScreenQueryAggreratePeopleMethod {

	@Injectable
	private CountNumberOfPeopleByEachWorkMethodService.Require require;
	
	@Inject
	private GetWorkingHoursInformationQuery getWorkingHoursInformationQuery;
	
	@Inject
	private ShiftMasterRepository shiftMasterRepository;
	
	public AggreratePeopleMethodOutput get(
			TargetOrgIdenInfor targetOrg,
			DatePeriod period,
			List<IntegrationOfDaily> scheduleList,
			List<IntegrationOfDaily> actualList,
			Boolean isShiftDisplay
			) {
		
		AggreratePeopleMethodOutput output = new AggreratePeopleMethodOutput();
		String companyId = AppContexts.user().companyId();
		// 1: シフト表示か == false
		if (!isShiftDisplay) {
			// 1.1: 勤務方法別に集計する(Require, 対象組織識別情報, 期間, List<日別勤怠(Work)>, List<日別勤怠(Work)>, 勤務方法の集計単位, 関数( ( String ) -> T ))
			Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<String>>> countWork = 
						CountNumberOfPeopleByEachWorkMethodService.countByWorkMethod(
								require,
								targetOrg,
								period,
								scheduleList,
								actualList,
								AggregationUnitOfWorkMethod.WORK_TIME,
								workMethod -> new String(workMethod));
			
			// 1.2: 就業時間帯情報リストを取得する(会社ID, List<就業時間帯コード>)
			List<WorkTimeSetting> workTimeSetting =
					getWorkingHoursInformationQuery.getListWorkTimeSetting(
							companyId,
							countWork.entrySet()
									 .stream()
									 .map(x -> x.getValue())
									 .flatMap(x -> x.stream())
									 .map(x -> x.getWorkMethod())
									 .distinct()
									 .collect(Collectors.toList()));
			
			output.setCountWork(countWork);
			
		} else { // 2: シフト表示か == true
			
			// 2.1 シフト別人数を取得する(Require, 対象組織識別情報, 期間, List<日別勤怠(Work)>, List<日別勤怠(Work)>)
			Map<GeneralDate, List<NumberOfPeopleByEachWorkMethod<ShiftMasterCode>>> shift = 
					CountNumberOfPeopleByEachWorkMethodService.getByShift(
							require,
							targetOrg,
							period,
							scheduleList,
							actualList);
			
			// 2.2: *get
			List<ShiftMaster> shirftMasters = 
					shiftMasterRepository.getByListShiftMaterCd2(
							companyId,
							shift.entrySet()
							 .stream()
							 .map(x -> x.getValue())
							 .flatMap(x -> x.stream())
							 .map(x -> x.getWorkMethod().v())
							 .distinct()
							 .collect(Collectors.toList()));
			
			
			output.setShift(shift);
			
		}
		
		/**
		 * 【Output】
			Map<年月日, List<勤務方法別の人数<T>>>
			※Tはそれぞれコードから取得した名称に置き換える
		 */
		
		
		return output;
		
		
	}
}
