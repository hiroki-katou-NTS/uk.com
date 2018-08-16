package nts.uk.screen.at.app.kaf022.find;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeBfDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeSetDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.AppCommonSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.ApprovalSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationsetting.ProxyAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.displayname.AppDispNameDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailapplicationapproval.ApprovalTempDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailcontenturlsetting.UrlEmbeddedDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.remandsetting.ContentOfRemandMailDto;
import nts.uk.ctx.at.request.app.find.setting.company.otrestappcommon.OvertimeRestAppCommonSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.applicationsetting.apptypesetting.DisplayReasonDto;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.vacationapplicationsetting.HdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.JobAssignSettingDto;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DtoKaf022 {
	public List<ClosureHistoryFindDto> allClosure;
	public ApprovalSetDto appSet;
	public AppCommonSetDto appCommon;
	public List<ProxyAppSetDto> proxy;
	public MailHdInstructionDto mailHd;
	public MailOtInstructionDto mailOt;
	public ApprovalTempDto appTemp;
	public ApplicationSettingDto appliSet;
	public List<AppDispNameDto> appName;
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
	// a7, 8
	public AppTypeBfDto appBf;
	// A14
	public JobAssignSettingDto jobAssign;
	public ApprovalSettingDto approvalSettingDto;
	// B8 -> B26
	public OvertimeRestAppCommonSetDto otRestAppCom;
	// G10 -> G23
	public OvertimeRestAppCommonSetDto otRestApp7;
	// A16_14, A16_15
	public ContentOfRemandMailDto contentMail;
	// A16_17
	public UrlEmbeddedDto url;
	//A8_36 -> A8_43
	public List<DisplayReasonDto> listDplReason;
}
