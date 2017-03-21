package nts.uk.ctx.basic.app.command.organization.position;


import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;


@Data
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
