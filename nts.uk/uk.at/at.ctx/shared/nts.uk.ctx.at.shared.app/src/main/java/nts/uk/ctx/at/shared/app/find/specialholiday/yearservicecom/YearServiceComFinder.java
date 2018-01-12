package nts.uk.ctx.at.shared.app.find.specialholiday.yearservicecom;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearservicecom.YearServiceCom;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceset.repository.YearServiceComRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class YearServiceComFinder {
	@Inject
	private YearServiceComRepository yearServiceComRep;
	public YearServiceComDto finder(String specialHolidayCode){
		String companyId = AppContexts.user().companyId();
		Optional<YearServiceCom> tam = this.yearServiceComRep.findAllCom(companyId, specialHolidayCode);
		if(tam.isPresent()){
			return new YearServiceComDto(specialHolidayCode, tam.get().getLengthServiceYearAtr());
		}
		return null;
	}
}
