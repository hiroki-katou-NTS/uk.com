package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;

/**
 * 残業時間の自動計算設定
 * @author keisuke_hoshina
 *
 */
@Value
public class AutoCalculationOfOverTimeWork {
	private AutoCalcSet legalOvertimeHours;
	private AutoCalcSet legalOtTime;
	private AutoCalcSet normalOvertimeHours;
	private AutoCalcSet normalOtTime;
	private AutoCalcSet earlyOvertimeHours;
	private AutoCalcSet earlyOtTime;
}
