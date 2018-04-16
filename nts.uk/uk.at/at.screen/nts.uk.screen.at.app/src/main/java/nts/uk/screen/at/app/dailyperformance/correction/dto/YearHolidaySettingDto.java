/**
 * 10:59:58 AM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class YearHolidaySettingDto {

	private String companyId;

	private boolean manageAtr;

	//private boolean permitAtr;

	// Enum
	// 0 : 先入れ先出し
	// 1 : 後入れ先出し
	private Integer priorityAtr;
}
