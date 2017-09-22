package nts.uk.ctx.at.request.app.command.application.stamp;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.stamp.command.AppStampCmd;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampDetailDomainService;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
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
public class UpdateAppStampCommandHandler extends CommandHandler<AppStampCmd>{
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppStampDetailDomainService applicationStampDetailDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<AppStampCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppStampCmd appStampCmd = context.getCommand();
		AppStamp appStamp = null;
		StampRequestMode stampRequestMode = EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class);
		switch(stampRequestMode){
			case STAMP_GO_OUT_PERMIT: 
				appStamp = new AppStamp(
					companyID, 
					appStampCmd.getAppID(),
					PrePostAtr.POSTERIOR,
					GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
					appStampCmd.getEnteredPerson(), 
					new AppReason(""),
					GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
					new AppReason(""),
					ApplicationType.STAMP_APPLICATION, 
					appStampCmd.getEmployeeID(),  
					ReflectPlanScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					ReflectPerScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					null,
					null,
					null,
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					appStampCmd.getAppStampGoOutPermitCmds().stream().map(
						x -> new AppStampGoOutPermit(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutAtr(), StampGoOutAtr.class), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())
					).collect(Collectors.toList()),
					null,
					null,
					null); 
				applicationStampDetailDomainService.appStampGoOutPermitUpdate(appStampCmd.getTitleReason(), appStampCmd.getDetailReason(), appStamp);
				break;
			case STAMP_ADDITIONAL: 
				appStamp = new AppStamp(
					companyID, 
					appStampCmd.getAppID(),
					PrePostAtr.POSTERIOR,
					GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
					appStampCmd.getEnteredPerson(), 
					new AppReason(""),
					GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
					new AppReason(""),
					ApplicationType.STAMP_APPLICATION, 
					appStampCmd.getEmployeeID(),  
					ReflectPlanScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					ReflectPerScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					null,
					null,
					null,
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					appStampCmd.getAppStampWorkCmds().stream().map(
						x -> new AppStampWork(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								EnumAdaptor.valueOf(x.getStampGoOutAtr(), StampGoOutAtr.class), 
								x.getSupportCard(), 
								x.getSupportLocationCD(), 
								x.getStartTime(), 
								x.getStartLocation(), 
								x.getEndTime(), 
								x.getEndLocation())	
					).collect(Collectors.toList()),
					null,
					null);
				applicationStampDetailDomainService.appStampWorkUpdate(appStampCmd.getTitleReason(), appStampCmd.getDetailReason(), appStamp);
				break;
			case STAMP_CANCEL: 
				appStamp = new AppStamp(
					companyID, 
					appStampCmd.getAppID(),
					PrePostAtr.POSTERIOR,
					GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
					appStampCmd.getEnteredPerson(), 
					new AppReason(""),
					GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
					new AppReason(""),
					ApplicationType.STAMP_APPLICATION, 
					appStampCmd.getEmployeeID(),  
					ReflectPlanScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					ReflectPerScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					null,
					null,
					null,
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					appStampCmd.getAppStampCancelCmds().stream().map(
						x -> new AppStampCancel(
								EnumAdaptor.valueOf(x.getStampAtr(), StampAtr.class), 
								x.getStampFrameNo(), 
								x.getCancelAtr())	
					).collect(Collectors.toList()),
					null);
				applicationStampDetailDomainService.appStampCancelUpdate(appStampCmd.getTitleReason(), appStampCmd.getDetailReason(), appStamp);
				break;
			case STAMP_ONLINE_RECORD: 
				appStamp = new AppStamp(
					companyID, 
					appStampCmd.getAppID(),
					PrePostAtr.POSTERIOR,
					GeneralDate.fromString(appStampCmd.getInputDate(), "yyyy/MM/dd"), 
					appStampCmd.getEnteredPerson(), 
					new AppReason(""),
					GeneralDate.fromString(appStampCmd.getApplicationDate(), "yyyy/MM/dd"), 
					new AppReason(""),
					ApplicationType.STAMP_APPLICATION, 
					appStampCmd.getEmployeeID(),  
					ReflectPlanScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO, 
					ReflectPerScheReason.NOTPROBLEM, 
					null, 
					ReflectPlanPerState.NOTREFLECTED, 
					ReflectPlanPerEnforce.NOTTODO,  
					null,
					null,
					null,
					EnumAdaptor.valueOf(appStampCmd.getStampRequestMode(), StampRequestMode.class),
					null,
					null,
					null,
					new AppStampOnlineRecord(
							EnumAdaptor.valueOf(appStampCmd.getAppStampOnlineRecordCmd().getStampCombinationAtr(), StampCombinationAtr.class),
							appStampCmd.getAppStampOnlineRecordCmd().getAppTime()));
				applicationStampDetailDomainService.appStampOnlineRecordUpdate(appStampCmd.getTitleReason(), appStampCmd.getDetailReason(), appStamp);
				break;
			default:
				break;
			
		}
	}
}
