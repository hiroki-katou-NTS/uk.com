/**
 * 9:52:52 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.WorkingTimesheetBonusPaySetting;

/**
 * @author hungnm
 *
 */
public interface WTBonusPaySettingRepository {

	List<WorkingTimesheetBonusPaySetting> getListSetting(String companyId);

	void addListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting);

	void updateListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting);

	void removeListSetting(List<WorkingTimesheetBonusPaySetting> lstSetting);
}
