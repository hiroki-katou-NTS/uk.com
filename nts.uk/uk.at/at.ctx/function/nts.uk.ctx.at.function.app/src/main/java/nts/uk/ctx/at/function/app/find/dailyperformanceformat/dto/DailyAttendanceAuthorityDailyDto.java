package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;

@Value
public class DailyAttendanceAuthorityDailyDto {
	
	private BigDecimal sheetNo;
	
	private String sheetName;
	
	private List<DailyAttendanceAuthorityDetailDto> dailyAttendanceAuthorityDetailDtos;

}
