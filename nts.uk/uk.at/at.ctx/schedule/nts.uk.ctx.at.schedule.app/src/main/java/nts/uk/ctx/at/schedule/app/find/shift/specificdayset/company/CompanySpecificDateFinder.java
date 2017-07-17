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

	// NO with name
	public List<CompanySpecificDateDto> getComSpecByDate(int comSpecDate) {
		String companyId = AppContexts.user().companyId();
		return companySpecDateRepo.getComSpecByDate(companyId, comSpecDate)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	// WITH name
	public List<CompanySpecificDateDto> getComSpecByDateWithName(String comSpecDate, int useAtr) {
		String companyId = AppContexts.user().companyId();
		return companySpecDateRepo.getComSpecByDateWithName(companyId, comSpecDate, useAtr)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
