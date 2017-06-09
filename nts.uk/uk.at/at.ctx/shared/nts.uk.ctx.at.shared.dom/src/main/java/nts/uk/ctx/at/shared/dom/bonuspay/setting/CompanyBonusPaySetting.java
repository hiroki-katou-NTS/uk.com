/**
 * 9:20:22 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class CompanyBonusPaySetting extends AggregateRoot {

	private CompanyId companyId;

	private BonusPaySettingCode bonusPaySettingCode;

	private CompanyBonusPaySetting(CompanyId companyId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.companyId = companyId;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private CompanyBonusPaySetting() {
		super();
	}

	public static CompanyBonusPaySetting createFromJavaType(String companyId, String bonusPaySettingCode) {
		return new CompanyBonusPaySetting(new CompanyId(companyId), new BonusPaySettingCode(bonusPaySettingCode));
	}
}
