package nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPerformanceAuthorityDto {
	/**
	 * ロールID
	 */
	// タイプID
	private String roleID;

	/**
	 * 日別実績の機能NO
	 */
	private BigDecimal functionNo;

	/**
	 * 利用区分
	 */
	private boolean availability;
}
