package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

/** １年間時間 */
@Getter
public class OneYearTimeShare {

	/** エラーアラーム時間 */
	@Setter
	private OneYearErrorAlarmTime erAlTime;
	/** 上限時間 */
	private AgreementOneYearTime upperLimit;

}
