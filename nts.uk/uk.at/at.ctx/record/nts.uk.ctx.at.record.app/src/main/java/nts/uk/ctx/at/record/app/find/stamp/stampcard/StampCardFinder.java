package nts.uk.ctx.at.record.app.find.stamp.stampcard;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampCardFinder {
	@Inject
	private StampCardRepository stampCardRepo;

	public List<StampCardDto> findByPersonID(String personId) {
		String companyId = AppContexts.user().companyId();
		List<StampCardDto> lstCardNumber = stampCardRepo.findByPersonID(companyId, personId)
				.stream()
				.map(item -> StampCardDto.fromDomain(item))
				.collect(Collectors.toList());;
		return lstCardNumber;
	}
}
