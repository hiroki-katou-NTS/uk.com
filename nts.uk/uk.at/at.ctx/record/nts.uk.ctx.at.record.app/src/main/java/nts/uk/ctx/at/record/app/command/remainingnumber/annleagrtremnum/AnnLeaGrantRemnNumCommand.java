package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
public class AnnLeaGrantRemnNumCommand {
	
	private String annLeavID;
	
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

	/**
	 * 年休期限日
	 */
	@PeregItem("IS00386")
	private GeneralDate deadline;

	/**
	 * 年休期限切れ状態
	 */
	@PeregItem("IS00387")
	private int expirationStatus;

	/**
	 * 付与日数
	 */
	@PeregItem("IS00390")
	private Double grantDays;

	/**
	 * 付与時間
	 */
	@PeregItem("IS00391")
	private Integer grantMinutes;


	/**
	 * 使用日数
	 */
	@PeregItem("IS00393")
	private Double usedDays;

	/**
	 * 使用時間
	 */
	@PeregItem("IS00394")
	private Integer usedMinutes;


	/**
	 * 残日数
	 */
	@PeregItem("IS00396")
	private Double remainingDays;

	/**
	 * 残時間
	 */
	@PeregItem("IS00397")
	private Integer remainingMinutes;

}
