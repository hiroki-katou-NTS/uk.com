package nts.uk.screen.at.app.kaf022.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppTypeBfCommand;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationSettingCommand;
import nts.uk.ctx.at.request.app.command.application.triprequestsetting.TripRequestSetCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival.LateEarlyRequestCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdappset.TimeHdAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkappset.WithdrawalAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting.AppCommonSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.ProxyAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.displayname.AppDispNameCommand;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.ApprovalTempCommand;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.ContentOfRemandMailCmd;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.MailHdInstructionCommand;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.MailOtInstructionCommand;
import nts.uk.ctx.at.request.app.command.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedCmd;
import nts.uk.ctx.at.request.app.command.setting.company.otrestappcommon.OvertimeRestAppCommonSetCmd;
import nts.uk.ctx.at.request.app.command.setting.company.request.apptypesetting.UpdateDisplayReasonCmd;
import nts.uk.ctx.at.request.app.command.setting.company.request.stamp.StampRequestSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.vacationapplicationsetting.HdAppSetCommand;
import nts.uk.ctx.at.request.app.command.setting.request.ApplicationDeadlineCommand;
import nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.ApprovalSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.JobAssignSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.JobtitleSearchSetCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kaf022AddCommand {
	private List<ApplicationDeadlineCommand> appDead;
//	private ApprovalSetCommand appSet; 
	private AppCommonSetCommand appCommon;
	private ProxyAppSetCommand proxy;
	private MailHdInstructionCommand mailHd;
	private MailOtInstructionCommand mailOt;
	private ApprovalTempCommand appTemp;
	private ApplicationSettingCommand appliSet;
	private List<AppDispNameCommand> appName;
//	private List<HdAppDispNameCommand> hdDisp;
	private StampRequestSettingCommand stampReq;
	private GoBackDirectlyCommonSettingCommand goBack;
	private AppOvertimeSettingCommand appOt;
	private HdAppSetCommand hdSet;
	private AppWorkChangeSetCommand appChange;
	private TripRequestSetCommand tripReq;
	private WithdrawalAppSetCommand wdApp;
	private TimeHdAppSetCommand timeHd;
	private UpdateWithDrawalReqSetCommand wdReq;
	private LateEarlyRequestCommand lateEarly;
	// a7, 8
	private AppTypeBfCommand appBf;
	// A15_4
	private List<JobtitleSearchSetCommand> jobSearch;
	// A14
	private JobAssignSettingCommand jobAssign;
	
	private ApprovalSettingCommand approvalSet;
	// B8 -> B26
	private OvertimeRestAppCommonSetCmd otRest;
	// G
	private OvertimeRestAppCommonSetCmd otRestApp7;
	// A16_14, A16_15
	private ContentOfRemandMailCmd contentMail;
	// A16_17
	private UrlEmbeddedCmd url;
	// A8_36 -> A8_43
	private UpdateDisplayReasonCmd dplReasonCmd;
}
