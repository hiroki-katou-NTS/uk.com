package nts.uk.ctx.at.schedule.app.find.schedule.setting.worktypedisplay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDisRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorktypeDisplayFinder {

	@Inject
	private WorktypeDisRepository repository;
	
	
	public WorktypeDisplayDto getWorkType() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		Optional<WorktypeDis> data = this.repository.findByCId(companyId);
		
		if(data.isPresent()){
			return WorktypeDisplayDto.fromDomain(data.get());
		}
		
		return null;
	}
}
