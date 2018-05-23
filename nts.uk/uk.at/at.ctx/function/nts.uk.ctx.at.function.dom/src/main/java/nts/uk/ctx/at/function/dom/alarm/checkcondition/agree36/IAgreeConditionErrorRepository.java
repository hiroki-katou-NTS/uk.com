package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.List;
import java.util.Optional;
/**
 * the AgreeConditionErrorRepository interface
 * @author yennth
 *
 */
public interface IAgreeConditionErrorRepository {
	/**
	 * find all AgreeConditionError
	 * @return
	 */
	public List<AgreeConditionError> findAll(String code, int category);
	/**
	 * get a optional AgreeConditionError by id
	 * @param id
	 * @return
	 */
	public Optional<AgreeConditionError> findById(String id, String code, String companyId, int category);
	/**
	 * update a AgreeConditionError
	 * @param agreeConditionError
	 */
	public void update(AgreeConditionError agreeConditionError);
	/**
	 * insert a AgreeConditionError
	 * @param agreeConditionError
	 */
	public void insert(AgreeConditionError agreeConditionError);
	/**
	 * delete a AgreeConditionError
	 * @param agreeConditionError
	 */
	public void delete(String code, int category);
}
