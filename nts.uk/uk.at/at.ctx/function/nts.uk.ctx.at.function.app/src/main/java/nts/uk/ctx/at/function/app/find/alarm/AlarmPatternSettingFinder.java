package nts.uk.ctx.at.function.app.find.alarm;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AlarmPatternSettingFinder {
	
	@Inject
	private AlarmPatternSettingRepository alarmPatternRepo;
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alarmCategoryRepo;
	
	public List<AlarmPatternSetting> findAllAlarmPattern() {		
		String companyId = AppContexts.user().companyId();
		return alarmPatternRepo.findByCompanyId(companyId);
	}
	
	public List<AlarmCheckConditionByCategory> findAllAlarmCheckCondition(){
		String companyId = AppContexts.user().companyId();
		return alarmCategoryRepo.findAll(companyId);		
	}
	
}
