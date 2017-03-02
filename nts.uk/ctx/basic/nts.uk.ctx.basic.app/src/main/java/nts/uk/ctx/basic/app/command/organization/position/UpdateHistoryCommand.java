//package nts.uk.ctx.basic.app.command.position.hoatt;
//
//import java.time.LocalDateTime;
//
//import nts.arc.time.GeneralDateTime;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.JobHistory;
//import nts.uk.shr.com.context.AppContexts;
//
//public class UpdateHistoryCommand {
//
//	private String endDate;
//
//	//private String companyCode;
//
//	private String historyId;
//
//	private String startDate; 
//	
//	public UpdateHistoryCommand(){
//		
//	}
//	public JobHistory toDomain(){
//		return JobHistory.createSimpleFromJavaType(this.endDate,
//				this.startDate, 
//				AppContexts.user().companyCode(),
//				this.historyId);
//	}
//}
