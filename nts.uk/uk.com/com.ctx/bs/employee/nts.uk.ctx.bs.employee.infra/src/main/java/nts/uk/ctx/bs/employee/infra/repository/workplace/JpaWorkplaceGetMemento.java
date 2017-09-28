/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;

/**
 * The Class JpaWorkplaceGetMemento.
 */
public class JpaWorkplaceGetMemento implements WorkplaceGetMemento {

	/** The company id. */
	private String companyId;

	/** The workplace id. */
	private String workplaceId;
	
	/** The workplace history. */
	private List<BsymtWorkplaceHist> workplaceHistory;
	
	/**
	 * Instantiates a new jpa workplace get memento.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param lstWorkplaceHistory the lst workplace history
	 */
	public JpaWorkplaceGetMemento(String companyId, String workplaceId, List<BsymtWorkplaceHist> lstWorkplaceHistory) {
		this.companyId = companyId;
		this.workplaceId = workplaceId;
		this.workplaceHistory = lstWorkplaceHistory;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceId()
	 */
	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.workplaceId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceHistory()
	 */
	@Override
	public List<WorkplaceHistory> getWorkplaceHistory() {
		return this.workplaceHistory.stream().map(item -> {
			return new WorkplaceHistory(new JpaWorkplaceHistoryGetMemento(item));
		}).collect(Collectors.toList());
	}

}
