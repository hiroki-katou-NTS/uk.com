/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;

/**
 * The Class JpaWorkplaceGetMemento.
 */
public class JpaWorkplaceGetMemento implements WorkplaceGetMemento {

	/** The lst workplace history. */
	private List<BsymtWorkplaceHist> lstWorkplaceHistory;
	
	/** The Constant ELEMENT_FIRST. */
	private static final Integer ELEMENT_FIRST = 0;
	
	/**
	 * Instantiates a new jpa workplace get memento.
	 *
	 * @param lstWorkplaceHistory the lst workplace history
	 */
	public JpaWorkplaceGetMemento(List<BsymtWorkplaceHist> lstWorkplaceHistory) {
		this.lstWorkplaceHistory = lstWorkplaceHistory;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.lstWorkplaceHistory.get(ELEMENT_FIRST).getBsymtWorkplaceHistPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceId()
	 */
	@Override
	public String getWorkplaceId() {
	    return this.lstWorkplaceHistory.get(ELEMENT_FIRST).getBsymtWorkplaceHistPK().getWkpid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento#getWorkplaceHistory()
	 */
	@Override
	public List<WorkplaceHistory> getWorkplaceHistory() {
        return this.lstWorkplaceHistory.stream()
                .map(item -> new WorkplaceHistory(new JpaWorkplaceHistoryGetMemento(item)))
                .collect(Collectors.toList());
	}

}
