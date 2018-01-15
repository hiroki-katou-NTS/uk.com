package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import java.util.List;

import lombok.Value;

@Value
public class DailyAttendanceItemAuthorityDto {
	
	private List<AttendanceItemDto> attendanceItemDtos;
	
	private List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityMonthlyDto;
	
	private DailyAttendanceAuthorityDailyDto dailyAttendanceAuthorityDailyDto;
	
	private int isDefaultInitial;

}
