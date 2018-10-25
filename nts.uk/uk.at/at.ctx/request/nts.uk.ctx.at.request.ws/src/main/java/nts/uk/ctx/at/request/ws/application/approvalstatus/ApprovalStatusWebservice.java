package nts.uk.ctx.at.request.ws.application.approvalstatus;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.RegisterApprovalStatusMailTempCommandHandler;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApplicationListDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusActivityData;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusByIdDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusFinder;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusPeriorDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalSttRequestContentDis;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.UnAppMailTransmisDto;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttByEmpListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;

@Path("at/request/application/approvalstatus")
@Produces("application/json")
public class ApprovalStatusWebservice extends WebService {
	@Inject
	private ApprovalStatusFinder approvalMailFinder;

	@Inject
	private RegisterApprovalStatusMailTempCommandHandler registerApprovalStatusMailTempCommandHandler;

	/** The finder. */
	@Inject
	private ApprovalStatusFinder finder;

	@POST
	@Path("getMailTemp")
	public List<ApprovalStatusMailTempDto> getMailTemp() {
		return approvalMailFinder.getMailTemp();
	}

	@POST
	@Path("registerMail")
	public void registerMail(List<ApprovalStatusMailTempCommand> command) {
		registerApprovalStatusMailTempCommandHandler.handle(command);
	}

	@POST
	@Path("confirmSenderMail")
	public JavaTypeResult<String> confirmSenderMail() {
		return new JavaTypeResult<String>(approvalMailFinder.confirmSenderMail());
	}
	
	@POST
	@Path("sendTestMail/{mailType}")
	public SendMailResultOutput sendTestMail(@PathParam("mailType") int mailType) {
		return approvalMailFinder.sendTestMail(mailType);
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
	public ApprovalStatusPeriorDto getApprovalStatusPerior(@PathParam("closureId") int closureId) {
		return this.finder.getApprovalStatusPerior(closureId);
	}
	
	/**
	 * getAppSttByWorkpace
	 */
	@POST
	@Path("getAppSttByWorkpace")
	public List<ApprovalSttAppOutput> getAppSttByWorkpace(ApprovalStatusActivityData param){
		return finder.getAppSttByWorkpace(param);
	}
	
	@POST
	@Path("getCheckSendMail")
	public List<String> getAppSttSendingUnapprovedMail(List<UnApprovalSendMail> listAppSttApp) {
		return this.finder.getAppSttSendingUnapprovedMail(listAppSttApp);
	}
	
	@POST
	@Path("exeSendUnconfirmedMail")
	public SendMailResultOutput exeSendUnconfirmedMail(UnAppMailTransmisDto unAppMailTransmis) {
		return this.finder.exeSendUnconfirmedMail(unAppMailTransmis);
	}
	
	@POST
	@Path("initApprovalSttByEmployee")
	public List<ApprovalSttByEmpListOutput> initApprovalSttByEmployee(ApprovalStatusByIdDto appSttById){
		return this.finder.initApprovalSttByEmployee(appSttById);
		
	}
	
	@POST
	@Path("initApprovalSttRequestContentDis")
	public ApplicationListDto initApprovalSttRequestContentDis(ApprovalSttRequestContentDis appSttContent) {
		return this.finder.initApprovalSttRequestContentDis(appSttContent);
	}
	
	@POST
	@Path("checkSendUnConfirMail")
	public boolean checkSendMailUnConf(List<UnApprovalSendMail> listWkp){
		return finder.checkSendUnConfMail(listWkp);
	}
}
