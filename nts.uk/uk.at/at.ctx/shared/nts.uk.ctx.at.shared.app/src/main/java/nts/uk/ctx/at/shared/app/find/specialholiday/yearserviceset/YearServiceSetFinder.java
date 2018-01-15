package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearServiceSetFinder {
	@Inject
	private YearServiceComRepository yearServiceSetRep;
	public List<YearServiceSetDto> finder(String specialHolidayCode){
		String companyId = AppContexts.user().companyId();
		return this.yearServiceSetRep.findAllSet(companyId, specialHolidayCode).stream().map(item ->{
			return new YearServiceSetDto(item.getSpecialHolidayCode(), item.getYearServiceNo(), item.getYear(), item.getMonth(), item.getDate());
		}).collect(Collectors.toList());
	}
}
