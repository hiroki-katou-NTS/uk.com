package nts.uk.ctx.at.function.dom.alarm;

import java.util.List;
import java.util.Optional;

import alarmPatternRepo.AlarmPatternSettingSimple;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

public interface AlarmPatternSettingRepository {
	
	/**
	 * @param companyId
	 * 	 
	 * @return AlarmPatternSetting List
	 */
	public List<AlarmPatternSetting> findByCompanyId(String companyId);
	
	
	/**
	 * @param companyId
	 * 	 
	 * @return simple domain
	 */
	public List<AlarmPatternSettingSimple> findByCompanyIdAndUser(String companyId);
	
	/**
	 * @param companyId
	 * @param alarmPatternCode
	 * @return AlarmPatternSetting domain
	 */
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode);
	
	public void create(AlarmPatternSetting domain);
	
	public void update(AlarmPatternSetting domain);
	
	public void delete(String companyId, String alarmPatternCode);
	
	public List<CheckCondition> getCheckCondition(String companyId, String alarmPatternCode);
		
}
