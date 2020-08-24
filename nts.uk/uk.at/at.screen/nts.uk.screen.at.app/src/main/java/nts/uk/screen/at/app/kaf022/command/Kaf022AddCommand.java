package nts.uk.screen.at.app.kaf022.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppTypeBfCommand;
import nts.uk.ctx.at.request.app.command.application.triprequestsetting.TripRequestSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSetCommand;
import nts.uk.ctx.at.request.app.command.applicationreflect.AppReflectExeConditionCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival.LateEarlyRequestCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
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
import nts.uk.ctx.at.request.app.command.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingCommand;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation.OvertimeWorkApplicationReflectCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.ApprovalSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.JobAssignSettingCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.JobtitleSearchSetCommand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kaf022AddCommand {
	private ApplicationSettingCommand applicationSetting;
	private List<DisplayReasonCommand> reasonDisplaySettings;

	private int nightOvertimeReflectAtr;

	private int approvalByPersonAtr;
	private int includeConcurrentPersonel;

	private AppReflectExeConditionCommand appReflectCondition;

	private OvertimeAppSetCommand overtimeApplicationSetting;
	private OvertimeWorkApplicationReflectCommand overtimeApplicationReflect;

	private AppWorkChangeSetCommand appWorkChangeSetting;

	private int goBackReflectAtr;

	private int lateEarlyCancelAtr;
	private int lateEarlyClearAlarmAtr;
}
