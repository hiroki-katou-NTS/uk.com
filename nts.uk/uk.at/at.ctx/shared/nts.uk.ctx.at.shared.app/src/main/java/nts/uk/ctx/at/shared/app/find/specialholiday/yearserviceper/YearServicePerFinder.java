package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearServicePerFinder {
	@Inject
	private YearServicePerRepository yearServicePerRep;
	
	private YearServicePerSetDto fromDomain(YearServicePerSet yearServicePerSet){
		YearServicePerSetDto yearServicePerSetDto = new YearServicePerSetDto();
		yearServicePerSetDto.setSpecialHolidayCode(yearServicePerSet.getSpecialHolidayCode());
		yearServicePerSetDto.setYearServiceCode(yearServicePerSet.getYearServiceCode());
		yearServicePerSetDto.setYearServiceNo(yearServicePerSet.getYearServiceNo());
		yearServicePerSetDto.setYear(yearServicePerSet.getYear());
		yearServicePerSetDto.setMonth(yearServicePerSet.getMonth());
		yearServicePerSetDto.setDate(yearServicePerSet.getDate());
		return yearServicePerSetDto;
	}
	
	public List<YearServicePerDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.yearServicePerRep.findAllPer(companyId).stream().map(item -> {
			return new YearServicePerDto(item.getSpecialHolidayCode(), item.getYearServiceCode(), item.getYearServiceName(), item.getYearServiceCls(), 
					item.getYearServicePerSets()
					.stream()
					.map(c -> fromDomain(c))
					.collect(Collectors.toList())
					);
		}).collect(Collectors.toList());
	}
}
