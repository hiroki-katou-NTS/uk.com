package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation;

import java.util.Optional;

public interface EventOperationRepository {
	/**
	 * find item by key
	 * @param companyId
	 * @param eventId
	 * @return
	 * @author yennth
	 */
	Optional<EventOperation> findByKey(String companyId, int eventId);
	/**
	 * insert a item
	 * @param eventOperation
	 * @author yennth
	 */
	void add(EventOperation eventOperation);
	/**
	 * update a item
	 * @param eventOperation
	 * @author yennth
	 */
	void update(EventOperation eventOperation);
}
