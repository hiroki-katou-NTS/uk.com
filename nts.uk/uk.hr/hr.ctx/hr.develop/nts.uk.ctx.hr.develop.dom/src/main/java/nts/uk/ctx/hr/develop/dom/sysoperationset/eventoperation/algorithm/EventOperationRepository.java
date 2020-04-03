package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.algorithm;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation.EventOperation;

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
	/**
	 * find EventId by companyId
	 */
	List<Integer> findByCid(String companyId);
}
