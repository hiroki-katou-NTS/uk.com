/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.workrule.closure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.pub.workrule.closure.ClosureExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ClosureQueryProcessorPub;

/**
 * The Class WorkClosurePubImpl.
 */
@Stateless
public class ClosureQueryProcessorPubImpl implements ClosureQueryProcessorPub {

	/** The query processor. */
	@Inject
	private WorkClosureQueryProcessor queryProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.closure.WorkClosurePub#findClosureByReferenceDate(nts.
	 * arc.time.GeneralDate)
	 */
	@Override
	public List<ClosureExport> findClosureByReferenceDate(GeneralDate refDate) {
		return this.queryProcessor.findClosureByReferenceDate(refDate).stream()
				.map(item -> ClosureExport.builder()
						.closureId(item.getClosureId())
						.closureName(item.getClosureName())
						.build()).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.closure.WorkClosurePub#findClosureByEmploymentCode(java.
	 * lang.String)
	 */
	@Override
	public Integer findClosureByEmploymentCode(String empCode) {
		return this.queryProcessor.findClosureByEmploymentCode(empCode);
	}
}
