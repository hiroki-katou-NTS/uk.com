/**
 * 9:44:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;

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

	Optional<BonusPayTimeItem> getBonusPayTimeItem(String companyId, TimeItemId timeItemId);

	Optional<BonusPayTimeItem> getSpecialBonusPayTimeItem(String companyId, TimeItemId timeItemId);

	void addListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);

	void updateListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);

}
