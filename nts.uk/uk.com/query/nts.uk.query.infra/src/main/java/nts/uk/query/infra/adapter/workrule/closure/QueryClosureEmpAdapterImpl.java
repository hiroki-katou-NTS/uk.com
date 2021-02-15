/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.adapter.workrule.closure;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosureEmployment;
import nts.uk.query.model.workrule.closure.QueryClosureEmpAdapter;

/**
 * The Class QueryClosureEmpAdapterImpl.
 */
@Stateless
public class QueryClosureEmpAdapterImpl extends JpaRepository implements QueryClosureEmpAdapter {

	/** The find by closure id. */
	private static final String FIND_BY_CLOSURE_ID = "SELECT c FROM KclmtClosureEmployment c"
			+ " WHERE c.closureId = :closureId"
			+ " AND c.kclmpClosureEmploymentPK.companyId = :cId";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.query.model.workrule.closure.QueryClosureEmpAdapter#
	 * findListEmpCdByClosureId(int)
	 */
	@Override
	public List<String> findListEmpCdByClosureId(String cId, int closureId) {
		return this.queryProxy().query(FIND_BY_CLOSURE_ID, KclmtClosureEmployment.class)
				.setParameter("closureId", closureId)
				.setParameter("cId", cId)
				.getList(e -> e.kclmpClosureEmploymentPK.employmentCD);
	}

}
