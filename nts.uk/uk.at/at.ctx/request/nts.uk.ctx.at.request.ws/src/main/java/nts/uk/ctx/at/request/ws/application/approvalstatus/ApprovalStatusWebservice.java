package nts.uk.ctx.at.request.ws.application.approvalstatus;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.RegisterApprovalStatusMailTempCommandHandler;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusActivityData;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusFinder;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusPeriorDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.EmployeeEmailDto;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;

@Path("at/request/application/approvalstatus")
@Produces("application/json")
public class ApprovalStatusWebservice extends WebService {
	@Inject
	private ApprovalStatusFinder approvalMailTempFinder;

	@Inject
	private RegisterApprovalStatusMailTempCommandHandler registerApprovalStatusMailTempCommandHandler;

	/** The finder. */
	@Inject
	private ApprovalStatusFinder finder;
	
	@POST
	@Path("getMail/{mailType}")
	public ApprovalStatusMailTempDto getMail(@PathParam("mailType") int mailType) {
		return approvalMailTempFinder.findByType(mailType);
	}

	@POST
	@Path("getMailBySetting")
	public List<ApprovalStatusMailTempDto> findBySetting() {
		return approvalMailTempFinder.findBySetting();
	}

	@POST
	@Path("registerMail")
	public void registerMail(List<ApprovalStatusMailTempCommand> command) {
		registerApprovalStatusMailTempCommandHandler.handle(command);
	}

	@POST
	@Path("getEmpMail")
	public EmployeeEmailDto getEmpMail() {
		return null;
	}
	
	@POST
	@Path("sendTestMail/{mailType}")
	public SendMailResultOutput sendTestMail(@PathParam("mailType") int mailType) {
		return approvalMailTempFinder.sendTestMail(mailType);
	}
	
	/**
	 * Find all closure
	 * 
	 * @return the list
	 */
	@POST
	@Path("findAllClosure")
	public ApprovalComfirmDto findAllClosure() {
		return this.finder.findAllClosure();
	}
	
	/**
	 * Find all closure
	 * 
	 * @return the list
	 */
	@POST
	@Path("getApprovalStatusPerior/{closureId}/{closureDate}")
	public ApprovalStatusPeriorDto getApprovalStatusPerior(@PathParam("closureId") int closureId, @PathParam("closureDate") int closureDate) {
		return this.finder.getApprovalStatusPerior(closureId, closureDate);
	}
	
	/**
	 * getAppSttByWorkpace
	 */
	@POST
	@Path("getAppSttByWorkpace")
	public List<ApprovalSttAppOutput> getAppSttByWorkpace(ApprovalStatusActivityData param){
		return finder.getAppSttByWorkpace(param);
	}
	
/*	@POST
	@Path("getCheckSendMail/")
	public List<String> getAppSttSendingUnapprovedMail(int empId) {
		return this.finder.getAppSttSendingUnapprovedMail(listAppSttApp);
	}*/
	
}
