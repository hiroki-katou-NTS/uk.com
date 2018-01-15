/**
 * 11:32:54 AM Aug 22, 2017
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
public class Com60HVacationDto {
	
	private String companyId;

	private boolean manageAtr;

	// Enum
	// 0:常に繰越
	// 1: 1ヶ月
	// 2: 2ヶ月
	// 3: 3ヶ月
	// 4: 4ヶ月
	// 5: 5ヶ月
	// 6: 6ヶ月
	// 7: 7ヶ月
	// 8: 8ヶ月
	// 9: 9ヶ月
	// 10: 10ヶ月
	// 11: 11ヶ月
	// 12: 12ヶ月
	private Integer sixtyHourExtra;

	// Enum
	// 0:1分
	// 1:15分
	// 2:30分
	// 3:1時間
	// 4:2時間
	private Integer digestiveUnit;
}
