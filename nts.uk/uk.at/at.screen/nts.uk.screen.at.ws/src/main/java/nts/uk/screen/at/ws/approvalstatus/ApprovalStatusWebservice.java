package nts.uk.screen.at.ws.approvalstatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.RegisterApprovalStatusMailTempCommandHandler;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttEmpDateContentDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttEmpParam;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttExecutionDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttExecutionParam;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttMailDestParam;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSendMailInfoDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSendMailInfoParam;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSpecDeadlineDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSpecDeadlineSetDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempDto;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprSttEmpDateParam;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.InitDisplayOfApprovalStatus;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttComfirmSet;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.approvalstatus.ApprovalStatusService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Path("at/screen/application/approvalstatus")
@Produces("application/json")
public class ApprovalStatusWebservice {
	
	@Inject
	private ApprovalStatusService approvalStatusService;
	
	@Inject
	private RegisterApprovalStatusMailTempCommandHandler registerApprovalStatusMailTempCommandHandler;
	
	@POST
	@Path("getApprovalStatusActivation")
	public ApprSttSpecDeadlineDto getApprovalStatusActivation(Integer selectClosureId){
		return approvalStatusService.getApprovalStatusActivation(selectClosureId);
	}
	
	@POST
	@Path("changeClosure/{selectClosureId}")
	public ApprSttSpecDeadlineSetDto changeClosure(@PathParam("selectClosureId") Integer selectClosureId){
		return approvalStatusService.changeClosure(selectClosureId);
	}
	
	@POST
	@Path("getStatusExecution")
	public List<ApprSttExecutionDto> getStatusExecution(ApprSttExecutionParam param){
		ClosureId closureId = EnumAdaptor.valueOf(param.getClosureId(), ClosureId.class);
		YearMonth processingYm = new YearMonth(param.getProcessingYm());
		DatePeriod period = new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd"));
		InitDisplayOfApprovalStatus initDisplayOfApprovalStatus = param.getInitDisplayOfApprovalStatus();
		List<DisplayWorkplace> displayWorkplaceLst = param.getWkpInfoLst();
		List<String> employmentCDLst = param.getEmploymentCDLst();
		ApprSttComfirmSet apprSttComfirmSet = param.getApprSttComfirmSet();
		return approvalStatusService.getStatusExecution(closureId, processingYm, period, initDisplayOfApprovalStatus, displayWorkplaceLst, employmentCDLst, apprSttComfirmSet)
				.stream().map(x -> ApprSttExecutionDto.fromDomain(x)).collect(Collectors.toList());
	}
	
	@POST
	@Path("getApprSttStartByEmp")
	public List<ApprSttEmp> getApprSttStartByEmp(ApprSttEmpParam param){
		return approvalStatusService.getApprSttStartByEmp(
				param.getWkpID(),
				new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd")),
				param.getEmpPeriodLst().stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
	
	@POST
	@Path("getApprSttStartByEmpDate")
	public List<ApprSttEmpDateContentDto> getApprSttStartByEmpDate(ApprSttEmpDateParam param) {
		DatePeriod period = new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd"));
		return approvalStatusService.getApprSttAppContent(param.getEmpID(), Arrays.asList(period))
				.stream().map(x -> ApprSttEmpDateContentDto.fromDomain(x)).collect(Collectors.toList());
	}
	
	@POST
	@Path("getMailTemp")
	public List<ApprovalStatusMailTempDto> getMailTemp() {
		return approvalStatusService.getMailTemp();
	}
	
	@POST
	@Path("registerMail")
	public void registerMail(List<ApprovalStatusMailTempCommand> command) {
		registerApprovalStatusMailTempCommandHandler.handle(command);
	}
	
	@POST
	@Path("confirmSenderMail")
	public JavaTypeResult<String> confirmSenderMail() {
		return new JavaTypeResult<String>(approvalStatusService.confirmApprovalStatusMailSender());
	}
	
	@POST
	@Path("sendTestMail/{mailType}")
	public SendMailResultOutput sendTestMail(@PathParam("mailType") int mailType) {
		return approvalStatusService.sendTestMail(mailType);
	}
	
	@POST
	@Path("getEmpSendMailInfo")
	public ApprSttSendMailInfoDto getApprSttSendMailInfo(ApprSttSendMailInfoParam param) {
		ApprovalStatusMailType mailType = EnumAdaptor.valueOf(param.getMailType(), ApprovalStatusMailType.class);
		ClosureId closureId = EnumAdaptor.valueOf(param.getClosureId(), ClosureId.class);
		YearMonth processingYm = new YearMonth(param.getProcessingYm());
		DatePeriod period = new DatePeriod(GeneralDate.fromString(param.getStartDate(), "yyyy/MM/dd"), GeneralDate.fromString(param.getEndDate(), "yyyy/MM/dd"));
		List<DisplayWorkplace> displayWorkplaceLst = param.getWkpInfoLst();
		List<String> employmentCDLst = param.getEmploymentCDLst();
		return ApprSttSendMailInfoDto.fromDomain(
				approvalStatusService.getApprSttSendMailInfo(mailType, closureId, processingYm, period, displayWorkplaceLst, employmentCDLst), 
				param.getMailType());
	}
	
	@POST
	@Path("sendMailToDestination")
	public SendMailResultOutput sendMailToDestination(ApprSttMailDestParam param) {
		String companyId = AppContexts.user().companyId();
		ApprovalStatusMailTempCommand command = param.getCommand();
		ApprovalStatusMailTemp approvalStatusMailTemp = new ApprovalStatusMailTemp(
				companyId, 
				EnumAdaptor.valueOf(command.getMailType(), ApprovalStatusMailType.class), 
				command.getUrlApprovalEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				command.getUrlDayEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				command.getUrlMonthEmbed()==0 ? NotUseAtr.NOT_USE : NotUseAtr.USE, 
				new Subject(command.getMailSubject()), 
				new Content(command.getMailContent()));
		return approvalStatusService.sendMailToDestination(approvalStatusMailTemp, param.getWkpEmpMailLst());
	}
}
