//package nts.uk.ctx.basic.app.command.position.hoatt;
//
//import java.time.LocalDateTime;
//
//import lombok.Getter;
//import lombok.Setter;
//import nts.arc.time.GeneralDateTime;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.JobHistory;
//import nts.uk.shr.com.context.AppContexts;
//
//@Getter
//@Setter
//public class DeleteHistoryCommand {
//
//	private String endDate;
//
//	//private String companyCode;
//
//	private String historyId;
//
//	private String startDate; 
//	
//	public JobHistory toDomain(){
//		return JobHistory.createSimpleFromJavaType(this.endDate,
//				this.startDate, 
//				AppContexts.user().companyCode(),
//				this.historyId);
//	}
//}
