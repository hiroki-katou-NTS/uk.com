package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * The time rounding of excess outside time finder.
 *
 */
@Stateless
public class TimeRoundingOfExcessOutsideTimeFinder {
	
	/** The repository. */
	@Inject
	private RoundingSetOfMonthlyRepository repository;
	
	/**
	 * Find excess time rounding setting.
	 *
	 * @return the excess time rounding setting
	 */
	public TimeRoundingOfExcessOutsideTimeDto findTimeRounding() {
		String companyId = AppContexts.user().companyId();
		Optional<TimeRoundingOfExcessOutsideTime> optTimeRounding = repository.findExcout(companyId);
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
