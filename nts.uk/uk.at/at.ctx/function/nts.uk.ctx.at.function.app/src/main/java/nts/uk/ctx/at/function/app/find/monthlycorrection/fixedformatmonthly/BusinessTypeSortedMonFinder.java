package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMon;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BusinessTypeSortedMonFinder {

	@Inject
	private BusinessTypeSortedMonRepository repo;
	
	public BusinessTypeSortedMonDto getBusinessTypeSortedMon() {
		String companyID = AppContexts.user().companyId();
		Optional<BusinessTypeSortedMon> data = repo.getOrderReferWorkType(companyID);
		if(data.isPresent())
			return BusinessTypeSortedMonDto.fromDomain(data.get());
		return null;
	}
	
}
