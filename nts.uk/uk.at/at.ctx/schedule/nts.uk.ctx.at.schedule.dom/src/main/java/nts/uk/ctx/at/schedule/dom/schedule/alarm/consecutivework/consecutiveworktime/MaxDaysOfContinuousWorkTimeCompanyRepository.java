package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import java.util.List;
import java.util.Optional;

/**
 * 会社の就業時間帯の連続勤務できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxDaysOfContinuousWorkTimeCompanyRepository {

	/**
	 * insert(会社ID, 会社の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void insert (String companyId, MaxDaysOfContinuousWorkTimeCompany domain);
	
	/**
	 * update(会社ID, 会社の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void update (String companyId, MaxDaysOfContinuousWorkTimeCompany domain);
	
	/**
	 * delete(会社ID, 就業時間帯連続コード)
	 * @param companyId
	 * @param code
	 */
	void delete (String companyId, ConsecutiveWorkTimeCode code);
	
	/**
	 * exists(会社ID, 就業時間帯連続コード)
	 * @param companyId
	 * @param code
	 * @return
	 */
	boolean exists (String companyId, ConsecutiveWorkTimeCode code);
	
	/**
	 * get
	 * @param companyId
	 * @param code
	 * @return
	 */
	Optional<MaxDaysOfContinuousWorkTimeCompany> get (String companyId, ConsecutiveWorkTimeCode code);
	
	/**
	 * get*
	 * @param companyId
	 * @return
	 */
	List<MaxDaysOfContinuousWorkTimeCompany> getAll (String companyId);
}
