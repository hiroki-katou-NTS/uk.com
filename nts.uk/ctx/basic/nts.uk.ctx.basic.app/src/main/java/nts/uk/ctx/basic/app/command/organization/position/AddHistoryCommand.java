package nts.uk.ctx.basic.app.command.organization.position;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;


@Getter
@Setter
public class AddHistoryCommand {

	private String endDate;

	private String companyCode;

	private String historyId;

	private String startDate; 
	
	public JobHistory toDomain(){
		return JobHistory.createFromJavaType( 
				this.companyCode,
				this.historyId,
				GeneralDate.localDate(LocalDate.parse(this.startDate)),
				GeneralDate.localDate(LocalDate.parse(this.endDate)));
	}
}
