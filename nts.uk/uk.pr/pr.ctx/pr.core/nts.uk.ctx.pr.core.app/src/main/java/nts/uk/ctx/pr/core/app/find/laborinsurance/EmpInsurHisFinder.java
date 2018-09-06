package nts.uk.ctx.pr.core.app.find.laborinsurance;

import nts.uk.ctx.pr.core.app.find.laborinsurance.EmpInsurHisDto;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmpInsurHisFinder {
	
	@Inject
	private EmpInsurHisRepository empInsurHisRepository;
	
	public List<EmpInsurHisDto>  getListEmplInsurHis(){
		String cId = AppContexts.user().companyId();
		Optional<EmpInsurHis> temp =empInsurHisRepository.getEmpInsurHisByCid(cId);
		if(temp.isPresent()){
			return null;
		}
//		return temp.get().stream().map(item ->EmpInsurHisDto.fromDomain(item));
		return null;
	}
}
