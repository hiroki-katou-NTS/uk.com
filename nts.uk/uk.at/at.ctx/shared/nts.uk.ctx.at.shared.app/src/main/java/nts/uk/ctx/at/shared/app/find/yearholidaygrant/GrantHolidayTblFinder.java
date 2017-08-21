package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Stateless
public class GrantHolidayTblFinder {
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	/**
	 * Find by ids.
	 *
	 * @return the list
	 */
	public List<GrantHolidayTblDto> findByCode(int conditionNo, String yearHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		return this.grantYearHolidayRepo.findByCode(companyId, conditionNo, yearHolidayCode).stream().map(c -> GrantHolidayTblDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
