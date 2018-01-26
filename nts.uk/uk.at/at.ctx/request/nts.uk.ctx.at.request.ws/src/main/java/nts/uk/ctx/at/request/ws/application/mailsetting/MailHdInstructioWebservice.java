package nts.uk.ctx.at.request.ws.application.mailsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval.ApprovalTempDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval.ApprovalTempFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionFinder;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionFinder;

@Path("at/request/application/mail")
@Produces("application/json")
public class MailHdInstructioWebservice {
	@Inject
	private MailHdInstructionFinder mailFinder;
	@Inject
	private ApprovalTempFinder tempFinder;
	@Inject 
	private MailOtInstructionFinder otFinder;
	
	@POST
	@Path("holiday")
	public MailHdInstructionDto getAppSet(){
		 return mailFinder.findByComId();
	}
	
	@POST
	@Path("template")
	public ApprovalTempDto getAppTemp(){
		 return tempFinder.findByComId();
	}
	
	@POST
	@Path("ot")
	public MailOtInstructionDto getOt(){
		 return otFinder.findByComId();
	}
}
