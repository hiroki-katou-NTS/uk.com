package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.SEmpHistImportDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.app.find.setting.employment.appemploymentsetting.AppEmploymentSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApprovalFunctionSetDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
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
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.primitive.Memo;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDispInfoWithDateCmd {
	/**
	 * ????????????????????????
	 */
	private ApprovalFunctionSetDto approvalFunctionSet;
	
	/**
	 * ??????????????????
	 */
	private int prePostAtr;
	
	/**
	 * ?????????
	 */
	private String baseDate;
	
	/**
	 * ?????????????????????????????????
	 */
	private SEmpHistImportDto empHistImport;
	
	/**
	 * ?????????????????????????????????
	 */
	private int appDeadlineUseCategory;
	
	/**
	 * ???????????????????????????
	 */
	private AppEmploymentSetDto opEmploymentSet;
	
	/**
	 * ???????????????
	 */
	private List<ApprovalPhaseStateForAppDto> opListApprovalPhaseState;
	
	/**
	 * ??????????????????????????????
	 */
	private List<MsgErrorOutput> opMsgErrorLst;
	
	/**
	 * ????????????????????????
	 */
	private List<ActualContentDisplayDto> opActualContentDisplayLst;
	
	/**
	 * ??????????????????????????????
	 */
	private List<PreAppContentDispCmd> opPreAppContentDispDtoLst;
	
	/**
	 * ?????????????????????
	 */
	private String opAppDeadline;
	
	/**
	 * ????????????????????????
	 */
	private List<WorkTimeSettingDto> opWorkTimeLst;
	
	/**
	 * ????????????????????????
	 */
	private List<String> errorMsgLst;
	
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
		if(!CollectionUtil.isEmpty(opMsgErrorLst)) {
			appDispInfoWithDateOutput.setOpMsgErrorLst(Optional.of(opMsgErrorLst));
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
			appDispInfoWithDateOutput.setOpWorkTimeLst(Optional.of(opWorkTimeLst.stream().map(x -> AppDispInfoWithDateCmd.toDomainWorkTime(x)).collect(Collectors.toList())));
		}
		if(!CollectionUtil.isEmpty(errorMsgLst)) {
			appDispInfoWithDateOutput.setErrorMsgLst(errorMsgLst);
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
//				new ColorCode(workTimeSetting.colorCode), 
				new WorkTimeDisplayName(
						new WorkTimeName(workTimeSetting.workTimeDisplayName.workTimeName), 
						new WorkTimeAbName(workTimeSetting.workTimeDisplayName.workTimeAbName)), 
				new Memo(workTimeSetting.memo), 
				new WorkTimeNote(workTimeSetting.note));
	}
}
