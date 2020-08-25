package nts.uk.screen.at.app.kaf022.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.application.applicationlist.UpdateAppTypeBfCommandHandler;
import nts.uk.ctx.at.request.app.command.application.common.UpdateApplicationSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.application.triprequestsetting.UpdateTripRequestSetCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.InsertAppWorkChangeSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival.UpdateLateEarReqHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.UpdateAppOvertimeSettingCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdappset.UpdateTimeHdAppSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset.UpdateWDAppSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.workchange.SaveAppWorkChangeSetCommandHandler;
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
import nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon.UpdateGoBackDirectlyCommonSettingCommandHandler;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExeConditionRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.ApplicationStampSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflect;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackReflectRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation.OvertimeWorkApplicationReflect;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.ApprovalSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.JobAssignSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.UpdateApprovalSettingCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.UpdateJobAssignSettingCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateJobtitleSearchSetCommandHandler;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateKaf022AddCommandHandler extends CommandHandler<Kaf022AddCommand>{
	// 申請締切設定
//	@Inject
//	private UpdateApplicationDeadlineCommandHandler updateApp;
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

	@Inject
	private ApplicationSettingRepository applicationSettingRepo;

	@Inject
	private DisplayReasonRepository displayReasonRepo;

	@Inject
	private AppReflectExeConditionRepository appReflectConditionRepo;

	@Inject
    private OvertimeAppSetRepository overtimeAppSetRepo;

	@Inject
	private SaveAppWorkChangeSetCommandHandler saveAppWorkChangeSetCommandHandler;

	@Inject
	private GoBackReflectRepository goBackReflectRepo;

	@Inject
	private LateEarlyCancelAppSetRepository lateEarlyCancelRepo;

	@Inject
	private ApplicationStampSettingRepository appStampSettingRepo;

	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepo;

	@Override
	protected void handle(CommandHandlerContext<Kaf022AddCommand> context) {
		String companyId = AppContexts.user().companyId();
		Kaf022AddCommand kaf022 = context.getCommand();
		ApplicationSetting applicationSetting = kaf022.getApplicationSetting().toDomain(companyId);
		List<DisplayReason> reasonDisplaySettings = kaf022.getReasonDisplaySettings().stream().map(r -> r.toDomain(companyId)).collect(Collectors.toList());
		applicationSettingRepo.save(applicationSetting, reasonDisplaySettings, kaf022.getNightOvertimeReflectAtr());
		displayReasonRepo.saveHolidayAppReason(companyId, reasonDisplaySettings);

		updateJobAssign.handle(new JobAssignSettingCommand(BooleanUtils.toBoolean(kaf022.getIncludeConcurrentPersonel())));
		updateAppro.handle(new ApprovalSettingCommand(kaf022.getApprovalByPersonAtr()));
		appReflectConditionRepo.save(kaf022.getAppReflectCondition().toDomain(companyId));

		overtimeAppSetRepo.saveOvertimeAppSet(kaf022.getOvertimeApplicationSetting().toDomain(companyId), kaf022.getOvertimeApplicationReflect().toDomain());

		saveAppWorkChangeSetCommandHandler.handle(kaf022.getAppWorkChangeSetting());

		if (goBackReflectRepo.findByCompany(companyId).isPresent()) {
			goBackReflectRepo.update(GoBackReflect.create(companyId, kaf022.getGoBackReflectAtr()));
		} else {
			goBackReflectRepo.add(GoBackReflect.create(companyId, kaf022.getGoBackReflectAtr()));
		}

		lateEarlyCancelRepo.save(
				companyId,
				new LateEarlyCancelAppSet(companyId, EnumAdaptor.valueOf(kaf022.getLateEarlyCancelAtr(), CancelAtr.class)),
				new LateEarlyCancelReflect(companyId, BooleanUtils.toBoolean(kaf022.getLateEarlyClearAlarmAtr()))
		);

		appStampSettingRepo.save(companyId, kaf022.getAppStampSetting().toDomain(companyId), kaf022.getAppStampReflect().toDomain(companyId));

		approvalListDispSetRepo.save(kaf022.getApprovalListDisplaySetting().toDomain(companyId));
	}

	private void oldmethod (CommandHandlerContext<Kaf022AddCommand> context) {
		Kaf022AddCommand kaf022 = context.getCommand();
		// update list command object
		// this.updateApp.handle(kaf022.getAppDead());

//		this.updateAppSet.handle(kaf022.getAppSet());

//		this.updateAppCom.handle(kaf022.getAppCommon());
//
//		this.updatePro.handle(kaf022.getProxy());
//
//		this.updateMailHd.handle(kaf022.getMailHd());
//
//		this.updateMailOt.handle(kaf022.getMailOt());
//
//		this.updateAppTemp.handle(kaf022.getAppTemp());
//
//		// update list command object
//		this.updateAppDisp.handle(kaf022.getAppName());
//		// update list command object
////		this.updateHdApp.handle(kaf022.getHdDisp());
//
//		this.updateStamp.handle(kaf022.getStampReq());
//
//		this.updateGoBack.handle(kaf022.getGoBack());
//
//		this.updateAppOver.handle(kaf022.getAppOt());
//
//		this.updateHd.handle(kaf022.getHdSet());
//
//		this.updateAppWork.handle(kaf022.getAppChange());
//
//		this.updateTrip.handle(kaf022.getTripReq());
//
//		this.updateWd.handle(kaf022.getWdApp());
//
//		this.updateTime.handle(kaf022.getTimeHd());
//
//		this.updateWdReq.handle(kaf022.getWdReq());
//
//		this.updateLateEar.handle(kaf022.getLateEarly());
//
//		this.updateBf.handle(kaf022.getAppBf());
//		// update list for A15_4
//		this.updateJobSearch.handle(kaf022.getJobSearch());
//		// A14
//		this.updateJobAssign.handle(kaf022.getJobAssign());
//		this.updateAppro.handle(kaf022.getApprovalSet());
//
////		this.updateAppliSet.handle(kaf022.getAppliSet());
//		this.updateOtRest.handle(kaf022.getOtRest());
//		// G
//		this.updateOtRest.handle(kaf022.getOtRestApp7());
//		// A16_14, A16_15
//		this.updateMail.handle(kaf022.getContentMail());
//
//		this.updateurl.handle(kaf022.getUrl());
//		// A8_36 -> A8_43
//		this.updateDplReason.handle(kaf022.getDplReasonCmd());
	}
}
