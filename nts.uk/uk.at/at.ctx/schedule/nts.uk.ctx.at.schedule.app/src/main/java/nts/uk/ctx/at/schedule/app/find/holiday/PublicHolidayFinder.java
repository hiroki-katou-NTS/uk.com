/**
 * 2:09:17 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.holiday;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.holiday.PublicHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class PublicHolidayFinder {

	@Inject
	private PublicHolidayRepository publicHolidayRepository;

	public List<PublicHolidayDto> getHolidaysByListDate(List<BigDecimal> lstDate) {
		return this.publicHolidayRepository.getHolidaysByListDate(AppContexts.user().companyId(), lstDate).stream()
				.map(domain -> PublicHolidayDto.fromDomain(domain)).collect(Collectors.toList());
	}
}
