package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStamp;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampNewDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.ApplicationStampWork;
import nts.uk.ctx.at.request.dom.application.stamp.StampAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampCombinationAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampGoOutAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class RegisterApplicationStampCommandHandler extends CommandHandler<AppStampCmd> {

	@Inject
	private ApplicationStampNewDomainService applicationStampNewDomainService;

	@Override
	protected void handle(CommandHandlerContext<AppStampCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppStampCmd appStampCmd = context.getCommand();
		ApplicationStamp applicationStamp = null;
		StampRequestMode stampRequestMode = EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class);
		switch(stampRequestMode){
			case STAMP_GO_OUT_PERMIT: 
				applicationStamp = ApplicationStamp.createFromJavaType(
					companyID, 
					PrePostAtr.POSTERIOR,
					GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
					appStampCmd.getEnteredPerson(), 
					GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
					appStampCmd.getEmployeeID(), 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					appStampCmd.getAppStampGoOutPermitCmds().stream().map(
						x -> new ApplicationStampGoOutPermit(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutReason(), StampGoOutAtr.class), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())
					).collect(Collectors.toList()),
					null,
					null,
					null);
				applicationStampNewDomainService.appStampGoOutPermitRegister(applicationStamp);
				break;
			case STAMP_ADDITIONAL: 
				applicationStamp = ApplicationStamp.createFromJavaType(
						companyID, 
						PrePostAtr.POSTERIOR,
						GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
						appStampCmd.getEnteredPerson(), 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
						appStampCmd.getEmployeeID(), 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					appStampCmd.getAppStampWorkCmds().stream().map(
						x -> new ApplicationStampWork(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutReason(), StampGoOutAtr.class), 
								x.getSupportCard(), 
								x.getSupportLocationCD(), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())	
					).collect(Collectors.toList()),
					null,
					null);
				applicationStampNewDomainService.appStampGoOutPermitRegister(applicationStamp);
				break;
			case STAMP_CANCEL: 
				applicationStamp = ApplicationStamp.createFromJavaType(
						companyID,  
						PrePostAtr.POSTERIOR,
						GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
						appStampCmd.getEnteredPerson(), 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
						appStampCmd.getEmployeeID(), 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					appStampCmd.getAppStampCancelCmds().stream().map(
						x -> new ApplicationStampCancel(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								x.getCancelAtr())	
					).collect(Collectors.toList()),
					null);
				applicationStampNewDomainService.appStampGoOutPermitRegister(applicationStamp);
				break;
			case STAMP_ONLINE_RECORD: 
				applicationStamp = ApplicationStamp.createFromJavaType(
						companyID, 
						PrePostAtr.POSTERIOR,
						GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
						appStampCmd.getEnteredPerson(), 
						GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
						appStampCmd.getEmployeeID(), 
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					null,
					new ApplicationStampOnlineRecord(
							EnumAdaptor.valueOf(appStampCmd.getAppStampOnlineRecordCmd().getStampCombinationAtr(), StampCombinationAtr.class),
							appStampCmd.getAppStampOnlineRecordCmd().getAppTime()));
				applicationStampNewDomainService.appStampGoOutPermitRegister(applicationStamp);
				break;
			default:
				break;
			
		}
	}

}
