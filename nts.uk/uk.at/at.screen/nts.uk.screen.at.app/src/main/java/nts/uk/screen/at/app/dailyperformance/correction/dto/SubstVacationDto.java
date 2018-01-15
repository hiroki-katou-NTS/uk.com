/**
 * 11:26:05 AM Aug 22, 2017
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
public class SubstVacationDto {

	private String companyId;

	private boolean manageAtr;

	// 休暇使用期限
	// 0: 当月
	// 1: 常に繰越
	// 2: 年度末クリア
	// 3: 1ヶ月
	// 4: 2ヶ月
	// 5: 3ヶ月
	// 6: 4ヶ月
	// 7: 5ヶ月
	// 8: 6ヶ月
	// 9: 7ヶ月
	// 10: 8ヶ月
	// 11: 9ヶ月
	// 12: 10ヶ月
	// 13: 11ヶ月
	// 14: 1年
	// 15: 2年
	// 16: 3年
	// 17: 4年
	// 18: 5年
	private Integer expirationDataSet;
	
	private boolean allowPrepaidLeave;

}
