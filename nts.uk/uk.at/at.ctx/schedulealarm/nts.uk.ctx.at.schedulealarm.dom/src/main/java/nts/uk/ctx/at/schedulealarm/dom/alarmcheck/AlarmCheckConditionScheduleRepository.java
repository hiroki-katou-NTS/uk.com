package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;

public interface AlarmCheckConditionScheduleRepository {
	/**
	 * insert(会社ID, 勤務予定のアラームチェック条件)
	 * @param cid
	 * @param domain
	 */
	void insert(String cid, AlarmCheckConditionSchedule domain);
	
	/**
	 * update(会社ID, 勤務予定のアラームチェック条件)
	 * @param cid
	 * @param domain
	 */
	void update(String cid, AlarmCheckConditionSchedule domain);
	
	/**
	 * get
	 * @param contractCd
	 * @param cid
	 * @param alarmCode
	 * @return
	 */
	AlarmCheckConditionSchedule get(String contractCd, String cid, AlarmCheckConditionScheduleCode alarmCode);
	
	/**
	 * get*
	 * 優先順リスト ASC	
	 * @param cid
	 * @return
	 */
	List<AlarmCheckConditionSchedule> getAll(String contractCd, String cid);
}
