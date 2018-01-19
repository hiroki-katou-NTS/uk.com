package nts.uk.ctx.at.function.dom.alarm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DefaultAlarmPatternSettingService implements AlarmPatternSettingService{
	@Inject
	private AlarmPatternSettingRepository repo;

	@Override
	public boolean checkDuplicateCode(String alarmPatternCode) {		
		if(repo.findByAlarmPatternCode(AppContexts.user().companyId(), alarmPatternCode).isPresent()) {
			return true;
		}
		return false;
	}

	
}
