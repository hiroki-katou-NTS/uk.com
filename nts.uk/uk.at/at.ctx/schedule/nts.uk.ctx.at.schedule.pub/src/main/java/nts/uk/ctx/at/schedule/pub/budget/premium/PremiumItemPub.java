package nts.uk.ctx.at.schedule.pub.budget.premium;

import java.util.List;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface PremiumItemPub {

	public List<PremiumItemDto> findByCompanyID(String companyID);
	
	public List<PremiumItemDto> findByCompanyIDAndDisplayNumber(String companyID, List<Integer> displayNumbers);
	
}
