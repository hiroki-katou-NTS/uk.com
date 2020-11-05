/**
 * 9:52:10 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.SpecBonusPayTimesheet;



/**
 * @author hungnm
 *
 */
public interface SpecBPTimesheetRepository {

	List<SpecBonusPayTimesheet> getListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode);

	void addListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<SpecBonusPayTimesheet> lstTimesheet);

	void updateListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<SpecBonusPayTimesheet> lstTimesheet);

	void removeListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<SpecBonusPayTimesheet> lstTimesheet);
}
