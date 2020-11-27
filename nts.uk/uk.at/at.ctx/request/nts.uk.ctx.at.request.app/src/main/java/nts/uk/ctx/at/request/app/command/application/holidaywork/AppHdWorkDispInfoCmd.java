package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AgreeOverTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.AppReflectOtHdWorkCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DivergenceReasonSelectCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.OvertimeWorkFrameCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkdayoffFrameCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * Refactor5
 * @author huylq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppHdWorkDispInfoCmd {

	/**
	 *  フレックス時間を表示する区分
	 */
	public boolean dispFlexTime;
	
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
	private List<WorkdayoffFrameCommand> workdayoffFrameList;
	
	/**
	 * 休出申請設定
	 */
	private HolidayWorkAppSetCommand holidayWorkAppSet;
	
	/**
	 * 休日出勤申請起動時の表示情報(申請対象日関係あり)
	 */
	private HdWorkDispInfoWithDateCmd hdWorkDispInfoWithDateOutput;
	
	/**
	 * 残業休日出勤申請の反映
	 */
	private AppReflectOtHdWorkCommand hdWorkOvertimeReflect;
	
	/**
	 * 残業時間枠
	 */
	private List<OvertimeWorkFrameCommand> overtimeFrameList;
	
	/**
	 * 申請用時間外労働時間
	 */
	private AgreeOverTimeCommand otWorkHoursForApplication;
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
	/**
	 * 乖離理由の選択肢
	 */
	private DivergenceReasonSelectCommand comboDivergenceReason;
	
	/**
	 * 計算結果
	 */
	private HolidayWorkCalculationResultCmd calculationResult;
	
	public AppHdWorkDispInfoOutput toDomain() {
		String companyId = AppContexts.user().companyId();
		
		return new AppHdWorkDispInfoOutput(this.dispFlexTime ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
				this.useInputDivergenceReason, this.useComboDivergenceReason, 
				this.workdayoffFrameList.stream().map(workdayoffFrame -> workdayoffFrame.toDomain()).collect(Collectors.toList()), 
				this.holidayWorkAppSet.toDomain(companyId), 
				this.hdWorkDispInfoWithDateOutput.toDomain(), 
				this.hdWorkOvertimeReflect.toDomain(), 
				this.overtimeFrameList.stream().map(overtimeFrame -> overtimeFrame.toDomain()).collect(Collectors.toList()), 
				this.otWorkHoursForApplication.toDomain(), 
				this.appDispInfoStartupOutput.toDomain(), Optional.of(this.comboDivergenceReason.toDomain()), 
				Optional.of(this.calculationResult.toDomain()));
	}
}
