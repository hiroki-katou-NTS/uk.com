package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
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
	
	/**
	 * Find by ids.
	 *
	 * @return the list
	 */
	public Optional<List<LengthOfServiceTblDto>> findByCode(String yearHolidayCode) {
		// user contexts
		String companyId = AppContexts.user().companyId();
		if( this.lengthServiceRepository.findByCode(companyId, yearHolidayCode).isPresent()) {
			List<LengthOfService> lengthOfService = this.lengthServiceRepository.findByCode(companyId, yearHolidayCode).orElse(null).getLengthOfService();
			return Optional.of(lengthOfService.stream().map(c->LengthOfServiceTblDto.fromDomain(companyId, yearHolidayCode, c)).collect(Collectors.toList()));
		}else {
			return Optional.empty();
		}




	}
	
	/**
	 * Calculate grant date
	 * 
	 * @return
	 */
	public List<GrantHolidayTblDto> calculateGrantDate(CalculateGrantHdTblParam param) {
		List<GrantHdTbl> grantHolidayList = param.getGrantHolidayTblList().stream()
				.map(x -> GrantHdTbl.createFromJavaType(x.getCompanyId(),
														x.getConditionNo(), x.getYearHolidayCode(), 
														x.getGrantNum(), x.getGrantDays(), 
														x.getLimitTimeHd(), x.getLimitDayYear()))
				.collect(Collectors.toList());
		
		List<GrantHolidayTblDto> result = new ArrayList<>();
		
		// calculate date
		for (GrantHdTbl item : grantHolidayList) {
			//item.calculateGrantDate(param.getReferDate(), param.getSimultaneousGrantDate(), EnumAdaptor.valueOf(param.getUseSimultaneousGrant(), UseSimultaneousGrant.class));
			result.add(GrantHolidayTblDto.fromDomain(item));
		}
		
		return result;
	}
}
