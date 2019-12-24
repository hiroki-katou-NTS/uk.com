package nts.uk.ctx.pr.core.app.find.laborinsurance;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpInsurHisFinder {
	
	@Inject
	private EmpInsurBusBurRatioRepository empInsurHisRepository;
	
	public List<EmpInsurHisDto>  getListEmplInsurHis(){
		String cId = AppContexts.user().companyId();
		EmpInsurHis empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
		List<EmpInsurHisDto> empInsurHisDto = new ArrayList<EmpInsurHisDto>();
		if (empInsurHis.getHistory() != null && !empInsurHis.getHistory().isEmpty()) {
			empInsurHisDto = EmpInsurHisDto.fromDomain(empInsurHis);
		}
		return empInsurHisDto;
	}
}
