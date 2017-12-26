package nts.uk.ctx.at.function.dom.alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmPatternSettingRepository {
	
	/**
	 * @param companyId
	 * 	 
	 * @return AlarmPatternSetting List
	 */
	public List<AlarmPatternSetting> findByCompanyId(String companyId);
	
	/**
	 * @param companyId
	 * @param alarmPatternCode
	 * @return AlarmPatternSetting domain
	 */
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode);
	
	public void create(AlarmPatternSetting domain);
	
	public void update(AlarmPatternSetting domain);
	
	public void delete(String companyId, String alarmPatternCode);
		
}
