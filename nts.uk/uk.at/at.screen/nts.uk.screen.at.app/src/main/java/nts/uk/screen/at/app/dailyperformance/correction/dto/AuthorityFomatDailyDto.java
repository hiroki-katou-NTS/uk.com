package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityFomatDailyDto {
	
	private String companyId;

	private String dailyPerformanceFormatCode;

	private int attendanceItemId;

	private BigDecimal sheetNo;

	private int displayOrder;

	private BigDecimal columnWidth;

}
