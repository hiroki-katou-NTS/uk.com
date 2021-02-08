package nts.uk.ctx.at.function.dom.alarm;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

import java.util.List;
import java.util.Optional;

/**
 * The interface Alarm pattern setting repository.<br>
 * Repository アラームリストパターン設定
 */
public interface AlarmPatternSettingRepository {

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the <code>AlarmPatternSetting</code> list
	 */
	public List<AlarmPatternSetting> findByCompanyId(String companyId);

	/**
	 * Find by company id and user list.
	 *
	 * @param companyId the company id
	 * @return simple domain
	 */
	public List<AlarmPatternSettingSimple> findByCompanyIdAndUser(String companyId);

	/**
	 * Find by alarm pattern code optional.
	 *
	 * @param companyId        the company id
	 * @param alarmPatternCode the alarm pattern code
	 * @return AlarmPatternSetting domain
	 */
	public Optional<AlarmPatternSetting> findByAlarmPatternCode(String companyId, String alarmPatternCode);

	/**
	 * Find average month optional.
	 *
	 * @param extractionId the extraction id
	 * @return the optional
	 */
	public Optional<AverageMonth> findAverageMonth(String extractionId);

	/**
	 * Create.
	 *
	 * @param domain the domain
	 */
	public void create(AlarmPatternSetting domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	public void update(AlarmPatternSetting domain);

	/**
	 * Delete.
	 *
	 * @param companyId        the company id
	 * @param alarmPatternCode the alarm pattern code
	 */
	public void delete(String companyId, String alarmPatternCode);

	/**
	 * 会社ID、アラームリストパターンコードからドメインモデル「チェック条件」を取得する
	 *
	 * @param companyId        the company id
	 * @param alarmPatternCode the alarm pattern code
	 * @return check condition
	 */
	public List<CheckCondition> getCheckCondition(String companyId, String alarmPatternCode);


	/**
	 * Create aver.
	 *
	 * @param domain the domain
	 */
	void createAver(AverageMonth domain);

}
