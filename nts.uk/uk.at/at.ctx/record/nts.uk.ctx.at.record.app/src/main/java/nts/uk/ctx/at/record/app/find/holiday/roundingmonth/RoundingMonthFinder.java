package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingMonth;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingMonthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoundingMonthFinder.
 *
 */
@Stateless
public class RoundingMonthFinder {

	/** The repository. */
	@Inject
	private RoundingMonthRepository repository;

	/**
	 * Find all Rounding Month.
	 *
	 * @return the list
	 */
	public List<RoundingMonthDto> findAllRounding() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Rounding Month.
	 *
	 * @param month the month
	 * @return the rounding month dto
	 */
	private RoundingMonthDto convertToDbType(RoundingMonth month) {

		RoundingMonthDto roundingMonthDto = new RoundingMonthDto();
		roundingMonthDto.setTimeItemId(month.getTimeItemId());
		roundingMonthDto.setUnit(month.getUnit().value);
		roundingMonthDto.setRounding(month.getRounding().value);

		return roundingMonthDto;
	}
}
