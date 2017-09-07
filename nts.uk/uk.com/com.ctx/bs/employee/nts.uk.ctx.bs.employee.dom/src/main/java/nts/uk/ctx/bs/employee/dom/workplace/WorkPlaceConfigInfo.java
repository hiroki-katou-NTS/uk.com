/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkPlaceConfigInfo.
 */
//職場構成情報
@Getter
public class WorkPlaceConfigInfo extends AggregateRoot {

	/** The company id. */
	//会社ID
	private String companyId;

	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The wkp hierarchy. */
	//階層
	private List<WorkplaceHierarchy> wkpHierarchy;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkPlaceConfigInfoSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setHistoryId(this.getHistoryId());
		memento.setWkpHierarchy(this.wkpHierarchy);
	}

	/**
	 * Instantiates a new work place config info.
	 *
	 * @param memento the memento
	 */
	public WorkPlaceConfigInfo(WorkPlaceConfigInfoGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.historyId = memento.getHistoryId();
		this.wkpHierarchy = memento.getWkpHierarchy();
	}

}
