package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AddResvLeaRemainCommand {
	
	@Setter
	protected String employeeId;
	
	/**
	 * 積立年休付与日
	 */
	@PeregItem("IS00398")
	protected GeneralDate grantDate;

	/**
	 * 積立年休期限日
	 */
	@PeregItem("IS00399")
	protected GeneralDate deadline;

	/**
	 * 積立年休期限切れ状態
	 */
	@PeregItem("IS00400")
	protected int expirationStatus;

	/**
	 * 付与数
	 */
	@PeregItem("IS00403")
	protected Double grantDays;

	/**
	 * 使用日数
	 */
	@PeregItem("IS00405")
	protected Double useDays;

	/**
	 * 上限超過消滅日数
	 */
	@PeregItem("IS00406")
	protected Double overLimitDays;

	/**
	 * 残数
	 */
	@PeregItem("IS00408")
	protected Double remainingDays;

}
