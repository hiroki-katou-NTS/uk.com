/**
 * 9:21:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;

/**
 * @author hungnm
 *
 */
@Getter
public class WorkingTimesheetBonusPaySetting extends AggregateRoot {

	private String companyId;

	private WorkingTimesheetCode workingTimesheetCode;

	private BonusPaySettingCode bonusPaySettingCode;

	private WorkingTimesheetBonusPaySetting(String companyId, WorkingTimesheetCode workingTimesheetCode,
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
		return new WorkingTimesheetBonusPaySetting(companyId,
				new WorkingTimesheetCode(workingTimesheetCode), new BonusPaySettingCode(bonusPaySettingCode));
	}
}
