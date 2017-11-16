package nts.uk.ctx.at.shared.app.find.specialholiday.yearservicecom;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearServiceComFinder {
	@Inject
	private YearServiceComRepository yearServiceComRep;
	public List<YearServiceComDto> finder(){
		String companyId = AppContexts.user().companyId();
		return this.yearServiceComRep.findAllCom(companyId).stream().map(item ->{
			return new YearServiceComDto(item.getSpecialHolidayCode(), item.getLengthServiceYearAtr());
		}).collect(Collectors.toList());
	}
}
