package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface PremiumItemRepository {
	
	public void update(PremiumItem premiumItem);
	
	public List<PremiumItem> findByCompanyID(String companyID);
	
	public List<PremiumItem> findByCompanyIDAndDisplayNumber(String companyID, List<Integer> displayNumbers);
	
	public List<PremiumItem> findByCompanyIDAndListPremiumNo (String companyID, List<Integer> premiumNos);
	
	public List<PremiumItem> findAllIsUse (String companyID);
}
