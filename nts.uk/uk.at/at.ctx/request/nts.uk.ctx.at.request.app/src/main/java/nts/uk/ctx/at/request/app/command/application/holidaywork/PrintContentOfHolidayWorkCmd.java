package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DivergenceReasonInputMethodCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DivergenceTimeRootCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.OvertimeWorkFrameCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.TimeZoneWithWorkNoCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkdayoffFrameCommand;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

/**
 * 休日出勤申請の印刷内容
 * @author huylq
 * Refactor5
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrintContentOfHolidayWorkCmd {
	
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
	private List<DivergenceTimeRootCommand> divergenceTimeRoots = Collections.emptyList();
	
	/**
	 * 乖離理由を反映する
	 */
	private int divergenceReasonReflect;
	
	/**
	 * 休出枠
	 */
	private List<WorkdayoffFrameCommand> workdayoffFrameList;
	
	/**
	 * 休憩を反映する
	 */
	private int breakReflect;
	
	/**
	 * 利用する乖離理由
	 */
	private List<DivergenceReasonInputMethodCommand> divergenceReasonInputMethod = Collections.emptyList();
	
	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
	
	/**
	 * 就業時間帯コード
	 */
	private String workTimeCode;
	
	/**
	 * 時間外深夜利用区分
	 */
	private int overtimeMidnightUseAtr;
	
	/**
	 * 残業時間枠
	 */
	private List<OvertimeWorkFrameCommand> overtimeFrameList;
	
	/**
	 * 休憩時間帯
	 */
	private List<TimeZoneWithWorkNoCommand> breakTimeList;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNoCommand> workingTimeList;
	
	/**
	 *勤務種類名
	 */
	private String workTypeName;
	
	/**
	 *就業時間帯名
	 */
	private String workTimeName;
	
	/**
	 * 申請時間
	 */
	private ApplicationTimeCommand applicationTime;
	
	public PrintContentOfHolidayWork toDomain() {
		return new PrintContentOfHolidayWork(
				this.divergenceTimeRoots.stream().map(root -> root.toDomain()).collect(Collectors.toList()),
				EnumAdaptor.valueOf(this.divergenceReasonReflect, NotUseAtr.class),
				this.workdayoffFrameList.stream().map(workdayoffFrameCmd -> workdayoffFrameCmd.toDomain()).collect(Collectors.toList()),
				EnumAdaptor.valueOf(this.breakReflect, NotUseAtr.class), 
				this.divergenceReasonInputMethod.stream().map(inputMethod -> inputMethod.toDomain()).collect(Collectors.toList()),
				new WorkTypeCode(this.workTypeCode), 
				new WorkTimeCode(this.workTimeCode),
				EnumAdaptor.valueOf(this.overtimeMidnightUseAtr, NotUseAtr.class),
				this.overtimeFrameList.stream().map(overtimeFrameCmd -> overtimeFrameCmd.toDomain()).collect(Collectors.toList()), 
				this.breakTimeList != null ? 
						Optional.of(
								this.breakTimeList.stream().map(breakTimeCmd -> breakTimeCmd.toDomain()).collect(Collectors.toList())
								) : Optional.empty(), 
				this.workingTimeList != null ? 
						Optional.of(
								this.workingTimeList.stream().map(workingTimeCmd -> workingTimeCmd.toDomain()).collect(Collectors.toList())
								) : Optional.empty(),
				this.workTypeName != null ? Optional.of(new WorkTypeName(this.workTypeName)) : Optional.empty(), 
				this.workTimeName != null ? Optional.of(new WorkTimeName(this.workTimeName)) : Optional.empty(), 
				this.applicationTime.toDomain());
	}
}
