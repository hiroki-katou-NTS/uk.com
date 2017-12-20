package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class RegisterAppStampCommandHandler extends CommandHandlerWithResult<AppStampCmd, String> {
	
	private final String DATE_FORMAT = "yyyy/MM/dd";

	@Inject
	private AppStampNewDomainService applicationStampNewDomainService;

	@Override
	protected String handle(CommandHandlerContext<AppStampCmd> context) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		AppStampCmd appStampCmd = context.getCommand();
		String applicationReason = "";
		if(!appStampCmd.getTitleReason().isEmpty() 
				|| !appStampCmd.getDetailReason().isEmpty()) {
			applicationReason = !appStampCmd.getTitleReason().isEmpty()? appStampCmd.getTitleReason() + System.lineSeparator() + appStampCmd.getDetailReason() : appStampCmd.getDetailReason();
		}
		
		AppStamp appStamp = null;
		List<AppApprovalPhase> appApprovalPhases = context.getCommand().getAppApprovalPhaseCmds()
				.stream().map(appApprovalPhaseCmd -> new AppApprovalPhase(
						companyID, 
						"", 
						"", 
						EnumAdaptor.valueOf(appApprovalPhaseCmd.approvalForm, ApprovalForm.class), 
						appApprovalPhaseCmd.dispOrder, 
						ApprovalAtr.UNAPPROVED, 
						appApprovalPhaseCmd.getListFrame().stream().map(approvalFrame -> new ApprovalFrame(
								companyID, 
								"", 
								approvalFrame.dispOrder, 
								approvalFrame.listApproveAccepted.stream().map(approveAccepted -> ApproveAccepted.createFromJavaType(
										companyID, 
										"", 
										approveAccepted.approverSID,
										ApprovalAtr.UNAPPROVED.value,
										approveAccepted.confirmATR,
										null,
										approveAccepted.reason,
										approveAccepted.representerSID
										)).collect(Collectors.toList())
								)).collect(Collectors.toList())
						))
				.collect(Collectors.toList());
		StampRequestMode stampRequestMode = EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class);
		switch(stampRequestMode){
			case STAMP_GO_OUT_PERMIT: 
				appStamp = AppStamp.createFromJavaType(
					companyID, 
					PrePostAtr.PREDICT,
					GeneralDateTime.now(), 
					employeeID, 
					GeneralDate.fromString(appStampCmd.getApplicationDate(), DATE_FORMAT), 
					employeeID, 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					appStampCmd.getAppStampGoOutPermitCmds().stream().map(
						x -> new AppStampGoOutPermit(
								EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())
					).collect(Collectors.toList()),
					null,
					null,
					null);
				return applicationStampNewDomainService.appStampRegister(applicationReason, appStamp, appApprovalPhases);
			case STAMP_ADDITIONAL: 
				appStamp = AppStamp.createFromJavaType(
						companyID, 
						PrePostAtr.POSTERIOR,
						GeneralDateTime.now(),  
						employeeID, 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), DATE_FORMAT), 
						employeeID, 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					appStampCmd.getAppStampWorkCmds().stream().map(
						x -> new AppStampWork(
								EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
								x.getSupportCard(), 
								x.getSupportLocationCD(), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())	
					).collect(Collectors.toList()),
					null,
					null);
				return applicationStampNewDomainService.appStampRegister(applicationReason, appStamp, appApprovalPhases);
				
			case STAMP_CANCEL: 
				appStamp = AppStamp.createFromJavaType(
						companyID,  
						PrePostAtr.POSTERIOR,
						GeneralDateTime.now(),  
						employeeID, 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), DATE_FORMAT), 
						employeeID, 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					appStampCmd.getAppStampCancelCmds().stream().map(
						x -> new AppStampCancel(
								EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
								x.getStampFrameNo(), 
								x.getCancelAtr())	
					).collect(Collectors.toList()),
					null);
				return applicationStampNewDomainService.appStampRegister(applicationReason, appStamp, appApprovalPhases);
				
			case STAMP_ONLINE_RECORD: 
				appStamp = AppStamp.createFromJavaType(
						companyID, 
						PrePostAtr.POSTERIOR,
						GeneralDateTime.now(),  
						employeeID, 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), DATE_FORMAT), 
						employeeID, 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					null,
					new AppStampOnlineRecord(
							EnumAdaptor.valueOf(appStampCmd.getAppStampOnlineRecordCmd().getStampCombinationAtr(), AppStampCombinationAtr.class),
							appStampCmd.getAppStampOnlineRecordCmd().getAppTime()));
				return applicationStampNewDomainService.appStampRegister(applicationReason, appStamp, appApprovalPhases);
				
			case OTHER: 
				appStamp = AppStamp.createFromJavaType(
						companyID, 
						PrePostAtr.POSTERIOR,
						GeneralDateTime.now(),  
						employeeID, 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), DATE_FORMAT), 
						employeeID, 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					appStampCmd.getAppStampWorkCmds().stream().map(
						x -> new AppStampWork(
								EnumAdaptor.valueOf(x.getStampAtr(), AppStampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutAtr(), AppStampGoOutAtr.class), 
								x.getSupportCard(), 
								x.getSupportLocationCD(), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())	
					).collect(Collectors.toList()),
					null,
					null);
				return applicationStampNewDomainService.appStampRegister(applicationReason, appStamp, appApprovalPhases);
				
			default:
				return null;
			
		}
	}

}
