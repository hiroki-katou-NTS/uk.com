package nts.uk.ctx.pr.core.app.find.laborinsurance;


import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsurPreRateFinder {
	
	@Inject
	private EmpInsurBusBurRatioRepository empInsurBusBurRatioReopository;
	
	public List<EmpInsurPreRateDto> getListEmplInsurPreRate(String hisId){
		return empInsurBusBurRatioReopository.getEmpInsurBusBurRatioByHisId(hisId).stream().
				map(item -> EmpInsurPreRateDto.fromDomain(item)).collect(Collectors.toList());
	}
}
