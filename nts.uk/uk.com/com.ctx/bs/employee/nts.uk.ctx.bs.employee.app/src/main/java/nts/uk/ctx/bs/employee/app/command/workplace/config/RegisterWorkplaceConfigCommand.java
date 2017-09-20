/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;

@Getter
@Setter
public class RegisterWorkplaceConfigCommand {

	/** The company id. */
	//会社ID
	private String companyId;

	/** The wkp config history. */
	//履歴
	private Set<WorkplaceConfigHistoryDto> wkpConfigHistory;
	
	/**
	 * To domain.
	 *
	 * @return the workplace config
	 */
	public WorkplaceConfig toDomain(){
		return new WorkplaceConfig(new WorkplaceConfigGetMementoImpl(this));
	}
	
	/**
	 * The Class WorkplaceConfigGetMementoImpl.
	 */
	public class WorkplaceConfigGetMementoImpl implements WorkplaceConfigGetMemento{

		/** The register workplace config command. */
		private RegisterWorkplaceConfigCommand registerWorkplaceConfigCommand;
		
		/**
		 * Instantiates a new workplace config get memento impl.
		 *
		 * @param registerWorkplaceConfigCommand the register workplace config command
		 */
		public WorkplaceConfigGetMementoImpl(RegisterWorkplaceConfigCommand registerWorkplaceConfigCommand) {
			this.registerWorkplaceConfigCommand = registerWorkplaceConfigCommand;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.registerWorkplaceConfigCommand.getCompanyId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigGetMemento#getWkpConfigHistory()
		 */
		@Override
		public List<WorkplaceConfigHistory> getWkpConfigHistory() {
			return this.registerWorkplaceConfigCommand.getWkpConfigHistory().stream().map(item->{
				return item.toDomain();
			}).collect(Collectors.toList());
		}
		
	}
}
