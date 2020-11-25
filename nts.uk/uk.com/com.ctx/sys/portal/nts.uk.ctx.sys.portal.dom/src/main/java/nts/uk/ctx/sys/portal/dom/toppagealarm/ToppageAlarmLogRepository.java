package nts.uk.ctx.sys.portal.dom.toppagealarm;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * 既読日時Repository
 *
 */
public interface ToppageAlarmLogRepository {

	/**
	 * [1]  insert(既読日時)
	 */
	void insert(String contractCd, ToppageAlarmLog domain);
	
	/**
	 * [2]  update(既読日時)
	 */
	void update(String contractCd, ToppageAlarmLog domain);
	
	/**
	 * [3] get(会社ID、アラーム分類、識別キー、表示社員ID)
	 */
	Optional<ToppageAlarmLog> get(String companyId, AlarmClassification alarmCls, String idenKey, String sId, DisplayAtr dispAtr);
	
	List<ToppageAlarmLog> getByEmployee(String companyId, String sId, GeneralDateTime afterDateTime);
	
}
