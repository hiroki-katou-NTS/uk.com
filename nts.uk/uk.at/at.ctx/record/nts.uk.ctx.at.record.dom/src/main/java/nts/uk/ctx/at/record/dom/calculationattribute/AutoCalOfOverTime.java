package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Getter;

/**
 * 
 * @author nampt
 * 残業時間の自動計算設定
 *
 */
@Getter
public class AutoCalOfOverTime {
	
	//早出残業時間
	private AutoCalculationSetting earlyOverTime;

	//早出深夜残業時間
	private AutoCalculationSetting earlyMidnightOverTime;

	//早出残業時間
	private AutoCalculationSetting normalOverTime;

	//早出残業時間
	private AutoCalculationSetting normalMidnightOverTime;

	//早出残業時間
	private AutoCalculationSetting legalOverTime;

	//早出残業時間
	private AutoCalculationSetting legalMidnightOverTime;

}
