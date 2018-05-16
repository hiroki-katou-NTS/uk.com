package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionOfHDManagementDataFinder {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriod(String sid, GeneralDate startDate, GeneralDate endDate){
		return null;
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBysiDRemCod(String empId) {
		
		String cid = AppContexts.user().companyId();
		return substitutionOfHDManaDataRepository.getBysiDRemCod(cid, empId).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
}
