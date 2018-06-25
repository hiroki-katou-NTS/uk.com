package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class FormatDailyDto {
	private String code;
	private Integer attendanceItemId;
	private BigDecimal columnWidth;
	private int order;
}
