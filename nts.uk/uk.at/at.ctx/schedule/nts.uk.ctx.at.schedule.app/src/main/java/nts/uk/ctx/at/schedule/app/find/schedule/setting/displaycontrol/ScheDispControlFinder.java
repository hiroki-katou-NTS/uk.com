package nts.uk.ctx.at.schedule.app.find.schedule.setting.displaycontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.DisplayControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Stateless
public class ScheDispControlFinder {
	@Inject
	private DisplayControlRepository repository;
	
	public ScheDispControlDto getScheDispControl() {
		// user contexts
		String companyId = AppContexts.user().companyId();
		
		Optional<ScheDispControl> data = this.repository.getScheDispControl(companyId);
		
		if(data.isPresent()){
			return ScheDispControlDto.fromDomain(data.get());
		}
		
		return null;
	}
}
