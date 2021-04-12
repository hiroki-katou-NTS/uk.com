package nts.uk.ctx.at.record.app.command.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkplacePossible;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkplacePossibleCmd {
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 職場ID
	 */
	private String workpalceId;
	
	public WorkplacePossible toDomain() {
		return new WorkplacePossible(this.companyId, this.workpalceId);
		
	}
	
	public static WorkplacePossibleCmd toDto(WorkplacePossible domain) {
		return new WorkplacePossibleCmd(domain.getCompanyId(), domain.getWorkpalceId());
		
	}
}
