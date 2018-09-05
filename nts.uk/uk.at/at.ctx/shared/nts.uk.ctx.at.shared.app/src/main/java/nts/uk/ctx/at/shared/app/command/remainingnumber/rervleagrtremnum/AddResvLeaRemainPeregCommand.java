package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
@Getter
public class AddResvLeaRemainPeregCommand {
	/**
	 * 社員ID
	 */
	@PeregEmployeeId
	private String employeeId;
	
	/**
	 * 積立年休付与日
	 */
	@PeregItem("IS00398")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	/**
	 * 積立年休期限日
	 */
	@PeregItem("IS00399")
	private GeneralDate deadline;

	/**
	 * 積立年休期限切れ状態
	 */
	@PeregItem("IS00400")
	private BigDecimal expirationStatus;

	/**
	 * 付与数
	 */
	@PeregItem("IS00403")
	private BigDecimal grantDays;

	/**
	 * 使用日数
	 */
	@PeregItem("IS00405")
	private BigDecimal useDays;

	/**
	 * 上限超過消滅日数
	 */
	@PeregItem("IS00406")
	private BigDecimal overLimitDays;

	/**
	 * 残数
	 */
	@PeregItem("IS00408")
	private BigDecimal remainingDays;
}
