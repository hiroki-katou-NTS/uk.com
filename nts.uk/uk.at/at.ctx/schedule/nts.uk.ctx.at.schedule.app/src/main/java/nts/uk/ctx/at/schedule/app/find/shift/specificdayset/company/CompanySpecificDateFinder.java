package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CompanySpecificDateFinder {
	@Inject
	private CompanySpecificDateRepository companySpecDateRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	// NO with name
	public Optional<CompanySpecificDateDto> getComSpecByDate(String comSpecDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate date = GeneralDate.fromString(comSpecDate, DATE_FORMAT);
		return this.companySpecDateRepo.get(companyId, date)
				.map(t -> Optional.of(CompanySpecificDateDto.fromDomain(t)))
				.orElse(Optional.empty());
	}

	// WITH name
	public List<CompanySpecificDateDto> getComSpecByDateWithName(String comSpecDate) {
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = GeneralDate.fromString(comSpecDate, DATE_FORMAT);
		GeneralDate endDate = startDate.addMonths(1).addDays(-1);
		DatePeriod period = new DatePeriod(startDate, endDate);
		return this.companySpecDateRepo.getList(companyId, period)
				.stream()
				.map(item -> CompanySpecificDateDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
