package nts.uk.ctx.at.schedule.pubimp.budget.premium;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumItemRepository;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemDto;
import nts.uk.ctx.at.schedule.pub.budget.premium.PremiumItemPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class PremiumItemPubImpl implements PremiumItemPub {

	@Inject
	private PremiumItemRepository premiumItemRepository;

	@Override
	public List<PremiumItemDto> findByCompanyID(String companyID) {
		return premiumItemRepository.findByCompanyID(companyID).stream()
				.map(x -> new PremiumItemDto(
						x.getCompanyID(),
						x.getDisplayNumber(),
						x.getName().v(),
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}

	@Override
	public List<PremiumItemDto> findByCompanyIDAndDisplayNumber(String companyID, List<Integer> displayNumbers) {
		return premiumItemRepository.findByCompanyIDAndDisplayNumber(companyID, displayNumbers).stream()
				.map(x -> new PremiumItemDto(
						x.getCompanyID(),
						x.getDisplayNumber(),
						x.getName().v(),
						x.getUseAtr().value))
				.collect(Collectors.toList());
	}
}
