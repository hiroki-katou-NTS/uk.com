package nts.uk.ctx.basic.app.command.organization.position;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;


@Getter
@Setter
public class AddHistoryCommand {

	private GeneralDate endDate;

	private String companyCode;

	private String historyId;

	private GeneralDate startDate; 
	
	public JobHistory toDomain(){
		return JobHistory.createFromJavaType( 
				this.companyCode,
				this.historyId,
				this.startDate,
				this.endDate);
	}
}
