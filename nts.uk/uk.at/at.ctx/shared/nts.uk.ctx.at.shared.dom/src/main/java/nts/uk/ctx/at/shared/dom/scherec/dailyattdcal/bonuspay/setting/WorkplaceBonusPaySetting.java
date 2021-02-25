/**
 * 9:20:41 AM Jun 6, 2017
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
public class WorkplaceBonusPaySetting extends AggregateRoot {
	
	private String companyId;

	private String workplaceId;

	private BonusPaySettingCode bonusPaySettingCode;

	private WorkplaceBonusPaySetting(String companyId, String workplaceId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.workplaceId = workplaceId;
		this.bonusPaySettingCode = bonusPaySettingCode;
		this.companyId = companyId;
	}

	private WorkplaceBonusPaySetting() {
		super();
	}

	public static WorkplaceBonusPaySetting createFromJavaType(String companyId, String workplaceId, String bonusPaySettingCode) {
		return new WorkplaceBonusPaySetting(companyId, workplaceId, new BonusPaySettingCode(bonusPaySettingCode));
	}
}
