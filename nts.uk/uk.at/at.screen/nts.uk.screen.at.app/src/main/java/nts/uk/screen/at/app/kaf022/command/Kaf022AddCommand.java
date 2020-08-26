package nts.uk.screen.at.app.kaf022.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.stampsetting.AppStampSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSetCommand;
import nts.uk.ctx.at.request.app.command.applicationreflect.AppReflectExeConditionCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.otworkapply.OtWorkAppReflectCommand;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.stampapplication.StampAppReflectCommand;

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
	private OtWorkAppReflectCommand overtimeApplicationReflect;

	private AppWorkChangeSetCommand appWorkChangeSetting;

	private int goBackReflectAtr;

	private int lateEarlyCancelAtr;
	private int lateEarlyClearAlarmAtr;

	private AppStampSettingCommand appStampSetting;
	private StampAppReflectCommand appStampReflect;

	private ApprovalListDispSettingCommand approvalListDisplaySetting;
}
