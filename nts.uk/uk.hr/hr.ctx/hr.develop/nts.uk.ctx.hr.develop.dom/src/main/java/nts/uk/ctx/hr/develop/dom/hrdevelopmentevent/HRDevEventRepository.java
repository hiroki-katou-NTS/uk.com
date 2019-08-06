package nts.uk.ctx.hr.develop.dom.hrdevelopmentevent;

import java.util.List;

public interface HRDevEventRepository {
	/**
	 * find item by id
	 * @author yennth
	 * @param eventId
	 * @return
	 */
	List<HRDevEvent> findByAvailable();
}
