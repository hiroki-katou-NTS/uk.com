/**
 * 9:44:12 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;

/**
 * @author hungnm
 *
 */
public interface BPTimeItemRepository {

	List<BonusPayTimeItem> getListBonusPayTimeItem();

	List<BonusPayTimeItem> getListSpecialBonusPayTimeItem();

	void addListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);

	void updateListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem);

}
