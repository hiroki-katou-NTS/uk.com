package nts.uk.ctx.at.record.app.command.remainingnumber.rervleagrtremnum;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class ResvLeaGrantRemNumCommand {
	
	private String employeeId;
	
	/**
	 * 積立年休付与日
	 */
	@PeregItem("IS00398")
	private GeneralDate grantDate;

	/**
	 * 積立年休期限日
	 */
	@PeregItem("IS00399")
	private GeneralDate deadline;

	/**
	 * 積立年休期限切れ状態
	 */
	@PeregItem("IS00400")
	private LeaveExpirationStatus expirationStatus;

	/**
	 * 付与数
	 */
	@PeregItem("IS00403")
	private Double grantDays;

	/**
	 * 使用日数
	 */
	@PeregItem("IS00405")
	private Double useDays;

	/**
	 * 上限超過消滅日数
	 */
	@PeregItem("IS00406")
	private Double overLimitDays;

	/**
	 * 残数
	 */
	@PeregItem("IS00408")
	private Double remainingDays;

}
