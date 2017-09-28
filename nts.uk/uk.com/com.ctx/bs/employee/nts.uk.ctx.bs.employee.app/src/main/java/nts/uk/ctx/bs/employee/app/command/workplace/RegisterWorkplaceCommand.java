/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceId;

/**
 * The Class WorkplaceCommand.
 */
@Getter
@Setter
public class RegisterWorkplaceCommand {
	
	/** The workplace id. */
	private String workplaceId;
	
	/** The workplace history. */
	private Set<WorkplaceHistoryDto> workplaceHistory;
	
	public Workplace toDomain(String companyId){
		return new Workplace(new WorkplaceGetMementoImpl(companyId,this));
	}
	public class WorkplaceGetMementoImpl implements WorkplaceGetMemento{
		
		private String companyId;
		
		private RegisterWorkplaceCommand workplaceCommand;

		public WorkplaceGetMementoImpl(String companyId,RegisterWorkplaceCommand workplaceCommand) {
			this.companyId = companyId;
			this.workplaceCommand = workplaceCommand;
		}

		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		@Override
		public WorkplaceId getWorkplaceId() {
			return new WorkplaceId(this.workplaceCommand.getWorkplaceId());
		}
		
		@Override
		public List<WorkplaceHistory> getWorkplaceHistory() {
			return this.workplaceCommand.getWorkplaceHistory().stream().map(item -> {
				return item.toDomain();
			}).collect(Collectors.toList());
		}
		
	}
}
