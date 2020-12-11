package nts.uk.screen.at.app.query.kmk004.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LaborTime {

	// 法定労働時間
	public Integer legalLaborTime;
	// 所定労働時間
	public Integer withinLaborTime;
	// 週平均時間
	public Integer weekAvgTime;

}
