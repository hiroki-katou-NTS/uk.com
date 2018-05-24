package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePerSet;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.repository.YearServicePerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearServicePerFinder {
	@Inject
	private YearServicePerRepository yearServicePerRep;
	/**
	 * convert from domain to dto
	 * @param yearServicePerSet
	 * @return
	 */
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
	/**
	 * find all year service per
	 * @return
	 */
	public List<YearServicePerDto> finder(int specialHolidayCode){
		String companyId = AppContexts.user().companyId();
		return this.yearServicePerRep.findAllPer(companyId, specialHolidayCode)
				.stream()
				.map(item -> {
			return new YearServicePerDto(item.getSpecialHolidayCode(), 
											item.getYearServiceCode().v(), 
											item.getYearServiceName().v(), 
											item.getProvision(),
											item.getYearServiceCls().value, 
											item.getYearServicePerSets()
					.stream()
					.map(c -> fromDomain(c))
					.collect(Collectors.toList())
					);
		}).collect(Collectors.toList());
	}
}
