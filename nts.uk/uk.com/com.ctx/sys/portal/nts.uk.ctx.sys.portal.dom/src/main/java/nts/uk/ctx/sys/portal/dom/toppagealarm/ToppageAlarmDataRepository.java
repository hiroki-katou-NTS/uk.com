package nts.uk.ctx.sys.portal.dom.toppagealarm;

import java.util.List;

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
	
	/**
	 * [3] get未読(会社ID,社員ID)
	 */
	List<ToppageAlarmData> getUnread(String companyId, String sId);
	
	/**
	 * [4] get未読と既読(会社ID,社員ID)
	 */
	List<ToppageAlarmData> getAll(String companyId, String sId);
	
}
