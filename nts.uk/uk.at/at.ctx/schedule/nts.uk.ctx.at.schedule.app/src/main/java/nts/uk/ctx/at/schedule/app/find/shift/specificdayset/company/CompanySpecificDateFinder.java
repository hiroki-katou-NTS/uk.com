package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanySpecificDateFinder {
	@Inject
	private CompanySpecificDateRepository companySpecDateRepo;

	public CompanySpecificDateDto getComSpecByDate(int comSpecDate) {
		String companyId = AppContexts.user().companyId();
		return companySpecDateRepo.getComSpecByDate(companyId, comSpecDate)
				.map(c -> toCompanySpecificDateDto(c)).get();
	}

	public CompanySpecificDateDto toCompanySpecificDateDto(CompanySpecificDateItem comSpecDateItem) {
		return new CompanySpecificDateDto(comSpecDateItem.getSpecificDate().v(), comSpecDateItem.getSpecificDate().v());

	}
}
