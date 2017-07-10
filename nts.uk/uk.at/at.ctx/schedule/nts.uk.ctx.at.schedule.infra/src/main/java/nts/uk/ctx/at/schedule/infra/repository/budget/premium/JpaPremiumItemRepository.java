package nts.uk.ctx.at.schedule.infra.repository.budget.premium;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItem;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseAttribute;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmnmpPremiumItemPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmnmtPremiumItem;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaPremiumItemRepository extends JpaRepository implements PremiumItemRepository{

	private final String findAll = "SELECT a FROM KmnmtPremiumItem a WHERE a.kmnmpPremiumItemPK.companyID = :CID";
	
	@Override
	public void update(PremiumItem premiumItem) {
		KmnmtPremiumItem item = this.queryProxy().find(new KmnmpPremiumItemPK(premiumItem.getCompanyID(), premiumItem.getID()), KmnmtPremiumItem.class).get();
		if(premiumItem.getUseAtr().equals(UseAttribute.Use)){
			item.setUseAtr(premiumItem.getUseAtr().value);
			item.setName(premiumItem.getName().v());
		} else {
			item.setUseAtr(premiumItem.getUseAtr().value);
		}
		this.commandProxy().update(item);
	}
	
	@Override
	public List<PremiumItem> findByCompanyID(String companyID) {
		return this.queryProxy().query(findAll, KmnmtPremiumItem.class).setParameter("CID", companyID)
				.getList(x -> convertToDomain(x));
	}
	
	/**
	 * convert PremiumItem Entity Object to PremiumItem Domain Object
	 * @param kmnmtPremiumItem PremiumItem Entity Object
	 * @return PremiumItem Domain Object
	 */ 
	private PremiumItem convertToDomain(KmnmtPremiumItem kmnmtPremiumItem){
		return new PremiumItem(
				kmnmtPremiumItem.kmnmpPremiumItemPK.companyID, 
				kmnmtPremiumItem.kmnmpPremiumItemPK.iD,
				kmnmtPremiumItem.attendanceID,
				new PremiumName(kmnmtPremiumItem.name), 
				kmnmtPremiumItem.displayNumber, 
				EnumAdaptor.valueOf(kmnmtPremiumItem.useAtr, UseAttribute.class));
	}
}
