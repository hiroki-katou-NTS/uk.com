package nts.uk.ctx.basic.app.command.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
public class DeletePositionCommand {

	private String companyCode;
	
	private String historyId;

	private String jobCode;
	
	private String jobName;
	
	private int presenceCheckScopeSet;

	private String jobOutCode;

	private String memo;

	
	public JobTitle toDomain(){
		return JobTitle.createSimpleFromJavaType(AppContexts.user().companyCode(),
				this.historyId, 
				this.jobCode,this.jobName,
				this.presenceCheckScopeSet,
				this.jobOutCode,this.memo);
	}
}
