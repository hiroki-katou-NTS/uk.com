/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config.info;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;

@Getter
@Setter
public class RegisterWkpConfigInfoCommand {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The history id. */
	// 履歴ID
	private String historyId;

	/** The wkp hierarchy. */
	// 階層
	private List<WorkplaceHierarchyDto> wkpHierarchy;
	
	/**
	 * To domain.
	 *
	 * @return the workplace config info
	 */
	public WorkplaceConfigInfo toDomain(){
		return new WorkplaceConfigInfo(new WorkplaceConfigInfoGetMementoImpl(this));
	}
	
	/**
	 * The Class WorkplaceConfigInfoGetMementoImpl.
	 */
	public class WorkplaceConfigInfoGetMementoImpl implements WorkplaceConfigInfoGetMemento{

		/** The register workplace config info command. */
		private RegisterWkpConfigInfoCommand registerWorkplaceConfigInfoCommand;
		
		/**
		 * Instantiates a new workplace config info get memento impl.
		 *
		 * @param registerWorkplaceConfigInfoCommand the register workplace config info command
		 */
		public WorkplaceConfigInfoGetMementoImpl(
				RegisterWkpConfigInfoCommand registerWorkplaceConfigInfoCommand) {
			this.registerWorkplaceConfigInfoCommand = registerWorkplaceConfigInfoCommand;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.registerWorkplaceConfigInfoCommand.companyId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getHistoryId()
		 */
		@Override
		public String getHistoryId() {
			return this.registerWorkplaceConfigInfoCommand.getHistoryId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoGetMemento#getWkpHierarchy()
		 */
		@Override
		public List<WorkplaceHierarchy> getWkpHierarchy() {
			return this.registerWorkplaceConfigInfoCommand.getWkpHierarchy().stream().map(item->{
				return item.toDomain();
			}).collect(Collectors.toList());
		}
		
	}
}
