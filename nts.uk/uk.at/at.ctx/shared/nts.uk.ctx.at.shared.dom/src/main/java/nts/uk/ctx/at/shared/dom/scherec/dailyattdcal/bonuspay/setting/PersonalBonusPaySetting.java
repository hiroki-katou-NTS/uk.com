/**
 * 9:21:27 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;

/**
 * @author hungnm
 *
 */
@Getter
public class PersonalBonusPaySetting extends AggregateRoot {

	private String employeeId;

	private BonusPaySettingCode bonusPaySettingCode;

	private PersonalBonusPaySetting(String employeeId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.employeeId = employeeId;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private PersonalBonusPaySetting() {
		super();
	}

	public static PersonalBonusPaySetting createFromJavaType(String employeeId, String bonusPaySettingCode) {
		return new PersonalBonusPaySetting(employeeId, new BonusPaySettingCode(bonusPaySettingCode));
	}
}
