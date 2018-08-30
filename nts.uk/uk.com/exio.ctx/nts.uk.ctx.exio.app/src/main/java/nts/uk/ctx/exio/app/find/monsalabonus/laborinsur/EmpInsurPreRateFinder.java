package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurPreRateRepository;

@Stateless
public class EmpInsurPreRateFinder {
	
	@Inject
	private EmpInsurPreRateRepository empInsurPreRateRepository;
	
	public List<EmpInsurPreRateDto> getListEmplInsurPreRate(String hisId){
		return empInsurPreRateRepository.getEmpInsurPreRateByHisId(hisId).stream().
				map(item -> EmpInsurPreRateDto.fromDomain(item)).collect(Collectors.toList());
	}
}
