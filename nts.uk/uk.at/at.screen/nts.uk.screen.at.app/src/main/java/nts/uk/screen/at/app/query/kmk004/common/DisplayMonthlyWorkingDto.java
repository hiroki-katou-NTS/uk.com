package nts.uk.screen.at.app.query.kmk004.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayMonthlyWorkingDto {

	// 年月
	private int yearMonth;
	
	// 労働時間
	private LaborTime laborTime;
}
