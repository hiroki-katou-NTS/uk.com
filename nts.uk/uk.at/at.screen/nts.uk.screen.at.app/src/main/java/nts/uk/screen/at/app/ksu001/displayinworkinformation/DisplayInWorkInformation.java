/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.screen.at.app.ksu001.processcommon.GetListWorkTypeAvailable;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.scheduleactualworkinfo.GetScheduleActualOfWorkInfo;
import nts.uk.screen.at.app.ksu001.scheduleactualworkinfo.ScheduleActualOfWorkOutput;

/**
 * @author laitv 
 * ScreenQuery 勤務情報で表示する
 */
@Stateless
public class DisplayInWorkInformation {

	@Inject
	private GetScheduleActualOfWorkInfo getScheduleActualOfWorkInfo;
	@Inject
	private GetListWorkTypeAvailable getListWorkTypeAvailable;
	
	public DisplayInWorkInfoResult getDataWorkInfo(DisplayInWorkInfoParam param) {

		DisplayInWorkInfoResult result = new DisplayInWorkInfoResult();

		List<WorkTypeInfomation> listWorkTypeInfo = new ArrayList<>();

		listWorkTypeInfo = getListWorkTypeAvailable.getData();
		
		result.setListWorkTypeInfo(listWorkTypeInfo);
		
		// Lấy data Schedule
		// Get schedule/actual results with work information
		List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor = getScheduleActualOfWorkInfo.getDataScheduleAndAactualOfWorkInfo(param);

		result.setListWorkScheduleWorkInfor(listWorkScheduleWorkInfor);
		return result;
	}
	
	public DisplayInWorkInfoResult_New getDataWorkInfo_New(DisplayInWorkInfoParam_New param) {
		// 1: <call>
		List<WorkTypeInfomation> listWorkTypeInfo = getListWorkTypeAvailable.getData_New();
		
		// 2: <call>
		ScheduleActualOfWorkOutput scheduleActualOfWorkOutput = 
				
			getScheduleActualOfWorkInfo.getDataScheduleAndAactualOfWorkInfoNew(
					param.getListSid(),
					new DatePeriod(param.getStartDate(), param.getEndDate()),
					param.getDay(),
					param.getActualData,
					param.getTargetOrgIdenInforDto().convertFromDomain(),
					Optional.ofNullable(param.getPersonalCounterOp()).flatMap(x -> Optional.of(EnumAdaptor.valueOf(x, PersonalCounterCategory.class))),
					Optional.ofNullable(param.getWorkplaceCounterOp()).flatMap(x -> Optional.of(EnumAdaptor.valueOf(x, WorkplaceCounterCategory.class)))
					);
		
		return new DisplayInWorkInfoResult_New(
				listWorkTypeInfo,
				scheduleActualOfWorkOutput.getWorkScheduleWorkInforDtos(),
				scheduleActualOfWorkOutput.getAggreratePersonal(),
				scheduleActualOfWorkOutput.getAggrerateWorkplace()
				);
		
		
	}
}
