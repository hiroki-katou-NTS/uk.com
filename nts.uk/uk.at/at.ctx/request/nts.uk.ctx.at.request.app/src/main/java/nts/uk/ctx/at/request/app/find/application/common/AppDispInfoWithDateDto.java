package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AchievementDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.SEmpHistImportDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDivisionDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeNote;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSymbol;
import nts.uk.shr.com.primitive.Memo;

public class AppDispInfoWithDateDto {
	/**
	 * 申請承認機能設定
	 */
	public ApprovalFunctionSettingDto approvalFunctionSet;
	
	/**
	 * 雇用別申請承認設定
	 */
	public AppEmploymentSettingDto employmentSet;
	
	/**
	 * 就業時間帯の設定
	 */
	public List<WorkTimeSettingDto> workTimeLst;
	
	/**
	 * 承認ルート
	 */
	public List<ApprovalPhaseStateForAppDto> listApprovalPhaseState;
	
	/**
	 * 承認ルートエラー情報
	 */
	public int errorFlag;
	
	/**
	 * 事前事後区分
	 */
	public int prePostAtr;
	
	/**
	 * 基準日
	 */
	public String baseDate;
	
	/**
	 * 表示する実績内容
	 */
	public List<AchievementDto> achievementOutputLst;
	
	/**
	 * 表示する事前申請内容
	 */
	public List<AppDetailContent> appDetailContentLst;
	
	/**
	 * 社員所属雇用履歴を取得
	 */
	public SEmpHistImportDto empHistImport;
	
	public static AppDispInfoWithDateDto fromDomain(AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		AppDispInfoWithDateDto appDispInfoWithDateDto = new AppDispInfoWithDateDto();
		appDispInfoWithDateDto.approvalFunctionSet = ApprovalFunctionSettingDto.convertToDto(appDispInfoWithDateOutput.getApprovalFunctionSet());
		appDispInfoWithDateDto.employmentSet = AppEmploymentSettingDto.fromDomain(appDispInfoWithDateOutput.getEmploymentSet());
		appDispInfoWithDateDto.workTimeLst = appDispInfoWithDateOutput.getWorkTimeLst().stream()
				.map(x -> AppDispInfoWithDateDto.fromDomainWorkTime(x)).collect(Collectors.toList());
		appDispInfoWithDateDto.listApprovalPhaseState = appDispInfoWithDateOutput.getApprovalRootState() == null ? Collections.emptyList() : 
			appDispInfoWithDateOutput.getApprovalRootState().getListApprovalPhaseState().stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList());
		appDispInfoWithDateDto.errorFlag = appDispInfoWithDateOutput.getErrorFlag().value;
		appDispInfoWithDateDto.prePostAtr = appDispInfoWithDateOutput.getPrePostAtr().value;
		appDispInfoWithDateDto.baseDate = appDispInfoWithDateOutput.getBaseDate().toString("yyyy/MM/dd");
		appDispInfoWithDateDto.achievementOutputLst = appDispInfoWithDateOutput.getAchievementOutputLst().stream().map(x -> AchievementDto.convertFromAchievementOutput(x)).collect(Collectors.toList());
		appDispInfoWithDateDto.appDetailContentLst = appDispInfoWithDateOutput.getAppDetailContentLst();
		appDispInfoWithDateDto.empHistImport = SEmpHistImportDto.fromDomain(appDispInfoWithDateOutput.getEmpHistImport());
		return appDispInfoWithDateDto;
	}
	
	public static WorkTimeSettingDto fromDomainWorkTime(WorkTimeSetting workTimeSetting) {
		WorkTimeSettingDto workTimeSettingDto = new WorkTimeSettingDto();
		workTimeSettingDto.companyId = workTimeSetting.getCompanyId();
		workTimeSettingDto.worktimeCode = workTimeSetting.getWorktimeCode().v();
		workTimeSettingDto.workTimeDivision = WorkTimeDivisionDto.builder()
				.workTimeDailyAtr(workTimeSetting.getWorkTimeDivision().getWorkTimeDailyAtr().value)
				.workTimeMethodSet(workTimeSetting.getWorkTimeDivision().getWorkTimeMethodSet().value)
				.build();
		workTimeSettingDto.isAbolish = workTimeSetting.isAbolish();
		workTimeSettingDto.colorCode = workTimeSetting.getColorCode().v();
		workTimeSettingDto.workTimeDisplayName = WorkTimeDisplayNameDto.builder()
				.workTimeName(workTimeSetting.getWorkTimeDisplayName().getWorkTimeName().v())
				.workTimeAbName(workTimeSetting.getWorkTimeDisplayName().getWorkTimeAbName().v())
				.workTimeSymbol(workTimeSetting.getWorkTimeDisplayName().getWorkTimeSymbol().v())
				.build();
		workTimeSettingDto.memo = workTimeSetting.getMemo().v();
		workTimeSettingDto.note = workTimeSetting.getNote().v();
		return workTimeSettingDto;
	}
	
	public AppDispInfoWithDateOutput toDomain() {
		AppDispInfoWithDateOutput output = new AppDispInfoWithDateOutput();
		output.setApprovalFunctionSet(ApprovalFunctionSettingDto.createFromJavaType(approvalFunctionSet));
		output.setEmploymentSet(employmentSet == null ? null : employmentSet.toDomain());
		output.setWorkTimeLst(workTimeLst.stream().map(x -> AppDispInfoWithDateDto.toDomainWorkTime(x)).collect(Collectors.toList()));
		output.setApprovalRootState(new ApprovalRootStateImport_New(
				listApprovalPhaseState.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		output.setErrorFlag(EnumAdaptor.valueOf(errorFlag, ErrorFlagImport.class));
		output.setPrePostAtr(EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class));
		output.setBaseDate(GeneralDate.fromString(baseDate, "yyyy/MM/dd"));
		output.setAchievementOutputLst(achievementOutputLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		output.setAppDetailContentLst(appDetailContentLst);
		output.setEmpHistImport(empHistImport.toDomain());
		return output;
	}
	
	public static WorkTimeSetting toDomainWorkTime(WorkTimeSettingDto workTimeSetting) {
		return new WorkTimeSetting(
				workTimeSetting.companyId, 
				new WorkTimeCode(workTimeSetting.worktimeCode), 
				new WorkTimeDivision(
						EnumAdaptor.valueOf(workTimeSetting.workTimeDivision.workTimeDailyAtr, WorkTimeDailyAtr.class), 
						EnumAdaptor.valueOf(workTimeSetting.workTimeDivision.workTimeMethodSet, WorkTimeMethodSet.class)),
				AbolishAtr.valueOf(workTimeSetting.isAbolish ? 0 : 1),
				new ColorCode(workTimeSetting.colorCode), 
				new WorkTimeDisplayName(
						new WorkTimeName(workTimeSetting.workTimeDisplayName.workTimeName), 
						new WorkTimeAbName(workTimeSetting.workTimeDisplayName.workTimeAbName), 
						new WorkTimeSymbol(workTimeSetting.workTimeDisplayName.workTimeSymbol)), 
				new Memo(workTimeSetting.memo), 
				new WorkTimeNote(workTimeSetting.note));
	}
}
