package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * web打刻用トップページアラームRepository
 * @author chungnt
 *
 */

public interface TopPageAlarmStampingRepository {

	/**
	 * 	[1]  insert(web打刻用トップページアラーム)
	 * @param domain
	 */
	public void insert(TopPageAlarmStamping domain);
	
	/**
	 * 	[2]  update(web打刻用トップページアラーム)
	 * @param domain
	 */
	public void update(TopPageAlarmStamping domain);
	
	/**
	 * 	[3]  取得する
	 * @param employeeId
	 * @param date
	 */
	public Optional<TopPageAlarmStamping> get(String employeeId, GeneralDate date);
}
