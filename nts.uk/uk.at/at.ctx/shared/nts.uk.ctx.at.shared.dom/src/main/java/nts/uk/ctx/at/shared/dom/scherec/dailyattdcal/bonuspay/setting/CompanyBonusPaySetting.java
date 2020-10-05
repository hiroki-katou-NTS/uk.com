/**
 * 9:20:22 AM Jun 6, 2017
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
public class CompanyBonusPaySetting extends AggregateRoot {

	private String companyId;

	private BonusPaySettingCode bonusPaySettingCode;

	private CompanyBonusPaySetting(String companyId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.companyId = companyId;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private CompanyBonusPaySetting() {
		super();
	}

	public static CompanyBonusPaySetting createFromJavaType(String companyId, String bonusPaySettingCode) {
		return new CompanyBonusPaySetting(companyId, new BonusPaySettingCode(bonusPaySettingCode));
	}
}
