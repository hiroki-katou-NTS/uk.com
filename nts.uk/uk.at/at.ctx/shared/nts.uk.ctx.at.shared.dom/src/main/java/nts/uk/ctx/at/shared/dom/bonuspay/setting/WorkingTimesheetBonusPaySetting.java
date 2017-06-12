/**
 * 9:21:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author hungnm
 *
 */
@Getter
public class WorkingTimesheetBonusPaySetting extends AggregateRoot {

	private CompanyId companyId;

	private WorkingTimesheetCode workingTimesheetCode;

	private BonusPaySettingCode bonusPaySettingCode;

	private WorkingTimesheetBonusPaySetting(CompanyId companyId, WorkingTimesheetCode workingTimesheetCode,
			BonusPaySettingCode bonusPaySettingCode) {
		super();
		this.companyId = companyId;
		this.workingTimesheetCode = workingTimesheetCode;
		this.bonusPaySettingCode = bonusPaySettingCode;
	}

	private WorkingTimesheetBonusPaySetting() {
		super();
	}

	public static WorkingTimesheetBonusPaySetting createFromJavaType(String companyId, String workingTimesheetCode,
			String bonusPaySettingCode) {
		return new WorkingTimesheetBonusPaySetting(new CompanyId(companyId),
				new WorkingTimesheetCode(workingTimesheetCode), new BonusPaySettingCode(bonusPaySettingCode));
	}
}
