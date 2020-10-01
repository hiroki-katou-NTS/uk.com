package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Optional;

/**
 * 	個人条件の表示制御Repository
 * @author HieuLt
 *
 */
public interface DisplayControlPersonalConditionRepo {

	/**
	 * [1] Insert(個人条件の表示制御)(Insert (control hiển thị điều kiện cá nhân)
	 * @param DisplayControlPersonalCondition
	 */
	void insert(DisplayControlPersonalCondition DisplayControlPersonalCondition);
	
	/**
	 * [2] Update(個人条件の表示制御) (Update  (control hiển thị điều kiện cá nhân))
	 * @param DisplayControlPersonalCondition
	 */
	void update(DisplayControlPersonalCondition DisplayControlPersonalCondition);
	
	/**
	 * [3] Delete(会社ID) (Delete(Company ID)
	 * @param companyId
	 */
	void delete(String companyId);
	/**
	 * [4] get
	 * @param companyId
	 * @return
	 */
	Optional<DisplayControlPersonalCondition> get(String companyId); 
	
	
}

