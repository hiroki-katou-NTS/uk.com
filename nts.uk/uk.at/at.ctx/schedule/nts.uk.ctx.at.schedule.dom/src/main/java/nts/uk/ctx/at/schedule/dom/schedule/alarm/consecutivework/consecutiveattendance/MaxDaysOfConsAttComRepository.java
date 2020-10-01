package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import java.util.Optional;

/**
 * 会社の連続出勤できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxDaysOfConsAttComRepository {

	/**
	 * insert(会社ID, 会社の連続出勤できる上限日数)
	 * @param companyId
	 * @param maxContAttCom
	 */
	void insert (String companyId, MaxDaysOfConsecutiveAttendanceCompany maxContAttCom);
	
	/**
	 * update(会社ID, 会社の連続出勤できる上限日数)
	 * @param companyId
	 * @param maxContAttCom
	 */
	void update (String companyId, MaxDaysOfConsecutiveAttendanceCompany maxContAttCom);
	
	/**
	 * delete(会社ID)
	 * @param companyId
	 */
	void delete(String companyId);
	
	/**
	 * exists(会社ID)
	 * @param companyId
	 * @return
	 */
	boolean exists(String companyId);
	
	/**
	 * get
	 * @param companyId
	 * @return
	 */
	Optional<MaxDaysOfConsecutiveAttendanceCompany> get (String companyId);
}
