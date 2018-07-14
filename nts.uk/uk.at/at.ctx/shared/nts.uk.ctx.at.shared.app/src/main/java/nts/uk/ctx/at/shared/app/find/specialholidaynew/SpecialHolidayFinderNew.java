package nts.uk.ctx.at.shared.app.find.specialholidaynew;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class SpecialHolidayFinderNew {
	@Inject
	private SpecialHolidayRepository sphdRepo;
	
	/**
	 * Get List Special Holiday by companyId
	 * @return
	 */
	public List<SpecialHolidayDtoNew> findByCompanyId() {
		String companyId = AppContexts.user().companyId();
		
		return this.sphdRepo.findByCompanyId(companyId).stream().map(c -> SpecialHolidayDtoNew.fromDomain(c))
				.collect(Collectors.toList());
	}
	
	/**
	 * Get Special Holiday by sphdCd
	 * @param specialHolidayCode
	 * @return
	 */
	public SpecialHolidayDtoNew getSpecialHoliday(int specialHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();

		Optional<SpecialHoliday> data = sphdRepo.findByCode(companyId, specialHolidayCode);
		
		if(data.isPresent()){
			return SpecialHolidayDtoNew.fromDomain(data.get());
		}
		
		return null;
	}
}
