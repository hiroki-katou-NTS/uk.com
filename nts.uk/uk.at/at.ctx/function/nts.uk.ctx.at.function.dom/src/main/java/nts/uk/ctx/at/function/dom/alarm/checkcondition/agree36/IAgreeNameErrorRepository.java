package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.List;
import java.util.Optional;
/**
 * the AgreeNameErrorRepository interface
 * @author yennth
 *
 */
public interface IAgreeNameErrorRepository {
	/**
	 * find name by list period and error alarm
	 * @param param
	 * @return
	 */
	public List<String> findName(List<ParamFind> param);
	/**
	 * get a optional AgreeNameError by id
	 * @param id
	 * @return
	 */
	public Optional<AgreeNameError> findById(int period, int errorAlarm);
	/**
	 * find all AgreeNameError
	 * @return
	 */
	public List<AgreeNameError> findAll();
	/**
	 * find AgreeNameError by period
	 * @param period
	 * @return
	 */
	public List<AgreeNameError> findByPer(int period);
	/**
	 * find AgreeNameError by errorAlarm
	 * @param errorAlarm
	 * @return
	 */
	public List<AgreeNameError> findByErr(int errorAlarm);
	/**
	 * update AgreeNameError
	 * @param agreeConditionError
	 */
	public void update(AgreeNameError agreeNameError);
	/**
	 * insert AgreeNameError
	 * @param agreeConditionError
	 */
	public void insert(AgreeNameError agreeNameError);

	/**
	 * delete AgreeNameError
	 * @param agreeConditionError
	 */
	public void delete(int period, int errorAlarm);
}
