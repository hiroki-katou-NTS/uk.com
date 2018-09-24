package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;

@Value
public class BusinessTypeFormatDailyDto {

	// private List<AttendanceItemDto> attendanceItemDtos;

	private BigDecimal sheetNo;

	private String sheetName;

	private List<BusinessTypeFormatDetailDto> businessTypeFormatDetailDtos;

}
