package nts.uk.screen.at.app.ksus01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforOnTargetPeriodInput {
	
	
	/**
	 * 勤務希望期間
	 */
	private PeriodCommand desiredPeriodWork;
	
	/**
	 * 勤務予定期間
	 */
	private PeriodCommand scheduledWorkingPeriod;
	
	/**
	 * 対象期間
	 */
	private PeriodCommand targetPeriod;
}
