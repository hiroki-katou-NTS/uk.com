package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurHisDto;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class EmpInsurHisFinder {
	
	@Inject
	private EmpInsurHisRepository empInsurHisRepository;
	
	public List<EmpInsurHisDto>  getListEmplInsurHis(){
		String cId = AppContexts.user().companyId();
		Optional<EmpInsurHis> empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
		List<EmpInsurHisDto> empInsurHisDto = new ArrayList<EmpInsurHisDto>();
		empInsurHisDto = empInsurHis.isPresent() ? EmpInsurHisDto.fromDomain(empInsurHis.get()) :empInsurHisDto;
		return empInsurHisDto;
	}
}
