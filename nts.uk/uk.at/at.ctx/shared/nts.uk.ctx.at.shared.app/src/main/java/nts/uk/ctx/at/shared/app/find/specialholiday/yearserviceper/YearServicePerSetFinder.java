package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.repository.YearServicePerRepository;
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
	
	public List<YearServicePerSetDto> finder(String contextspecialHolidayCode, String yearServiceCode){
		String companyId = AppContexts.user().companyId();
		return this.yearServicePerRep.findPerSet(companyId, contextspecialHolidayCode, yearServiceCode)
				.stream()
				.map(c -> fromDomain(c))
				.collect(Collectors.toList());
	}
}
