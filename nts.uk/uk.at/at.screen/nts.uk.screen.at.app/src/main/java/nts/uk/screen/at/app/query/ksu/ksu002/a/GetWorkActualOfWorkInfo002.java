package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.displayinworkinformation.DisplayInWorkInfoParam;
import nts.uk.screen.at.app.ksu001.scheduleactualworkinfo.GetWorkActualOfWorkInfo;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.EditStateOfDailyAttdDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.DisplayInWorkInfoInput;
import nts.uk.screen.at.app.query.ksu.ksu002.a.input.GetDateInfoDuringThePeriodInput;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * @author chungnt
 * ScreenQuery : 勤務実績（勤務情報）を取得する(KSU002)
 */
@Stateless
public class GetWorkActualOfWorkInfo002 {

	@Inject
	private GetWorkActualOfWorkInfo getWorkActualOfWorkInfo;
	@Inject
	private GetDateInfoDuringThePeriod getDateInfoDuringThePeriod;


	public List<WorkScheduleWorkInforDto> getDataActualOfWorkInfo(DisplayInWorkInfoInput param) {
		
		List<WorkScheduleWorkInforDto> result = new ArrayList<>();

		DisplayInWorkInfoParam input = new DisplayInWorkInfoParam(param.listSid, param.startDate, param.endDate, param.getActualData());
		
		List<nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto> inforDtos = this.getWorkActualOfWorkInfo.getDataActualOfWorkInfo(input);
		
		result = inforDtos
				.stream()
				.map(m -> {
					List<String> sids = new ArrayList<>();
					sids.add(AppContexts.user().employeeId());
					GetDateInfoDuringThePeriodInput param1 = new GetDateInfoDuringThePeriodInput();
					param1.setGeneralDate(m.getDate());
					param1.setSids(sids);
					
					EditStateOfDailyAttdDto workTypeEditStatus = null;
					if (m.workTypeEditStatus != null) {
						workTypeEditStatus = new EditStateOfDailyAttdDto(m.workTypeEditStatus.getAttendanceItemId(), m.workTypeEditStatus.getEditStateSetting());
					}
					
					EditStateOfDailyAttdDto workTimeEditStatus = null;
					if (m.workTimeEditStatus != null) {
						workTimeEditStatus = new EditStateOfDailyAttdDto(m.workTimeEditStatus.getAttendanceItemId(), m.workTimeEditStatus.getEditStateSetting());
					}
					
					EditStateOfDailyAttdDto startTimeEditState = null;
					if (m.startTimeEditState != null) {
						startTimeEditState = new EditStateOfDailyAttdDto(m.startTimeEditState.getAttendanceItemId(), m.startTimeEditState.getEditStateSetting());
					}
					
					EditStateOfDailyAttdDto endTimeEditState = null;
					if (m.endTimeEditState != null) {
						endTimeEditState = new EditStateOfDailyAttdDto(m.endTimeEditState.getAttendanceItemId(), m.endTimeEditState.getEditStateSetting());
					}
					
					WorkScheduleWorkInforDto dto = WorkScheduleWorkInforDto.builder()
							.employeeId(m.getEmployeeId())
							.date(m.getDate())
							.haveData(m.haveData)
							.achievements(null)
							.confirmed(m.confirmed)
							.needToWork(m.needToWork)
							.supportCategory(m.supportCategory)
							.workTypeCode(m.workTypeCode)
							.workTypeName(m.workTypeName)
							.workTypeEditStatus(workTypeEditStatus)
							.workTimeCode(m.getWorkTimeCode())
							.workTimeName(m.getWorkTimeName())
							.workTimeEditStatus(workTimeEditStatus)
							.startTime(m.startTime)
							.startTimeEditState(startTimeEditState)
							.endTime(m.endTime)
							.endTimeEditState(endTimeEditState)
							.workHolidayCls(m.workHolidayCls)
							.dateInfoDuringThePeriod(this.getDateInfoDuringThePeriod.get(param1))
							.build();
					return dto;
				}).collect(Collectors.toList());
		
		return result;
	}
}
