package nts.ctx.at.shared.dom.worktype;

import java.util.List;


public interface WorkTypeRepository {

	/**
	 * get possible work type
	 * @param companyId
	 * @param lstPossible
	 * @return
	 */
	List<WorkType> getPossibleWorkType(String companyId, List<Integer> lstPossible);
}
