package nts.uk.ctx.basic.app.command.organization.position;



import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;


@Getter
@Setter
public class AddHistoryCommand {

	private JobHist jobHist;
	
	private JobTitle jobTitle;
	
	private String checkAddJhist;
	
	private String checkAddJtitle;
	
	public JobHistory toDomain(){
		return JobHistory.createFromJavaType( 
				this.jobHist.getCompanyCode(),
				this.jobHist.getHistoryId(),
				this.jobHist.getStartDate(),
				this.jobHist.getEndDate());
	}

	
}
