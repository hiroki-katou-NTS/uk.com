package nts.uk.ctx.at.shared.app.find.worktype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeFinder {

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	//user contexts
	String companyId = AppContexts.user().companyId();
	
	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		List<WorkTypeDto> lst = this.workTypeRepo.getPossibleWorkType(companyId, lstPossible)
				.stream()
				.map(c -> WorkTypeDto.fromDomain(c)).collect(Collectors.toList());
		return lst;
	}
}
