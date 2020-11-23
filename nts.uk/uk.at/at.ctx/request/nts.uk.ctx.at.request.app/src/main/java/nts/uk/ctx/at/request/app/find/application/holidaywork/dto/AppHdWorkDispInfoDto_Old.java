package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeRestAppCommonSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetDto;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput_Old;
import nts.uk.ctx.at.shared.app.find.workdayoff.frame.WorkdayoffFrameFindDto;

@NoArgsConstructor
public class AppHdWorkDispInfoDto_Old {
	
	/**
	 * 申請表示情報
	 */
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 休出申請指示
	 */
	public WithdrawalAppSetDto withdrawalAppSet;
	
	/**
	 * 申請用時間外労働時間
	 */
	public AgreeOverTimeDto agreeOverTimeOutput;
	
	/**
	 * 休出時間枠
	 */
	public List<WorkdayoffFrameFindDto> breaktimeFrames;
	
	/**
	 * 乖離理由の入力を利用する
	 */
	public boolean useInputDivergenceReason;
	
	/**
	 * 乖離理由の選択肢を利用する
	 */
	public boolean useComboDivergenceReason;
	
	/**
	 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
	 */
	public HdWorkDispInfoWithDateDto_Old hdWorkDispInfoWithDateOutput;
	
	/**
	 * 残業時間枠
	 */
	public String overtimeFrame;
	
	/**
	 * フレックス時間を表示する区分
	 */
	public boolean dispFlexTime;
	
	/**
	 * 乖離理由の選択肢
	 */
	public List<DivergenceReasonDto> comboDivergenceReason;
	
	public OvertimeRestAppCommonSettingDto overtimeRestAppCommonSettingDto;
	
	public PreAppCheckResult preAppCheckResult;
	
	public ActualStatusCheckResult actualStatusCheckResult;
	
	public static AppHdWorkDispInfoDto_Old fromDomain(AppHdWorkDispInfoOutput_Old appHdWorkDispInfoOutput) {
		AppHdWorkDispInfoDto_Old result = new AppHdWorkDispInfoDto_Old();
		result.appDispInfoStartupOutput = AppDispInfoStartupDto.fromDomain(appHdWorkDispInfoOutput.getAppDispInfoStartupOutput());
		result.withdrawalAppSet = WithdrawalAppSetDto.convertToDto(appHdWorkDispInfoOutput.getWithdrawalAppSet());
		if(appHdWorkDispInfoOutput.getAgreeOverTimeOutput() != null) {
			result.agreeOverTimeOutput = AgreeOverTimeDto.fromDomain(appHdWorkDispInfoOutput.getAgreeOverTimeOutput());
		}
		result.breaktimeFrames = appHdWorkDispInfoOutput.getBreaktimeFrames().stream().map(x -> {
			WorkdayoffFrameFindDto dto = new WorkdayoffFrameFindDto();
			x.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
		result.useInputDivergenceReason = appHdWorkDispInfoOutput.isUseInputDivergenceReason();
		result.useComboDivergenceReason = appHdWorkDispInfoOutput.isUseComboDivergenceReason();
		result.hdWorkDispInfoWithDateOutput = HdWorkDispInfoWithDateDto_Old.fromDomain(appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput());
		result.overtimeFrame = appHdWorkDispInfoOutput.getOvertimeFrame();
		result.dispFlexTime = appHdWorkDispInfoOutput.isDispFlexTime();
		if(appHdWorkDispInfoOutput.getComboDivergenceReason() != null) {
			result.comboDivergenceReason = appHdWorkDispInfoOutput.getComboDivergenceReason().map(item -> 
				item.stream().map(x -> DivergenceReasonDto.fromDomain(x)).collect(Collectors.toList())
			).orElse(Collections.emptyList());
		}
		result.overtimeRestAppCommonSettingDto = OvertimeRestAppCommonSettingDto.convertToDto(appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting());
		result.preAppCheckResult = appHdWorkDispInfoOutput.getPreAppCheckResult();
		result.actualStatusCheckResult = appHdWorkDispInfoOutput.getActualStatusCheckResult();
		return result;
	}
}
