//package nts.uk.ctx.at.request.app.command.setting.request;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
//import nts.uk.shr.com.context.AppContexts;
///**
// * 
// * @author yennth
// *
// */
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ApplicationDeadlineCommand {
//	public Integer closureId;
//	public Integer userAtr;
//	public Integer deadline;
//	public Integer deadlineCriteria;
//	
//	public ApplicationDeadline toDomain(Integer closureId){
//		String companyId = AppContexts.user().companyId();
//		ApplicationDeadline appDead = ApplicationDeadline.createSimpleFromJavaType(companyId, this.closureId, 
//										this.userAtr, this.deadline, this.deadlineCriteria);
//		return appDead;
//	}
//}
