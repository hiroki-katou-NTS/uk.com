package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import java.util.Optional;

/**
 * 会社の連続出勤できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxNumberDaysOfContAttComRepository {

	/**
	 * insert(会社ID, 会社の連続出勤できる上限日数)
	 * @param companyId
	 * @param maxContAttCom
	 */
	void insert (String companyId, MaxNumberDaysOfContinuousAttendanceCom maxContAttCom);
	
	/**
	 * update(会社ID, 会社の連続出勤できる上限日数)
	 * @param companyId
	 * @param maxContAttCom
	 */
	void update (String companyId, MaxNumberDaysOfContinuousAttendanceCom maxContAttCom);
	
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
	Optional<MaxNumberDaysOfContinuousAttendanceCom> get (String companyId);
}
