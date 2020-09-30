package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime;

import java.util.List;
import java.util.Optional;

/**
 * 会社の就業時間帯の連続勤務できる上限日数Repository
 * @author hiroko_miura
 *
 */
public interface MaxNumberDaysOfContWorkTimeComRepository {

	/**
	 * insert(会社ID, 会社の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void insert (String companyId, MaxNumberDaysOfContinuousWorkTimeCom domain);
	
	/**
	 * update(会社ID, 会社の就業時間帯の連続勤務できる上限日数)
	 * @param companyId
	 * @param domain
	 */
	void update (String companyId, MaxNumberDaysOfContinuousWorkTimeCom domain);
	
	/**
	 * delete(会社ID, 就業時間帯連続コード)
	 * @param companyId
	 * @param code
	 */
	void delete (String companyId, WorkTimeContinuousCode code);
	
	/**
	 * exists(会社ID, 就業時間帯連続コード)
	 * @param companyId
	 * @param code
	 * @return
	 */
	boolean exists (String companyId, WorkTimeContinuousCode code);
	
	/**
	 * get
	 * @param companyId
	 * @param code
	 * @return
	 */
	Optional<MaxNumberDaysOfContinuousWorkTimeCom> get (String companyId, WorkTimeContinuousCode code);
	
	/**
	 * get*
	 * @param companyId
	 * @return
	 */
	List<MaxNumberDaysOfContinuousWorkTimeCom> getAll (String companyId);
}
