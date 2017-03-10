package nts.uk.ctx.basic.app.command.organization.position;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter

public class UpdatePositionCommand {

	private LocalDateTime endDate;

	private String jobName;

	private String jobOutCode;

	private String memo;

	private String companyCode;

	private String jobCode;

	private LocalDateTime startDate;
	
//	public JobTitle toDomain(){
//		return JobTitle.createSimpleFromJavaType(GeneralDateTime.localDateTime(this.endDate), GeneralDateTime.localDateTime(this.startDate), 
//				this.jobCode,this.jobName, this.jobOutCode,AppContexts.user().companyCode(),this.memo);
//	}
}
