/**
 * 9:49:57 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPayTimesheet;



/**
 * @author hungnm
 *
 */
public interface BPTimesheetRepository {

	List<BonusPayTimesheet> getListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode);

	void addListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);

	void updateListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);

	void removeListTimesheet(String companyId, BonusPaySettingCode bonusPaySettingCode, List<BonusPayTimesheet> lstTimesheet);
}
