package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class WorkTypeFormatDailyDto {
	
	private String attendanceItemId;

	private String sheetName;

	private BigDecimal sheetNo;

	private BigDecimal order;

	private BigDecimal columnWidth;

}
