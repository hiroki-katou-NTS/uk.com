/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkHierarchy.
 */
@Getter
public class WorkPlaceHierarchy extends DomainObject {

	/** The workplace id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/** The hierarchy code. */
	// 階層コード
	private HierarchyCode hierarchyCode;
	
	/**
	 * Instantiates a new work hierarchy.
	 *
	 * @param memento the memento
	 */
	public WorkPlaceHierarchy(WorkPlaceHierarchyGetMemento memento) {
		this.workplaceId = memento.getWorkplaceId();
		this.hierarchyCode = memento.getHierarchyCode();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkPlaceHierarchySetMemento memento){
		memento.setWorkplaceId(this.workplaceId);
		memento.setHierarchyCode(this.hierarchyCode);
	}
}
