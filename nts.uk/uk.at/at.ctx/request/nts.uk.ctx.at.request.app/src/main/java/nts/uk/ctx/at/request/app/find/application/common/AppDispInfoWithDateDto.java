package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.SEmpHistImportDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting.AppEmploymentSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApprovalFunctionSetDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
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
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.primitive.Memo;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDispInfoWithDateDto {
	/**
	 * 申請承認機能設定
	 */
	private ApprovalFunctionSetDto approvalFunctionSet;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 基準日
	 */
	private String baseDate;
	
	/**
	 * 社員所属雇用履歴を取得
	 */
	private SEmpHistImportDto empHistImport;
	
	/**
	 * 申請締め切り日利用区分
	 */
	private int appDeadlineUseCategory;
	
	/**
	 * 雇用別申請承認設定
	 */
	private AppEmploymentSetDto opEmploymentSet;
	
	/**
	 * 承認ルート
	 */
	private List<ApprovalPhaseStateForAppDto> opListApprovalPhaseState;
	
	/**
	 * 承認ルートエラー情報
	 */
	private Integer opErrorFlag;
	
	/**
	 * 表示する実績内容
	 */
	private List<ActualContentDisplayDto> opActualContentDisplayLst;
	
	/**
	 * 表示する事前申請内容
	 */
	private List<PreAppContentDispDto> opPreAppContentDispDtoLst;
	
	/**
	 * 申請締め切り日
	 */
	private String opAppDeadline;
	
	/**
	 * 就業時間帯の設定
	 */
	private List<WorkTimeSettingDto> opWorkTimeLst;
	
	public static AppDispInfoWithDateDto fromDomain(AppDispInfoWithDateOutput appDispInfoWithDateOutput) {
		return new AppDispInfoWithDateDto(
				ApprovalFunctionSetDto.fromDomain(appDispInfoWithDateOutput.getApprovalFunctionSet()), 
				appDispInfoWithDateOutput.getPrePostAtr().value, 
				appDispInfoWithDateOutput.getBaseDate().toString(), 
				SEmpHistImportDto.fromDomain(appDispInfoWithDateOutput.getEmpHistImport()), 
				appDispInfoWithDateOutput.getAppDeadlineUseCategory().value, 
				appDispInfoWithDateOutput.getOpEmploymentSet().map(x -> AppEmploymentSetDto.fromDomain(x)).orElse(null), 
				appDispInfoWithDateOutput.getOpListApprovalPhaseState()
					.map(x -> x.stream().map(y -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(y)).collect(Collectors.toList())).orElse(null), 
				appDispInfoWithDateOutput.getOpErrorFlag().map(x -> x.value).orElse(null), 
				appDispInfoWithDateOutput.getOpActualContentDisplayLst()
					.map(x -> x.stream().map(y -> ActualContentDisplayDto.fromDomain(y)).collect(Collectors.toList())).orElse(null),
				appDispInfoWithDateOutput.getOpPreAppContentDisplayLst().map(x -> x.stream().map(y -> PreAppContentDispDto.fromDomain(y)).collect(Collectors.toList())).orElse(null), 
				appDispInfoWithDateOutput.getOpAppDeadline().map(x -> x.toString()).orElse(null), 
				appDispInfoWithDateOutput.getOpWorkTimeLst().map(x -> x.stream()
					.map(y -> AppDispInfoWithDateDto.fromDomainWorkTime(y)).collect(Collectors.toList())).orElse(null));
	}
	
	private static WorkTimeSettingDto fromDomainWorkTime(WorkTimeSetting workTimeSetting) {
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
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = new AppDispInfoWithDateOutput(
				approvalFunctionSet.toDomain(), 
				EnumAdaptor.valueOf(prePostAtr, PrePostInitAtr.class), 
				GeneralDate.fromString(baseDate, "yyyy/MM/dd"), 
				empHistImport.toDomain(), 
				EnumAdaptor.valueOf(appDeadlineUseCategory, NotUseAtr.class));
		if(opEmploymentSet != null) {
			appDispInfoWithDateOutput.setOpEmploymentSet(Optional.of(opEmploymentSet.toDomain()));
		}
		if(opListApprovalPhaseState != null) {
			appDispInfoWithDateOutput.setOpListApprovalPhaseState(Optional.of(opListApprovalPhaseState.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		}
		if(opErrorFlag != null) {
			appDispInfoWithDateOutput.setOpErrorFlag(Optional.of(EnumAdaptor.valueOf(opErrorFlag, ErrorFlagImport.class)));
		}
		if(opActualContentDisplayLst != null) {
			appDispInfoWithDateOutput.setOpActualContentDisplayLst(Optional.of(opActualContentDisplayLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		}
		if(opPreAppContentDispDtoLst != null) {
			appDispInfoWithDateOutput.setOpPreAppContentDisplayLst(Optional.of(opPreAppContentDispDtoLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		}
		if(opAppDeadline != null) {
			appDispInfoWithDateOutput.setOpAppDeadline(Optional.of(GeneralDate.fromString(opAppDeadline, "yyyy/MM/dd")));
		}
		if(opWorkTimeLst != null) {
			appDispInfoWithDateOutput.setOpWorkTimeLst(Optional.of(opWorkTimeLst.stream().map(x -> AppDispInfoWithDateDto.toDomainWorkTime(x)).collect(Collectors.toList())));
		}
		return appDispInfoWithDateOutput;
	}
	
	private static WorkTimeSetting toDomainWorkTime(WorkTimeSettingDto workTimeSetting) {
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
