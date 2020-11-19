package nts.uk.screen.at.app.query.kmk004.b;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
// 労働時間
class LaborTime {

	// 法定労働時間
	public Integer legalLaborTime;
	// 所定労働時間
	public Integer withinLaborTime;
	// 週平均時間
	public Integer weekAvgTime;

}
