package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Stateless
public class LengthOfServiceTblFinder {
	@Inject
	private LengthServiceRepository lengthServiceRepository;
	
	public List<LengthOfServiceTblDto> findByCode(String yearHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		return this.lengthServiceRepository.findByCode(companyId, yearHolidayCode).stream().map(c -> LengthOfServiceTblDto.fromDomain(c))
				.collect(Collectors.toList());
	}
}
