package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import java.util.List;

import lombok.Value;

@Value
public class BusinessTypeDetailDto {
	
	private List<AttendanceItemDto> attendanceItemDtos;

	private BusinessTypeFormatDailyDto businessTypeFormatDailyDto;

	private List<BusinessTypeFormatDetailDto> businessTypeFormatMonthlyDtos;
}
