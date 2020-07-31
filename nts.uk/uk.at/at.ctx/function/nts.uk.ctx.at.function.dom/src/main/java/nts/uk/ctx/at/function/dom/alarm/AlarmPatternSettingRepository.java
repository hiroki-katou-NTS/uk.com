package nts.uk.ctx.at.function.dom.alarm;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

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
	
	public Optional<AverageMonth> findAverageMonth(String extractionId);
	
	public void create(AlarmPatternSetting domain);
	
	public void update(AlarmPatternSetting domain);
	
	public void delete(String companyId, String alarmPatternCode);
	/**
	 * 会社ID、アラームリストパターンコードからドメインモデル「チェック条件」を取得する
	 * @param companyId
	 * @param alarmPatternCode
	 * @return
	 */
	public List<CheckCondition> getCheckCondition(String companyId, String alarmPatternCode);


	void createAver(AverageMonth domain);
		
}
