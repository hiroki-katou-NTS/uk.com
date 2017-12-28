package nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth;

import java.util.List;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author phongtq
 */

@Stateless
public class RoundingMonthFinder {

	@Inject
	private RoundingMonthRepository repository;

	/**
	 * Find all Rounding Month
	 * @param timeItemId
	 * @return
	 */
	public List<RoundingMonthDto> findAllRounding(String timeItemId) {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId,timeItemId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Rounding Month
	 * @param month
	 * @return
	 */
	private RoundingMonthDto convertToDbType(RoundingMonth month) {

		RoundingMonthDto roundingMonthDto = new RoundingMonthDto();
		roundingMonthDto.setTimeItemId(month.getTimeItemId().v());
		roundingMonthDto.setUnit(month.getUnit().value);
		roundingMonthDto.setRounding(month.getRounding().value);

		return roundingMonthDto;
	}
}
