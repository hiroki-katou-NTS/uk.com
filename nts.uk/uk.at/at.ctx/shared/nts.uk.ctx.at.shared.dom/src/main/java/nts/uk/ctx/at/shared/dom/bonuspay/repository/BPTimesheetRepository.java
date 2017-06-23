/**
 * 9:49:57 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;

/**
 * @author hungnm
 *
 */
public interface BPTimesheetRepository {

	List<BonusPayTimesheet> getListTimesheet(String companyId, String bonusPaySettingCode);

	void addListTimesheet(String companyId, String bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);

	void updateListTimesheet(String companyId, String bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);

	void removeListTimesheet(String companyId, String bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);

}
