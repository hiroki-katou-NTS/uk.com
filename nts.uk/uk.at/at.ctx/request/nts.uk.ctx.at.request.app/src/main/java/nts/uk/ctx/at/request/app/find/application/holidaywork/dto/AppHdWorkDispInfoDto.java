package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.overtime.AppReflectOtHdWorkDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DivergenceReasonSelectDto;
import nts.uk.ctx.at.request.app.find.application.overtime.OvertimeWorkFrameDto;
import nts.uk.ctx.at.request.app.find.application.overtime.WorkdayoffFrameDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppHdWorkDispInfoDto {
	
	/**
	 * フレックス時間を表示する区分
	 */
	private boolean dispFlexTime;
	
	/**
	 * 乖離理由の入力を利用する
	 */
	private boolean useInputDivergenceReason;
	
	/**
	 * 乖離理由の選択肢を利用する
	 */
	private boolean useComboDivergenceReason;
	
	/**
	 * 休出時間枠
	 */
	private List<WorkdayoffFrameDto> workdayoffFrameList;
	
	/**
	 * 休出申請設定
	 */
	private HolidayWorkAppSetDto holidayWorkAppSet;
	
	/**
	 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
	 */
	private HdWorkDispInfoWithDateDto hdWorkDispInfoWithDateOutput;
	
	/**
	 * 残業休日出勤申請の反映
	 */
	private AppReflectOtHdWorkDto hdWorkOvertimeReflect;
	
	/**
	 * 残業時間枠
	 */
	private List<OvertimeWorkFrameDto> overtimeFrameList;
	
	/**
	 * 申請用時間外労働時間
	 */
	private AgreeOverTimeDto otWorkHoursForApplication;
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 乖離理由の選択肢
	 */
	private DivergenceReasonSelectDto comboDivergenceReason;
	
	/**
	 * 計算結果
	 */
	private HolidayWorkCalculationResultDto calculationResult;
	
	public static AppHdWorkDispInfoDto fromDomain(AppHdWorkDispInfoOutput domain) {
		if(domain == null) return null;
		return new AppHdWorkDispInfoDto(domain.getDispFlexTime().equals(NotUseAtr.USE), 
				domain.isUseInputDivergenceReason(), domain.isUseComboDivergenceReason(), 
				domain.getWorkdayoffFrameList()
					.stream()
					.map(workdayoffFrame -> WorkdayoffFrameDto.fromDomain(workdayoffFrame))
					.collect(Collectors.toList()), 
				HolidayWorkAppSetDto.fromDomain(domain.getHolidayWorkAppSet()), 
				HdWorkDispInfoWithDateDto.fromDomain(domain.getHdWorkDispInfoWithDateOutput()), 
				AppReflectOtHdWorkDto.fromDomain(domain.getHdWorkOvertimeReflect()), 
				domain.getOvertimeFrameList()
					.stream()
					.map(overtimeFrame -> OvertimeWorkFrameDto.fromDomain(overtimeFrame))
					.collect(Collectors.toList()), 
				domain.getOtWorkHoursForApplication() != null ? AgreeOverTimeDto.fromDomain(domain.getOtWorkHoursForApplication()) : null, 
				AppDispInfoStartupDto.fromDomain(domain.getAppDispInfoStartupOutput()), 
				domain.getComboDivergenceReason().isPresent() ? DivergenceReasonSelectDto.fromDomain(domain.getComboDivergenceReason().get()) : null, 
				HolidayWorkCalculationResultDto.fromDomain(domain.getCalculationResult().orElse(null)));
	}
}
