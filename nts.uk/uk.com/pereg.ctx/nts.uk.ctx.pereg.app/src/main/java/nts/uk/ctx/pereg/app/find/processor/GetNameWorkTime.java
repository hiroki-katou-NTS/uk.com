package nts.uk.ctx.pereg.app.find.processor;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */


@Stateless
public class GetNameWorkTime {

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	
	public GetNameWorkTimeOutput getNameWorkTime(String workTimeCode) {
		
		Optional<WorkTimeSetting> optional = this.workTimeSettingRepo.findByCode(AppContexts.user().companyId(), workTimeCode);
		
		if (!optional.isPresent()) {
			return null;
		}
		
		return new GetNameWorkTimeOutput(optional.get().getWorkTimeDisplayName().getWorkTimeName().v());
	}
	
}
