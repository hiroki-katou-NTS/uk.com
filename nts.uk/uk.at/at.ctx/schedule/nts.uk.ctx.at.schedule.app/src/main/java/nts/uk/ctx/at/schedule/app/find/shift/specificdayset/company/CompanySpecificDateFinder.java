package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanySpecificDateFinder {
	@Inject
	private CompanySpecificDateRepository companySpecDateRepo;

	public List<CompanySpecificDateDto> getComSpecByDate(int comSpecDate) {
		String companyId = AppContexts.user().companyId();
		return companySpecDateRepo.getComSpecByDate(companyId, comSpecDate)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
