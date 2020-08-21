package nts.uk.screen.at.app.kaf022.find;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto;
import nts.uk.ctx.at.request.app.find.applicationreflect.AppReflectExeConditionDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.otrestappcommon.OvertimeRestAppCommonSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.DisplayReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.shared.app.find.ot.frame.OvertimeWorkFrameFindDto;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.overtimeworkapplycation.OvertimeWorkApplicationReflectDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameExport;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingDto;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class DtoKaf022 {
	public List<ClosureHistoryFindDto> allClosure;

	public StampRequestSettingDto stampReq;
	public GoBackDirectlyCommonSettingDto goBack;
	public AppOvertimeSettingDto appOt;
	public HdAppSetDto hdSet;
	public AppWorkChangeSetDto appChange;
	public TripRequestSetDto tripReq;
	public WithdrawalAppSetDto wdApp;
	public TimeHdAppSetDto timeHd;
	public WithDrawalReqSetDto wdReq;
	public LateEarlyRequestDto lateEarly;

	// A14
	public ApprovalSettingDto approvalSettingDto;
	// B8 -> B26
	public OvertimeRestAppCommonSetDto otRestAppCom;
	// G10 -> G23
	public OvertimeRestAppCommonSetDto otRestApp7;

	// refactor 4
	public ApplicationSettingDto applicationSetting;
	public List<DisplayReasonDto> reasonDisplaySettings;
	public List<StandardMenuNameExport> menus;
	public JobAssignSettingDto jobAssign;
	public AppReflectExeConditionDto appReflectCondition;
	public Integer nightOvertimeReflectAtr;
//	public List<OvertimeWorkFrameFindDto> overtimeWorkFrames;
	public OvertimeAppSetDto overtimeAppSetting;
	public OvertimeWorkApplicationReflectDto overtimeAppReflect;
}
