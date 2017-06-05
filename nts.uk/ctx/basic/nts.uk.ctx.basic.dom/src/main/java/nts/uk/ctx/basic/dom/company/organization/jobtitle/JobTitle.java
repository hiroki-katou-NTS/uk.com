/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;

/**
 * The Class JobTitle.
 */
public class JobTitle extends AggregateRoot {

	/** The position id. */
	private PositionId positionId;

	/** The company id. */
	private CompanyId companyId;

	/** The sequence code. */
	private SequenceCode sequenceCode;

	/** The period. */
	private Period period;

	/** The position code. */
	private PositionCode positionCode;

	/** The position name. */
	private PositionName positionName;

	/**
	 * Instantiates a new job title.
	 *
	 * @param memento the memento
	 */
	public JobTitle(JobTitleGetMemento memento) {
		this.positionId = memento.getPositionId();
		this.companyId = memento.getCompanyId();
		this.sequenceCode = memento.getSequenceCode();
		this.period = memento.getPeriod();
		this.positionCode = memento.getPositionCode();
		this.positionName = memento.getPositionName();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(JobTitleSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setPositionId(this.positionId);
		memento.setSequenceCode(this.sequenceCode);
		memento.setPeriod(this.period);
		memento.setPositionCode(this.positionCode);
		memento.setPositionName(this.positionName);
	}
}
