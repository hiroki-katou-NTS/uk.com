package nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm;

import java.util.List;

import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;

public interface HRDevMenuRepository {
	/**
	 * find item by id
	 * @param programId
	 * @return
	 * @author yennth
	 */
	List<HRDevMenu> findByAvailable();
}
