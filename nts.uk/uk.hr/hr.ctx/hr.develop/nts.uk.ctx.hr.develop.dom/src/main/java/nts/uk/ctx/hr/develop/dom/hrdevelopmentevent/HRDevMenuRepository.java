package nts.uk.ctx.hr.develop.dom.hrdevelopmentevent;

import java.util.List;

public interface HRDevMenuRepository {
	/**
	 * find item by id
	 * @param programId
	 * @return
	 * @author yennth
	 */
	List<HRDevMenu> findByAvailable();
}
