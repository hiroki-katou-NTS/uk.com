package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * A - Screen
 * @author TanLV
 *
 */
@Stateless
public class YearHolidayGrantFinder {
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<YearHolidayGrantDto> findAll() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		RequireImpl require= new RequireImpl(grantYearHolidayRepo);
		
		return this.yearHolidayRepo.findAll(companyId).stream().map(c -> YearHolidayGrantDto.fromDomain(c, require))
				.collect(Collectors.toList());
	}
	
	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public YearHolidayGrantDto findByCode(String yearHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		RequireImpl require= new RequireImpl(grantYearHolidayRepo);

		Optional<GrantHdTblSet> data = this.yearHolidayRepo.findByCode(companyId, yearHolidayCode);
		
		if(data.isPresent()){
			return YearHolidayGrantDto.fromDomain(data.get(), require);
		}
		
		return null;
	}
	
	@AllArgsConstructor
	public class RequireImpl implements YearHolidayGrantDto.Require{
		
		private GrantYearHolidayRepository grantYearHolidayRepo;
		
		@Override
		public List<GrantHdTbl> findByCode(String companyId, int conditionNo, String yearHolidayCode){
			return grantYearHolidayRepo.findByCode(companyId, conditionNo, yearHolidayCode);
		}
	}
}

