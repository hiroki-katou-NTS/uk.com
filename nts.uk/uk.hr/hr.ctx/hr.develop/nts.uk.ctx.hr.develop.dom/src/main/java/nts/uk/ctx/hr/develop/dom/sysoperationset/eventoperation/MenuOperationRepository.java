package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation;

import java.util.List;
import java.util.Optional;

public interface MenuOperationRepository {
	/**
	 * find item by key
	 * @param companyId
	 * @param eventId
	 * @return
	 * @author yennth
	 */
	Optional<MenuOperation> findByKey(String companyId, String programId);
	/**
	 * insert a item
	 * @param eventOperation
	 * @author yennth
	 */
	void add(MenuOperation menuOperation);
	/**
	 * update a item
	 * @param eventOperation
	 * @author yennth
	 */
	void update(MenuOperation menuOperation);
	/**
	 * 承認機能を使用するイベント種類を取得
	 * @param companyId
	 * @return
	 */
	List<MenuInfoEx> findByApprUse(String companyId);
}
