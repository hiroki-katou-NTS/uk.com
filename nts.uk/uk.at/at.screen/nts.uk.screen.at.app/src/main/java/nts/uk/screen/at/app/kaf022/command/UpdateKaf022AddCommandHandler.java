package nts.uk.screen.at.app.kaf022.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.application.triprequestsetting.UpdateTripRequestSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.InsertAppWorkChangeSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival.UpdateLateEarReqHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.UpdateAppOvertimeSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdappset.UpdateTimeHdAppSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset.UpdateWDAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting.UpdateAppCommonSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting.UpdateApprovalSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.UpdateProxyAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.displayname.UpdateAppDispNameCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.displayname.UpdateHdAppDispNameCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateApprovalTempCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateMailHdInstructionCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateMailOtInstructionCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.UpdateStampRequestSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.vacationapplicationsetting.UpdateHdAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.request.UpdateApplicationDeadlineCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon.UpdateGoBackDirectlyCommonSettingCommandHandler;

@Stateless
@Transactional
public class UpdateKaf022AddCommandHandler extends CommandHandler<Kaf022AddCommand>{
	@Inject
	private UpdateApplicationDeadlineCommandHandler updateApp;
	
	@Inject
	private UpdateApprovalSetCommandHandler updateAppSet;
	
	@Inject 
	private UpdateAppCommonSetCommandHandler updateAppCom;
	
	@Inject
	private UpdateProxyAppSetCommandHandler updatePro;
	
	@Inject
	private UpdateMailHdInstructionCommandHandler updateMailHd;
	
	@Inject
	private UpdateMailOtInstructionCommandHandler updateMailOt;
	
	@Inject
	private UpdateApprovalTempCommandHandler updateAppTemp;
	
	@Inject
	private UpdateApplicationSettingCommandHandler updateAppliSet;
	
	@Inject 
	private UpdateAppDispNameCommandHandler updateAppDisp;
	
	@Inject
	private UpdateHdAppDispNameCommandHandler updateHdApp;
	
	@Inject
	private UpdateStampRequestSettingCommandHandler updateStamp;
	
	@Inject
	private UpdateGoBackDirectlyCommonSettingCommandHandler updateGoBack;
	
	@Inject 
	private UpdateAppOvertimeSettingCommandHandler updateAppOver;
	
	@Inject
	private UpdateHdAppSetCommandHandler updateHd;
	
	@Inject
	private InsertAppWorkChangeSetCommandHandler updateAppWork;
	
	@Inject
	private UpdateTripRequestSetCommandHandler updateTrip;
	
	@Inject
	private UpdateWDAppSetCommandHandler updateWd;
	
	@Inject
	private UpdateTimeHdAppSetHandler updateTime;
	
	@Inject
	private UpdateWithDrawalReqSetHandler updateWdReq;
	
	@Inject
	private UpdateLateEarReqHandler updateLateEar;
	
	@Override
	protected void handle(CommandHandlerContext<Kaf022AddCommand> context) {
		Kaf022AddCommand kaf022 = context.getCommand();
		
		this.updateApp.handle(kaf022.getAppDead());
		
		this.updateAppSet.handle(kaf022.getAppSet());
		
		this.updateAppCom.handle(kaf022.getAppCommon());
		
		this.updatePro.handle(kaf022.getProxy());
		
		this.updateMailHd.handle(kaf022.getMailHd());
		
		this.updateMailOt.handle(kaf022.getMailOt());
		
		this.updateAppTemp.handle(kaf022.getAppTemp());
		
		this.updateAppliSet.handle(kaf022.getAppliSet());
		
		this.updateAppDisp.handle(kaf022.getAppName());
		
		this.updateHdApp.handle(kaf022.getHdDisp());
		
		this.updateStamp.handle(kaf022.getStampReq());
		
		this.updateGoBack.handle(kaf022.getGoBack());
		
		this.updateAppOver.handle(kaf022.getAppOt());
		
		this.updateHd.handle(kaf022.getHdSet());
		
		this.updateAppWork.handle(kaf022.getAppChange());
		
		this.updateTrip.handle(kaf022.getTripReq());
		
		this.updateWd.handle(kaf022.getWdApp());
		
		this.updateTime.handle(kaf022.getTimeHd());
		
		this.updateWdReq.handle(kaf022.getWdReq());
		
		this.updateLateEar.handle(kaf022.getLateEarly());
	}

}
