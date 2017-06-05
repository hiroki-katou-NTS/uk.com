/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;

/**
 * 職位.
 */
public class JobTitle extends AggregateRoot {

	/** 職位ID. */
	private PositionId positionId;

	/** 会社ID. */
	private CompanyId companyId;

	/** 序列コード. */
	private SequenceCode sequenceCode;

	/** 期間. */
	private Period period;

	/** 職位コード. */
	private PositionCode positionCode;

	/** 職位名称. */
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
