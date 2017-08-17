package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearHolidayGrantFinder {
	@Inject
	private YearHolidayRepository yearHolidayRepository;
	
	// user contexts
	String companyId = AppContexts.user().companyId();
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<YearHolidayGrantDto> findAll() {
//		return this.yearHolidayRepository.findAll(companyId).stream().map(c -> YearHolidayGrantDto.fromDomain(c))
//				.collect(Collectors.toList());
		return null;
	}
}
