package nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
@Getter
public class AddAnnLeaGrantRemnNumPeregCommand {
	/**
	 * 社員ID
	 */
	@PeregEmployeeId
	private String employeeId;

	/**
	 * 年休付与日
	 */
	@PeregItem("IS00385")
	private GeneralDate grantDate;
	
	public String grantDateItemName;
	public String deadlineDateItemName;

	/**
	 * 年休期限日
	 */
	@PeregItem("IS00386")
	private GeneralDate deadline;

	/**
	 * 年休期限切れ状態
	 */
	@PeregItem("IS00387")
	private BigDecimal expirationStatus;

	/**
	 * 付与日数
	 */
	@PeregItem("IS00390")
	private BigDecimal grantDays;

	/**
	 * 付与時間
	 */
	@PeregItem("IS00391")
	private BigDecimal grantMinutes;

	/**
	 * 使用日数
	 */
	@PeregItem("IS00393")
	private BigDecimal usedDays;

	/**
	 * 使用時間
	 */
	@PeregItem("IS00394")
	private BigDecimal usedMinutes;

	/**
	 * 残日数
	 */
	@PeregItem("IS00396")
	private BigDecimal remainingDays;

	/**
	 * 残時間
	 */
	@PeregItem("IS00397")
	private BigDecimal remainingMinutes;

}
