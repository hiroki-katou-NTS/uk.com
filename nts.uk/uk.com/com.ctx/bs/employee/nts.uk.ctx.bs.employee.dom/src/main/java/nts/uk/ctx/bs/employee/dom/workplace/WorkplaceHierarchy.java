/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkHierarchy.
 */
@Getter
//職場階層
public class WorkplaceHierarchy extends DomainObject {

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/** The hierarchy code. */
	// 階層コード
	private String hierarchyCode;
	
	/**
	 * Instantiates a new work hierarchy.
	 *
	 * @param memento the memento
	 */
	public WorkplaceHierarchy(WorkplaceHierarchyGetMemento memento) {
		this.workplaceId = memento.getWorkplaceId();
		this.hierarchyCode = memento.getHierarchyCode();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceHierarchySetMemento memento){
		memento.setWorkplaceId(this.workplaceId);
		memento.setHierarchyCode(this.hierarchyCode);
	}
}
