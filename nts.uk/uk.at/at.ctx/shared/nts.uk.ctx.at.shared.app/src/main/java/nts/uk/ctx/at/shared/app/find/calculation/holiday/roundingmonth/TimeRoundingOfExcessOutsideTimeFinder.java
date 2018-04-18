package nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * The time rounding of excess outside time finder
 * @author HoangNDH
 *
 */
@Stateless
public class TimeRoundingOfExcessOutsideTimeFinder {
	@Inject
	RoundingSetOfMonthlyRepository repository;
	
	/**
	 * Find excess time rounding setting
	 * @return the excess time rounding setting
	 */
	public TimeRoundingOfExcessOutsideTimeDto findTimeRounding() {
		String companyId = AppContexts.user().companyId();
		Optional<TimeRoundingOfExcessOutsideTime> optTimeRounding = repository.find(companyId);
		if (optTimeRounding.isPresent()) {
			TimeRoundingOfExcessOutsideTime timeRounding = optTimeRounding.get();
			TimeRoundingOfExcessOutsideTimeDto dto = new TimeRoundingOfExcessOutsideTimeDto();
			dto.setRoundingUnit(timeRounding.getRoundingUnit().value);
			dto.setRoundingProcess(timeRounding.getRoundingProcess().value);
			return dto;
		}
		return null;
	}
}
