package nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class ScheFuncControlFinder {
	@Inject
	private FunctionControlRepository repository;
	
	public ScheFuncControlDto getScheFuncControl() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		ScheFuncControl data = this.repository.getScheFuncControl(companyId);
		
		if(data != null){
			return ScheFuncControlDto.fromDomain(data);
		}
		
		return null;
	}
}
