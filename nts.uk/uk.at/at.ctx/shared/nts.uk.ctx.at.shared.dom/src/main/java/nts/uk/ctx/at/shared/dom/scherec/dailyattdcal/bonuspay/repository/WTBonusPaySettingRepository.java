/**
 * 9:52:52 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkingTimesheetBonusPaySetting;



/**
 * @author hungnm
 *
 */
public interface WTBonusPaySettingRepository {

	List<WorkingTimesheetBonusPaySetting> getListSetting(String companyId);

	void addWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting);

	void updateWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting);

	void removeWTBPSetting(WorkingTimesheetBonusPaySetting workingTimesheetBonusPaySetting);

	Optional<WorkingTimesheetBonusPaySetting> getWTBPSetting(String companyId, WorkingTimesheetCode workingTimesheetCode);
}
