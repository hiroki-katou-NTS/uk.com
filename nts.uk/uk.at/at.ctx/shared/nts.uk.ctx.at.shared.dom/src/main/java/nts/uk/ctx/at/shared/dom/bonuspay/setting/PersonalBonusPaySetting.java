/**
 * 9:21:27 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.EmployeeId;

/**
 * @author hungnm
 *
 */
@Getter
public class PersonalBonusPaySetting extends AggregateRoot {

	private EmployeeId employeeId;

	private BonusPaySettingCode bonusPaySettingCode;

	private PersonalBonusPaySetting(EmployeeId employeeId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.employeeId = employeeId;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private PersonalBonusPaySetting() {
		super();
	}

	public static PersonalBonusPaySetting createFromJavaType(String employeeId, String bonusPaySettingCode) {
		return new PersonalBonusPaySetting(new EmployeeId(employeeId), new BonusPaySettingCode(bonusPaySettingCode));
	}
}
