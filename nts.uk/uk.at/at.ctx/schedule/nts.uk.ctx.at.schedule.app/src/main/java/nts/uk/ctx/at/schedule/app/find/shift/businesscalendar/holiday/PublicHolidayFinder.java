/**
 * 2:09:17 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.holiday;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class PublicHolidayFinder {

	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	public List<PublicHolidayDto> getHolidaysByListDate(List<String> lstDate) {
		List<GeneralDate> lstGeneralDate = lstDate.stream().map(x -> GeneralDate.fromString(x, DATE_FORMAT)).collect(Collectors.toList());
		return this.publicHolidayRepository.getHolidaysByListDate(AppContexts.user().companyId(), lstGeneralDate).stream()
				.map(domain -> PublicHolidayDto.fromDomain(domain)).collect(Collectors.toList());
	}
	
	public List<PublicHolidayDto> getAllHolidays() {
		return this.publicHolidayRepository.getAllHolidays(AppContexts.user().companyId()).stream()
				.map(domain -> PublicHolidayDto.fromDomain(domain)).collect(Collectors.toList());
	}
	
	public Optional<PublicHolidayDto> getHolidayByDate(String date){
		return this.publicHolidayRepository.getHolidaysByDate(AppContexts.user().companyId(), 
				GeneralDate.fromString(date, DATE_FORMAT)).map(x -> PublicHolidayDto.fromDomain(x));
	}
}
