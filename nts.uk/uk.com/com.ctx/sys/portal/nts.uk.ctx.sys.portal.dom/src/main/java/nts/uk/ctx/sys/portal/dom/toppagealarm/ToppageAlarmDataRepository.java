package nts.uk.ctx.sys.portal.dom.toppagealarm;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * トップページアラームデータRepository
 *
 */
public interface ToppageAlarmDataRepository {

	/**
	 * [1]  insert(トップページアラームデータ)
	 */
	void insert(String contractCd, ToppageAlarmData domain);
	
	/**
	 * [2]  update(トップページアラームデータ)
	 */
	void update(String contractCd, ToppageAlarmData domain);

	Optional<ToppageAlarmData> get(String companyId, AlarmClassification alarmCls, String idenKey, String sId, DisplayAtr dispAtr);
	
	/**
	 * [3] get未読(会社ID,社員ID)
	 */
	List<ToppageAlarmData> getUnread(String companyId, String sId, GeneralDateTime afterDateTime);
	
	/**
	 * [4] get未読と既読(会社ID,社員ID)
	 */
	List<ToppageAlarmData> getAll(String companyId, String sId, GeneralDateTime afterDateTime);
	
}
