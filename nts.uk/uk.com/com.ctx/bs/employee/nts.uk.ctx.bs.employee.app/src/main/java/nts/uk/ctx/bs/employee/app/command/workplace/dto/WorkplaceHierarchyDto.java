/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchyGetMemento;

@Getter
@Setter
public class WorkplaceHierarchyDto {

	/** The workplace id. */
	// 職場ID
	private String workplaceId;

	/** The hierarchy code. */
	// 階層コード
	private String hierarchyCode;

	/**
	 * To domain.
	 *
	 * @return the workplace hierarchy
	 */
	public WorkplaceHierarchy toDomain() {
		return new WorkplaceHierarchy(new WorkplaceHierarchyGetMementoImpl(this));
	}

	/**
	 * The Class WorkplaceHierarchyGetMementoImpl.
	 */
	public class WorkplaceHierarchyGetMementoImpl implements WorkplaceHierarchyGetMemento {

		/** The workplace hierarchy dto. */
		private WorkplaceHierarchyDto workplaceHierarchyDto;

		/**
		 * Instantiates a new workplace hierarchy get memento impl.
		 *
		 * @param workplaceHierarchyDto the workplace hierarchy dto
		 */
		public WorkplaceHierarchyGetMementoImpl(WorkplaceHierarchyDto workplaceHierarchyDto) {
			this.workplaceHierarchyDto = workplaceHierarchyDto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceHierarchyGetMemento#getWorkplaceId()
		 */
		@Override
		public String getWorkplaceId() {
			return this.workplaceHierarchyDto.getWorkplaceId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceHierarchyGetMemento#getHierarchyCode()
		 */
		@Override
		public HierarchyCode getHierarchyCode() {
			return new HierarchyCode(this.workplaceHierarchyDto.getHierarchyCode());
		}
	}
}
