package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class PremiumItemFinder {
	
	@Inject
	private PremiumItemRepository premiumItemRepository;
	
	public List<PremiumItemDto> findByCompanyID(){
		String companyID = AppContexts.user().companyId();
		return this.premiumItemRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> new PremiumItemDto(
						companyID, 
						x.getID(),
						x.getAttendanceID(),
						x.getName().v(), 
						x.getDisplayNumber(), 
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}
}
