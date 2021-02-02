package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** 集計用の３６協定基本設定 */
@Getter
@Setter
@AllArgsConstructor
public class BasicAgreementSettingForCalc {

	/** 36協定基本設定: ３６協定基本設定 */
	private BasicAgreementSetting basicSetting;
	
	/** 社員の上限設定あるか: boolean */
	private boolean haveEmployeeSetting;
}
