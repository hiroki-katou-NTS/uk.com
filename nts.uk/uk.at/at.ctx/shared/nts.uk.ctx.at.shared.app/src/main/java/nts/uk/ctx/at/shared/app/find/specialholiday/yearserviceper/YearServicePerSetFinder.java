package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class YearServicePerSetFinder {
	@Inject
	private YearServicePerRepository yearServicePerRep;
	/**
	 * convert form domain to dto
	 * @param yearServicePerSet
	 * @return
	 */
	private YearServicePerSetDto fromDomain(YearServicePerSet yearServicePerSet){
		YearServicePerSetDto yearServicePerSetDto = new YearServicePerSetDto();
		yearServicePerSetDto.setSpecialHolidayCode(yearServicePerSetDto.getSpecialHolidayCode());
		yearServicePerSetDto.setYearServiceCode(yearServicePerSetDto.getYearServiceCode());
		yearServicePerSetDto.setYearServiceNo(yearServicePerSetDto.getYearServiceNo());
		yearServicePerSetDto.setYear(yearServicePerSetDto.getYear());
		yearServicePerSetDto.setMonth(yearServicePerSetDto.getMonth());
		yearServicePerSetDto.setDate(yearServicePerSetDto.getDate());
		return yearServicePerSetDto;
	}
	/**
	 * Find all year service per set
	 * @param contextspecialHolidayCode
	 * @param yearServiceCode
	 * @return
	 */
	public List<YearServicePerSetDto> finder(String specialHolidayCode, String yearServiceCode){
		String companyId = AppContexts.user().companyId();
		return this.yearServicePerRep.findPerSet(companyId, specialHolidayCode, yearServiceCode)
				.stream()
				.map(c -> fromDomain(c))
				.collect(Collectors.toList());
	}
}
