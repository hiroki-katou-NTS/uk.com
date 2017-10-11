package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;
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
	
	/**
	 * Calculate grant date
	 * 
	 * @return
	 */
	public List<GrantHolidayTblDto> calculateGrantDate(CalculateGrantHdTblParam param) {
		String companyId = AppContexts.user().companyId();
		
		List<GrantHdTbl> grantHolidayList = param.getGrantHolidayTblList().stream()
				.map(x -> GrantHdTbl.createFromJavaType(
						companyId, 
						x.getGrantYearHolidayNo(), 
						x.getConditionNo(), 
						x.getYearHolidayCode(), 
						x.getGrantDays(), 
						x.getLimitedTimeHdDays(), 
						x.getLimitedHalfHdCnt(), 
						x.getLengthOfServiceMonths(), 
						x.getLengthOfServiceYears(), 
						x.getGrantReferenceDate(), 
						x.getGrantSimultaneity()))
				.collect(Collectors.toList());
		
		GrantHdTbl.validateInput(grantHolidayList);
		
		List<GrantHolidayTblDto> result = new ArrayList<>();
		
		// calculate date
		for (GrantHdTbl item : grantHolidayList) {
			item.calculateGrantDate(param.getReferDate(), param.getSimultaneousGrantDate(), EnumAdaptor.valueOf(param.getUseSimultaneousGrant(), UseSimultaneousGrant.class));
			result.add(GrantHolidayTblDto.fromDomain(item));
		}
		
		return result;
	}
}
