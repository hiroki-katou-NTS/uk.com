package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class DailyAttendanceAuthorityDetailDto {

	private int attendanceItemId;

	private int order;

	private BigDecimal columnWidth;

}
