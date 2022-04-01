package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
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

		List<LengthOfService> lengthOfServices = param.getGrantHolidayTblList().stream().map(c ->
																			LengthOfService.createFromJavaType(c.getGrantNum(),
																													c.getAllowStatus(),
																													c.getStandGrantDay(),
																													c.getYear(),
																													c.getMonth())
		).collect(Collectors.toList());
		LengthServiceTbl.validateInput(lengthOfServices);

		return lengthOfServices.stream().map(c -> {
			CalculateDateDto calcDate = param.getGrantHolidayTblList().get(lengthOfServices.indexOf(c));

			Optional<GeneralDate> grantDate = c.calculateGrantDate(param.getUseSimultaneousGrant(),
																					param.getReferDate(),
																					param.getSimultaneousGrantDate(),
																					lengthOfServices.indexOf(c) > 0 ? param.getGrantHolidayTblList().get(lengthOfServices.indexOf(c)-1).getGrantDate() : Optional.empty());
			calcDate.setGrantDate(grantDate);
			return calcDate;
		}).collect(Collectors.toList());
	}
}
