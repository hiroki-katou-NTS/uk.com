package nts.uk.screen.at.app.kaf022.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.application.applicationlist.UpdateAppTypeBfCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.application.triprequestsetting.UpdateTripRequestSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.InsertAppWorkChangeSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival.UpdateLateEarReqHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.UpdateAppOvertimeSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdappset.UpdateTimeHdAppSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset.UpdateWDAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting.UpdateAppCommonSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.UpdateProxyAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.displayname.UpdateAppDispNameCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateApprovalTempCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateContentOfRemandMailCmdHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateMailHdInstructionCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.UpdateMailOtInstructionCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.mailcontenturlsetting.UpdateUrlEmbeddedCmdHandler;
import nts.uk.ctx.at.request.app.command.setting.company.otrestappcommon.UpdateOvertimeRestAppCommonSetCmdHandler;
import nts.uk.ctx.at.request.app.command.setting.company.request.apptypesetting.UpdateDisplayReasonCmdHandler;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.UpdateStampRequestSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.vacationapplicationsetting.UpdateHdAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.request.UpdateApplicationDeadlineCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon.UpdateGoBackDirectlyCommonSettingCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.UpdateApprovalSettingCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.UpdateJobAssignSettingCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateJobtitleSearchSetCommandHandler;

@Stateless
@Transactional
public class UpdateKaf022AddCommandHandler extends CommandHandler<Kaf022AddCommand>{
	// 申請締切設定
	@Inject
	private UpdateApplicationDeadlineCommandHandler updateApp;
	// 申請一覧共通設定
	@Inject 
	private UpdateAppCommonSetCommandHandler updateAppCom;
	// 代行申請で利用できる申請設定
	@Inject
	private UpdateProxyAppSetCommandHandler updatePro;
	// 休出指示のメール内容
	@Inject
	private UpdateMailHdInstructionCommandHandler updateMailHd;
	// 残業指示のメール内容
	@Inject
	private UpdateMailOtInstructionCommandHandler updateMailOt;
	// 申請承認メールテンプレート
	@Inject
	private UpdateApprovalTempCommandHandler updateAppTemp;
	// 申請設定/申請承認設定/申請表示設定/申請制限設定 // YEN merged
	@Inject
	private UpdateApplicationSettingCommandHandler updateAppliSet;
	// 申請表示名設定
	@Inject 
	private UpdateAppDispNameCommandHandler updateAppDisp;
	// 打刻申請設定
	@Inject
	private UpdateStampRequestSettingCommandHandler updateStamp;
	// 直行直帰申請共通設定
	@Inject
	private UpdateGoBackDirectlyCommonSettingCommandHandler updateGoBack;
	// 残業申請設定
	@Inject 
	private UpdateAppOvertimeSettingCommandHandler updateAppOver;
	// 休暇申請設定
	@Inject
	private UpdateHdAppSetCommandHandler updateHd;
	// 勤務変更申請設定
	@Inject
	private InsertAppWorkChangeSetCommandHandler updateAppWork;
	// 出張申請設定
	@Inject
	private UpdateTripRequestSetCommandHandler updateTrip;
	// 休出申請設定, 休出事後反映設定, 休出事前反映設定
	@Inject
	private UpdateWDAppSetCommandHandler updateWd;
	// 時間休申請設定
	@Inject
	private UpdateTimeHdAppSetHandler updateTime;
	// 振休振出申請設定
	@Inject
	private UpdateWithDrawalReqSetHandler updateWdReq;
	//遅刻早退取消申請設定
	@Inject
	private UpdateLateEarReqHandler updateLateEar;
	// 
	@Inject
	private UpdateAppTypeBfCommandHandler updateBf;
	
	@Inject
	private UpdateJobtitleSearchSetCommandHandler updateJobSearch;
	
	@Inject
	private UpdateJobAssignSettingCommandHandler updateJobAssign;
	
	@Inject
	private UpdateApprovalSettingCommandHandler updateAppro;
	// B8->12 残業休出申請共通設定
	@Inject
	private UpdateOvertimeRestAppCommonSetCmdHandler updateOtRest;
	// 差し戻しのメール内容
	@Inject
	private UpdateContentOfRemandMailCmdHandler updateMail;
	// メール内容のURL埋込設定
	@Inject 
	private UpdateUrlEmbeddedCmdHandler updateurl;
	// 申請理由表示
	@Inject
	private UpdateDisplayReasonCmdHandler updateDplReason;
	
	@Override
	protected void handle(CommandHandlerContext<Kaf022AddCommand> context) {
		Kaf022AddCommand kaf022 = context.getCommand();
		// update list command object
		this.updateApp.handle(kaf022.getAppDead());
		
//		this.updateAppSet.handle(kaf022.getAppSet());
		
		this.updateAppCom.handle(kaf022.getAppCommon());
		
		this.updatePro.handle(kaf022.getProxy());
		
		this.updateMailHd.handle(kaf022.getMailHd());
		
		this.updateMailOt.handle(kaf022.getMailOt());
		
		this.updateAppTemp.handle(kaf022.getAppTemp());
		
		// update list command object
		this.updateAppDisp.handle(kaf022.getAppName());
		// update list command object
//		this.updateHdApp.handle(kaf022.getHdDisp());
		
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
		
		this.updateBf.handle(kaf022.getAppBf());
		// update list for A15_4
		this.updateJobSearch.handle(kaf022.getJobSearch());
		// A14
		this.updateJobAssign.handle(kaf022.getJobAssign());
		this.updateAppro.handle(kaf022.getApprovalSet());
		
		this.updateAppliSet.handle(kaf022.getAppliSet());
		this.updateOtRest.handle(kaf022.getOtRest());
		// G
		this.updateOtRest.handle(kaf022.getOtRestApp7());
		// A16_14, A16_15
		this.updateMail.handle(kaf022.getContentMail());
		
		this.updateurl.handle(kaf022.getUrl());
		// A8_36 -> A8_43
		this.updateDplReason.handle(kaf022.getDplReasonCmd());
	}
}
