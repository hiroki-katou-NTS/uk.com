package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeDivisionDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

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
	public GeneralDate baseDate;
	
	/**
	 * 表示する実績内容
	 */
	public List<AchievementOutput> achievementOutputLst;
	
	/**
	 * 表示する事前申請内容
	 */
	public List<AppDetailContent> appDetailContentLst;
	
	public static AppDispInfoWithDateDto fromDomain(AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		AppDispInfoWithDateDto appDispInfoWithDateDto = new AppDispInfoWithDateDto();
		appDispInfoWithDateDto.approvalFunctionSet = ApprovalFunctionSettingDto.convertToDto(appDispInfoWithDateOutput.getApprovalFunctionSet());
		appDispInfoWithDateDto.employmentSet = AppEmploymentSettingDto.fromDomain(appDispInfoWithDateOutput.getEmploymentSet());
		appDispInfoWithDateDto.workTimeLst = appDispInfoWithDateOutput.getWorkTimeLst().stream()
				.map(x -> AppDispInfoWithDateDto.fromDomainWorkTime(x)).collect(Collectors.toList());
		appDispInfoWithDateDto.listApprovalPhaseState = appDispInfoWithDateOutput.getApprovalRootState()
				.getListApprovalPhaseState().stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList());
		appDispInfoWithDateDto.errorFlag = appDispInfoWithDateOutput.getErrorFlag().value;
		appDispInfoWithDateDto.prePostAtr = appDispInfoWithDateOutput.getPrePostAtr().value;
		appDispInfoWithDateDto.baseDate = appDispInfoWithDateOutput.getBaseDate();
		appDispInfoWithDateDto.achievementOutputLst = appDispInfoWithDateOutput.getAchievementOutputLst();
		appDispInfoWithDateDto.appDetailContentLst = appDispInfoWithDateOutput.getAppDetailContentLst();
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
}
