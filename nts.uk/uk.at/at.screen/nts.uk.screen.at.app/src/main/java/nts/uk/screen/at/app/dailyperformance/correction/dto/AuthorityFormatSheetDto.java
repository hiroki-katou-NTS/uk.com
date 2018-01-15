package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityFormatSheetDto {
	
	private String companyId;

	private String dailyPerformanceFormatCode;

	private BigDecimal sheetNo;

	private String sheetName;
}
