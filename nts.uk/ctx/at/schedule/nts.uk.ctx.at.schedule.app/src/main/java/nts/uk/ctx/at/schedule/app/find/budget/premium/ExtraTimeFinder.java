package nts.uk.ctx.at.schedule.app.find.budget.premium;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.schedule.dom.budget.premium.ExtraTimeRepository;

@Stateless
@Transactional
public class ExtraTimeFinder {
	
	@Inject
	private ExtraTimeRepository extraTimeRepository;
	
	public List<ExtraTimeDto> findByCompanyID(){
		String companyID = "ABC123456789";
		return this.extraTimeRepository.findByCompanyID(companyID)
				.stream()
				.map(x -> new ExtraTimeDto(companyID, x.getExtraItemID(), x.getPremiumName().v(), x.getExtraItemID(), x.getUseClassification().value))
				.collect(Collectors.toList());
	}
}
