/**
 * 9:20:41 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * @author hungnm
 *
 */
@Getter
public class WorkplaceBonusPaySetting extends AggregateRoot {

	private WorkplaceId workplaceId;

	private BonusPaySettingCode bonusPaySettingCode;

	private WorkplaceBonusPaySetting(WorkplaceId workplaceId, BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.workplaceId = workplaceId;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private WorkplaceBonusPaySetting() {
		super();
	}

	public static WorkplaceBonusPaySetting createFromJavaType(String workplaceId, String bonusPaySettingCode) {
		return new WorkplaceBonusPaySetting(new WorkplaceId(workplaceId), new BonusPaySettingCode(bonusPaySettingCode));
	}
}
