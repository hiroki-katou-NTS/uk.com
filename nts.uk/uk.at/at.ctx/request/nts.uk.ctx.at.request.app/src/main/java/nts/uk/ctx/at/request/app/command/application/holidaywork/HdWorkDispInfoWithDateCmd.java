package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.BreakTimeZoneSettingCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkHoursCommand;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.shr.com.context.AppContexts;

/**
 * Refactor5
 * @author huylq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HdWorkDispInfoWithDateCmd {
	
	/**
	 * 代休管理区分
	 */
	private boolean subHdManage;
	
	/**
	 * 勤務時間
	 */
	private WorkHoursCommand workHours;
	
	/**
	 * 休憩時間帯設定リスト
	 */
	private BreakTimeZoneSettingCommand breakTimeZoneSettingList;
	
	/**
	 * 勤務種類リスト
	 */
	private List<WorkTypeCommandBase> workTypeList;
	
	/**
	 * 初期選択勤務種類
	 */
	private String initWorkType;
	
	/**
	 * 初期選択勤務種類名称
	 */
	private String initWorkTypeName;
	
	/**
	 * 初期選択就業時間帯
	 */
	private String initWorkTime;
	
	/**
	 *初期選択就業時間帯名称
	 */
	private String initWorkTimeName;

	/**
	 * 勤怠時間の超過状態
	 */
	private OvertimeStatusCommand overtimeStatus;
	
	/**
	 * 実績の申請時間
	 */
	private ApplicationTimeCommand actualApplicationTime;
	
	/**
	 * 月別実績の36協定時間状態
	 */
	private Integer actualMonthlyAgreeTimeStatus;
	
	public HdWorkDispInfoWithDateOutput toDomain() {
		String companyId = AppContexts.user().companyId();
		return new HdWorkDispInfoWithDateOutput(this.subHdManage, 
				this.workHours.toDomain(), 
				Optional.ofNullable(this.breakTimeZoneSettingList != null ? this.breakTimeZoneSettingList.toDomain() : null), 
				Optional.ofNullable(this.workTypeList != null ? 
						this.workTypeList.stream().map(workTypeCmd -> workTypeCmd.toDomain(companyId)).collect(Collectors.toList()) : null), 
				Optional.ofNullable(this.initWorkType != null ? new WorkTypeCode(this.initWorkType) : null), 
				Optional.ofNullable(this.initWorkTypeName != null ? new WorkTypeName(this.initWorkTypeName) : null), 
				Optional.ofNullable(this.initWorkTime != null ? new WorkTimeCode(this.initWorkTime) : null), 
				Optional.ofNullable(this.initWorkTimeName != null ? new WorkTimeName(this.initWorkTimeName) : null), 
				Optional.ofNullable(this.overtimeStatus != null ? this.overtimeStatus.toDomain() : null), 
				Optional.ofNullable(this.actualApplicationTime != null ? this.actualApplicationTime.toDomain() : null),
				Optional.ofNullable(this.actualMonthlyAgreeTimeStatus != null ? 
						EnumAdaptor.valueOf(this.actualMonthlyAgreeTimeStatus, AgreementTimeStatusOfMonthly.class) : null)
				);
	}
}
