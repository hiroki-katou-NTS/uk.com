package nts.uk.ctx.at.function.dom.scheduletable;

import java.util.List;
import java.util.Optional;

public interface ScheduleTableOutputSettingRepository {
	
	/**
	 * insert(会社ID, スケジュール表の出力設定）
	 * @param companyId
	 * @param domain
	 */
	void insert(String companyId, ScheduleTableOutputSetting domain);
	
	/**
	 * update(会社ID, スケジュール表の出力設定）
	 * @param companyId
	 * @param domain
	 */
	void update(String companyId, ScheduleTableOutputSetting domain);
	
	/**
	 * delete(会社ID, 出力設定コード）
	 * @param companyId
	 * @param code
	 */
	void delete(String companyId, OutputSettingCode code);
	
	/**
	 * get
	 * コードを指定してスケジュール表の出力設定を取得する。
	 * @param companyId
	 * @param code
	 * @return
	 */
	Optional<ScheduleTableOutputSetting> get(String companyId, OutputSettingCode code);
	
	/**
	 * get*
	 * 会社のスケジュール表の出力設定リストを取得する。	
	 * @param companyId
	 * @return
	 */
	List<ScheduleTableOutputSetting> getList(String companyId);
	
	/**
	 * exists(会社ID, 出力設定コード）
	 * @param companyId
	 * @param code
	 * @return
	 */
	boolean exists(String companyId, OutputSettingCode code);

}
