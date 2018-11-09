/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.workrule.closure;

import java.util.List;

/**
 * The Interface QueryClosureEmpAdapter.
 */
public interface QueryClosureEmpAdapter {

	/**
	 * Find list emp cd by closure id.
	 *
	 * @param closureId the closure id
	 * @return the list
	 */
	// 締めに紐付く雇用コード一覧を取得
	List<String> findListEmpCdByClosureId(String cId, int closureId);
}
