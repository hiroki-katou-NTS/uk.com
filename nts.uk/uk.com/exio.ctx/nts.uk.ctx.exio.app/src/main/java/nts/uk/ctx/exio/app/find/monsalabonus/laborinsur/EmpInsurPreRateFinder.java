package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatioRepository;

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
