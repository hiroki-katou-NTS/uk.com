package nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm;

import java.util.List;

import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevEvent;

public interface HRDevEventRepository {
	/**
	 * find item by id
	 * @author yennth
	 * @param eventId
	 * @return
	 */
	List<HRDevEvent> findByAvailable();
}
