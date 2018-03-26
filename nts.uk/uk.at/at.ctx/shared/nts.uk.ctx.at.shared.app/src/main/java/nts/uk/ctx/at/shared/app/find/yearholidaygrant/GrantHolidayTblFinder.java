package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
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
	public List<CalculateDateDto> calculateGrantDate(CalculateGrantHdTblParam param) {
		List<CalculateDateDto> result = new ArrayList<>();
		List<LengthServiceTbl> lengthServiceData = new ArrayList<>();
		
		// calculate date
		for (CalculateDateDto item : param.getGrantHolidayTblList()) {
			LengthServiceTbl lengthServiceTbl = LengthServiceTbl.createFromJavaType(item.getCompanyId(), item.getYearHolidayCode(), item.getGrantNum(), 
					item.getAllowStatus(), item.getStandGrantDay(), item.getYear(), item.getMonth());
			
			lengthServiceData.add(lengthServiceTbl);
			
			lengthServiceTbl.calculateGrantDate(param.getReferDate(), param.getSimultaneousGrantDate(), EnumAdaptor.valueOf(param.getUseSimultaneousGrant(), UseSimultaneousGrant.class));
			
			item.setGrantDate(lengthServiceTbl.getGrantDate());
			
			result.add(item);
		}
		
		LengthServiceTbl.validateInput(lengthServiceData);
		
		return result;
	}
}
