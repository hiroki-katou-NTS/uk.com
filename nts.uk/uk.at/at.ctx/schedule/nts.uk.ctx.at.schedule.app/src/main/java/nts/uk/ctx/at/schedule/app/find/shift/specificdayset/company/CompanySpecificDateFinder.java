package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanySpecificDateFinder {
	@Inject
	private CompanySpecificDateRepository companySpecDateRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	// NO with name
	public List<CompanySpecificDateDto> getComSpecByDate(String comSpecDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.fromString(comSpecDate, DATE_FORMAT);
		return companySpecDateRepo.getComSpecByDate(companyId, date)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	// WITH name
	public List<CompanySpecificDateDto> getComSpecByDateWithName(String comSpecDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = GeneralDate.fromString(comSpecDate, DATE_FORMAT);
		GeneralDate endDate = startDate.addMonths(1).addDays(-1);
		return companySpecDateRepo.getComSpecByDateWithName(companyId, startDate, endDate)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
