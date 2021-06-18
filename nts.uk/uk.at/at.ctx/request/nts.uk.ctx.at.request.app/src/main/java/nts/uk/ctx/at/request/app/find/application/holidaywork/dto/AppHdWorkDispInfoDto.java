package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.overtime.AppReflectOtHdWorkDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DivergenceReasonInputMethodDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DivergenceTimeRootDto;
import nts.uk.ctx.at.request.app.find.application.overtime.OverTimeWorkHoursDto;
import nts.uk.ctx.at.request.app.find.application.overtime.OvertimeWorkFrameDto;
import nts.uk.ctx.at.request.app.find.application.overtime.WorkdayoffFrameDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkInfo;
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
	
//	/**
//	 * 乖離理由の入力を利用する
//	 */
//	private boolean useInputDivergenceReason;
//	
//	/**
//	 * 乖離理由の選択肢を利用する
//	 */
//	private boolean useComboDivergenceReason;
	
	/**
	 * 乖離時間枠
	 */
	private List<DivergenceTimeRootDto> divergenceTimeRoots = Collections.emptyList();
	
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
	 * 申請表示情報
	 */
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
//	/**
//	 * 乖離理由の選択肢
//	 */
//	private List<DivergenceReasonSelectDto> comboDivergenceReason;
	
	/**
	 * 利用する乖離理由
	 */
	private List<DivergenceReasonInputMethodDto> divergenceReasonInputMethod = Collections.emptyList();
	
	/**
	 * 申請用時間外労働時間
	 */
	private OverTimeWorkHoursDto otWorkHoursForApplication;
	
	/**
	 * 計算結果
	 */
	private HolidayWorkCalculationResultDto calculationResult;
	
	private WorkInfo workInfo;
	
	public static AppHdWorkDispInfoDto fromDomain(AppHdWorkDispInfoOutput domain) {
		if(domain == null) return null;
		return new AppHdWorkDispInfoDto(domain.getDispFlexTime().equals(NotUseAtr.USE), 
				!domain.getDivergenceTimeRoots().isEmpty() ? 
						domain.getDivergenceTimeRoots().stream()
							.map(root -> DivergenceTimeRootDto.fromDomain(root)).collect(Collectors.toList()) : Collections.emptyList(),
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
				AppDispInfoStartupDto.fromDomain(domain.getAppDispInfoStartupOutput()), 
				!domain.getDivergenceReasonInputMethod().isEmpty() ? 
						domain.getDivergenceReasonInputMethod().stream()
							.map(inputMethod -> DivergenceReasonInputMethodDto.fromDomain(inputMethod)).collect(Collectors.toList()) : Collections.emptyList(),
				domain.getOtWorkHoursForApplication().isPresent() ? OverTimeWorkHoursDto.fromDomain(domain.getOtWorkHoursForApplication().get()) : null, 
				HolidayWorkCalculationResultDto.fromDomain(domain.getCalculationResult().orElse(null)),
				domain.getWorkInfo().orElse(null)
				);
	}
}
