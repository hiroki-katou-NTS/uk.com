/**
 * 9:44:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;



/**
 * @author hungnm
 *
 */
public interface BPTimeItemRepository {

	int checkInit(String companyId);

	List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId);

	List<BonusPayTimeItem> getListBonusPayTimeItemInUse(String companyId);

	List<BonusPayTimeItem> getListSpecialBonusPayTimeItem(String companyId);

	List<BonusPayTimeItem> getListSpecialBonusPayTimeItemInUse(String companyId);

	Optional<BonusPayTimeItem> getBonusPayTimeItem(String companyId, BigDecimal timeItemNo);

	Optional<BonusPayTimeItem> getSpecialBonusPayTimeItem(String companyId, BigDecimal timeItemNo);

	void addListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);

	void updateListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);
	
	List<BonusPayTimeItem> getListBonusPayTimeItemName(String companyId, List<Integer> timeItemNos);
	
	List<BonusPayTimeItem> getListSpecialBonusPayTimeItemName(String companyId, List<Integer> timeItemNos);

}
