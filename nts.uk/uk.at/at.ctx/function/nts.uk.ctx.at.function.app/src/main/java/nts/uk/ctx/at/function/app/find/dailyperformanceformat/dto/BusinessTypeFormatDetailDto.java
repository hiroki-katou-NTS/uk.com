package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class BusinessTypeFormatDetailDto {
	
	private int attendanceItemId;
	
/*	private int dislayNumber;
	
	private String attendanceItemName;
*/
	private int order;

	private BigDecimal columnWidth;
}
