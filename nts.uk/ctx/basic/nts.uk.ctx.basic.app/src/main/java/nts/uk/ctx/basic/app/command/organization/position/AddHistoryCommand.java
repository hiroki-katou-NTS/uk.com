//package nts.uk.ctx.basic.app.command.organization.position;
//
//
//
//import java.time.LocalDate;
//
//import lombok.Getter;
//import lombok.Setter;
//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.basic.dom.organization.position.JobHistory;
//
//
//@Getter
//@Setter
//public class AddHistoryCommand {
//
//	private JobHist jobHist;
//	
//	private JobTitle jobTitle;
//	
//	private String checkAddJhist;
//	
//	private String checkAddJtitle;
//	
//	public JobHistory toDomain(){
//		return JobHistory.createFromJavaType( 
//				this.jobHist.getCompanyCode(),
//				this.jobHist.getHistoryId(),
//				GeneralDate.localDate(LocalDate.parse(this.jobHist.getStartDate())),
//				GeneralDate.localDate(LocalDate.parse(this.jobHist.getEndDate())));
//	}
//
//	
//}
