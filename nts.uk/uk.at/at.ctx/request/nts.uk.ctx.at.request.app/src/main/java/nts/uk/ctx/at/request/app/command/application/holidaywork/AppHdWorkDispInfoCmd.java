package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AgreeOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeRestAppCommonSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput_Old;
import nts.uk.ctx.at.shared.app.command.workdayoff.frame.WorkdayoffFrameCommandDto;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

@NoArgsConstructor
@AllArgsConstructor
public class AppHdWorkDispInfoCmd {
	
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
	public List<WorkdayoffFrameCommandDto> breaktimeFrames;
	
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
	public HdWorkDispInfoWithDateCmd hdWorkDispInfoWithDateOutput;
	
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
	
	public AppHdWorkDispInfoOutput_Old toDomain() {
		AppHdWorkDispInfoOutput_Old result = new AppHdWorkDispInfoOutput_Old();
		result.setAppDispInfoStartupOutput(appDispInfoStartupOutput.toDomain());
		result.setWithdrawalAppSet(withdrawalAppSet.toDomain());
		result.setAgreeOverTimeOutput(null);
		result.setBreaktimeFrames(breaktimeFrames.stream().map(x -> new WorkdayoffFrame(x)).collect(Collectors.toList()));
		result.setUseInputDivergenceReason(useInputDivergenceReason);
		result.setUseComboDivergenceReason(useComboDivergenceReason);
		result.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput.toDomain());
		result.setOvertimeFrame(overtimeFrame);
		result.setDispFlexTime(dispFlexTime);
		result.setComboDivergenceReason(Optional.empty());
		result.setOvertimeRestAppCommonSetting(overtimeRestAppCommonSettingDto.toDomain());
		return result;
	}
}
