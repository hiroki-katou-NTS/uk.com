package nts.uk.query.pubimp.ccg005.work.information;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.query.app.ccg005.query.work.information.EmployeeWorkInformationDto;
import nts.uk.query.app.ccg005.query.work.information.WorkInformationQuery;
import nts.uk.query.pub.ccg005.work.information.EmployeeWorkInformationExport;
import nts.uk.query.pub.ccg005.work.information.WorkInformationQueryPub;
import nts.uk.query.pub.ccg005.work.information.dto.DailyWorkDto;
import nts.uk.query.pub.ccg005.work.information.dto.EmployeeDailyPerErrorDto;
import nts.uk.query.pub.ccg005.work.information.dto.TimeLeavingOfDailyPerformanceDto;
import nts.uk.query.pub.ccg005.work.information.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.query.pub.ccg005.work.information.dto.WorkScheduleDto;
import nts.uk.query.pub.ccg005.work.information.dto.WorkTypeDto;

@Stateless
public class WorkInformationQueryPubImpl implements WorkInformationQueryPub {

	private WorkInformationQuery query;

	@Override
	public List<EmployeeWorkInformationExport> getWorkInformationQuery(List<String> sids, GeneralDate baseDate) {
		List<EmployeeWorkInformationDto> workInformation = query.getWorkInformationQuery(sids, baseDate);
		return workInformation.stream().map(item -> {

			WorkScheduleDto workScheduleDto = WorkScheduleDto.builder().sid(item.getWorkScheduleDto().getSid())
					.ymd(item.getWorkScheduleDto().getYmd())
					.backStraightAtr(item.getWorkScheduleDto().getBackStraightAtr())
					.attendanceTime(item.getWorkScheduleDto().getAttendanceTime())
					.leaveTime(item.getWorkScheduleDto().getLeaveTime()).build();

			WorkTypeDto workTypeDto = WorkTypeDto.builder()
					.dailyWork(
							DailyWorkDto.builder().workTypeUnit(item.getWorkTypeDto().getDailyWork().getWorkTypeUnit())
									.oneDay(item.getWorkTypeDto().getDailyWork().getOneDay())
									.morning(item.getWorkTypeDto().getDailyWork().getMorning())
									.afternoon(item.getWorkTypeDto().getDailyWork().getAfternoon()).build())
					.code(item.getWorkTypeDto().getCode()).displayName(item.getWorkTypeDto().getDisplayName()).build();

			TimeLeavingOfDailyPerformanceDto timeLeavingOfDailyPerformanceDto = TimeLeavingOfDailyPerformanceDto
					.builder().ymd(item.getTimeLeavingOfDailyPerformanceDto().getYmd())
					.sid(item.getTimeLeavingOfDailyPerformanceDto().getSid())
					.attendanceTime(item.getTimeLeavingOfDailyPerformanceDto().getAttendanceTime())
					.leaveTime(item.getTimeLeavingOfDailyPerformanceDto().getLeaveTime()).build();

			WorkInfoOfDailyPerformanceDto workPerformanceDto = WorkInfoOfDailyPerformanceDto.builder()
					.sid(item.getWorkPerformanceDto().getSid()).ymd(item.getWorkPerformanceDto().getYmd())
					.goStraightAtr(item.getWorkPerformanceDto().getGoStraightAtr())
					.backStraightAtr(item.getWorkPerformanceDto().getBackStraightAtr()).build();

			List<EmployeeDailyPerErrorDto> employeeDailyPerErrorDtos = item.getEmployeeDailyPerErrorDtos().stream()
					.map(mapper -> {
						return EmployeeDailyPerErrorDto.builder()
								.sid(mapper.getSid())
								.ymd(mapper.getYmd())
								.errorAlarmWorkRecordCode(mapper.getErrorAlarmWorkRecordCode())
								.build();
					}).collect(Collectors.toList());

			return EmployeeWorkInformationExport.builder()
					.sid(item.getSid())
					.workScheduleDto(workScheduleDto)
					.workTypeDto(workTypeDto)
					.timeLeavingOfDailyPerformanceDto(timeLeavingOfDailyPerformanceDto)
					.employeeDailyPerErrorDtos(employeeDailyPerErrorDtos)
					.workPerformanceDto(workPerformanceDto)
					.build();
		}).collect(Collectors.toList());
	}
}
