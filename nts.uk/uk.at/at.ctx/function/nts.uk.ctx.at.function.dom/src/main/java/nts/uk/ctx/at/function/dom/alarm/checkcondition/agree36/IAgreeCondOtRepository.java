package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.List;
import java.util.Optional;

/**
 * the AgreeCondOtRepository interface
 * @author yennth
 *
 */
public interface IAgreeCondOtRepository {
	/**
	 * get a optional AgreeCondOt by id
	 * @param id
	 * @return
	 */
	public Optional<AgreeCondOt> findById(String id, int no, String code, String companyId, int category);
	/**
	 * find all AgreeCondOt
	 * @return
	 */
	public List<AgreeCondOt> findAll(String code, int category);
	/**
	 * find AgreeNameError by id
	 * @param period
	 * @return
	 */
	public List<AgreeCondOt> findByPer(String id);
	/**
	 * find AgreeNameError by no
	 * @param errorAlarm
	 * @return
	 */
	public List<AgreeCondOt> findByErr(int no);
	/**
	 * update AgreeCondOt
	 * @param agreeCondOt
	 */
	public void update(AgreeCondOt agreeCondOt);
	/**
	 * insert AgreeCondOt
	 * @param agreeCondOt
	 */
	public void insert(AgreeCondOt agreeCondOt);
	/** 
	 * delete AgreeCondOt
	 * @param agreeCondOt
	 */
	public void delete(String code, int category);
	/**
	 * delete by code, category and id
	 * @param code
	 * @param category
	 * @param id
	 */
	public void deleteId(String code, int category, String id, int no);
}
