package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class BusinessTypeFormatDetailDto {
	
	private BigDecimal attendanceItemId;

	private BigDecimal order;

	private BigDecimal columnWidth;
}
