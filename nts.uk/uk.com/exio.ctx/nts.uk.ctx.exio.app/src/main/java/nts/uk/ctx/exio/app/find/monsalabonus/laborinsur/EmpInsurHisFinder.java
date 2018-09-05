package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsurHisFinder {
	
	@Inject
	private EmpInsurHisRepository empInsurHisRepository;
	
	public List<EmpInsurHisDto> getListEmplInsurHis(){
		String cId = AppContexts.user().companyId();
		return empInsurHisRepository.getEmpInsurHisByCid(cId).stream().
				map(item -> EmpInsurHisDto.fromDomain(item)).collect(Collectors.toList());
	}
}
