/**
 * 9:52:10 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;

/**
 * @author hungnm
 *
 */
public interface SpecBPTimesheetRepository {

	List<SpecBonusPayTimesheet> getListTimesheet(String companyId, String bonusPaySettingCode);

	void addListTimesheet(List<SpecBonusPayTimesheet> lstTimesheet);

	void updateListTimesheet(List<SpecBonusPayTimesheet> lstTimesheet);

	void removeListTimesheet(List<SpecBonusPayTimesheet> lstTimesheet);
}
