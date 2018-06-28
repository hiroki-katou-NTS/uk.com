package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityFomatMonthlyDto {

	private String companyId;

	private String dailyPerformanceFormatCode;

	private int attendanceItemId;

	private int displayOrder;

	private BigDecimal columnWidth;
}
